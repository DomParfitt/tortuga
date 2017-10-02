package grammar;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class TestState {

    private State s1, s2, s3, s4;

    @Before
    public void setUp() {

    }

    @Test
    public void testStateEquality() {

    }

    @Test
    public void testStatePrinting() {
        FiniteStateMachine fsm = new UnionFSM("abc");
        System.out.println(fsm.initialState);
        fsm = new FollowedByFSM("abc");
        System.out.println(fsm.initialState);
//        fsm = new LoopingFSM("a");
//        System.out.println(fsm.initialState);
    }
}
