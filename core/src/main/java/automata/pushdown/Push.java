package automata.pushdown;

import grammar.LexerGrammar;

public class Push extends PushdownAutomaton {

    public Push(LexerGrammar token) {
        super();
        this.addState(true);
//        this.addTransition(this.getInitialState(), this.getTerminalState(), token, StackAction.StackActionType.PUSH, token);
    }
}
