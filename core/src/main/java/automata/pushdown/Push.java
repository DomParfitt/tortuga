package automata.pushdown;

import automata.State;
import automata.actions.StackPush;
import grammar.LexerGrammar;

public class Push extends ParserMachine {

    public Push(LexerGrammar token) {
        super();
        State initialState = this.addState(false);
        State terminalState = this.addState(true);
        this.addAction(initialState, new StackPush<>(token, initialState, terminalState, this.getStack()));
    }

}
