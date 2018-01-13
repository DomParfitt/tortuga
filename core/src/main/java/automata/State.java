package automata;

/**
 * A class to represent a State within an FSM
 */
public class State implements Comparable<State> {

    private int number;
    private boolean isCurrentState;
    private boolean isAcceptingState;

    public State(int number, boolean isAcceptingState) {
        this.number = number;
        this.isAcceptingState = isAcceptingState;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isAcceptingState() {
        return this.isAcceptingState;
    }

    public void setAcceptingState(boolean isAcceptingState) {
        this.isAcceptingState = isAcceptingState;
    }

    public boolean isCurrentState() {
        return this.isCurrentState;
    }

    public void setCurrentState(boolean isCurrentState) {
        this.isCurrentState = isCurrentState;
    }

    public State copy() {
        State copy = new State(this.getNumber(), this.isAcceptingState());
        copy.setCurrentState(this.isCurrentState());
        return copy;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof State)) {
            return false;
        }

        State otherState = (State) other;

        return (this.getNumber() == otherState.getNumber())
                && (this.isAcceptingState() == otherState.isAcceptingState());
    }

    @Override
    public String toString() {
        String output = "";

        if(this.isAcceptingState()) {
            output += "TERMINAL STATE";
        } else {
            output += "NON-TERMINAL STATE";
        }

        output += " (#" + this.getNumber() + ")";

        return output;
    }

    @Override
    public int compareTo(State o) {
        return ((Integer) this.getNumber()).compareTo(o.getNumber());
    }
}
