package automata.pushdown;

import automata.State;
import automata.actions.Transition;
import grammar.LexerGrammar;

public class None extends ParserMachine {

    public None(LexerGrammar token) {
        super();
        State initialState = this.getInitialState();
        State terminalState = this.addState(true);
        this.addAction(initialState, new Transition<>(token, initialState, terminalState));
    }
}
