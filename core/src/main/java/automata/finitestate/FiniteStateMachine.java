package automata.finitestate;

import automata.State;
import automata.actions.AutomataAction;
import grammar.Grammar;

import java.util.*;

/**
 * Abstract base class for a finite state machine
 */
public abstract class FiniteStateMachine<T> {

    protected int stateCounter = 0;
    protected int initialStateIndex = 0;
    protected int terminalStateIndex = 0;

    protected Map<State, Set<AutomataAction<T>>> states;

    public FiniteStateMachine() {
        this.states = new TreeMap<>();
        this.addState(false, true);
//        this.transitions = new TreeSet<>();
//        State initialState = new State(this.stateCounter++, false); //TODO: Initialise this as accepting?
//        initialState.setCurrentState(true);
//        this.states.add(initialState);

    }

    /**
     * Gets a Set of all the States contained within the automaton
     * @return a set of States
     */
    public Set<State> getStates() {
        return this.states.keySet();
    }

    /**
     * Gets a Map of all the States in the automaton along with the actions
     * associated with each state
     * @return a Map of States to AutomataActions
     */
    public Map<State, Set<AutomataAction<T>>> getStatesWithActions() {
        return this.states;
    }

    /**
     * Removes a state, all its actions and all actions which result in it
     * @param state the state to remove
     */
    public void removeState(State state) {

    }

    /**
     * Gets the set of AutomataActions for a given state
     * @param state the State to get the actions for
     * @return a set of AutomataActions
     */
    public Set<AutomataAction<T>> getActions(State state) {
        return this.states.get(state);
    }

    /**
     * Gets the set of AutomataActions for the current state
     * @return a set of AutomataActions
     */
    public Set<AutomataAction<T>> getActions() {
        return this.getActions(this.getCurrentState());
    }

    /**
     * Gets all actions which have a resulting state equal to the state provided
     * @param resultingState the resulting state of the actions to retrieve
     * @return a set of actions
     */
    public Set<AutomataAction<T>> getActionsByResultingState(State resultingState) {
        Set<AutomataAction<T>> actions = new TreeSet<>();
        for(State state : this.getStates()) {
            for(AutomataAction<T> action : this.getStatesWithActions().get(state)) {
                if(action.getResultingState().equals(resultingState)) {
                    actions.add(action);
                }
            }
        }

        return actions;
    }

    /**
     * Gets the initial state of the automata
     * @return the initial state
     */
    public final State getInitialState() {
        for(State state : this.states.keySet()) {
            if(state.getNumber() == this.initialStateIndex) {
                return state;
            }
        }

        return null; //throw Exception
    }

    /**
     * Gets the terminal state of the automata
     * @return the terminal state
     */
    public final State getTerminalState() {
        for(State state : this.states.keySet()) {
            if(state.getNumber() == this.terminalStateIndex) {
                return state;
            }
        }

        return null;// throw Exception
//        return this.states.get(this.terminalStateIndex);
    }

    /**
     * Gets the current state of the automata
     * @return the current state
     */
    public final State getCurrentState() {
        for (State state : this.states.keySet()) {
            if (state.isCurrentState()) {
                return state;
            }
        }

        return this.getInitialState();
    }

    /**
     * Sets the current state of the automata to the state provided if
     * that state is present in the automata
     * @param newState the state to set as the current state
     */
    public final void setCurrentState(State newState) {
        State oldState = this.getCurrentState();
        for (State state : this.states.keySet()) {
            if (state.equals(newState)) {
                state.setCurrentState(true);
                oldState.setCurrentState(false);
                break;
            }
        }
    }

    /**
     * Checks whether there are any possibly actions in the current state
     * @return true, if there are actions in the current state, false otherwise
     */
    public boolean hasAction() {
        Set<AutomataAction<T>> actions = this.getActions();
        return (actions != null) && !actions.isEmpty();
    }

