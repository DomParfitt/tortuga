package grammar;

import automata.pushdown.ParserMachine;
import org.junit.Test;

public class TestParserGrammar {

    ParserGrammar grammar;

    @Test
    public void testMathOp() {
        ParserMachine mathOp = ParserGrammarFactory.getMathematicalOperator();
        System.out.println(mathOp);

    }

    @Test
    public void testIntExpr() {
        ParserMachine intExpr = ParserGrammarFactory.getIntExpression();
        System.out.println(intExpr);
    }
}
