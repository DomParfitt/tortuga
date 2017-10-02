package grammar;

import java.util.List;

public class UnionFSM extends FiniteStateMachine {

//    public UnionFSM(String characters) {
//        super(characters);
//
//    }

    public UnionFSM(List<Character> characters) {
        super(characters);
        State acceptingState = new State(true);
        for(Character character : characters) {
            this.initialState.addTransition(character, acceptingState);
        }
    }

    @Override
    public String toString() {
       return super.toString("|");
    }
}
