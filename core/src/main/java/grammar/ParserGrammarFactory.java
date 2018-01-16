package grammar;

import automata.pushdown.None;
import automata.pushdown.ParserMachine;
import automata.State;
import automata.actions.Transition;

import java.util.*;

public class ParserGrammarFactory {

    public static ParserMachine getMachine(ParserGrammar grammar) {
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

    public static ParserMachine getIntExpression() {
        //TODO: Needs to be recursive
        ParserMachine intLit = new None(LexerGrammar.INT_LITERAL);
        ParserMachine intExpr = intLit.concatenate(getMathematicalOperator()).concatenate(intLit);
        intExpr = intExpr.union(intLit);
        return intExpr;
    }

    public static ParserMachine getMathematicalOperator() {
        ParserMachine add = new None(LexerGrammar.PLUS);
        ParserMachine minus = new None(LexerGrammar.MINUS);
        ParserMachine times = new None(LexerGrammar.MULTIPLY);
        ParserMachine divide = new None(LexerGrammar.DIVIDE);

        ParserMachine mathOp = add.union(minus).union(times).union(divide);

        return mathOp;
    }

    public static ParserMachine getMathematicalExpression() {

        return null;

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
