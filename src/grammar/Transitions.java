package grammar;

import java.util.HashMap;
import java.util.Map;

public class Transitions {

    Map<Character, State> transitions;

    public Transitions() {
        this.transitions = new HashMap<>();
    }

    public Transitions(Map<Character, State> transitions) {
        this();
        for(Map.Entry<Character, State> transition : transitions.entrySet()) {
            this.transitions.put(transition.getKey(), transition.getValue());
        }
    }
}
