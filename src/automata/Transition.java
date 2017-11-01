package automata;

import java.util.Set;
import java.util.TreeSet;

public class Transition implements Comparable {

    Set<Character> characters = new TreeSet<>();
    State fromState, toState;

    public Transition(Character character, State from, State to) {
        this.characters.add(character);
        this.fromState = from;
        this.toState = to;
    }

    public Transition(String characters, State from, State to) {
        for(Character character : characters.toCharArray()) {
            this.characters.add(character);
        }
        this.fromState = from;
        this.toState = to;
    }

    public State getFromState() {
        return this.fromState;
    }

    public State getToState() {
        return this.toState;
    }

    public Set<Character> getCharacters() {
        return this.characters;
    }

    public boolean hasTransition(State from, Character character) {
        return (this.fromState == from) && (this.characters.contains(character));
    }

    public boolean hasTransition(Character character) {
        return this.characters.contains(character);
    }

    public State getResultingState(Character character) {
        return this.toState;
    }

    @Override
    public String toString() {
        String output = "[";
        String prefix = "";
        for(Character character : this.characters) {
            output += prefix + character;
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
