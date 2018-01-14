package automata.actions;

import automata.State;
import automata.finitestate.FiniteStateMachine;
import automata.memory.Stack;

import java.util.Set;

/**
 * Implementation of StackAction where the action is to push the inputSymbol onto the stack
 * and then transition to the resulting state.
 * @param <T>
 */
public class StackPush<T> extends StackAction<T> {

    public StackPush(T inputSymbol, State from, State to, Stack<T> stack) {
        super(inputSymbol, from, to, stack);
    }

    //TODO: Is this one relevant?
    public StackPush(Set<T> inputSymbols, State from, State to, Stack<T> stack) {
        super(inputSymbols, from, to, stack);
    }

    @Override
    public boolean doAction(FiniteStateMachine<T> machine) {
        //TODO: This should probably only push one symbol
        for(T inputSymbol : this.getInputSymbols()) {
            this.getStack().push(inputSymbol);
        }
        return super.doAction(machine);
    }
}
