package automata;

import lexer.TokenType;

import java.util.HashSet;
import java.util.Set;

public class PDATransition extends Transition<TokenType> {

//    Set<T> stackSymbols = new HashSet<>();
    StackAction stackAction;

    public PDATransition(TokenType inputSymbol, StackAction stackAction, State from, State to) {
        super(inputSymbol, from, to);
        this.stackAction = stackAction;
    }

    public PushdownAction getResultingState() {
        return new PushdownAction(this.stackAction, this.fromState);
    }

}
