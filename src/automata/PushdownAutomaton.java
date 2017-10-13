package automata;

import automata.FiniteStateMachine;
import lexer.Token;
import parser.Stack;

public class PushdownAutomaton extends FiniteStateMachine {

    protected Stack<Token> stack;

    @Override
    public FiniteStateMachine copy() {
        return null;
    }
}
