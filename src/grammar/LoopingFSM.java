package grammar;

import java.util.Map;
import java.util.Set;

/**
 * Concrete implementation of a FiniteStateMachine, which validates on zero or
 * more of the inner FSM
 */
public class LoopingFSM extends FiniteStateMachine {

    public LoopingFSM() {

    }

    public LoopingFSM(FiniteStateMachine fsm) {
        FiniteStateMachine copy = fsm.copy();
        this.initialise(copy);
        for(Transition transition : copy.transitions) {
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
    public FiniteStateMachine copy() {
        FiniteStateMachine copy = new LoopingFSM();
        copy.states = this.copyStates();
        copy.transitions = this.copyTransitions(copy.states);

        return copy;
    }
}
