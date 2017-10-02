package grammar;

import java.util.ArrayList;
import java.util.List;

public class UnionFSM extends FiniteStateMachine {

    public UnionFSM(String characters) {
        super(characters);
        List<Character> charList = FiniteStateMachine.stringToCharList(characters);
        this.initialise(charList);
//        State acceptingState = new State(true);
//        for(Character character : characters.toCharArray()) {
//            this.initialState.addTransition(character, acceptingState);
//        }
    }

    public UnionFSM(List<Character> characters) {
        super(characters);
        this.initialise(characters);
//        State acceptingState = new State(true);
//        for(Character character : characters) {
//            this.initialState.addTransition(character, acceptingState);
//        }
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
