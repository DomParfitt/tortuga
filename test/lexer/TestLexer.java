package lexer;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


public class TestLexer {

    private static Lexer lexer;

//    @BeforeAll
//    public static void setUp() {
//
//    }

    @Test
    public void testSplitOnWhiteSpaceWithSpaces() {
        lexer = new Lexer("This is a test");
        List<String> expected = Arrays.asList("This", "is", "a", "test");
        List<String> actual = lexer.splitInputOnWhiteSpace();
        this.testListEquality(expected, actual);
    }

    @Test
    public void testSplitOnWhiteSpaceWithConsecutiveSpaces() {
        lexer = new Lexer("This  is   a test");
        List<String> expected = Arrays.asList("This", "is", "a", "test");
        List<String> actual = lexer.splitInputOnWhiteSpace();
        this.testListEquality(expected, actual);
    }

    @Test
    public void testSplitOnWhiteSpaceWithTabs() {
        lexer = new Lexer("This\tis\t a test");
        List<String> expected = Arrays.asList("This", "is", "a", "test");
        List<String> actual = lexer.splitInputOnWhiteSpace();
        this.testListEquality(expected, actual);
    }

    @Test
    public void testSplitOnWhiteSpaceWithConsecutiveTabs() {
        lexer = new Lexer("This\tis\t\t a test");
        List<String> expected = Arrays.asList("This", "is", "a", "test");
        List<String> actual = lexer.splitInputOnWhiteSpace();
        this.testListEquality(expected, actual);
    }

    @Test
    public void testSplitOnWhiteSpaceWithNewLine() {
        lexer = new Lexer("This\nis\n a test");
        List<String> expected = Arrays.asList("This", "is", "a", "test");
        List<String> actual = lexer.splitInputOnWhiteSpace();
        this.testListEquality(expected, actual);
    }

    @Test
    public void testSplitOnWhiteSpaceWithConsecutiveNewLines() {
        lexer = new Lexer("This\nis\n\n a test");
        List<String> expected = Arrays.asList("This", "is", "a", "test");
        List<String> actual = lexer.splitInputOnWhiteSpace();
        this.testListEquality(expected, actual);
    }

    private void testListEquality(List<String> expected, List<String> actual) {
        assertEquals(expected.size(), actual.size());
        for(String token : expected) {
            assertTrue(actual.contains(token));
        }
    }
}
