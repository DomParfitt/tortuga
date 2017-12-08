package automata;

import utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of a FiniteStateMachine representing the case of
 * multiple tokens, whereby consuming any one of them will pass the validation
 */
public class UnionFSM extends CharacterFSM {

    public UnionFSM(String characters) {
        super();
        State terminalState = new State(this.stateCounter++, true);
        this.states.add(terminalState);
        this.terminalStateIndex = this.stateCounter - 1;
        Transition<Character> transition = new Transition<Character>(StringUtils.toCharacterList(characters), this.getInitialState(), this.getTerminalState());
        this.transitions.add(transition);
    }

    public UnionFSM(CharacterFSM first, CharacterFSM second) {
        CharacterFSM firstCopy = first.copy();
        CharacterFSM secondCopy = second.copy();
        this.initialise(firstCopy);

        State secondInitial = secondCopy.getInitialState();
        State secondTerminal = secondCopy.getTerminalState();

        //Add all states from secondCopy into this, except initial and terminal state
        for(State state : secondCopy.states) {

            //We don't want to copy across the initial or terminal states
            if(!state.equals(secondInitial) && !state.equals(secondTerminal)) {

                //Add the state and get its new number
                int newNumber = this.addState(state);

                //Find any transitions referencing that state and update their number
                for(Transition<Character> transition : secondCopy.transitions) {
                    if(transition.fromState.equals(state)) {
                        transition.fromState.setNumber(newNumber);
                    }

                    if(transition.toState.equals(state)) {
                        transition.toState.setNumber(newNumber);
                    }
                }
            }
        }

        //Add all transitions from secondCopy into this, replacing initial and terminal states with this's
        for(Transition<Character> transition : secondCopy.transitions) {
            if(transition.fromState.equals(secondInitial)) {
                transition.fromState = this.getInitialState();
            }

            if(transition.toState.equals(secondTerminal)) {
                transition.toState = this.getTerminalState();
            }

            this.addTransition(transition);
        }

    }

    @Override
    public CharacterFSM copy() {
        CharacterFSM copy = new UnionFSM("");
        copy.stateCounter = this.stateCounter;
        copy.terminalStateIndex = this.terminalStateIndex;
        copy.states = this.copyStates();
        copy.transitions = this.copyTransitions(copy.states);

        return copy;
    }

}
