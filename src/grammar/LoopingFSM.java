package grammar;

public class LoopingFSM extends FiniteStateMachine {

    public LoopingFSM(String character) {
        super();
        this.initialState.addTransition(character, this.initialState);
        this.initialState.setIsAcceptingState(true);
    }
}
