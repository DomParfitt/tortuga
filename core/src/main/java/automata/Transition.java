package automata;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Transition<T> implements Comparable {

    Set<T> inputSymbols = new HashSet<>();
    State fromState, toState;
    TransitionAction action;

    public Transition(T inputSymbol, State from, State to) {
        this.inputSymbols.add(inputSymbol);
        this.fromState = from;
        this.toState = to;
    }

    public Transition(List<T> inputSymbols, State from, State to) {
        for(T token : inputSymbols) {
            this.inputSymbols.add(token);
        }
        this.fromState = from;
        this.toState = to;
    }

    public Transition(T inputSymbol, State from, State to, TransitionAction action) {
        this(inputSymbol, from, to);
        this.action = action;
    }

    public Transition(List<T> inputSymbols, State from, State to, TransitionAction action) {
        this(inputSymbols, from, to);
        this.action = action;
    }

    public State getFromState() {
        return this.fromState;
    }

    public State getToState() {
        return this.toState;
    }

    public Set<T> getInputSymbols() {
        return this.inputSymbols;
    }

    public boolean hasAction() {
        return this.action != null;
    }

    public TransitionAction getAction() {
        return action;
    }

    public boolean hasTransition(State from, T inputSymbol) {
        return (this.fromState == from) && (this.inputSymbols.contains(inputSymbol));
    }

    public boolean hasTransition(T inputSymbol) {
        return this.inputSymbols.contains(inputSymbol);
    }

    public State getResultingState(T inputSymbol) {
        return this.toState;
    }

    @Override
    public String toString() {
        String output = "[";
        String prefix = "";
        for(T inputSymbol : this.inputSymbols) {
            output += prefix + inputSymbol;
            prefix = "|";
        }
        output += "]";
        return this.fromState + " -> " + output + " -> " + this.toState;
    }

    @Override
    public int compareTo(Object o) {
        Transition other = (Transition) o;
        if(this.fromState.getNumber() != other.fromState.getNumber()) {
            return this.fromState.getNumber() - other.fromState.getNumber();
        } else {
            return this.toState.getNumber() - other.toState.getNumber();
        }
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Transition)) {
            return false;
        }

        Transition otherTransition = (Transition) other;

        return this.fromState.equals(otherTransition.fromState) && this.toState.equals(otherTransition.toState);
    }
}
