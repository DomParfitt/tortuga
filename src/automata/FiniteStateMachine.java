package automata;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Abstract base class for a finite state machine
 */
public abstract class FiniteStateMachine {

    protected int stateCounter = 0;
    protected int initialStateIndex = 0;
    protected int terminalStateIndex = 0;

    protected List<State> states;
    protected Set<Transition> transitions;

    public FiniteStateMachine() {
        this.states = new ArrayList<>();
        this.transitions = new TreeSet<>();
        State initialState = new State(this.stateCounter++, false); //TODO: Initialise this as accepting?
        initialState.setCurrentState(true);
        this.states.add(initialState);
    }

    protected final void initialise(FiniteStateMachine fsm) {
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

    public final boolean hasTransition(Character character) {
        for (Transition transition : this.transitions) {
            if (transition.getFromState().equals(this.getCurrentState())) {
                if (transition.hasTransition(character)) {
                    return true;
                }
            }
        }

        return false;
    }

    public final State getTransition(Character character) {
        for (Transition transition : this.transitions) {
            if (transition.getFromState().equals(this.getCurrentState())) {
                if (transition.hasTransition(character)) {
                    return transition.getResultingState(character);
                }
            }
        }

        return null;
    }

    public final void addTransition(Transition transition) {
        boolean exists = false;
        for (Transition existing : this.transitions) {
            if (existing.equals(transition)) {
                for (Character character : transition.characters) {
                    existing.characters.add(character);
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

    public final boolean parse(String input) {
        this.setCurrentState(this.getInitialState());
        char[] characters = input.toCharArray();
        for (Character character : characters) {
            if (this.hasTransition(character)) {
                this.setCurrentState(this.getTransition(character));
            } else {
                return false;
            }
        }
        return this.getCurrentState().isAcceptingState();
    }

    public final boolean parse(Character input) {
        this.setCurrentState((this.getInitialState()));
        if (this.hasTransition(input)) {
            return this.getTransition(input).isAcceptingState();
        } else {
            return false;
        }
    }

    public abstract FiniteStateMachine copy();

    protected final List<State> copyStates() {
        List<State> states = new ArrayList<>();
        for (State state : this.states) {
            states.add(state.copy());
        }

        return states;
    }

    protected final Set<Transition> copyTransitions(List<State> states) {
        Set<Transition> transitions = new TreeSet<>();
        for (Transition transition : this.transitions) {
            String characters = "";
            for (Character character : transition.characters) {
                characters += character;
            }

            State fromState = states.get(states.indexOf(transition.getFromState()));
            State toState = states.get(states.indexOf(transition.getToState()));
            Transition transitionCopy = new Transition(characters, fromState, toState);

            transitions.add(transitionCopy);
        }

        return transitions;
    }

    @Override
    public String toString() {
        String output = "STATES\n";
        for (State state : this.states) {
            output += "\t" + state + "\n";
        }

        output += "\nTRANSITIONS\n";
        for (Transition transition : this.transitions) {
            output += "\t" + transition + "\n";
        }

        return output;
    }

}
