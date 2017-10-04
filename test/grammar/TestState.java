package grammar;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestState {

    private State s1, s2, s3, s4;

    @Before
    public void setUp() {

    }

    @Test
    public void testStateEquality() {

    }

    @Test
    public void testSingleStateSingleFinal() {
        s1 = new State(true);
        Set<State> expected = new HashSet<>();
        expected.add(s1);
        Set<State> actual = s1.getFinalStates();
        assertSetEquality(expected, actual);
    }

    @Test
    public void testMultipleStatesSingleFinal() {
        s1 = new State(false);
        s2 = new State(true);
        s1.addTransition('a', s2);
        Set<State> expected = new HashSet<>();
        expected.add(s2);
        Set<State> actual = s1.getFinalStates();
        assertSetEquality(expected, actual);
    }

    @Test
    public void testMultipleStatesMultipleFinal() {
        s1 = new State(false);
        s2 = new State(true);
        s3 = new State(true);
        s1.addTransition('a', s2);
        s1.addTransition('b', s3);
        Set<State> expected = new HashSet<>();
        expected.add(s2);
        expected.add(s3);
        Set<State> actual = s1.getFinalStates();
        assertSetEquality(expected, actual);
    }

    @Test
    public void testSingleStateWithLoop() {
        s1 = new State(true);
        s1.addTransition('a', s1);

        Set<State> expected = new HashSet<>();
        expected.add(s1);
        Set<State> actual = s1.getFinalStates();
        assertSetEquality(expected, actual);
    }

    @Test
    public void testMultipleStatesMultipleFinalDifferentDepths() {
        s1 = new State(false);
        s2 = new State(true);
        s3 = new State(false);
        s4 = new State(true);
        s1.addTransition('a', s2);
        s1.addTransition('b', s3);
        s3.addTransition('c', s4);
        Set<State> expected = new HashSet<>();
        expected.add(s2);
        expected.add(s4);
        Set<State> actual = s1.getFinalStates();
        assertSetEquality(expected, actual);
    }

    /*
     * COPY
     */

    @Test
    public void testCopyStateWithNoTransitions() {
        s1 = new State(false);
        s2 = s1.copy();
        assertTrue(s1.equals(s2));
        assertFalse(s1 == s2);
//        assertEquals(s1, s2);
    }

    @Test
    public void testCopyStateWithSingleTransition() {
        s1 = new State(false);
        s2 = new State(true);
        s1.addTransition('a', s2);
        s3 = s1.copy();
        assertTrue(s1.equals(s3));
        assertFalse(s1 == s3);
    }

    @Test
    public void testCopyStateWithMultipleTransitionToSingleTerminal() {
        s1 = new State(false);
        s2 = new State(true);
        s1.addTransition('a', s2);
        s1.addTransition('b', s2);

        s3 = s1.copy();
        assertTrue(s1.equals(s3));
        assertFalse(s1 == s3);
    }

    @Test
    public void testCopyStateWithMultipleTransitionToMultipleTerminal() {
        s1 = new State(false);
        s2 = new State(true);
        s3 = new State(true);
        s1.addTransition('a', s2);
        s1.addTransition('b', s3);

        s4 = s1.copy();
        assertTrue(s1.equals(s4));
        assertFalse(s1 == s4);
    }

    @Test
    public void testCopyStateWithSingleLoopTransition() {
        s1 = new State(true);
        s1.addTransition('a', s1);

        s2 = s1.copy();
        assertTrue(s1.equals(s2));
        assertFalse(s1 == s2);
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

    private <T>  boolean assertSetEquality(Set<T> expected, Set<T> actual) {
        if(expected.size() != actual.size()) {
            System.err.println("Expected result set of size " + expected.size() + " but was " + actual.size());
            return false;
        }

        for(T element : expected) {
            if(!actual.contains(element)) {
                System.err.println("Expected result set to contain " + element + " but it did not");
                return false;
            }
        }

        return true;
    }
}
