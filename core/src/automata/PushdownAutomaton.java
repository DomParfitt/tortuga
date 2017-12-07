package automata;

import lexer.Token;
import parser.Stack;
import parser.StackUnderflowError;

import java.util.List;
import java.util.Set;

public class PushdownAutomaton {

    protected Set<State> states;
    protected Stack<Token> stack;
    protected Set<PDATransition<Token>> transitions;

    public PushdownAutomaton() {
        //TODO: Implement
    }

    public boolean parse(List<Token> tokens) {
        for(Token token : tokens) {
            if(this.hasTransition(token)) {
                PDATransition<Token> transition = this.getTransition(token);
                PushdownAction action = transition.getResultingState();
                switch (action.getStackAction().getStackActionType()) {
                    case PUSH: {
                        this.stack.push(token);
                        break;
                    }
                    case POP: {
                        boolean isExpectedToken = this.popAndCompare(action.getStackAction().getToken());
                        if(!isExpectedToken) {
                            return false;
                        }
                        break;
                    }
                    case BOTH: {
                        boolean isExpectedToken = this.popAndCompare(action.getStackAction().getToken());
                        if(!isExpectedToken) {
                            return false;
                        }
                        this.stack.push(token);
                        break;
                    }
                    case NONE: {
                        break;
                    }
                }

                //Set action.getState() as current state
            }
        }
        return false; //TODO: Probably remove this
    }

    private boolean hasTransition(Token token) {
        for(PDATransition<Token> transition : this.transitions) {
            if(transition.hasTransition(token)) {
                return true;
            }
        }

        return false;
    }

    private PDATransition<Token> getTransition(Token token) {
        for(PDATransition<Token> transition : this.transitions) {
            if(transition.hasTransition(token)) {
                return transition;
            }
        }

        return null;
    }

    private boolean popAndCompare(Token expected) {
        try {
            return this.stack.pop().equals(expected);
        } catch (StackUnderflowError e) {
            return false;
        }
    }


}
