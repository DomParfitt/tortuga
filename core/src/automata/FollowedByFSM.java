package automata;

/**
 * Concrete implementation of FiniteStateMachine representing the case
 * of some token A followed by another token B. These tokens can either
 * be characters or other FSMs
 */
public class FollowedByFSM extends FiniteStateMachine<Character> {


    public FollowedByFSM(String characters) {
        super();
        for (Character character : characters.toCharArray()) {
            State nextState = new State(this.stateCounter++, false);
            this.states.add(nextState);
            this.terminalStateIndex = this.stateCounter - 1;
            Transition<Character> transition = new Transition<>(character, this.getCurrentState(), nextState);
            this.transitions.add(transition);
            this.setCurrentState(nextState);
        }
        this.getTerminalState().setAcceptingState(true);

    }

    public FollowedByFSM(FiniteStateMachine<Character> first, FiniteStateMachine<Character> second) {
        FiniteStateMachine<Character> firstCopy = first.copy();
        FiniteStateMachine<Character> secondCopy = second.copy();
        this.initialise(firstCopy);

        State secondInitial = secondCopy.getInitialState();
        //Add each state of secondCopy except initial
        boolean addedState = false;
        for (State state : secondCopy.states) {
            if (!state.equals(secondInitial)) {
                int newNumber = this.addState(state);
                addedState = true;

                //Find any transitions referencing that state and update their number
                for (Transition<Character> transition : secondCopy.transitions) {
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
        for (Transition<Character> transition : secondCopy.transitions) {
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
    public FiniteStateMachine<Character> copy() {
        FiniteStateMachine<Character> copy = new FollowedByFSM("");
        copy.stateCounter = this.stateCounter;
        copy.terminalStateIndex = this.terminalStateIndex;
        copy.states = this.copyStates();
        copy.transitions = this.copyTransitions(copy.states);

        return copy;
    }
}
