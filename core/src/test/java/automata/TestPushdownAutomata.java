package automata;

import lexer.Lexer;
import lexer.Token;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class TestPushdownAutomata {

    Lexer lexer;
    PushdownAutomaton pda;

    @Before
    public void setup() {
        lexer = new Lexer();
    }

    @Test
    public void testAdditionStatement() {
        pda = PDAFactory.getMathematicalStatement();
        List<Token> tokens = lexer.tokenize("1 + 2");
        for(Token token : tokens) {
            System.out.println(token);
        }
        assertTrue(pda.parse(tokens));
    }
}
