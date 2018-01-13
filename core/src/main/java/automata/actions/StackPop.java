package automata.actions;

import automata.State;
import automata.finitestate.FiniteStateMachine;
import parser.Stack;

import java.util.Set;

/**
 * Implementation of StackAction where the action is to pop the top of the stack, compare
 * against an expected token and then transition to the resulting state if the comparison
 * passes.
 * @param <T>
 */
public class StackPop<T> extends StackAction<T> {

    private T expectedToken;

    public StackPop(T inputSymbol, State from, State to, Stack<T> stack, T expectedToken) {
        super(inputSymbol, from, to, stack);
        this.expectedToken = expectedToken;
    }

    //TODO: Is this one relevant?
    public StackPop(Set<T> inputSymbols, State from, State to, Stack<T> stack, T expectedToken) {
        super(inputSymbols, from, to, stack);
        this.expectedToken = expectedToken;
    }

    /**
     * Gets the token to expect on the top of the stack
     * @return the expected token
     */
    public T getExpectedToken() {
        return this.expectedToken;
    }

    /**
     * Set the token to expect on the top of the stack
     * @param expectedToken the token to expect
     */
    public void setExpectedToken(T expectedToken) {
        this.expectedToken = expectedToken;
    }

    @Override
    public boolean doAction(FiniteStateMachine<T> machine) {
        T poppedToken = this.getStack().pop();
        if (!this.getExpectedToken().equals(poppedToken)) {
            //TODO: Replace popped token?
            return false;
        }
        return super.doAction(machine);
    }
}
