package automata;

import java.util.HashSet;
import java.util.Set;

public class PDATransition<T> extends Transition<T> {

    Set<T> stackSymbols = new HashSet<>();
    StackAction stackAction;

    public PDATransition(T inputSymbol, StackAction stackAction, State from, State to) {
        super(inputSymbol, from, to);
        this.stackAction = stackAction;
    }

    public PushdownAction getResultingState() {
        return new PushdownAction(this.stackAction, this.fromState);
    }

}
