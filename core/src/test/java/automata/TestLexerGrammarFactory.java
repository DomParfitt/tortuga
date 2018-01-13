package automata;

import automata.finitestate.FiniteStateMachine;
import grammar.LexerGrammarFactory;
import org.junit.Test;

public class TestLexerGrammarFactory {

    private FiniteStateMachine fsm;

    @Test
    public void testGetIdentifierFSM() {
//        try {
            fsm = LexerGrammarFactory.getIdentifierMachine();
//        } catch (StackOverflowError e) {
//            System.err.println("Stack overflow");
//            fail();
//        }
    }
}
