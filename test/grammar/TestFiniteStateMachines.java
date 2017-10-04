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

    /*
     * BASICS
     */

    @Test
    // a|b|c
    public void testUnion() {
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
    public void testFollowedBy() {
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
    // a*
    public void testLooping() {
        fsm = new LoopingFSM(new UnionFSM("a"));

        System.out.print(fsm);

        try {
            //Passes on empty input
            assertTrue(fsm.parse(""));

            //Passes on single valid character
            assertTrue(fsm.parse("a"));

            //Passes on multiple valid characters
            assertTrue(fsm.parse("aaaa"));

            //Fails on invalid character
            assertFalse(fsm.parse("b"));

            //Fails on valid plus invalid characters
            assertFalse(fsm.parse("aab"));

            System.out.println(" - PASSING");

        } catch (AssertionError e ) {
            System.out.println(" - FAILING");
            throw e;
        }


    }

    /*
     * LOOPING ON BASICS
     */

    @Test
    // (a|b)*
    public void testLoopingUnion() {
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
    public void testLoopingFollowedBy() {
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

    /*
     * SIMPLE COMPOUNDS
     *
     * TODO:
     *  - Single FollowedBy FollowedBy -> a(bc) ( == abc)
     *  - FollowedBy FollowedBy Single -> (ab)c ( == abc)
     *  - Single FollowedBy Union -> a(b|c) - DONE
     *  - Union FollowedBy Single -> (a|b)c - DONE
     *  - Single Union FollowedBy -> a|(bc) - DONE
     *  - FollowedBy Union Single -> (ab)|c
     *  - Single Union Union -> a|(b|c) ( == a|b|c)
     *  - Union Union Single -> (a|b)|c ( == a|b|c)
     *
     */

    @Test
    // (a|b)c
    public void testUnionFollowedBySingle() {

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
    // a(b|c)
    public void testSingleFollowedByUnion() {

        fsm = new FollowedByFSM(new FollowedByFSM("a"), new UnionFSM("bc"));
        System.out.print(fsm);

        try {
            //Passes on valid chain
            assertTrue(fsm.parse("ab"));
            assertTrue(fsm.parse("ac"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            //Fails on partial chain
            assertFalse(fsm.parse("a"));
            assertFalse(fsm.parse("b"));

            //Fails on invalid character
            assertFalse(fsm.parse("d"));

            //Fails on valid chain plus invalid character
            assertFalse(fsm.parse("abd"));

            System.out.println(" - PASSING");

        } catch (AssertionError e ) {
            System.out.println(" - FAILING");
            throw e;
        }

    }

    @Test
    // a|(bc)
    public void testSingleUnionFollowedBy() {

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
     * TRUE COMPOUNDS
     *
     * TODO:
     *  - FollowedBy FollowedBy FollowedBy -> (ab)(cd) ( == abcd) - DONE
     *  - FollowedBy FollowedBy Union -> (ab)(c|d)
     *  - Union FollowedBy FollowedBy -> (a|b)(cd)
     *  - Union FollowedBy Union -> (a|b)(c|d) - DONE
     *  - FollowedBy Union FollowedBy -> (ab)|(cd)
     *  - Union Union Union -> (a|b)|(c|d) ( == a|b|c|d) - DONE
     *  - FollowedBy Union Union -> (ab)|(c|d)
     *  - Union Union FollowedBy -> (a|b)|(cd)
     *
     */

    @Test
    // (a|b)(c|d)
    public void testUnionFollowedByUnion() {

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
    // (ab)(cd)
    public void testFollowedByFollowedByFollowedBy() {

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
    // (a|b)|(c|d)
    public void testUnionUnionUnion() {

        fsm = new UnionFSM(new UnionFSM("ab"), new UnionFSM("cd"));
        System.out.println(fsm);

        try {
            //Passes on single valid character
            assertTrue(fsm.parse("a"));
            assertTrue(fsm.parse("b"));
            assertTrue(fsm.parse("c"));
            assertTrue(fsm.parse("d"));
            
            //Fails on invalid chain
            assertFalse(fsm.parse("ac"));
            assertFalse(fsm.parse("ad"));
            assertFalse(fsm.parse("bc"));
            assertFalse(fsm.parse("bd"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            //Fails on single invalid character
            assertFalse(fsm.parse("e"));

            //Fails on valid chain plus invalid character
            assertFalse(fsm.parse("acd"));

            System.out.println(" - PASSING");

        } catch (AssertionError e ) {
            System.out.println(" - FAILING");
            throw e;
        }
    }


    /*
     * LOOPING ON COMPOUNDS
     *
     * TODO:
     *  - FollowedBy FollowedBy FollowedBy -> ((ab)(cd))* ( == (abcd)*)
     *  - FollowedBy FollowedBy Union -> ((ab)(c|d))*
     *  - Union FollowedBy FollowedBy -> ((a|b)(cd))*
     *  - Union FollowedBy Union -> ((a|b)(c|d))*
     *  - FollowedBy Union FollowedBy -> ((ab)|(cd))*
     *  - Union Union Union -> ((a|b)|(c|d))* ( == (a|b|c|d)*)
     *  - FollowedBy Union Union -> ((ab)|(c|d))*
     *  - Union Union FollowedBy -> ((a|b)|(cd))*
     */

    @Test
    // ((a|b)c)*
    public void testLoopingOnUnionFollowedByFollowedBy() {

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
    // ((a|b)|(c|d))*
    public void testLoopingOnUnionUnionUnion() {
        FiniteStateMachine union = new UnionFSM(new UnionFSM("ab"), new UnionFSM("cd"));
        fsm = new LoopingFSM(union);
        System.out.print(fsm);

        try {
            //Passes on empty input
            assertTrue(fsm.parse(""));

            //Passes on single valid token
            assertTrue(fsm.parse("a"));
            assertTrue(fsm.parse("b"));
            assertTrue(fsm.parse("c"));
            assertTrue(fsm.parse("d"));

            //Passes on multiple valid tokens
            assertTrue(fsm.parse("ab"));
            assertTrue(fsm.parse("aba"));
            assertTrue(fsm.parse("bcd"));
            assertTrue(fsm.parse("abcd"));

            //Passes on repeating valid tokens
            assertTrue(fsm.parse("aaaaaa"));

            //Fails on single invalid token
            assertFalse(fsm.parse("e"));

            //Fails on multiple invalid tokens
            assertFalse(fsm.parse("ef"));

            //Fails on repeating invalid tokens
            assertFalse(fsm.parse("eee"));

            //Fails on invalid token with valid tokens
            assertFalse(fsm.parse("abcde"));

            System.out.println(" - PASSING");

        } catch (AssertionError e ) {
            System.out.println(" - FAILING");
            throw e;
        }
    }

    /*
     * COMPOUNDS CONTAINING LOOPS
     */

    @Test
    // (a|b)c*
    public void testUnionFollowedByLoopingFollowedBy() {

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

    /*
     * ADDITIONAL TESTS
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

    // FSM Test Case
    public void testTemplate() {

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
