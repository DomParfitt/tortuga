package grammar;

import java.io.FileInputStream;
import java.util.*;

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
        State intialState = new State(this.stateCounter++, false); //TODO: Initialise this as accepting?
        intialState.setCurrentState(true);
        this.states.add(intialState);
    }

//    /**
//     * Copy constructor
//     * @param fsm
//     */
//    public FiniteStateMachine(FiniteStateMachine fsm) {
//        FiniteStateMachine copy = fsm.copy();
//        this.stateCounter = copy.stateCounter;
//        this.states = copy.states;
//        this.transitions = copy.transitions;
//        this.terminalStateIndex = copy.terminalStateIndex;
//
//    }

    protected void initialise(FiniteStateMachine fsm) {
        this.states = fsm.states;
        this.transitions = fsm.transitions;
        this.terminalStateIndex = fsm.terminalStateIndex;
        this.stateCounter = fsm.stateCounter;
    }

    public State getInitialState() {
        return this.states.get(this.initialStateIndex);
    }

    public State getTerminalState() {
        return this.states.get(this.terminalStateIndex);
    }

    public State getCurrentState() {
        for (State state : this.states) {
            if (state.isCurrentState()) {
                return state;
            }
        }

        return this.getInitialState();
    }

    public void setCurrentState(State newState) {
        this.getCurrentState().setCurrentState(false);
        for (State state : this.states) {
            if (state.equals(newState)) {
                state.setCurrentState(true);

            }
        }
    }

    public boolean hasTransition(Character character) {
        for (Transition transition : this.transitions) {
            if (transition.getFromState().equals(this.getCurrentState())) {
                if (transition.hasTransition(character)) {
                    return true;
                }
            }
        }

        return false;
    }

    public State getTransition(Character character) {
        for (Transition transition : this.transitions) {
            if (transition.getFromState().equals(this.getCurrentState())) {
                if (transition.hasTransition(character)) {
                    return transition.getResultingState(character);
                }
            }
        }

        return null;
    }

    public void addTransition(Transition transition) {
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
    public int addState(State state) {
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

    public abstract FiniteStateMachine copy();

    protected List<State> copyStates() {
        List<State> states = new ArrayList<>();
        for (State state : this.states) {
            states.add(state.copy());
        }

        return states;
    }

    protected Set<Transition> copyTransitions(List<State> states) {
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
