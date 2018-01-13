package grammar;

import automata.pushdown.PushdownAutomaton;

import java.util.List;

public enum ParserGrammar implements Grammar<LexerGrammar> {

    BOOLEAN_EXPRESSION,
    INT_EXPRESSION,
    FLOAT_EXPRESSION,
    MATH_EXPRESSION,
    ASSIGNMENT_STATEMENT,
    IF_STATEMENT,
    FOR_STATEMENT,
    WHILE_STATEMENT;

//    PushdownAutomaton machine;

//    ParserGrammar() {
//        this.machine = ParserGrammarFactory.getMachine(this);
//    }

//    ParserGrammar(PushdownAutomaton machine) {
//        this.machine = machine;
//    }

//    @Override
//    public boolean parse(LexerGrammar input) {
//        return this.machine.parse(input);
//    }

    @Override
    public boolean parse(List<LexerGrammar> input) {
        return this.getMachine().parse(input);
    }

    @Override
    public PushdownAutomaton getMachine() {
        return ParserGrammarFactory.getMachine(this);
    }
}
