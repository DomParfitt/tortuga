package automata.actions;

import automata.State;
import automata.memory.Stack;

import java.util.Set;

/**
 * Abstract class for actions involving manipulation of a stack and, optionally,
 * a transition.
 * @param <T>
 */
public abstract class StackAction<T> extends Transition<T> {

    private Stack<T> stack;

    /**
     *
     * @param inputSymbol
     * @param from
     * @param to
     * @param stack
     */
    public StackAction(T inputSymbol, State from, State to, Stack<T> stack) {
        super(inputSymbol, from, to);
        this.stack = stack;
    }

    /**
     *
     * @param inputSymbols
     * @param from
     * @param to
     * @param stack
     */
    public StackAction(Set<T> inputSymbols, State from, State to, Stack<T> stack) {
        super(inputSymbols, from, to);
        this.stack = stack;
    }

    /**
     * Gets the stack that this action can manipulate
     * @return the stack
     */
    public Stack<T> getStack() {
        return stack;
    }

    /**
     * Sets the stack
     * @param stack the stack that this action can manipulate
     */
    public void setStack(Stack<T> stack) {
        this.stack = stack;
    }


}
