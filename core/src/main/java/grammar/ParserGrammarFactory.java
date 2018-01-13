package grammar;

import automata.State;
import automata.actions.StackAction;
import automata.actions.Transition;
import automata.pushdown.None;
import automata.pushdown.PushdownAutomaton;

import java.util.*;

public class ParserGrammarFactory {

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

    public static PushdownAutomaton getIntExpression() {
//        List<State> states = ParserGrammarFactory.getListOfStates(2, 1);
//        Set<Transition<LexerGrammar>> transitions = new HashSet<>();
//        transitions.add(new PDATransition(LexerGrammar.INT_LITERAL, new StackAction(StackAction.StackActionType.NONE), states.get(0), states.get(1)));
//
//        return new PushdownAutomaton(states, transitions);

        PushdownAutomaton intExpression = new None(LexerGrammar.INT_LITERAL);
        return intExpression;
    }

    private static PushdownAutomaton getMathematicalOperator() {
        return new None(LexerGrammar.PLUS).union(new None(LexerGrammar.MINUS)).union(new None(LexerGrammar.MULTIPLY)).union(new None(LexerGrammar.DIVIDE));
    }

    public static PushdownAutomaton getMathematicalExpression() {
        List<State> states = ParserGrammarFactory.getListOfStates(4, 3);

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

//        PushdownAutomaton mathExpr = new StackPush(LexerGrammar.OPEN_PAREN).loop().concatenate(getIntExpression()).concatenate(getMathematicalOperator()).concatenate(getIntExpression()).concatenate(new Pop(LexerGrammar.CLOSE_PAREN, LexerGrammar.OPEN_PAREN));
//        return mathExpr;

    }

    private static List<State> getListOfStates(int numberOfStates, int acceptingState) {
        return ParserGrammarFactory.getListOfStates(numberOfStates, Arrays.asList(acceptingState));
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
