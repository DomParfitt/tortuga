package grammar;

import java.util.List;
import java.util.Map;

public class LoopingFSM extends FiniteStateMachine {

    private FiniteStateMachine inner;
//    public LoopingFSM(List<String> characters) {
//        super(characters);
//        this.initialState.setIsAcceptingState(true);
//        for(String character : characters) {
//            this.initialState.addTransition(character, this.initialState);
//        }
//    }

    @Deprecated
    public LoopingFSM(String character) {
        super(character);
        this.initialState.setIsAcceptingState(true);
        this.initialState.addTransition(character, this.initialState);
    }

    public LoopingFSM(FiniteStateMachine fsm) {
        this.inner = fsm;
        State finalState = fsm.getFinalState();
        for(Map.Entry<String, State> transition : fsm.initialState.getTransitions().entrySet()) {
            finalState.addTransition(transition.getKey(), transition.getValue());
        }
        this.initialState = fsm.initialState;
        this.characters = fsm.characters; //Not sure about this
    }

    @Override
    public String toString() {
//        return "(" + this.characters.get(0) + "*)";
//        return this.characters.get(0) + "*";
        return "(" + this.inner + ")*";
    }
}
