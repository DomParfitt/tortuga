package automata;

import grammar.LexerGrammar;

public class Both extends PushdownAutomaton {

    public Both(LexerGrammar token, LexerGrammar expected) {
        super();
        this.addState(true);
        this.addTransition(this.getInitialState(), this.getTerminalState(), token, StackAction.StackActionType.BOTH, expected);
    }
}
