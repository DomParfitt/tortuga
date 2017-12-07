package automata;

import java.util.HashSet;
import java.util.Set;

public class GenericTransition<T> implements Comparable {

    Set<T> inputSymbols = new HashSet<>();
    State fromState, toState;

    public GenericTransition(T inputSymbol, State from, State to) {
        this.inputSymbols.add(inputSymbol);
        this.fromState = from;
        this.toState = to;
    }

//    public GenericTransition(String inputSymbols, State from, State to) {
//        for(Character character : inputSymbols.toCharArray()) {
//            this.inputSymbols.add(character);
//        }
//        this.fromState = from;
//        this.toState = to;
//    }

    public State getFromState() {
        return this.fromState;
    }

    public State getToState() {
        return this.toState;
    }

    public Set<T> getInputSymbols() {
        return this.inputSymbols;
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
        GenericTransition other = (GenericTransition) o;
        if(this.fromState.getNumber() != other.fromState.getNumber()) {
            return this.fromState.getNumber() - other.fromState.getNumber();
        } else {
            return this.toState.getNumber() - other.toState.getNumber();
        }
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof GenericTransition)) {
            return false;
        }

        GenericTransition otherTransition = (GenericTransition) other;

        return this.fromState.equals(otherTransition.fromState) && this.toState.equals(otherTransition.toState);
    }
}
