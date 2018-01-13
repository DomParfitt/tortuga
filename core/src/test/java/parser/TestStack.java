package parser;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestStack {

    private Stack<String> stack;

    @Before
    public void setUp() {
        this.stack = new Stack<>();
    }

    @Test
    public void testPushAndPopSingle() {
        this.stack.push("Test");
        assertEquals("Test", this.stack.pop());
    }

    @Test
    public void testPushMultiplePopSingle() {
        this.stack.push("Test1");
        this.stack.push("Test2");
        this.stack.push("Test3");
        assertEquals("Test3", this.stack.pop());
    }

    @Test
    public void testPopEmptyStack() {
        try {
            this.stack.pop();
            fail();
        } catch (StackUnderflowError e) {

        }
    }

    @Test
    public void testPeek() {
        this.stack.push("Test");
        assertEquals("Test", this.stack.peek());
        assertEquals("Test", this.stack.pop());
    }
}
