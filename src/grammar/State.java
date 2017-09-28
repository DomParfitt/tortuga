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

    public State getResultingState(String character) {
        return this.transitions.get(character);
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
}
