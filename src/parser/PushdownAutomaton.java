package parser;

import grammar.FiniteStateMachine;
import lexer.Token;

public class PushdownAutomaton extends FiniteStateMachine {

    protected Stack<Token> stack;

    @Override
    public FiniteStateMachine copy() {
        return null;
    }
}
