package automata;

import automata.finitestate.FollowedByFSM;
import automata.finitestate.UnionFSM;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LexerMachineTest {

    @Test
    public void concatenate() {

        LexerMachine union = new UnionFSM("ab");

        assertTrue(union.parse("a"));
        assertTrue(union.parse("b"));

        LexerMachine concat = new FollowedByFSM("c");

        assertTrue(concat.parse("c"));

        union.concatenate(concat);
        assertTrue(union.parse("ac"));
        assertTrue(union.parse("bc"));

    }

    @Test
    public void union() {
        LexerMachine union = new UnionFSM("ab");
        LexerMachine concat = new FollowedByFSM("c");

        union.union(concat);
        assertTrue(union.parse("a"));
        assertTrue(union.parse("b"));
        assertTrue(union.parse("c"));

    }

    @Test
    public void loop() {
        LexerMachine union = new UnionFSM("ab");

        union.loop();
        assertTrue(union.parse(""));
        assertTrue(union.parse("aaaa"));
        assertTrue(union.parse("abababa"));


    }

}