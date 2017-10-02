package grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 */
public abstract class FiniteStateMachine {

    protected State initialState;
    protected List<Character> characters;

    public FiniteStateMachine(List<Character> characters) {
        this.initialState = new State(false);
        this.characters = characters;
    }

    public FiniteStateMachine(String characters) {
        this(new ArrayList<>());
        char[] chars = characters.toCharArray();
        for(char character : chars) {
            this.characters.add(character);
        }
//        this.characters.add(Arrays.asList(chars));
    }

    public FiniteStateMachine() {
        this(new ArrayList<>());
    }

    public final boolean parse(String input) {
        State currentState = this.initialState;
        for(int i = 0; i < input.length(); i++) {
          char character = input.charAt(i);
//          String character = input.substring(i, i+1);
          if(currentState.hasTransition(character)) {
              currentState = currentState.getResultingState(character);
          } else {
              //FSM cannot transition with current character, therefore illegal character
              return false;
          }
        }

        return currentState.isAcceptingState();
    }

    public final void combine(FiniteStateMachine next) {
        State finalState = this.getFinalState();
        finalState.setIsAcceptingState(false);
        for(Map.Entry<Character, State> transition : next.initialState.getTransitions().entrySet()) {
//            State nextState = transition.getValue();
//            nextState.setIsAcceptingState(true);
            finalState.addTransition(transition.getKey(), transition.getValue());
        }
        System.out.println("");
//        finalState.addTransition(, next.initialState);

    }

    public State getFinalState() {
        State currentState = this.initialState;
        while(!currentState.isAcceptingState()) {
            currentState = currentState.getNextState();
        }

        return currentState;
    }

    protected String toString(String separator) {
        String output = ""; //"(";
        String prefix = "";
        for(Character character : this.characters) {
            output += prefix + character;
            prefix = separator;
        }
//        output += ")";
        return output;
    }
}
