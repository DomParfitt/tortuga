package grammar;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
    public void testLoopingCompoundFSM() {
        /*
         * ((a|b)c)*
         */
        CompoundFSM compound = new CompoundFSM(new UnionFSM("ab"));
        compound.addFiniteStateMachine(new FollowedByFSM("c"));
//        System.out.println("Compound: " + compound);
        fsm = new LoopingFSM(compound);
//        System.out.println("Looping: " + fsm);
        System.out.print(fsm);

        try {
            //Passes on empty input
            assertTrue(fsm.parse(""));

            //Passes on single instance
            assertTrue(fsm.parse("ac"));
            assertTrue(fsm.parse("bc"));

            //Passes on multiple instances
            assertTrue(fsm.parse("acac"));
            assertTrue(fsm.parse("bcbc"));
            assertTrue(fsm.parse("acbc"));

            //Fails on partial chain
            assertFalse(fsm.parse("acb"));

            //Fails on chain with character not in provided set
            assertFalse(fsm.parse("acd"));

            System.out.println(" - PASSING");
        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }

    }

    @Test
    public void testCompoundUnionFollowedByFSM() {
        CompoundFSM compound = new CompoundFSM(new UnionFSM("ab"));
        compound.addFiniteStateMachine(new FollowedByFSM("c"));
        System.out.print(compound);

        try {
            //Passes on expected chain
            assertTrue(compound.parse("ac"));
            assertTrue(compound.parse("bc"));

            //Fails on empty input
            assertFalse(compound.parse(""));

            //Fails on partial chain
            assertFalse(compound.parse("a"));

            //Fails on chain containing character not in provided set
            assertFalse(compound.parse("ad"));

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

        CompoundFSM compound = new CompoundFSM(new UnionFSM("ab"));
        inner = new FollowedByFSM("c");
        FiniteStateMachine looping = new LoopingFSM(inner);
        compound.addFiniteStateMachine(looping);
        System.out.print(compound);

        try {

            //Passes on a or b followed by no c
            assertTrue(compound.parse("a"));
            assertTrue(compound.parse("b"));

            //Passes on a or b followed by single c
            assertTrue(compound.parse("ac"));
            assertTrue(compound.parse("bc"));

            //Passes on a or b followed by multiple cs
            assertTrue(compound.parse("accccc"));
            assertTrue(compound.parse("bcc"));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }

    }

    @Test
    public void testCompoundTwoUnionFSM() {
        /*
         * (a|b)(c|d)
         */
        CompoundFSM compound = new CompoundFSM(new UnionFSM("ab"));
        compound.addFiniteStateMachine(new UnionFSM("cd"));
        System.out.print(compound);

        try {
            //Passes on single character from first union and single from second union
            assertTrue(compound.parse("ac"));
            assertTrue(compound.parse("ad"));
            assertTrue(compound.parse("bc"));
            assertTrue(compound.parse("bd"));

            //Fails on empty input
            assertFalse(compound.parse(""));

            //Fails on single character from first union
            assertFalse(compound.parse("a"));
            assertFalse(compound.parse("b"));

            //Fails on single character from second union
            assertFalse(compound.parse("c"));
            assertFalse(compound.parse("d"));

            //Fails on legal chain with extra characters
            assertFalse(compound.parse("ace"));

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
        CompoundFSM compound = new CompoundFSM(new FollowedByFSM("ab"));
        compound.addFiniteStateMachine(new FollowedByFSM("cd"));
        System.out.print(compound);

        try {
            //Passes on legal chain
            assertTrue(compound.parse("abcd"));

            //Fails on single character from provided set
            assertFalse(compound.parse("a"));

            //Fails on repeated characters in provided set
            assertFalse(compound.parse("aabbbc"));

            //Fails on chain containing character not in provided set
            assertFalse(compound.parse("abcde"));

            //Fails on empty input
            assertFalse(compound.parse(""));

            System.out.println(" - PASSING");

        } catch (AssertionError e ) {
            System.out.println(" - FAILING");
            throw e;
        }

    }


    @Test
    public void testUnionCompoundFSM() {
        /*
         * a|(bc)
         */
        FiniteStateMachine fsm1,fsm2;
        fsm1 = new UnionFSM("a");
        fsm2 = new FollowedByFSM("bc");
        fsm = new UnionFSM(fsm1, fsm2);
        System.out.print(fsm);

        try {
            //Tests go here
            fail();
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
