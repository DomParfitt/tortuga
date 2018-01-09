package automata;

import grammar.Grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Abstract base class for a finite state machine
 */
public abstract class FiniteStateMachine<T> {

    protected int stateCounter = 0;
    protected int initialStateIndex = 0;
    protected int terminalStateIndex = 0;

    protected List<State> states;
    protected Set<Transition<T>> transitions;

    public FiniteStateMachine() {
        this.states = new ArrayList<>();
        this.transitions = new TreeSet<>();
        State initialState = new State(this.stateCounter++, false); //TODO: Initialise this as accepting?
        initialState.setCurrentState(true);
        this.states.add(initialState);
    }

    protected final void initialise(FiniteStateMachine<T> fsm) {
        this.states = fsm.states;
        this.transitions = fsm.transitions;
        this.terminalStateIndex = fsm.terminalStateIndex;
        this.stateCounter = fsm.stateCounter;
    }

    public final State getInitialState() {
        return this.states.get(this.initialStateIndex);
    }

    public final State getTerminalState() {
        return this.states.get(this.terminalStateIndex);
    }

    public final State getCurrentState() {
        for (State state : this.states) {
            if (state.isCurrentState()) {
                return state;
            }
        }

        return this.getInitialState();
    }

    public final void setCurrentState(State newState) {
        this.getCurrentState().setCurrentState(false);
        for (State state : this.states) {
            if (state.equals(newState)) {
                state.setCurrentState(true);

            }
        }
    }

    public boolean hasTransition(T token) {
        for (Transition<T> transition : this.transitions) {
            if (transition.getFromState().equals(this.getCurrentState())) {
                if (transition.hasTransition(token)) {
                    return true;
                }
            }
        }

        return false;
    }

    public final State getResultingState(T token) {
        for (Transition<T> transition : this.transitions) {
            if (transition.getFromState().equals(this.getCurrentState())) {
                if (transition.hasTransition(token)) {
                    return transition.getResultingState(token);
                }
            }
        }

        return null;
    }

    public final void addTransition(Transition<T> transition) {
        boolean exists = false;
        for (Transition<T> existing : this.transitions) {
            if (existing.equals(transition)) {
                for (T token : transition.inputSymbols) {
                    existing.inputSymbols.add(token);
                }
                exists = true;
            }
        }

        if (!exists) {
            this.transitions.add(transition);
        }
    }

    /**
     * Adds a new state to the set of state belonging to this FSM. Updates the
     * number of the added state to reflect the state counter of this FSM.
     *
     * @param state the state to be added
     * @return the new number of the added state
     */
    public final int addState(State state) {
        State newState = state.copy();
        newState.setNumber(this.stateCounter++);
        this.states.add(newState);
        return this.stateCounter - 1;
    }

    public boolean parse(List<T> input) {
        this.setCurrentState(this.getInitialState());
//        char[] characters = input.toCharArray();
        for (T token : input) {
            if (this.hasTransition(token)) {
                this.setCurrentState(this.getResultingState(token));
            } else {
                return false;
            }
        }
        return this.getCurrentState().isAcceptingState();
    }

    public boolean parse(T input) {
        this.setCurrentState((this.getInitialState()));
        if (this.hasTransition(input)) {
            return this.getResultingState(input).isAcceptingState();
        } else {
            return false;
        }
    }

    public void concatenate(FiniteStateMachine<T> other) {
        FiniteStateMachine<T> copy = other.copy();

        State secondInitial = copy.getInitialState();
        //Add each state of secondCopy except initial
        boolean addedState = false;
        for (State state : copy.getStates()) {
            if (!state.equals(secondInitial)) {
                int newNumber = this.addState(state);
                addedState = true;

                //Find any transitions referencing that state and update their number
                for (Transition<T> transition : copy.getTransitions()) {
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
        for (Transition<T> transition : copy.getTransitions()) {
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

    public final void concatenate(Grammar<T> grammar) {
        this.concatenate(grammar.getMachine());
    }

    public void union(FiniteStateMachine<T> other) {
        FiniteStateMachine<T> copy = other.copy();

        State secondInitial = copy.getInitialState();
        State secondTerminal = copy.getTerminalState();

        //Add all states from secondCopy into this, except initial and terminal state
        for(State state : copy.getStates()) {

            //We don't want to copy across the initial or terminal states
            if(!state.equals(secondInitial) && !state.equals(secondTerminal)) {

                //Add the state and get its new number
                int newNumber = this.addState(state);

                //Find any transitions referencing that state and update their number
                for(Transition<T> transition : copy.getTransitions()) {
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
        for(Transition<T> transition : copy.getTransitions()) {
            if(transition.fromState.equals(secondInitial)) {
                transition.fromState = this.getInitialState();
            }

            if(transition.toState.equals(secondTerminal)) {
                transition.toState = this.getTerminalState();
            }

            this.addTransition(transition);
        }

    }

    public final void union(Grammar<T> grammar) {
        this.union(grammar.getMachine());
    }

//    public abstract void loop();

    public void loop() {

        for(Transition<T> transition : this.getTransitions()) {
            if(transition.getToState().equals(this.getTerminalState())) {
                transition.toState = this.getInitialState();
            }
        }

        State terminalState = this.getTerminalState();
        this.getStates().remove(terminalState);
        this.getInitialState().setAcceptingState(true);
        this.terminalStateIndex = this.initialStateIndex;
    }

    public abstract FiniteStateMachine<T> copy();

    protected final List<State> copyStates() {
        List<State> states = new ArrayList<>();
        for (State state : this.states) {
            states.add(state.copy());
        }

        return states;
    }

    protected final Set<Transition<T>> copyTransitions(List<State> states) {
        Set<Transition<T>> transitions = new TreeSet<>();
        for (Transition<T> transition : this.transitions) {
            List<T> inputSymbols = new ArrayList<>();
            for (T token : transition.inputSymbols) {
                inputSymbols.add(token);
            }

            State fromState = states.get(states.indexOf(transition.getFromState()));
            State toState = states.get(states.indexOf(transition.getToState()));
            Transition<T> transitionCopy = new Transition<T>(inputSymbols, fromState, toState);

            transitions.add(transitionCopy);
        }

        return transitions;
    }

    public List<State> getStates() {
        return this.states;
    }

    public Set<Transition<T>> getTransitions() {
        return this.transitions;
    }

    @Override
    public String toString() {
        String output = "STATES\n";
        for (State state : this.states) {
            output += "\t" + state + "\n";
        }

        output += "\nTRANSITIONS\n";
        for (Transition<T> transition : this.transitions) {
            output += "\t" + transition + "\n";
        }

        return output;
    }

}
