package automata;

import grammar.LexerGrammar;
import grammar.ParserGrammarFactory;
import lexer.Lexer;
import lexer.Token;
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
        pda = ParserGrammarFactory.getMathematicalExpression();
        List<Token> tokens = lexer.tokenize("(10) + 326");
        List<LexerGrammar> lexerGrammars = new ArrayList<>();

        for(Token token : tokens) {
            System.out.println(token);
            lexerGrammars.add(token.getTokenType());
        }

        assertTrue(pda.parse(lexerGrammars));
        
    }
}
