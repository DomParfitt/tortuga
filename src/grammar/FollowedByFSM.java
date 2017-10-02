package grammar;

import com.sun.deploy.util.StringUtils;

import java.util.List;

public class FollowedByFSM extends FiniteStateMachine{

    public FollowedByFSM(String characters) {
        super(characters);
        List<Character> charList = FiniteStateMachine.stringToCharList(characters);
        this.initialise(charList);
//        State currentState = this.initialState;
//        for(Character character: characters.toCharArray()) {
//            State nextState = new State(false);
//            currentState.addTransition(character, nextState);
//            currentState = nextState;
//        }
//        currentState.setIsAcceptingState(true);
    }

    public FollowedByFSM(List<Character> characters) {
        super(characters);
        this.initialise(characters);
//        State currentState = this.initialState;
//        for(Character character: characters) {
//            State nextState = new State(false);
//            currentState.addTransition(character, nextState);
//            currentState = nextState;
//        }
//        currentState.setIsAcceptingState(true);
    }

    private void initialise(List<Character> characters) {
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
