package automata;

import automata.actions.StackAction;
import automata.finitestate.FollowedByFSM;
import automata.finitestate.UnionFSM;
import automata.pushdown.PushdownAutomaton;
import grammar.LexerGrammar;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void loopPDA() {
        PushdownAutomaton pda = new PushdownAutomaton();
        State state1 = new State(0, false);
        state1.setCurrentState(true);
        State state2 = new State(1, true);
        pda.addState(state1);
        pda.addState(state2);
        PDATransition transition = new PDATransition(LexerGrammar.MINUS, new StackAction(StackAction.StackActionType.NONE), state1, state2);
        pda.addTransition(transition);

        assertTrue(pda.parse(LexerGrammar.MINUS));

        pda.loop();
        List<LexerGrammar> tokens = new ArrayList<>();
        tokens.add(LexerGrammar.MINUS);
        tokens.add(LexerGrammar.MINUS);
        tokens.add(LexerGrammar.MINUS);

        assertTrue(pda.parse(tokens));

    }
}