package automata;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestFiniteStateMachines {

    LexerMachine fsm, inner;
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
        System.out.print("a|b|c");

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
        System.out.print("abc");

        try {
            //Passes on character chain matching provided set
            assertTrue(fsm.parse("abc"));

            //Fails on single character from provided set
            assertFalse(fsm.parse("a"));

            //Fails on repeated tokens in provided set
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
//        fsm = new LoopingFSM(new UnionFSM("a"));
        fsm = new UnionFSM("a");
        fsm.loop();
        System.out.print("a*");

        try {
            //Passes on empty input
            assertTrue(fsm.parse(""));

            //Passes on single valid character
            assertTrue(fsm.parse("a"));

            //Passes on multiple valid tokens
            assertTrue(fsm.parse("aaaa"));

            //Fails on invalid character
            assertFalse(fsm.parse("b"));

            //Fails on valid plus invalid tokens
            assertFalse(fsm.parse("aab"));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
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
        fsm = new UnionFSM("ab");
        fsm.loop();
//        fsm = new LoopingFSM(inner);
        System.out.print("(a|b)*");

        try {
            //Passes on repeated tokens from provided set
            assertTrue(fsm.parse("aaaaaaa"));

            //Passes on empty input
            assertTrue(fsm.parse(""));

            //Passes on single character from provided set
            assertTrue(fsm.parse("a"));

            //Passes on input containing all tokens from provided set
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
        fsm = new FollowedByFSM(characters);
        fsm.loop();
//        fsm = new LoopingFSM(inner);
        System.out.print("(abc)*");

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
     *  - Single FollowedBy FollowedBy -> a(bc) ( == abc) - DONE
     *  - FollowedBy FollowedBy Single -> (ab)c ( == abc) - DONE
     *  - Single FollowedBy Union -> a(b|c) - DONE
     *  - Union FollowedBy Single -> (a|b)c - DONE
     *  - Single Union FollowedBy -> a|(bc) - DONE
     *  - FollowedBy Union Single -> (ab)|c - DONE
     *  - Single Union Union -> a|(b|c) ( == a|b|c) - DONE
     *  - Union Union Single -> (a|b)|c ( == a|b|c) - DONE
     *
     */

    @Test
    // a(bc)
    public void testSingleFollowedByFollowedBy() {

        fsm = new FollowedByFSM("a");//new FollowedByFSM(new FollowedByFSM("a"), new FollowedByFSM("bc"));
        fsm.concatenate(new FollowedByFSM("bc"));
        System.out.print("a(bc)");

        try {
            //Passes on valid chain
            assertTrue(fsm.parse("abc"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            //Fails on single character from chain
            assertFalse(fsm.parse("a"));

            //Fails on single character not from chain
            assertFalse(fsm.parse("d"));

            //Fails on chain containing invalid character
            assertFalse(fsm.parse("abcd"));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }
    }

    @Test
    // (ab)c
    public void testFollowedByFollowedBySingle() {

        fsm = new FollowedByFSM("ab");//new FollowedByFSM(new FollowedByFSM("ab"), new FollowedByFSM("c"));
        fsm.concatenate(new FollowedByFSM("c"));
        System.out.print("(ab)c)");

        try {
            //Passes on valid chain
            assertTrue(fsm.parse("abc"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            //Fails on single character from chain
            assertFalse(fsm.parse("a"));

            //Fails on single character not from chain
            assertFalse(fsm.parse("d"));

            //Fails on chain containing invalid character
            assertFalse(fsm.parse("abcd"));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }
    }

    @Test
    // (a|b)c
    public void testUnionFollowedBySingle() {

        fsm = new UnionFSM("ab");//new FollowedByFSM(new UnionFSM("ab"), new FollowedByFSM("c"));
        fsm.concatenate(new FollowedByFSM("c"));
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

        fsm = new FollowedByFSM("a");//new FollowedByFSM(new FollowedByFSM("a"), new UnionFSM("bc"));
        fsm.concatenate(new UnionFSM("bc"));
        System.out.print("a(b|c)");

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

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }

    }

    @Test
    // a|(bc)
    public void testSingleUnionFollowedBy() {

        fsm = new UnionFSM("a");//new UnionFSM(new UnionFSM("a"), new FollowedByFSM("bc"));
        fsm.union(new FollowedByFSM("bc"));
        System.out.print("a|(bc)");

        try {
            //Passes on either of expected inputs
            assertTrue(fsm.parse("a"));
            assertTrue(fsm.parse("bc"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            //Fails on invalid chain
            assertFalse(fsm.parse("abc"));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }

    }

    @Test
    // (ab)|c
    public void testFollowedByUnionSingle() {
        fsm = new FollowedByFSM("ab");//new UnionFSM(new FollowedByFSM("ab"), new FollowedByFSM("c"));
        fsm.union(new FollowedByFSM("c"));
        System.out.print("a|(bc)");

        try {
            //Passes on either of expected inputs
            assertTrue(fsm.parse("ab"));
            assertTrue(fsm.parse("c"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            //Fails on invalid chain
            assertFalse(fsm.parse("abc"));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }

    }

    @Test
    // a|(b|c)
    public void testSingleUnionUnion() {

        fsm = new UnionFSM("a");//new UnionFSM(new UnionFSM("a"), new UnionFSM("bc"));
        fsm.union(new UnionFSM("bc"));
        System.out.print("a|(b|c)");

        try {
            //Passes on any of valid input tokens
            assertTrue(fsm.parse("a"));
            assertTrue(fsm.parse("b"));
            assertTrue(fsm.parse("c"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            //Fails on multiple valid tokens
            assertFalse(fsm.parse("ab"));

            //Fails on invalid character
            assertFalse(fsm.parse("d"));


            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }

    }

    @Test
    // (a|b)|c
    public void testUnionUnionSingle() {

        fsm = new UnionFSM("ab");//new UnionFSM(new UnionFSM("ab"), new UnionFSM("c"));
        fsm.union(new UnionFSM("c"));
        System.out.print("(a|b)|c");

        try {
            //Passes on any of valid input tokens
            assertTrue(fsm.parse("a"));
            assertTrue(fsm.parse("b"));
            assertTrue(fsm.parse("c"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            //Fails on multiple valid tokens
            assertFalse(fsm.parse("ab"));

            //Fails on invalid character
            assertFalse(fsm.parse("d"));


            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }

    }

    /*
     * TRUE COMPOUNDS
     *
     * TODO:
     *  - FollowedBy FollowedBy FollowedBy -> (ab)(cd) ( == abcd) - DONE
     *  - FollowedBy FollowedBy Union -> (ab)(c|d) - DONE
     *  - Union FollowedBy FollowedBy -> (a|b)(cd) - DONE
     *  - Union FollowedBy Union -> (a|b)(c|d) - DONE
     *  - FollowedBy Union FollowedBy -> (ab)|(cd) - DONE
     *  - Union Union Union -> (a|b)|(c|d) ( == a|b|c|d) - DONE
     *  - FollowedBy Union Union -> (ab)|(c|d) - DONE
     *  - Union Union FollowedBy -> (a|b)|(cd) - DONE
     *
     */

    @Test
// (ab)(cd)
    public void testFollowedByFollowedByFollowedBy() {

        fsm = new FollowedByFSM("ab");//new FollowedByFSM(new FollowedByFSM("ab"), new FollowedByFSM("cd"));
        fsm.concatenate(new FollowedByFSM("cd"));
        System.out.print("(ab)(cd)");

        try {
            //Passes on legal chain
            assertTrue(fsm.parse("abcd"));

            //Fails on single character from provided set
            assertFalse(fsm.parse("a"));

            //Fails on repeated tokens in provided set
            assertFalse(fsm.parse("aabbbc"));

            //Fails on chain containing character not in provided set
            assertFalse(fsm.parse("abcde"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }
    }

    @Test
    // (ab)(c|d)
    public void testFollowedByFollowedByUnion() {

        fsm = new FollowedByFSM("ab");//new FollowedByFSM(new FollowedByFSM("ab"), new UnionFSM("cd"));
        fsm.concatenate(new UnionFSM("cd"));
        System.out.print("(ab)(c|d)");

        try {
            //Passes on valid input
            assertTrue(fsm.parse("abc"));
            assertTrue(fsm.parse("abd"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            //Fails on partial chain
            assertFalse(fsm.parse("ab"));
            assertFalse(fsm.parse("c"));

            //Fails on invalid character
            assertFalse(fsm.parse("e"));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }
    }

    @Test
    // (a|b)(cd)
    public void UnionFollowedByFollowedBy() {
        fsm = new UnionFSM("ab");//new FollowedByFSM(new UnionFSM("ab"), new FollowedByFSM("cd"));
        fsm.concatenate(new FollowedByFSM("cd"));
        System.out.print("(a|b)(cd)");

        try {
            //Passes on valid input
            assertTrue(fsm.parse("acd"));
            assertTrue(fsm.parse("bcd"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            //Fails on partial chain
            assertFalse(fsm.parse("ab"));
            assertFalse(fsm.parse("c"));

            //Fails on invalid character
            assertFalse(fsm.parse("e"));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }
    }

    @Test
// (a|b)(c|d)
    public void testUnionFollowedByUnion() {

        fsm = new UnionFSM("ab");//new FollowedByFSM(new UnionFSM("ab"), new UnionFSM("cd"));
        fsm.concatenate(new UnionFSM("cd"));
        System.out.print("(a|b)(c|d)");

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

            //Fails on legal chain with extra tokens
            assertFalse(fsm.parse("ace"));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }
    }

    @Test
    // (ab)|(cd)
    public void testFollowedByUnionFollowedBy() {

        fsm = new FollowedByFSM("ab");//new UnionFSM(new FollowedByFSM("ab"), new FollowedByFSM("cd"));
        fsm.union(new FollowedByFSM("cd"));
        System.out.print("(ab)|(cd)");

        try {
            //Passes on either valid chain
            assertTrue(fsm.parse("ab"));
            assertTrue(fsm.parse("cd"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            //Fails on both
            assertFalse(fsm.parse("abcd"));

            //Fails on invalid character
            assertFalse(fsm.parse("e"));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }
    }

    @Test
// (a|b)|(c|d)
    public void testUnionUnionUnion() {

        fsm = new UnionFSM("ab");//new UnionFSM(new UnionFSM("ab"), new UnionFSM("cd"));
        fsm.union(new UnionFSM("cd"));
        System.out.print("(a|b)|(c|d)");

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

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }
    }

    @Test
    //(ab)|(c|d)
    public void testFollowedByUnionUnion() {

        fsm = new FollowedByFSM("ab");//new UnionFSM(new FollowedByFSM("ab"), new UnionFSM("cd"));
        fsm.union(new UnionFSM("cd"));
        System.out.print("(ab)|(c|d)");

        try {
            //Passes on any valid chain
            assertTrue(fsm.parse("ab"));
            assertTrue(fsm.parse("c"));
            assertTrue(fsm.parse("d"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            //Fails on multiple valid chains
            assertFalse(fsm.parse("abcd"));

            //Fails on invalid character
            assertFalse(fsm.parse("e"));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }
    }

    @Test
    // (a|b)|(cd)
    public void testUnionUnionFollowedBy() {
        fsm = new UnionFSM("ab");//new UnionFSM(new UnionFSM("ab"), new FollowedByFSM("cd"));
        fsm.union(new FollowedByFSM("cd"));
        System.out.print("(a|b)|(cd)");

        try {
            //Passes on any valid chain
            assertTrue(fsm.parse("a"));
            assertTrue(fsm.parse("b"));
            assertTrue(fsm.parse("cd"));

            //Fails on empty input
            assertFalse(fsm.parse(""));

            //Fails on multiple valid chains
            assertFalse(fsm.parse("abcd"));

            //Fails on invalid character
            assertFalse(fsm.parse("e"));

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
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
     *  - Union Union Union -> ((a|b)|(c|d))* ( == (a|b|c|d)*) - DONE
     *  - FollowedBy Union Union -> ((ab)|(c|d))*
     *  - Union Union FollowedBy -> ((a|b)|(cd))*
     */

    @Test
// ((a|b)c)*
    public void testLoopingOnUnionFollowedByFollowedBy() {

        fsm = new UnionFSM("ab");//new FollowedByFSM(new UnionFSM("ab"), new FollowedByFSM("c"));
        fsm.concatenate(new FollowedByFSM("c"));
        fsm.loop();
        System.out.print("((a|b)c)*");

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
// ((a|b)|(c|d))*
    public void testLoopingOnUnionUnionUnion() {
//        LexerMachine union = new UnionFSM(new UnionFSM("ab"), new UnionFSM("cd"));
        fsm = new UnionFSM("ab");
        fsm.union(new UnionFSM("cd"));
        fsm.loop();
        System.out.print("((a|b)|(c|d))*");

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

        } catch (AssertionError e) {
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

        fsm = new UnionFSM("ab");//new FollowedByFSM(new UnionFSM("ab"), new LoopingFSM(new FollowedByFSM("c")));
        LexerMachine inner = new FollowedByFSM("c");
        inner.loop();
        fsm.union(inner);
        System.out.print("(a|b)c*");
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

    // FSM Test Case
    public void testTemplate() {

        System.out.print("Finite State Machine");

        try {
            //Tests go here

            System.out.println(" - PASSING");

        } catch (AssertionError e) {
            System.out.println(" - FAILING");
            throw e;
        }

    }

}
