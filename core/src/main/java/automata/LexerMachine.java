package automata;

import utils.StringUtils;

public class LexerMachine extends FiniteStateMachine<Character> {

    public final boolean parse(String input) {
        return super.parse(StringUtils.toCharacterList(input));
    }

    @Override
    public void concatenate(FiniteStateMachine<Character> other) {
        LexerMachine copy = (LexerMachine) other.copy();

        State secondInitial = copy.getInitialState();
        //Add each state of secondCopy except initial
        boolean addedState = false;
        for (State state : copy.getStates()) {
            if (!state.equals(secondInitial)) {
                int newNumber = this.addState(state);
                addedState = true;

                //Find any transitions referencing that state and update their number
                for (Transition<Character> transition : copy.getTransitions()) {
                    if (transition.fromState.equals(state)) {
                        transition.fromState.setNumber(newNumber);
                    }

                    if (transition.toState.equals(state)) {
                        transition.toState.setNumber(newNumber);
                    }
                }

            }
        }
        //Update secondCopy transitions with initial to use this's terminal
        //Add transitions
        //Add all transitions from secondCopy into this, replacing initial and terminal states with this's
        for (Transition<Character> transition : copy.getTransitions()) {
            if (transition.fromState.equals(secondInitial)) {
                transition.fromState = this.getTerminalState();
            }

            if (transition.toState.equals(secondInitial)) {
                transition.toState = this.getTerminalState();
            }

            this.addTransition(transition);
        }

        //Mark terminal state of this as non-terminal if new states have been added
        if (addedState) {
            this.getTerminalState().setAcceptingState(false);
        }

        //Update terminal index of this
        this.terminalStateIndex = this.stateCounter - 1;
    }

    @Override
    public void union(FiniteStateMachine<Character> other) {
        LexerMachine copy = (LexerMachine) other.copy();

        State secondInitial = copy.getInitialState();
        State secondTerminal = copy.getTerminalState();

        //Add all states from secondCopy into this, except initial and terminal state
        for(State state : copy.getStates()) {

            //We don't want to copy across the initial or terminal states
            if(!state.equals(secondInitial) && !state.equals(secondTerminal)) {

                //Add the state and get its new number
                int newNumber = this.addState(state);

                //Find any transitions referencing that state and update their number
                for(Transition<Character> transition : copy.getTransitions()) {
                    if(transition.fromState.equals(state)) {
                        transition.fromState.setNumber(newNumber);
                    }

                    if(transition.toState.equals(state)) {
                        transition.toState.setNumber(newNumber);
                    }
                }
            }
        }

        //Add all transitions from secondCopy into this, replacing initial and terminal states with this's
        for(Transition<Character> transition : copy.getTransitions()) {
            if(transition.fromState.equals(secondInitial)) {
                transition.fromState = this.getInitialState();
            }

            if(transition.toState.equals(secondTerminal)) {
                transition.toState = this.getTerminalState();
            }

            this.addTransition(transition);
        }

    }

    @Override
    public void loop() {

        for(Transition<Character> transition : this.getTransitions()) {
            if(transition.getToState().equals(this.getTerminalState())) {
                transition.toState = this.getInitialState();
            }
        }

        State terminalState = this.getTerminalState();
        this.getStates().remove(terminalState);
        this.getInitialState().setAcceptingState(true);
        this.terminalStateIndex = this.initialStateIndex;
    }

    @Override
    public  LexerMachine copy() {
        LexerMachine copy = new LexerMachine();
        copy.stateCounter = this.stateCounter;
        copy.terminalStateIndex = this.terminalStateIndex;
        copy.states = this.copyStates();
        copy.transitions = this.copyTransitions(copy.states);

        return copy;
    }
}
