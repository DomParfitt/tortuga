package automata.pushdown;

import automata.State;
import automata.actions.StackPopAndPush;
import grammar.LexerGrammar;

public class Both extends ParserMachine {

    public Both(LexerGrammar token, LexerGrammar expected) {
        super();
        State initialState = this.addState(false);
        State terminalState = this.addState(true);
        this.addAction(initialState, new StackPopAndPush<>(token, initialState, terminalState, this.getStack(), expected));
    }
}
