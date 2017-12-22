package automata;

import lexer.TokenType;
import parser.Stack;
import parser.StackUnderflowError;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PushdownAutomaton extends FiniteStateMachine<TokenType> {

//    protected Set<State> states;
    protected Stack<TokenType> stack;
//    protected Set<PDATransition> transitions; //TODO: Does this override the named variable from superclass?

    public PushdownAutomaton() {
        //TODO: Implement
        super();
        this.stack = new Stack<>();
        this.transitions = new TreeSet<>();
    }

    public PushdownAutomaton(List<State> states, Set<Transition<TokenType>> transitions) {
        this();
        this.states = states;
        this.transitions = transitions;
    }

//    public boolean parse(List<Token> tokens) {
//        List<TokenType> tokenTypes = new ArrayList<>();
//        for(Token token : tokens) {
//            tokenTypes.add(token.getTokenType());
//        }
//
//        return this.parse(tokenTypes);
//    }

    @Override
    public boolean parse(List<TokenType> tokens) {
        for(TokenType token : tokens) {
            if(this.hasTransition(token)) {
                PDATransition transition = this.getTransition(token);
                PushdownAction action = transition.getResultingState();

                //Do any required actions on the stack
                switch (action.getStackAction().getStackActionType()) {
                    case PUSH: {
                        this.stack.push(token);
                        break;
                    }
                    case POP: {
                        boolean isExpectedToken = this.popAndCompare(action.getStackAction().getTokenType());
                        if(!isExpectedToken) {
                            return false;
                        }
                        break;
                    }
                    case BOTH: {
                        boolean isExpectedToken = this.popAndCompare(action.getStackAction().getTokenType());
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

                //Set the resulting state as the current state
                this.setCurrentState(action.getResultingState());
            }
        }

        return this.getCurrentState().isAcceptingState() && this.stack.isEmpty();
//        return false; //TODO: Probably remove this
    }

    @Override
    public FiniteStateMachine<TokenType> copy() {
        return null;
    }

//    @Override
//    public boolean hasTransition(Token token) {
//        for(PDATransition transition : this.transitions) {
//            if(transition.hasTransition(token)) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
    //TODO: Rename this to getResultingState and resolve Transition to a single type
    private PDATransition getTransition(TokenType token) {
        for(Transition transition : this.transitions) {
            if (transition.getFromState().equals(this.getCurrentState())) {
                if (transition.hasTransition(token)) {
                    return (PDATransition) transition;
                }
            }
        }

        return null;
    }

    private boolean popAndCompare(TokenType expected) {
        try {
            return this.stack.pop().equals(expected);
        } catch (StackUnderflowError e) {
            return false;
        }
    }


}
