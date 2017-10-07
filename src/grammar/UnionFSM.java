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
        FiniteStateMachine secondCopy = second.copy();
        this.initialise(firstCopy);

        //Update the state numbers in the secondCopy
        for(State state : secondCopy.states) {

            //Update any transitions that contain this state
            for(Transition transition : secondCopy.transitions) {
                State fromState = transition.fromState;
                State toState = transition.toState;
                if(fromState.equals(state)) {
                    fromState.setNumber(fromState.getNumber() + this.stateCounter);
                }
                if(toState.equals(state)) {
                    toState.setNumber(toState.getNumber() + this.stateCounter);
                }
            }

            //Update the state number to account for states in first FSM
            state.setNumber(state.getNumber() + this.stateCounter++);
        }

        State secondInitial = secondCopy.getInitialState();
        State secondTerminal = secondCopy.getTerminalState();

        //Add all states from secondCopy into this, except initial and terminal state
        for(State state : secondCopy.states) {
            if(!state.equals(secondInitial) && !state.equals(secondTerminal)) {
                this.addState(state);
            }
        }

        //Add all transitions from secondCopy into this, replacing initial and terminal states with this's

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
