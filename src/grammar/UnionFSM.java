package grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Concrete implementation of a FiniteStateMachine representing the case of
 * multiple tokens, whereby consuming any one of them will pass the validation
 */
public class UnionFSM extends FiniteStateMachine {

    public UnionFSM(String characters) {
        super();
        State terminalState = new State(this.stateCounter++, true);
        this.states.add(terminalState);
        this.terminalStateIndex = this.stateCounter - 1;
        Transition transition = new Transition(characters, this.getInitialState(), this.getTerminalState());
        this.transitions.add(transition);
    }

    public UnionFSM(FiniteStateMachine first, FiniteStateMachine second) {
        FiniteStateMachine firstCopy = first.copy();

    }

    @Override
    public FiniteStateMachine copy() {
        FiniteStateMachine copy = new UnionFSM("");
        copy.terminalStateIndex = this.terminalStateIndex;
        copy.states = this.copyStates();
        copy.transitions = this.copyTransitions(copy.states);

        return copy;
    }

    @Override
    public void addTransition(Character character) {
        State initalState = this.getInitialState();
        State terminalState = this.getTerminalState();
        Transition transition = new Transition(character, initalState, terminalState);
        this.transitions.add(transition);
    }

    @Override
    public void addState(State state) {

    }
}
