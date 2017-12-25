package grammar;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestLexerGrammar {

    LexerGrammar grammar;

    @Test
    public void testIdentifier() {
        grammar = LexerGrammar.IDENTIFIER;
        parsePass("hello");
        parsePass("hello1");
        parsePass("_hello");

        parseFail("1hello");
    }

    @Test
    public void testStringLiteral() {
        grammar = LexerGrammar.STRING_LITERAL;

        parsePass("\"Hello, world!\"");
    }

    @Test
    public void testIntLiteral() {
        grammar = LexerGrammar.INT_LITERAL;

        parsePass("1");
        parseFail("11.0");
    }

    @Test
    public void testFloatLiteral() {
        grammar = LexerGrammar.FLOAT_LITERAL;

        parsePass("1.0");
        parsePass("11.0");
        parsePass("1.01");
        parsePass("11.01");
        parseFail("1");
    }

    private void parsePass(String input) {
        assertTrue(grammar.parse(input));
    }

    private void parseFail(String input) {
        assertFalse(grammar.parse(input));
    }

}
