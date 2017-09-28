package grammar;

import java.util.List;

public class FollowedByFSM extends FiniteStateMachine{

    public FollowedByFSM(List<String> characters) {
        super();
        State currentState = this.initialState;
        for(String character: characters) {
            State nextState = new State(false);
            currentState.addTransition(character, nextState);
            currentState = nextState;
        }
        currentState.setIsAcceptingState(true);
    }
}
