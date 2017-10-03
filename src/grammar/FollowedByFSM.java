package grammar;

import com.sun.deploy.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FollowedByFSM extends FiniteStateMachine {

    public FollowedByFSM(String characters) {
        super(characters);
        List<Character> charList = FiniteStateMachine.stringToCharList(characters);
        this.initialise(charList);
    }

    public FollowedByFSM(List<Character> characters) {
        super(characters);
        this.initialise(characters);
    }

    public FollowedByFSM(FiniteStateMachine first, FiniteStateMachine second) {
        FiniteStateMachine firstCopy, secondCopy;
        firstCopy = first.copy();
        secondCopy = second.copy();
        this.initialState = firstCopy.initialState;
        Set<State> finalStates = firstCopy.getFinalStates();
        for (State finalState : finalStates) {
            for (Map.Entry<Character, State> transition : secondCopy.initialState.getTransitions().entrySet()) {

                finalState.addTransition(transition.getKey(), transition.getValue());
                finalState.setIsAcceptingState(secondCopy.initialState.isAcceptingState());
            }
//            finalState.setIsAcceptingState(false);
        }
    }

    private void initialise(List<Character> characters) {
        State currentState = this.initialState;
        for (Character character : characters) {
            State nextState = new State(false);
            currentState.addTransition(character, nextState);
            currentState = nextState;
        }
        currentState.setIsAcceptingState(true);
    }

    @Override
    public FiniteStateMachine copy() {
        FiniteStateMachine copy = new FollowedByFSM(this.characters);
        copy.initialState = this.initialState.copy();
        return copy;
//        return new FollowedByFSM(this.characters);
    }

    @Override
    public String toString() {
        return super.toString("");
    }

}
