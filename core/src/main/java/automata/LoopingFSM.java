package automata;

import grammar.LexerGrammar;

/**
 * Concrete implementation of a FiniteStateMachine, which validates on zero or
 * more of the inner FSM
 */
@Deprecated
public class LoopingFSM extends LexerMachine {

    public LoopingFSM() {

    }

    public LoopingFSM(LexerGrammar grammar) {
        this(grammar.getMachine());
    }

    public LoopingFSM(LexerMachine fsm) {
        LexerMachine copy = fsm.copy();
        this.initialise(copy);
        for(Transition<Character> transition : copy.transitions) {
            if(transition.toState.equals(copy.getTerminalState())) {
                transition.toState = copy.getInitialState();
            }
        }

        State terminalState = copy.getTerminalState();
        copy.states.remove(terminalState);
        copy.getInitialState().setAcceptingState(true);
        this.terminalStateIndex = this.initialStateIndex;

    }

}
