package grammar;

import java.util.*;

/**
 * A class to represent a State within an FSM
 */
public class State {

    private int number;
    private Map<Character, State> transitions;
    private boolean isAcceptingState;

    /**
     * Initialises a State with no transitions
     *
     * @param isAcceptingState true if the state is accepting, false otherwise
     */
    public State(boolean isAcceptingState, int stateNumber) {
        this.transitions = new HashMap<>();
        this.isAcceptingState = isAcceptingState;
        this.number = stateNumber;
    }

//    /**
//     * Initialises a state with transitions
//     * @param isAcceptingState true if the state is accepting, false otherwise
//     * @param transitions a map of transitions
//     */
//    public State(boolean isAcceptingState, Map<Character, State> transitions) {
//        this(isAcceptingState);
//        this.transitions = transitions;
//    }

    /**
     * Method to check whether there is a transition with a given character
     *
     * @param character the character to check for a transition with
     * @return true if there is a transition, false otherwise
     */
    public boolean hasTransition(Character character) {
        return this.transitions.containsKey(character);
    }

    /**
     * Gets all the transitions from this state
     *
     * @return the transitions
     */
    public Map<Character, State> getTransitions() {
        return this.transitions;
    }

    /**
     * Gets the state that results from following the transition of the given
     * character
     *
     * @param character the character to use for the transition
     * @return the state resulting from the transition
     */
    public State getResultingState(Character character) {
        return this.transitions.get(character);
    }

    /**
     * Checks whether there exists any transition from this state
     *
     * @return true if there is a transition, false otherwise
     */
    public boolean hasNextState() {
        return !this.transitions.isEmpty();
    }

    /**
     * Gets the set of states that can be transitioned to from this state
     *
     * @return a set of states
     */
    public Set<State> getNextStates() {
        Set<State> nextStates = new HashSet<>();
        for (Map.Entry<Character, State> transition : this.transitions.entrySet()) {
            nextStates.add(transition.getValue());
//            return transition.getValue();
        }

        return nextStates;
    }

    @Deprecated
    public State getNextState() {
        for (Map.Entry<Character, State> transition : this.transitions.entrySet()) {
            return transition.getValue();
        }

        return this;
    }

    /**
     * Gets a set of terminal states that can be reached from this state
     *
     * @return a set of terminal states
     */
    //TODO: This isn't working and causes an SO
    public Set<State> getFinalStates() {
        Set<State> finalStates = new HashSet<>();

        if (this.isAcceptingState()) {
            finalStates.add(this);
        }

        Set<State> nextStates = this.getNextStates();
        for (State state : nextStates) {
            if (!finalStates.contains(state)) {
                Set<State> transitionFinalStates = state.getFinalStates();
                for (State finalState : transitionFinalStates) {
//                    if (!finalStates.contains(finalState)) {
                    finalStates.add(finalState);
//                    }
                }
            }
        }

        return finalStates;

    }

    /**
     * Checks whether this state is accepting, i.e. a parse can end on it
     *
     * @return true if this state is accepting, false otherwise
     */
    public boolean isAcceptingState() {
        return this.isAcceptingState;
    }

    /**
     * Sets the accepting state flag of this state
     *
     * @param isAcceptingState the flag to set
     */
    public void setIsAcceptingState(boolean isAcceptingState) {
        this.isAcceptingState = isAcceptingState;
    }

    /**
     * Adds a transition from this state to another
     *
     * @param character       the character to transition using
     * @param transitionState the state to transition to
     */
    public void addTransition(Character character, State transitionState) {
        this.transitions.put(character, transitionState);
    }

    /**
     * Creates a copy of this state which is equal
     *
     * @return a copy of this state
     */
    public State oldCopy() {
        State copy = new State(this.isAcceptingState(), this.number);
        for (Map.Entry<Character, State> transition : this.transitions.entrySet()) {
            copy.addTransition(transition.getKey(), transition.getValue()); //TODO: Copying here causes a SO error, but should it copy?
        }
        return copy;
    }

    public State copy() {
        CopyTracker copyTracker = new CopyTracker();
        List<State> copiedStates = new ArrayList<>();
        copyTracker.copiedStates = copiedStates;
        copyTracker.copy = this;
//        copyTracker.initialState = null;

        copyTracker = this.safeCopyHelper(copyTracker);

        return copyTracker.initialState;
//        copiedStates = this.safeCopyHelper(copiedStates);

//        return copiedStates.get(copiedStates.indexOf(this));

    }

    public CopyTracker safeCopyHelper(CopyTracker copyTracker) {

        //Create a copy of this state without any transitions
        State copy = new State(this.isAcceptingState(), this.number);

        //Check whether a state with the same ID number is already present in the tracking list
        for (State state : copyTracker.copiedStates) {

            //If a copy is present return with the original list and the entry which is the pre-existing copy
            if (state.number == this.number) {
                CopyTracker ct = new CopyTracker();
                ct.copiedStates = copyTracker.copiedStates;
                ct.copy = state;
                ct.initialState = copyTracker.initialState;
                return ct;
            }
        }

        //Copy is not present in list so add it and set it as the copy to be returned
        copyTracker.copiedStates.add(copy);
        copyTracker.copy = copy;
        if(copyTracker.initialState == null) {
            copyTracker.initialState = copy;
        }

        //Recursively copy the states from each transition and add them
        for (Map.Entry<Character, State> transition : this.transitions.entrySet()) {
            State nextState = transition.getValue();
            copyTracker = nextState.safeCopyHelper(copyTracker);

            copy.addTransition(transition.getKey(), copyTracker.copy);
        }

        return copyTracker;
    }

    @Override
    public boolean equals(Object other) {
        //If other isn't a State then non-equal
        if (!(other instanceof State)) {
            return false;
        }

        State otherState = (State) other;

        if (this == otherState) {
            return true;
        }

        //If number of transitions are different then non-equal
        if (this.transitions.entrySet().size() != otherState.transitions.entrySet().size()) {
            return false;
        }

        //Loop through transitions and return false if any are missing
        for (Map.Entry<Character, State> transition : this.transitions.entrySet()) {
            if (!otherState.transitions.entrySet().contains(transition)) {
                return false;
            }
        }

        if (this.number != otherState.number) {
            return false;
        }

        return true;

    }

    @Override
    public String toString() {
        return this.simplePrint();
//        return this.print(null, 0);
    }

    private String print(State parent, int level) {
        String output = "";
        if (this.isAcceptingState()) {
            output += "FINAL STATE";
        } else {
            output += "NON-FINAL STATE";
        }

        output += " (#" + this.number + ")\n";

        if (this != parent) {

            for (Map.Entry<Character, State> transition : this.transitions.entrySet()) {
                for (int i = 0; i < level; i++) {
                    for (int j = i; j <= level; j++) {
                        output += "\t";
                    }
                }
                output += "\t" + transition.getKey() + " -> " + transition.getValue().print(this, level + 1);
            }
        }

        return output;
    }

    private String simplePrint() {
        String output = "";

        if(this.isAcceptingState()) {
            output += "FINAL STATE";
        } else {
            output += "NON-FINAL STATE";
        }

        output += " (#" + this.number + ") [" + this.transitions.size() + " transitions]";

        return output;
    }

    private class CopyTracker {
        List<State> copiedStates;
        boolean addedNewCopy;
        State initialState, copy;
    }
}
