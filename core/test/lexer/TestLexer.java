package lexer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class TestLexer {

    private static Lexer lexer;

    @Before
    public void setUp() {
        lexer = new Lexer();
    }

//    @Test
//    public void testSplitOnWhiteSpaceWithSpaces() {
//        lexer = new Lexer("This is a test");
//        List<String> expected = Arrays.asList("This", "is", "a", "test");
//        List<String> actual = lexer.splitInputOnWhiteSpace();
//        this.assertListEquality(expected, actual);
//    }
//
//    @Test
//    public void testSplitOnWhiteSpaceWithConsecutiveSpaces() {
//        lexer = new Lexer("This  is   a test");
//        List<String> expected = Arrays.asList("This", "is", "a", "test");
//        List<String> actual = lexer.splitInputOnWhiteSpace();
//        this.assertListEquality(expected, actual);
//    }
//
//    @Test
//    public void testSplitOnWhiteSpaceWithTabs() {
//        lexer = new Lexer("This\tis\t a test");
//        List<String> expected = Arrays.asList("This", "is", "a", "test");
//        List<String> actual = lexer.splitInputOnWhiteSpace();
//        this.assertListEquality(expected, actual);
//    }
//
//    @Test
//    public void testSplitOnWhiteSpaceWithConsecutiveTabs() {
//        lexer = new Lexer("This\tis\t\t a test");
//        List<String> expected = Arrays.asList("This", "is", "a", "test");
//        List<String> actual = lexer.splitInputOnWhiteSpace();
//        this.assertListEquality(expected, actual);
//    }
//
//    @Test
//    public void testSplitOnWhiteSpaceWithNewLine() {
//        lexer = new Lexer("This\nis\n a test");
//        List<String> expected = Arrays.asList("This", "is", "a", "test");
//        List<String> actual = lexer.splitInputOnWhiteSpace();
//        this.assertListEquality(expected, actual);
//    }
//
//    @Test
//    public void testSplitOnWhiteSpaceWithConsecutiveNewLines() {
//        lexer = new Lexer("This\nis\n\n a test");
//        List<String> expected = Arrays.asList("This", "is", "a", "test");
//        List<String> actual = lexer.splitInputOnWhiteSpace();
//        this.assertListEquality(expected, actual);
//    }

    @Test
    public void testValidIdentifiers() {
        String input;
        List<Token> expected = new ArrayList<>();
        List<Token> actual;

        //Passes on alphabetic identifier
        input = "var";
        expected.add(new Token(TokenType.IDENTIFIER, "var", 0, 0));
        actual = lexer.tokenize(input);
        this.assertListEquality(expected, actual);
        expected.clear();

        //Passes on alphanumeric identifier
        input = "var123";
        expected.add(new Token(TokenType.IDENTIFIER, "var123", 0, 0));
        actual = lexer.tokenize(input);
        this.assertListEquality(expected, actual);
        expected.clear();

        //Passes on identifier beginning with underscore
        input = "_var123";
        expected.add(new Token(TokenType.IDENTIFIER, "_var123", 0, 0));
        actual = lexer.tokenize(input);
        this.assertListEquality(expected, actual);
        expected.clear();

        //Passes on alphanumeric containing underscore
        input = "var_123";
        expected.add(new Token(TokenType.IDENTIFIER, "var_123", 0, 0));
        actual = lexer.tokenize(input);
        this.assertListEquality(expected, actual);
        expected.clear();

        //Fails on identifier containing invalid character
        try {
            input = "var_1?23";
            actual = lexer.tokenize(input);
            fail();
        } catch (TokenizeException e) {
            System.out.println(e.getMessage());
        }

        //"Fails" on identifier which is a keyword (checks the category return is keyword, not identifier)
        input = "if";
        expected.add(new Token(TokenType.IF, "if", 0, 0));
        actual = lexer.tokenize(input);
        Token token = actual.get(0);
        assertTrue(token.getTokenCategory() == TokenCategory.KEYWORD);
    }

    @Test
    public void testLexer() {
        List<Token> tokens = lexer.tokenize("if _var123 = ( 1 + 2 )");
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    @Test
    public void testNewTokenize() {

        List<Token> tokens = lexer.tokenize("if a = 1;\n4>3;\nx=2");
        for(Token token : tokens) {
            System.out.println(token);
        }
    }

    private <T> void assertListEquality(List<T> expected, List<T> actual) {
        assertEquals(expected.size(), actual.size());
        for (T token : expected) {
            assertTrue(actual.contains(token));
        }
    }

}
