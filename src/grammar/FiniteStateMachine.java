package grammar;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Abstract base class for a finite state machine
 */
public abstract class FiniteStateMachine {

    protected State initialState;
    protected List<Character> characters;

    /**
     * Initialises a FiniteStateMachine using a list of characters
     * @param characters the characters which are used for transitioning between states
     */
    public FiniteStateMachine(List<Character> characters) {
        this.initialState = new State(false);
        this.characters = characters;
    }

    /**
     * Convenience constructor to initialise an FSM using a string in place of a list
     * of characters
     * @param characters the characters which are used for transitioning between states
     */
    public FiniteStateMachine(String characters) {
        this(new ArrayList<>());
        char[] chars = characters.toCharArray();
        for(char character : chars) {
            this.characters.add(character);
        }
    }


    public FiniteStateMachine() {
        this(new ArrayList<>());
    }

//    protected abstract void initialise(List<Character> characters);

    public abstract FiniteStateMachine copy();

    /**
     * Parses the input string and determines whether it is a valid string
     * for the transitions within the FSM
     * @param input the string to test
     * @return true if the string is valid according to the FSM, false otherwise
     */
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

    /**
     * Gets the final state of the FSM, a.k.a the accepting state
     * @return the final state
     */
    public State getFinalState() {
        State currentState = this.initialState;
        while(!currentState.isAcceptingState()) {
            currentState = currentState.getNextState();
        }

        return currentState;
    }

    public State getPenultimateState() {
        State currentState = this.initialState;
        while(currentState.hasNextState()) {
            State nextState = currentState.getNextState();
            if(nextState.isAcceptingState()) {
                return currentState;
            }
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

    protected static List<Character> stringToCharList(String characters) {
        List<Character> characterList = new ArrayList<>();
        for(Character character : characters.toCharArray()) {
            characterList.add(character);
        }

        return characterList;
    }
}
