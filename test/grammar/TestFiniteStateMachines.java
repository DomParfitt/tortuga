package grammar;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestFiniteStateMachines {

    FiniteStateMachine fsm;
    List<String> characters;

    @Before
    public void setUp() {
        characters = Arrays.asList("a", "b", "c");
    }

    @Test
    public void testUnionFSM() {
        fsm = new UnionFSM(characters);
        System.out.println(fsm);
        assertTrue(fsm.parse("a"));
        assertTrue(fsm.parse("b"));
        assertTrue(fsm.parse("c"));
        assertFalse(fsm.parse("d"));
    }

    @Test
    public void testLoopingFSM() {
        fsm = new LoopingFSM("a");
        System.out.println(fsm);
        assertTrue(fsm.parse("aaaaa"));
        assertFalse(fsm.parse("aaadaa"));
    }

    @Test
    public void testFollowedByFSM() {
        fsm = new FollowedByFSM(characters);
        System.out.println(fsm);
        assertTrue(fsm.parse("abc"));
        assertFalse(fsm.parse("aabbbc"));
        assertFalse(fsm.parse("abcd"));

    }

    @Test
    public void testCompoundFSM() {
        FiniteStateMachine or = new UnionFSM(Arrays.asList("a", "b"));
        FiniteStateMachine after = new FollowedByFSM(Arrays.asList("c"));
        or.combine(after);
        assertTrue(or.parse("ac"));

        FiniteStateMachine fsm1 = new FollowedByFSM(Arrays.asList("a", "b"));
        FiniteStateMachine fsm2 = new FollowedByFSM(Arrays.asList("c", "d"));
        fsm1.combine(fsm2);
        assertTrue(fsm1.parse("abcd"));

        FiniteStateMachine fsm3 = new FollowedByFSM(Arrays.asList("a"));
        FiniteStateMachine fsm4 = new LoopingFSM("b");
        fsm3.combine(fsm4);
        assertTrue(fsm3.parse("abbbbbb"));

        FiniteStateMachine fsm5 = new UnionFSM(characters);
        FiniteStateMachine fsm6 = new LoopingFSM("");
        fsm5.combine(fsm6);
        assertTrue(fsm5.parse("aaabbabcc"));

    }

    @Test
    public void testCompoundFSM2() {
        FiniteStateMachine fsm1, fsm2, fsm3;
        fsm1 = new UnionFSM(Arrays.asList("a", "b", "c"));
        fsm2 = new LoopingFSM("");
        CompoundFSM compound = new CompoundFSM(fsm1);
        System.out.println(compound);
        compound.addFiniteStateMachine(fsm2);
        System.out.println(compound);
    }
}
