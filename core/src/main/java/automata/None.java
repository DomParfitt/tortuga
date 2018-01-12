package automata;

import grammar.LexerGrammar;

public class None extends PushdownAutomaton {

    public None(LexerGrammar token) {
        super();
        this.addState(true);
        this.addTransition(this.getInitialState(), this.getTerminalState(), token, StackAction.StackActionType.NONE, token);
    }
}
