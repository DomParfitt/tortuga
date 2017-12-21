package automata;

import lexer.Token;
import lexer.TokenType;

import java.util.HashSet;
import java.util.Set;

public class PDATransition extends Transition<Token> {

//    Set<T> stackSymbols = new HashSet<>();
    StackAction stackAction;

    public PDATransition(Token inputSymbol, StackAction stackAction, State from, State to) {
        super(inputSymbol, from, to);
        this.stackAction = stackAction;
    }

    public PushdownAction getResultingState() {
        return new PushdownAction(this.stackAction, this.toState);
    }

    public boolean hasTransition(Token token) {
        for(Token inputSymbol : this.inputSymbols) {
            if(token.getTokenType().equals(inputSymbol.getTokenType())) {
                return true;
            }
        }

        return false;
    }

}
