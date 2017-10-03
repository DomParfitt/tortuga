package grammar;

import java.util.Map;
import java.util.Set;

/**
 * Concrete implementation of a FiniteStateMachine, which validates on zero or
 * more of the inner FSM
 */
public class LoopingFSM extends FiniteStateMachine {

    private FiniteStateMachine inner;

    /**
     * Initialises a looping FSM 'around' the provided one
     * @param fsm the FSM to loop on
     */
    public LoopingFSM(FiniteStateMachine fsm) {
        this.inner = fsm.copy();
        this.initialState = this.inner.initialState;
        this.initialState.setIsAcceptingState(true);
        Set<State> finalStates = this.inner.getFinalStates();
        for (State finalState : finalStates) {
            for (Map.Entry<Character, State> transition : this.inner.initialState.getTransitions().entrySet()) {
                finalState.addTransition(transition.getKey(), transition.getValue());
            }
        }
        this.characters = this.inner.characters; //Not sure about this
    }

    @Override
    public FiniteStateMachine copy() {
        FiniteStateMachine copy = new LoopingFSM(this.inner.copy());
        copy.initialState = this.initialState.copy();
        return copy;
//        return new LoopingFSM(this.inner);
    }

    @Override
    public String toString() {
//        return "(" + this.characters.get(0) + "*)";
//        return this.characters.get(0) + "*";
        return "(" + this.inner + ")*";
    }
}
