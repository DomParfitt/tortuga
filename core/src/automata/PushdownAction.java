package automata;

public class PushdownAction {

    private StackAction stackAction;
    private State resultingState;

    public PushdownAction(StackAction stackAction, State resultingState) {
        this.stackAction = stackAction;
        this.resultingState = resultingState;
    }

    public StackAction getStackAction() {
        return stackAction;
    }

    public State getResultingState() {
        return resultingState;
    }
}
