package grammar;

import automata.PDAFactory;
import automata.PushdownAutomaton;

import java.util.List;

public enum ParserGrammar implements Grammar<LexerGrammar> {

    MATH_STATEMENT;

    PushdownAutomaton machine;

    ParserGrammar() {
        this.machine = PDAFactory.getMachine(this);
    }

    ParserGrammar(PushdownAutomaton machine) {
        this.machine = machine;
    }

//    @Override
//    public boolean parse(LexerGrammar input) {
//        return this.machine.parse(input);
//    }

    @Override
    public boolean parse(List<LexerGrammar> input) {
        return this.machine.parse(input);
    }

    @Override
    public PushdownAutomaton getMachine() {
        return machine;
    }
}
