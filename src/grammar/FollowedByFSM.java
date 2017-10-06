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


    public FollowedByFSM(String characters) {
        super();
        for(Character character : characters.toCharArray()) {
            State nextState = new State(this.stateCounter++, false);
            this.states.add(nextState);
            this.terminalStateIndex = this.stateCounter - 1;
            Transition transition = new Transition(character, this.getCurrentState(), nextState);
            this.transitions.add(transition);
            this.setCurrentState(nextState);
        }
        this.getTerminalState().setAcceptingState(true);

    }

    public FollowedByFSM(FiniteStateMachine first, FiniteStateMachine second) {

    }

    @Override
    public void addTransition(Character character) {
        //TODO: Implement
    }

    @Override
    public void addState(State state) {
        //TODO: Implement
    }

    @Override
    public FiniteStateMachine copy() {
        FiniteStateMachine copy = new FollowedByFSM("");
        copy.terminalStateIndex = this.terminalStateIndex;
        copy.states = this.copyStates();
        copy.transitions = this.copyTransitions(copy.states);

        return copy;
    }
}
