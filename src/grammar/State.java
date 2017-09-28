package grammar;

import java.util.HashMap;
import java.util.Map;

/**
 * A class to represent a State within an FSM
 */
public class State {

    private Map<String, State> transitions;
    private boolean isAcceptingState;

    public State(boolean isAcceptingState) {
        this.transitions = new HashMap<>();
        this.isAcceptingState = isAcceptingState;
    }

    public State(boolean isAcceptingState, Map<String, State> transitions) {
        this(isAcceptingState);
        this.transitions = transitions;
    }

    public boolean hasTransition(String character) {
        return this.transitions.containsKey(character);
    }

    public Map<String, State> getTransitions() {
        return this.transitions;
    }

    public State getResultingState(String character) {
        return this.transitions.get(character);
    }

    public State getNextState() {
        for(Map.Entry<String, State> transition : this.transitions.entrySet()) {
            return transition.getValue();
        }

        return this;
    }

    public boolean isAcceptingState() {
        return this.isAcceptingState;
    }

    public void setIsAcceptingState(boolean isAcceptingState) {
        this.isAcceptingState = isAcceptingState;
    }

    public void addTransition(String character, State transitionState) {
        this.transitions.put(character, transitionState);
    }

    @Override
    public boolean equals(Object other) {
        //If other isn't a State then non-equal
        if(!(other instanceof State)) {
            return false;
        }

        State otherState = (State) other;

        if(this == otherState) {
            return true;
        }

        //If number of transitions are different then non-equal
        if(this.transitions.entrySet().size() != otherState.transitions.entrySet().size()) {
            return false;
        }

        //Loop through transitions and return false if any are missing
        for(Map.Entry<String, State> transition : this.transitions.entrySet()) {
            if(!otherState.transitions.entrySet().contains(transition)) {
                return false;
            }
        }

        return true;

    }
}
