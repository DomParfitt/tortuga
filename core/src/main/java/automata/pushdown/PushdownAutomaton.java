package automata.pushdown;

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

    public void addStackAction() {
        //TODO: Figure out possible implementation for this
    }

    public PushdownAutomaton<T> concatenate(PushdownAutomaton<T> other) {
        return (PushdownAutomaton<T>) super.concatenate(other);
    }

    public PushdownAutomaton<T> concatenate(Grammar grammar) {
        return (PushdownAutomaton<T>)super.concatenate(grammar.getMachine());
    }

    public PushdownAutomaton<T> union(PushdownAutomaton<T> other) {
        return (PushdownAutomaton<T>) super.union(other);
    }

    public PushdownAutomaton<T> union(Grammar grammar) {
        return (PushdownAutomaton<T>) super.union(grammar.getMachine());
    }

    public PushdownAutomaton<T> loop() {
        return (PushdownAutomaton<T>) super.loop();
    }

}
