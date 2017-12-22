package automata;

import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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
        List<Token> tokens = lexer.tokenize("(10) + 326");
        List<TokenType> tokenTypes = new ArrayList<>();

        for(Token token : tokens) {
            System.out.println(token);
            tokenTypes.add(token.getTokenType());
        }

        assertTrue(pda.parse(tokenTypes));
        
    }
}
