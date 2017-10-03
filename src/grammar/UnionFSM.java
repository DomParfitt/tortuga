package grammar;

import java.util.List;
import java.util.Map;

/**
 * Concrete implementation of a FiniteStateMachine representing the case of
 * multiple tokens, whereby consuming any one of them will pass the validation
 */
public class UnionFSM extends FiniteStateMachine {

    /**
     * Convenience constructor using a string in place of a list
     * @param characters
     */
    public UnionFSM(String characters) {
        super(characters);
        List<Character> charList = FiniteStateMachine.stringToCharList(characters);
        this.initialise(charList);
    }

    /**
     * Initialises with a list of characters, each of which represents a valid
     * transition from the initial state to the final state
     * @param characters
     */
    public UnionFSM(List<Character> characters) {
        super(characters);
        this.initialise(characters);
    }

    /**
     * Compound constructor for creating an FSM from two FSMs where validating on either
     * is enough to validate an input
     * @param first the first FSM
     * @param second the second FSM
     */
    //TODO: This build a functional FSM but doesn't provide any string output when printing
    public UnionFSM(FiniteStateMachine first, FiniteStateMachine second) {
        FiniteStateMachine firstCopy, secondCopy;
        firstCopy = first.copy();
        secondCopy = second.copy();
        this.initialState = firstCopy.initialState;
        for(Map.Entry<Character, State> transition : secondCopy.initialState.getTransitions().entrySet()) {
            this.initialState.addTransition(transition.getKey(), transition.getValue());
        }

    }

    private void initialise(List<Character> characters) {
        State acceptingState = new State(true);
        for(Character character : characters) {
            this.initialState.addTransition(character, acceptingState);
        }
    }

    @Override
    public FiniteStateMachine copy(){
        FiniteStateMachine copy = new UnionFSM(this.characters);
        copy.initialState = this.initialState.copy();
        return copy;
//        return new UnionFSM(this.characters);
    }

    @Override
    public String toString() {
       return super.toString("|");
    }
}
