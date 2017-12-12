package automata;

import org.junit.Test;

public class TestFSMFactory {

    private FiniteStateMachine fsm;

    @Test
    public void testGetIdentifierFSM() {
//        try {
            fsm = FSMFactory.getIdentifierFSM();
//        } catch (StackOverflowError e) {
//            System.err.println("Stack overflow");
//            fail();
//        }
    }
}
