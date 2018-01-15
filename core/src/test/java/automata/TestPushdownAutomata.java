package automata;

import automata.pushdown.ParserMachine;
import automata.pushdown.Pop;
import automata.pushdown.Push;
import automata.pushdown.PushdownAutomaton;
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
    ParserMachine pda;

    @Before
    public void setup() {
        lexer = new Lexer();
    }

    @Test
    public void testMatchingParens() {
        pda = new Push(LexerGrammar.OPEN_PAREN);
        pda = pda.concatenate(new Pop(LexerGrammar.CLOSE_PAREN, LexerGrammar.OPEN_PAREN));
        System.out.println(pda);
        List<LexerGrammar> lexemes =new ArrayList<>();
        lexemes.add(LexerGrammar.OPEN_PAREN);
        lexemes.add(LexerGrammar.CLOSE_PAREN);
        assertTrue(pda.parse(lexemes));
    }

    @Test
    public void testAnyNumberMatchingParens() {
        pda = new Push(LexerGrammar.OPEN_PAREN);
        pda = pda.loop();
        ParserMachine pop = new Pop(LexerGrammar.CLOSE_PAREN, LexerGrammar.OPEN_PAREN).loop();
        pda = pda.concatenate(pop);
        System.out.println(pda);
        List<LexerGrammar> lexemes =new ArrayList<>();
        lexemes.add(LexerGrammar.OPEN_PAREN);
        lexemes.add(LexerGrammar.OPEN_PAREN);
        lexemes.add(LexerGrammar.OPEN_PAREN);

        lexemes.add(LexerGrammar.CLOSE_PAREN);
        lexemes.add(LexerGrammar.CLOSE_PAREN);
        lexemes.add(LexerGrammar.CLOSE_PAREN);

        assertTrue(pda.parse(lexemes));
    }

    @Test
    public void testAdditionStatement() {
        pda = ParserGrammarFactory.getMathematicalExpression();
        List<Token> tokens = lexer.tokenize("(10 + 326)");
        List<LexerGrammar> lexerGrammars = new ArrayList<>();

        for (Token token : tokens) {
            System.out.println(token);
            lexerGrammars.add(token.getTokenType());
        }

        assertTrue(pda.parse(lexerGrammars));

    }

}