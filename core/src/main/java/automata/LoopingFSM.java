package automata;

/**
 * Concrete implementation of a FiniteStateMachine, which validates on zero or
 * more of the inner FSM
 */
public class LoopingFSM extends LexerMachine {

    public LoopingFSM() {

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

    @Override
    public LexerMachine copy() {
        LexerMachine copy = new LoopingFSM();
        copy.states = this.copyStates();
        copy.transitions = this.copyTransitions(copy.states);

        return copy;
    }
}
