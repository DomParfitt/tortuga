package automata;

import grammar.LexerGrammar;
import grammar.ParserGrammar;

import java.util.*;

public class PDAFactory {

    public static PushdownAutomaton getMachine(ParserGrammar grammar) {
        switch (grammar) {

            case BOOLEAN_EXPRESSION:
                break;
            case INT_EXPRESSION:
                break;
            case FLOAT_EXPRESSION:
                break;
            case MATH_EXPRESSION:
                return getMathematicalExpression();
            case ASSIGNMENT_STATEMENT:
                break;
            case IF_STATEMENT:
                break;
            case FOR_STATEMENT:
                break;
            case WHILE_STATEMENT:
                break;
        }

        return null;

    }

    public PushdownAutomaton getIntExpression() {
        List<State> states = PDAFactory.getListOfStates(2, 1);
        Set<Transition<LexerGrammar>> transitions = new HashSet<>();
        transitions.add(new PDATransition(LexerGrammar.INT_LITERAL, new StackAction(StackAction.StackActionType.NONE), states.get(0), states.get(1)));

        return new PushdownAutomaton(states, transitions);
    }

    public static PushdownAutomaton getMathematicalExpression() {
        List<State> states = PDAFactory.getListOfStates(4, 3);

        Set<Transition<LexerGrammar>> transitions = new HashSet<>();
        transitions.add(new PDATransition(LexerGrammar.OPEN_PAREN, new StackAction(StackAction.StackActionType.PUSH), states.get(0), states.get(0)));
        transitions.add(new PDATransition(LexerGrammar.INT_LITERAL, new StackAction(StackAction.StackActionType.NONE), states.get(0), states.get(1)));
        transitions.add(new PDATransition(LexerGrammar.PLUS, new StackAction(StackAction.StackActionType.NONE), states.get(1), states.get(2)));
        transitions.add(new PDATransition(LexerGrammar.MINUS, new StackAction(StackAction.StackActionType.NONE), states.get(1), states.get(2)));
        transitions.add(new PDATransition(LexerGrammar.DIVIDE, new StackAction(StackAction.StackActionType.NONE), states.get(1), states.get(2)));
        transitions.add(new PDATransition(LexerGrammar.MULTIPLY, new StackAction(StackAction.StackActionType.NONE), states.get(1), states.get(2)));
        transitions.add(new PDATransition(LexerGrammar.CLOSE_PAREN, new StackAction(StackAction.StackActionType.POP, LexerGrammar.OPEN_PAREN), states.get(1), states.get(1)));
        transitions.add(new PDATransition(LexerGrammar.INT_LITERAL, new StackAction(StackAction.StackActionType.NONE), states.get(2), states.get(3)));
        transitions.add(new PDATransition(LexerGrammar.OPEN_PAREN, new StackAction(StackAction.StackActionType.PUSH), states.get(2), states.get(0)));
        transitions.add(new PDATransition(LexerGrammar.CLOSE_PAREN, new StackAction(StackAction.StackActionType.POP, LexerGrammar.OPEN_PAREN), states.get(3), states.get(3)));

        return new PushdownAutomaton(states, transitions);


    }

    private static List<State> getListOfStates(int numberOfStates, int acceptingState) {
        return PDAFactory.getListOfStates(numberOfStates, Arrays.asList(acceptingState));
    }

    private static List<State> getListOfStates(int numberOfStates, List<Integer> acceptingStates) {
        List<State> states = new ArrayList<>();
        for (int i = 0; i < numberOfStates; i++) {

            State state;

            if (acceptingStates.contains(i)) {
                state = new State(i, true);
            } else {
                state = new State(i, false);
            }

            if (i == 0) {
                state.setCurrentState(true);
            }

            states.add(state);
        }

        return states;
    }
}
