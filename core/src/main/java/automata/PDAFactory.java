package automata;

import lexer.Token;
import lexer.TokenType;

import java.util.*;

public class PDAFactory {

    private static PushdownAutomaton mathematicalStatement;

    public static PushdownAutomaton getMathematicalStatement() {
        if(PDAFactory.mathematicalStatement == null) {
            List<State> states = PDAFactory.getListOfStates(4, Arrays.asList(3));

            Set<Transition<Token>> transitions = new HashSet<>();
            transitions.add(new PDATransition(new Token(TokenType.DIGIT), new StackAction(StackAction.StackActionType.NONE), states.get(0), states.get(1)));
            transitions.add(new PDATransition(new Token(TokenType.PLUS), new StackAction(StackAction.StackActionType.NONE), states.get(1), states.get(2)));
            transitions.add(new PDATransition(new Token(TokenType.DIGIT), new StackAction(StackAction.StackActionType.NONE), states.get(2), states.get(3)));

//            PDAFactory.mathematicalStatement = new PushdownAutomaton(states, transitions);
            return new PushdownAutomaton(states, transitions);

        }
            return PDAFactory.mathematicalStatement;

    }

    private static List<State> getListOfStates(int numberOfStates, List<Integer> acceptingStates) {
        List<State> states = new ArrayList<>();
        for(int i = 0; i < numberOfStates; i++) {

            State state;

            if(acceptingStates.contains(i)) {
                state = new State(i, true);
            } else {
                state = new State(i, false);
            }

            if(i == 0) {
                state.setCurrentState(true);
            }

            states.add(state);
        }

        return states;
    }
}
