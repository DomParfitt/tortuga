package grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UnionFSM extends FiniteStateMachine {

    public UnionFSM(String characters) {
        super(characters);
        List<Character> charList = FiniteStateMachine.stringToCharList(characters);
        this.initialise(charList);
    }

    public UnionFSM(List<Character> characters) {
        super(characters);
        this.initialise(characters);
    }

    public UnionFSM(FiniteStateMachine first, FiniteStateMachine second) {
        FiniteStateMachine firstCopy, secondCopy;
        firstCopy = first.copy();
        secondCopy = second.copy();
        this.initialState = firstCopy.initialState;
        State penultimateState = secondCopy.getPenultimateState();
        for(Map.Entry<Character, State> transition : penultimateState.getTransitions().entrySet()) {
            penultimateState.addTransition(transition.getKey(), firstCopy.getFinalState());
        }
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
        return new UnionFSM(this.characters);
    }

    @Override
    public String toString() {
       return super.toString("|");
    }
}
