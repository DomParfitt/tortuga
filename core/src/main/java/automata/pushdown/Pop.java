package automata.pushdown;

import automata.State;
import automata.actions.StackPop;
import grammar.LexerGrammar;

public class Pop extends ParserMachine {

    public Pop(LexerGrammar token, LexerGrammar expected) {
        super();
        State initialState = this.addState(false);
        State terminalState = this.addState(true);
        this.addAction(initialState, new StackPop<>(token, initialState, terminalState, this.getStack(), expected));
    }
}
