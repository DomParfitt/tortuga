package grammar;

import java.util.List;

public class LoopingFSM extends FiniteStateMachine {

//    public LoopingFSM(List<String> characters) {
//        super(characters);
//        this.initialState.setIsAcceptingState(true);
//        for(String character : characters) {
//            this.initialState.addTransition(character, this.initialState);
//        }
//    }

    public LoopingFSM(String character) {
        super(character);
        this.initialState.setIsAcceptingState(true);
        this.initialState.addTransition(character, this.initialState);
    }

//    public LoopingFSM(FiniteStateMachine fsm) {
//
//    }

    @Override
    public String toString() {
//        return "(" + this.characters.get(0) + "*)";
        return this.characters.get(0) + "*";
    }
}
