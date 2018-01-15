package automata.pushdown;

import automata.State;
import automata.actions.AutomataAction;
import automata.actions.StackAction;
import automata.finitestate.FiniteStateMachine;
import automata.memory.Stack;
import grammar.Grammar;

import java.util.List;

/**
 * Extension of FiniteStateMachine with memory added in the form of a stack
 */
public abstract class PushdownAutomaton<T> extends FiniteStateMachine<T> {

    protected Stack<T> stack;

    public PushdownAutomaton() {
        super();
        this.stack = new Stack<>();
    }

    public Stack<T> getStack() {
        return stack;
    }

    @Override
    public boolean parse(List<T> tokens) {
        return super.parse(tokens) && this.getStack().isEmpty();
    }

    @Override
    public boolean parse(T token) {
        return super.parse(token) && this.getStack().isEmpty();
    }

    public void addStackAction() {
        //TODO: Figure out possible implementation for this
    }

    public PushdownAutomaton<T> concatenate(PushdownAutomaton<T> other) {
        PushdownAutomaton<T> pda = (PushdownAutomaton<T>) super.concatenate(other);
        pda.updateStackReferences();
        return pda;
    }

    public PushdownAutomaton<T> concatenate(Grammar grammar) {
        PushdownAutomaton<T> pda =  (PushdownAutomaton<T>)super.concatenate(grammar.getMachine());
        pda.updateStackReferences();
        return pda;
    }

    public PushdownAutomaton<T> union(PushdownAutomaton<T> other) {
        PushdownAutomaton<T> pda = (PushdownAutomaton<T>) super.union(other);
        pda.updateStackReferences();
        return pda;
    }

    public PushdownAutomaton<T> union(Grammar grammar) {
        PushdownAutomaton<T> pda =  (PushdownAutomaton<T>)super.union(grammar.getMachine());
        pda.updateStackReferences();
        return pda;
    }

    public PushdownAutomaton<T> loop() {
        return (PushdownAutomaton<T>) super.loop();
    }

    private void updateStackReferences() {
        for(State state : this.getStates()) {
            for(AutomataAction<T> action : this.getActions(state)) {
                if(action instanceof StackAction) {
                    ((StackAction<T>) action).setStack(this.getStack());
                }
            }

        }
    }

}
