package automata;

import grammar.LexerGrammar;

@Deprecated
public class PDATransition extends Transition<LexerGrammar> {

//    Set<T> stackSymbols = new HashSet<>();
    StackAction stackAction;

    public PDATransition(LexerGrammar inputSymbol, StackAction stackAction, State from, State to) {
        super(inputSymbol, from, to);
        this.stackAction = stackAction;
    }

    public PushdownAction getResultingState() {
        return new PushdownAction(this.stackAction, this.toState);
    }

    public boolean hasTransition(LexerGrammar token) {
        for(LexerGrammar inputSymbol : this.inputSymbols) {
            if(token.equals(inputSymbol)) {
                return true;
            }
        }

        return false;
    }

}
