package automata;

import automata.actions.StackAction;
import automata.actions.Transition;
import automata.pushdown.PushdownAutomaton;
import grammar.LexerGrammar;
import grammar.ParserGrammarFactory;
import lexer.Lexer;
import lexer.Token;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        List<Token> tokens = lexer.tokenize("(10 + 326)");
        List<LexerGrammar> lexerGrammars = new ArrayList<>();

        for(Token token : tokens) {
            System.out.println(token);
            lexerGrammars.add(token.getTokenType());
        }

        assertTrue(pda.parse(lexerGrammars));
        
    }

    @Test
    public void testConcatenate() {
        List<State> states = new ArrayList<>();
        for(int i = 0; i <= 1; i++) {
            State state = new State(i, false);
            if(i == 0) {
                state.setCurrentState(true);
            }

            if(i == 1) {
                state.setAcceptingState(true);
            }
            states.add(state);
        }

        Set<Transition<LexerGrammar>> transitions = new HashSet<>();
        transitions.add(new PDATransition(LexerGrammar.MINUS, new StackAction(StackAction.StackActionType.NONE), states.get(0), states.get(1)));
        pda = new PushdownAutomaton(states, transitions);


        List<State> states2 = new ArrayList<>();
        for(int i = 0; i <= 1; i++) {
            State state = new State(i, false);
            if(i == 0) {
                state.setCurrentState(true);
            }
            if(i == 1) {
                state.setAcceptingState(true);
            }
            states2.add(state);
        }

        Set<Transition<LexerGrammar>> transitions2 = new HashSet<>();
        transitions2.add(new PDATransition(LexerGrammar.INT_LITERAL, new StackAction(StackAction.StackActionType.NONE), states2.get(0), states2.get(1)));

        PushdownAutomaton pda2 = new PushdownAutomaton(states2, transitions2);

        PushdownAutomaton compound = pda.concatenate(pda2);

        List<LexerGrammar> tokens = new ArrayList<>();
        tokens.add(LexerGrammar.MINUS);
        tokens.add(LexerGrammar.INT_LITERAL);

        assertTrue(compound.parse(tokens));

    }
}
