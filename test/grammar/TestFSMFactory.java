package grammar;

import org.junit.Test;

import static org.junit.Assert.fail;

public class TestFSMFactory {

    private FiniteStateMachine fsm;

    @Test
    public void testGetIdentifierFSM() {
        try {
            fsm = FSMFactory.getIdentifierFSM();
        } catch (StackOverflowError e) {
            System.err.println("Stack overflow");
            fail();
        }
    }
}
