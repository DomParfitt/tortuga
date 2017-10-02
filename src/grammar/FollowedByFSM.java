package grammar;

import java.util.List;

public class FollowedByFSM extends FiniteStateMachine{

    public FollowedByFSM(List<Character> characters) {
        super(characters);
        State currentState = this.initialState;
        for(Character character: characters) {
            State nextState = new State(false);
            currentState.addTransition(character, nextState);
            currentState = nextState;
        }
        currentState.setIsAcceptingState(true);
    }

    @Override
    public String toString() {
       return super.toString("");
    }

}
