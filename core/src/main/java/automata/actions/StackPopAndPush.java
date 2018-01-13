package automata.actions;

import automata.State;
import automata.finitestate.FiniteStateMachine;
import parser.Stack;

import java.util.Set;

/**
 * Implementation of StackAction where the action is to pop the top of the stack, compare
 * against an expected token and then push the input symbol to the stakc and transition to
 * the resulting state if the comparison passes.
 * @param <T>
 */
public class StackPopAndPush<T> extends StackPop<T> {

    public StackPopAndPush(T inputSymbol, State from, State to, Stack<T> stack, T expectedToken) {
        super(inputSymbol, from, to, stack, expectedToken);
    }

    public StackPopAndPush(Set<T> inputSymbols, State from, State to, Stack<T> stack, T expectedToken) {
        super(inputSymbols, from, to, stack, expectedToken);
    }

    @Override
    public boolean doAction(FiniteStateMachine<T> machine) {
        boolean actionSucceeded = super.doAction(machine); //Pops and compares, then updates current state
        for(T inputSymbol : this.getInputSymbols()) {
            this.getStack().push(inputSymbol);
        }

        return actionSucceeded;
    }
}