    /**
     * Checks if there is an action in the current state that consumes a given
     * token.
     * @param token the token the action consumes
     * @return true, if there is an action in the current state which consumes the
     *          token, false otherwise
     */
    public boolean hasAction(T token) {
        Set<AutomataAction<T>> actions = this.getActions();
        for(AutomataAction<T> action : actions) {
            if(action.appliesTo(token)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Associates an action with a particular state. Adds the state to the automata if
     * it is not already present
     * @param state the state that the action is associated with
     * @param action the action to be taken
     */
    public void addAction(State state, AutomataAction<T> action) {
        if(!this.states.containsKey(state)) {
            this.addState(state.isAcceptingState());
        }
        action.setFromState(state);
        this.getActions(state).add(action);
    }

    /**
     * Associates a set of actions with a particular state without overwriting any
     * existing actions.
     * @param state the state that the actions are associcated with
     * @param actions a set of actions
     */
    public void addActions(State state, Set<AutomataAction<T>> actions) {
        for(AutomataAction<T> action : actions) {
            this.addAction(state, action);
        }
    }

    /**
     * Adds a new state to the automata, updating the state counter and terminal
     * state index as required
     * @param isAcceptingState true if the new state is a terminal, false otherwise
     * @param isCurrentState true if the new state should be set as the current state, false otherwise
     * @return a reference to the new state
     */
    //TODO: Should this be private? Only used as convenience in constructor?
    public final State addState(boolean isAcceptingState, boolean isCurrentState) {
        State state = new State(this.stateCounter++, isAcceptingState);
        state.setCurrentState(isCurrentState);
        this.states.put(state, new HashSet<>()); //TODO: Treeset for ordering?
        if(isAcceptingState) {
            this.terminalStateIndex = state.getNumber();
        }

        return state;
    }

    /**
     * Adds a new state to the automata, updating the state counter and terminal
     * state index as required
     * @param isAcceptingState true if the new state is a terminal, false otherwise
     * @return a reference to the new state
     */
    public final State addState(boolean isAcceptingState) {
        return this.addState(isAcceptingState, false);
    }

    /**
     * Adds a new state to the automata, updating the state counter and terminal
     * state index as required
     * @param state the state to be added
     * @return the new State
     */
    public final State addState(State state) {
        return this.addState(state.isAcceptingState());
    }

    /**
     * Parses a list of input tokens
     * @param input list of tokens to parse
     * @return true, if consuming each token in turn results in a terminal state with no further tokens, false otherwise
     */
    public boolean parse(List<T> input) {
        this.setCurrentState(this.getInitialState());
        for(T token : input) {

            if(!this.parseHelper(token)) {
                return false;
            }
        }

        return this.getCurrentState().isAcceptingState();
    }

    /**
     * Parses a single input token
     * @param input the tokens to parse
     * @return true, if consuming the token results in a terminal state, false otherwise
     */
    public boolean parse(T input) {
        this.setCurrentState(this.getInitialState());
        return this.parseHelper(input) && this.getCurrentState().isAcceptingState();
    }

    /**
     * Helper method for parsing
     * @param token the token to parse
     * @return true, if the automata has a valid action from the current state consuming this token, false otherwise
     */
    private boolean parseHelper(T token) {
        //Check if we have an action from the current state
        if(!this.hasAction(token)) {
            return false;
        }

        //Get the actions and if we have one for this token then do it
        Set<AutomataAction<T>> actions = this.getActions();
        for(AutomataAction<T> action : actions) {
            if(action.appliesTo(token)) {
                return action.doAction(this);
            }
        }

        return false;
    }

    /**
     * Concatenate this automata to another. Assumes this automata has a single terminal
     * state, which replaces the initial state of the other automata.
     * @param other the automata to concatenate to this
     * @return a new automata resulting from the concatenation, not mutating either original
     */
    public FiniteStateMachine<T> concatenate(FiniteStateMachine<T> other) {
        FiniteStateMachine<T> copy = this.copy();
        FiniteStateMachine<T> otherCopy = other.copy();

        State secondInitial = otherCopy.getInitialState();

        Map<State, State> oldToNew = new TreeMap<>();

        //Add all states from secondCopy into this, except initial and terminal state
        for(State state : otherCopy.getStates()) {

            State newState;
            if(state.equals(secondInitial)) {
                State terminal = copy.getTerminalState();
                newState = terminal;
                terminal.setAcceptingState(false);
            } else {
                newState = copy.addState(state);
            }

            oldToNew.put(state, newState);
        }

        for(State oldState : oldToNew.keySet()) {
            //Update outward actions to the new state in the copy
            State newState = oldToNew.get(oldState);
            Set<AutomataAction<T>> actions = otherCopy.getActions(oldState);//statesCopy.get(oldState);
            copy.addActions(newState, actions);

            //Update inward actions to point to the new state
            for(AutomataAction<T> action : otherCopy.getActionsByResultingState(oldState)) {
                action.setResultingState(newState);
            }
        }

        return copy;
    }

    /**
     * Concatenate with a grammar
     * @param grammar the grammar to concatenate with
     * @return
     */
    public final FiniteStateMachine<T> concatenate(Grammar<T> grammar) {
        return this.concatenate(grammar.getMachine());
    }

    /**
     * Creates a new automata by unioning this one with another
     * @param other the other automata
     * @return a new automata which is the union of the two originals
     */
    public FiniteStateMachine<T> union(FiniteStateMachine<T> other) {
        FiniteStateMachine<T> copy = this.copy();
        FiniteStateMachine<T> otherCopy = other.copy();

        State secondInitial = otherCopy.getInitialState();
        State secondTerminal = otherCopy.getTerminalState();

        Map<State, State> oldToNew = new TreeMap<>();

        //Add all states from secondCopy into this, except initial and terminal state
        for(State state : otherCopy.getStates()) {

            State newState;
            if(state.equals(secondInitial)) {
                newState = copy.getInitialState();
            } else if (state.equals(secondTerminal)) {
                newState = copy.getTerminalState();
            } else {
                newState = copy.addState(state);
            }

            oldToNew.put(state, newState);

        }

        for(State oldState : oldToNew.keySet()) {
            //Update outward actions to the new state in the copy
            State newState = oldToNew.get(oldState);
            Set<AutomataAction<T>> actions = otherCopy.getActions(oldState);//statesCopy.get(oldState);
            copy.addActions(newState, actions);

            //Update inward actions to point to the new state
            for(AutomataAction<T> action : otherCopy.getActionsByResultingState(oldState)) {
                action.setResultingState(newState);
            }
        }

        return copy;

    }

    /**
     * Unions with a grammar
     * @param grammar the grammar to union with this
     * @return a new automata which is the union of this and the grammar
     */
    public final FiniteStateMachine<T> union(Grammar<T> grammar) {
        return this.union(grammar.getMachine());
    }

    /**
     * Create a copy of this automata which loops on itself.
     * @return a copy of this automata with a loop
     */
    public FiniteStateMachine<T> loop() {

        FiniteStateMachine<T> copy = this.copy();

        for(State state : this.getStates()) {
            Set<AutomataAction<T>> actions = copy.getActions(state);
            boolean isTerminal = state.equals(copy.getTerminalState());
            for(AutomataAction<T> action : actions) {

                //If the action goes into the terminal state, move it to go into the initial state
                if(action.getResultingState().equals(copy.getTerminalState())) {
                    action.setResultingState(copy.getInitialState());
                }

                //Move any actions from the terminal state to the initial state
                if(isTerminal) {
                    this.addAction(this.getInitialState(), action);
                }
            }
        }

        //Remove the terminal state and update the terminal state index
        State terminalState = copy.getTerminalState();
        copy.getStates().remove(terminalState);
        copy.getInitialState().setAcceptingState(true);
        copy.terminalStateIndex = copy.initialStateIndex;

        return copy;
    }

    /**
     * Make a copy of this automata
     * @return an exact copy of this automata
     */
    //TODO: Should this be implemented here?
    public abstract FiniteStateMachine<T> copy();

    /**
     * Make a copy of the state-action map
     * @return a Map of States and associated Actions
     */
    //TODO: Needs fixing
    protected final Map<State, Set<AutomataAction<T>>> copyStatesWithActions() {
        Map<State, Set<AutomataAction<T>>> states = new TreeMap<>();
        for(Map.Entry<State, Set<AutomataAction<T>>> state : this.getStatesWithActions().entrySet()) {
            Set<AutomataAction<T>> actions = new HashSet<>();
            for(AutomataAction<T> action : state.getValue()) {
                actions.add(action); //TODO: Should this add a copy?
            }
            states.put(state.getKey().copy(), actions);
        }
        return states;
    }

    @Override
    public String toString() {
        String output = "STATES\n";
        for (State state : this.states.keySet()) {
            output += "\t" + state + "\n";
        }

        output += "\nACTIONS\n";
        for (Map.Entry<State, Set<AutomataAction<T>>> state : this.states.entrySet()) {
            for (AutomataAction<T> action : state.getValue()) {
                output += "\t" + action + "\n";
            }
        }
        return output;
    }

}
