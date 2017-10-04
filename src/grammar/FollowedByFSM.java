package grammar;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Concrete implementation of FiniteStateMachine representing the case
 * of some token A followed by another token B. These tokens can either
 * be characters or other FSMs
 */
public class FollowedByFSM extends FiniteStateMachine {

    /**
     * Initialise using a string of characters
     * @param characters the characters to use for parsing
     */
    public FollowedByFSM(String characters) {
        super(characters);
        List<Character> charList = FiniteStateMachine.stringToCharList(characters);
        this.initialise(charList);
    }

    /**
     * Initialise with a list of characters
     * @param characters the characters to use for parsing
     */
    public FollowedByFSM(List<Character> characters) {
        super(characters);
        this.initialise(characters);
    }

    /**
     * Initialise this FSM by compounding two other FSMs. Results in an FSM
     * which validates a string on the first followed by the second
     * @param first the first FSM
     * @param second the second FSM
     */
    public FollowedByFSM(FiniteStateMachine first, FiniteStateMachine second) {
        FiniteStateMachine firstCopy, secondCopy;
        firstCopy = first.copy();
        secondCopy = second.copy();
        this.initialState = firstCopy.initialState;
        Set<State> finalStates = firstCopy.getFinalStates();
        for (State finalState : finalStates) {
            for (Map.Entry<Character, State> transition : secondCopy.initialState.getTransitions().entrySet()) {

                finalState.addTransition(transition.getKey(), transition.getValue());

            }

            //If the initial state of the 2nd FSM is accepting then the final state can be as well
            finalState.setIsAcceptingState(secondCopy.initialState.isAcceptingState());
        }
    }

    private void initialise(List<Character> characters) {
        State currentState = this.initialState;
        for (Character character : characters) {
            State nextState = new State(false, this.stateCounter++);
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
