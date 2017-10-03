package grammar;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.util.*;

import static org.junit.Assert.*;

public class TestFiniteStateMachines {

    FiniteStateMachine fsm, inner;
    String characters;

    @Before
    public void setUp() {
        characters = "abc";
    }

    @Test
    // a|b|c
    public void testUnionFSM() {
        fsm = new UnionFSM(characters);
        System.out.print(fsm);

        try {
            //Passes on any single character in provided set
            assertTrue(fsm.parse("a"));
            assertTrue(fsm.parse("b"));
            assertTrue(fsm.parse("c"));

            //Fails on single character not in provided set
            assertFalse(fsm.parse("d"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            //Fails on multi-character input
            assertFalse(fsm.parse("abc"));

            System.out.println(" - PASSING");
        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }
    }

    @Test
    // abc
    public void testFollowedByFSM() {
        fsm = new FollowedByFSM(characters);
        System.out.print(fsm);

        try {
            //Passes on character chain matching provided set
            assertTrue(fsm.parse("abc"));

            //Fails on single character from provided set
            assertFalse(fsm.parse("a"));

            //Fails on repeated characters in provided set
            assertFalse(fsm.parse("aabbbc"));

            //Fails on chain containing character not in provided set
            assertFalse(fsm.parse("abcd"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }
    }

    @Test
    // (a|b)*
    public void testLoopingUnionFSM() {
        inner = new UnionFSM("ab");
        fsm = new LoopingFSM(inner);
        System.out.print(fsm);

        try {
            //Passes on repeated characters from provided set
            assertTrue(fsm.parse("aaaaaaa"));

            //Passes on empty input
            assertTrue(fsm.parse(""));

            //Passes on single character from provided set
            assertTrue(fsm.parse("a"));

            //Passes on input containing all characters from provided set
            assertTrue(fsm.parse("aaababa"));

            //Fails on input containing character not from provided set
            assertFalse(fsm.parse("aaaac"));

            //Fails on single character not from provided set
            assertFalse(fsm.parse("c"));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }
    }

    @Test
    // (abc)*
    public void testLoopingFollowedByFSM() {
        inner = new FollowedByFSM(characters);
        fsm = new LoopingFSM(inner);
        System.out.print(fsm);

        try {
            //Passes on empty input
            assertTrue(fsm.parse(""));

            //Passes on single instance of chain
            assertTrue(fsm.parse("abc"));

            //Passes on multiple instances of chain
            assertTrue(fsm.parse("abcabc"));

            //Fails on partial chain
            assertFalse(fsm.parse("ab"));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }
    }

    @Test
    // ((a|b)c)*
    public void testLoopingCompoundUnionFollowedByFSM() {
        /*
         * ((a|b)c)*
         */

        fsm = new FollowedByFSM(new UnionFSM("ab"), new FollowedByFSM("c"));
        FiniteStateMachine loop = new LoopingFSM(fsm);
        System.out.print(fsm);
        System.out.print("((a|b)c)*");

        try {
            //Passes on empty input
            assertTrue(loop.parse(""));

            //Passes on single instance
            assertTrue(loop.parse("ac"));
            assertTrue(loop.parse("bc"));

            //Passes on multiple instances
            assertTrue(loop.parse("acac"));
            assertTrue(loop.parse("bcbc"));
            assertTrue(loop.parse("acbc"));

            //Fails on partial chain
            assertFalse(loop.parse("acb"));

            //Fails on chain with character not in provided set
            assertFalse(loop.parse("acd"));

            System.out.println(" - PASSING");
        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }

    }

    @Test
    // (a|b)c
    public void testCompoundUnionFollowedByFSM() {

        fsm = new FollowedByFSM(new UnionFSM("ab"), new FollowedByFSM("c"));
        System.out.print(fsm);
        System.out.print("(a|b)c");

        try {
            //Passes on expected chain
            assertTrue(fsm.parse("ac"));
            assertTrue(fsm.parse("bc"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            //Fails on partial chain
            assertFalse(fsm.parse("a"));

            //Fails on chain containing character not in provided set
            assertFalse(fsm.parse("ad"));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }
    }

    @Test
    public void testCompoundUnionLoopingFollowedByFSM() {
        /*
         * (a|b)c*
         */

        fsm = new FollowedByFSM(new UnionFSM("ab"), new LoopingFSM(new FollowedByFSM("c")));
        System.out.println(fsm);

        try {

            //Passes on a or b followed by no c
            assertTrue(fsm.parse("a"));
            assertTrue(fsm.parse("b"));

            //Passes on a or b followed by single c
            assertTrue(fsm.parse("ac"));
            assertTrue(fsm.parse("bc"));

            //Passes on a or b followed by multiple cs
            assertTrue(fsm.parse("accccc"));
            assertTrue(fsm.parse("bcc"));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }

    }

    @Test
    public void testCompoundUnionFollowedByUnion() {
        /*
         * (a|b)(c|d)
         */

        fsm = new FollowedByFSM(new UnionFSM("ab"), new UnionFSM("cd"));
        System.out.println(fsm);

        try {
            //Passes on single character from first union and single from second union
            assertTrue(fsm.parse("ac"));
            assertTrue(fsm.parse("ad"));
            assertTrue(fsm.parse("bc"));
            assertTrue(fsm.parse("bd"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            //Fails on single character from first union
            assertFalse(fsm.parse("a"));
            assertFalse(fsm.parse("b"));

            //Fails on single character from second union
            assertFalse(fsm.parse("c"));
            assertFalse(fsm.parse("d"));

            //Fails on legal chain with extra characters
            assertFalse(fsm.parse("ace"));

            System.out.println(" - PASSING");

        } catch (AssertionError e ) {
            System.out.println(" - FAILING");
            throw e;
        }
    }

    @Test
    public void testCompoundTwoFollowedByFSMs() {
        /*
         * (ab)(cd)
         */

        fsm = new FollowedByFSM(new FollowedByFSM("ab"), new FollowedByFSM("cd"));
        System.out.println(fsm);

        try {
            //Passes on legal chain
            assertTrue(fsm.parse("abcd"));

            //Fails on single character from provided set
            assertFalse(fsm.parse("a"));

            //Fails on repeated characters in provided set
            assertFalse(fsm.parse("aabbbc"));

            //Fails on chain containing character not in provided set
            assertFalse(fsm.parse("abcde"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            System.out.println(" - PASSING");

        } catch (AssertionError e ) {
            System.out.println(" - FAILING");
            throw e;
        }

    }


    @Test
    public void testCompoundUnionUnionFollowedBy() {
        /*
         * a|(bc)
         */
        fsm = new UnionFSM(new UnionFSM("a"), new FollowedByFSM("bc"));
        System.out.print(fsm);

        try {
            //Passes on either of expected inputs
            assertTrue(fsm.parse("a"));
            assertTrue(fsm.parse("bc"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            //Fails on invalid chain
            assertFalse(fsm.parse("abc"));

            System.out.println(" - PASSING");

        } catch (AssertionError e ) {
            System.out.println(" - FAILING");
            throw e;
        }

    }

    /*
     * TODO: Need tests for:
     *  - a(b|c) - Compounding followed by with union
     *  - a|(bc) - Compounding followed by inside union
     */

    @Test
    public void testGetFinalStates() {
        fsm = new UnionFSM("abc");
        LoopingFSM loop = new LoopingFSM(fsm);

        Set<State> expected = new HashSet<>();
        expected.add(loop.initialState);
        expected.add(loop.initialState.getResultingState('a'));

        Set<State> actual = loop.getFinalStates();

        assertEquals(expected.size(), actual.size());
        for(State state : actual) {
            assertTrue(expected.contains(state));
        }

    }


    public void testTemplate() {
        /*
         * FSM Test Case
         */
        System.out.print("Finite State Machine");

        try {
            //Tests go here

            System.out.println(" - PASSING");

        } catch (AssertionError e ) {
            System.out.println(" - FAILING");
            throw e;
        }

    }

}
