package grammar;

import java.util.List;

public class UnionFSM extends FiniteStateMachine {

    public UnionFSM(List<String> characters) {
        super(characters);
        State acceptingState = new State(true);
        for(String character : characters) {
            this.initialState.addTransition(character, acceptingState);
        }
    }

    @Override
    public String toString() {
       return super.toString("|");
    }
}
