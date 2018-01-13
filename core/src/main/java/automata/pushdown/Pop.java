package automata.pushdown;

import automata.actions.StackAction;
import grammar.LexerGrammar;

public class Pop extends PushdownAutomaton {

    public Pop(LexerGrammar token, LexerGrammar expected) {
        super();
        this.addState(true);
        this.addTransition(this.getInitialState(), this.getTerminalState(), token, StackAction.StackActionType.POP, expected);
    }
}
