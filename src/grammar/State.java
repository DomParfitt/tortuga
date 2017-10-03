package grammar;

import java.util.*;

/**
 * A class to represent a State within an FSM
 */
public class State {

    private Map<Character, State> transitions;
    private boolean isAcceptingState;

    public State(boolean isAcceptingState) {
        this.transitions = new HashMap<>();
        this.isAcceptingState = isAcceptingState;
    }

    public State(boolean isAcceptingState, Map<Character, State> transitions) {
        this(isAcceptingState);
        this.transitions = transitions;
    }

    public boolean hasTransition(Character character) {
        return this.transitions.containsKey(character);
    }

    public Map<Character, State> getTransitions() {
        return this.transitions;
    }

    public State getResultingState(Character character) {
        return this.transitions.get(character);
    }

    public boolean hasNextState() {
        return !this.transitions.isEmpty();
    }

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

    public boolean isAcceptingState() {
        return this.isAcceptingState;
    }

    public void setIsAcceptingState(boolean isAcceptingState) {
        this.isAcceptingState = isAcceptingState;
    }

    public void addTransition(Character character, State transitionState) {
        this.transitions.put(character, transitionState);
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

        return true;

    }

    @Override
    public String toString() {
        return this.print(null, 0);
    }

    private String print(State parent, int level) {
        String output = "";
        if (this.isAcceptingState()) {
            output += "FINAL STATE\n";
        } else {
            output += "NON-FINAL STATE\n";
        }

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
}
