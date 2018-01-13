package automata.finitestate;

import automata.LexerMachine;
import automata.State;
import automata.actions.Transition;
import utils.StringUtils;

/**
 * Concrete implementation of a FiniteStateMachine representing the case of
 * multiple tokens, whereby consuming any one of them will pass the validation
 */
public class UnionFSM extends LexerMachine {

    public UnionFSM(String characters) {
        super();
        State terminalState = this.addState(true);
        Transition<Character> transition = new Transition<>(StringUtils.toCharacterSet(characters), this.getInitialState(), this.getTerminalState());
        this.addAction(this.getInitialState(), transition);
    }


}
