package grammar;

import automata.LexerMachine;
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
        grammar = LexerGrammar.STRING;

        assertTrue(grammar.parse("\"Hello, world!\""));
    }

    @Test
    public void testIntLiteral() {
        grammar = LexerGrammar.INT;

        assertTrue(grammar.parse("1"));
        assertFalse(grammar.parse("11.0"));
    }

    @Test
    public void testFloatLiteral() {
        grammar = LexerGrammar.FLOAT;

        assertTrue(grammar.parse("1.0"));
        assertTrue(grammar.parse("11.0"));
        assertTrue(grammar.parse("1.01"));
        assertTrue(grammar.parse("11.01"));
        assertFalse(grammar.parse("1"));
    }

    private void parsePass(String input) {
        assertTrue(grammar.parse(input));
    }

    private void parseFail(String input) {
        assertFalse(grammar.parse(input));
    }

}
