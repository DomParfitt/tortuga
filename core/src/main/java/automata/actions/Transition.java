package automata.actions;

import automata.State;
import automata.finitestate.FiniteStateMachine;

import java.util.HashSet;
import java.util.Set;

/**
 * Class to represent a Transition action for an automaton
 *
 * @param <T> the type of object over which the automaton transitions
 */
public class Transition<T> implements AutomataAction<T>, Comparable {

    private Set<T> inputSymbols = new HashSet<>();
    private State fromState, resultingState;

    /**
     * Initialises a Transition with an input symbol, a from state and a resulting state
     *
     * @param inputSymbol    the input symbol to consume when transitioning
     * @param from           the state to transition from
     * @param resultingState the state resulting from the transition
     */
    public Transition(T inputSymbol, State from, State resultingState) {
        this.inputSymbols.add(inputSymbol);
        this.fromState = from;
        this.resultingState = resultingState;
    }

    /**
     * Initialises a Transition with a list of input symbols, a from state and a resulting state
     *
     * @param inputSymbols   the input symbols that can be consumed when transitioning
     * @param from           the state to transition from
     * @param resultingState the state resulting from the transition
     */
    public Transition(Set<T> inputSymbols, State from, State resultingState) {
        for (T token : inputSymbols) {
            this.inputSymbols.add(token);
        }
        this.fromState = from;
        this.resultingState = resultingState;
    }

    @Override
    public Set<T> getInputSymbols() {
        return this.inputSymbols;
    }

    @Override
    public State getFromState() {
        return this.fromState;
    }

    @Override
    public void setFromState(State fromState) {
        this.fromState = fromState;
    }

    @Override
    public State getResultingState() {
        return this.resultingState;
    }

    @Override
    public void setResultingState(State resultingState) {
        this.resultingState = resultingState;
    }

    @Override
    public AutomataAction<T> copy() {
        return new Transition<>(this.inputSymbols, this.fromState, this.resultingState);
    }

    @Override
    public boolean appliesTo(T token) {
        return this.inputSymbols.contains(token);
    }

    @Override
    public boolean doAction(FiniteStateMachine<T> machine) {
        machine.setCurrentState(this.getResultingState());
        return machine.getCurrentState().equals(this.getResultingState());
    }

    @Override
    public int compareTo(Object o) {
        Transition other = (Transition) o;
        State from = this.getFromState();
        State resulting = this.getResultingState();
        State otherFrom = other.getFromState();
        State otherResulting = other.getResultingState();

        if (from.compareTo(otherFrom) != 0) {
            return from.compareTo(otherFrom);
        } else if (resulting.compareTo(otherResulting) != 0) {
            return resulting.compareTo(otherResulting);
        } else if (((Boolean) from.isAcceptingState()).compareTo(otherFrom.isAcceptingState()) != 0) {
            return ((Boolean) from.isAcceptingState()).compareTo(otherFrom.isAcceptingState());
        } else {
            return ((Boolean) resulting.isAcceptingState()).compareTo(otherResulting.isAcceptingState());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Transition)) {
            return false;
        }

        Transition otherTransition = (Transition) other;

        return this.fromState.equals(otherTransition.fromState) && this.resultingState.equals(otherTransition.resultingState);
    }

    @Override
    public String toString() {
        String output = "[";
        String prefix = "";
        for (T inputSymbol : this.inputSymbols) {
            output += prefix + inputSymbol;
            prefix = "|";
        }
        output += "]";
        return this.fromState + "->" + output + " -> " + this.resultingState;
    }

}
