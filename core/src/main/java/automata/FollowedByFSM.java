package automata;

import automata.actions.Transition;

/**
 * Concrete implementation of FiniteStateMachine representing the case
 * of some token A followed by another token B. These tokens can either
 * be characters or other FSMs
 */
public class FollowedByFSM extends LexerMachine {


    public FollowedByFSM(String characters) {
        super();
        for (Character character : characters.toCharArray()) {
            State currentState = this.getCurrentState();
            State nextState = this.addState(false);
            this.terminalStateIndex = nextState.getNumber();
            Transition<Character> transition = new Transition<>(character, currentState, nextState);
            this.addAction(currentState, transition);
            this.setCurrentState(nextState);
        }
        this.getTerminalState().setAcceptingState(true);

    }

}
