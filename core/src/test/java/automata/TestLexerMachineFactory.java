package automata;

import org.junit.Test;

public class TestLexerMachineFactory {

    private FiniteStateMachine fsm;

    @Test
    public void testGetIdentifierFSM() {
//        try {
            fsm = LexerMachineFactory.getIdentifierMachine();
//        } catch (StackOverflowError e) {
//            System.err.println("Stack overflow");
//            fail();
//        }
    }
}
