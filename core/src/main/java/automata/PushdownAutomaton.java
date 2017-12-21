package automata;

import lexer.Token;
import parser.Stack;
import parser.StackUnderflowError;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PushdownAutomaton extends FiniteStateMachine<Token> {

//    protected Set<State> states;
    protected Stack<Token> stack;
//    protected Set<PDATransition> transitions; //TODO: Does this override the named variable from superclass?

    public PushdownAutomaton() {
        //TODO: Implement
        super();
        this.stack = new Stack<>();
        this.transitions = new TreeSet<>();
    }

    public PushdownAutomaton(List<State> states, Set<Transition<Token>> transitions) {
        this();
        this.states = states;
        this.transitions = transitions;
    }

    @Override
    public boolean parse(List<Token> tokens) {
        for(Token token : tokens) {
            if(this.hasTransition(token)) {
                PDATransition transition = this.getTransition(token);
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
                this.setCurrentState(action.getResultingState());
            }
        }

        return this.getCurrentState().isAcceptingState() && this.stack.isEmpty();
//        return false; //TODO: Probably remove this
    }

    @Override
    public FiniteStateMachine<Token> copy() {
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
    private PDATransition getTransition(Token token) {
        for(Transition transition : this.transitions) {
            if (transition.getFromState().equals(this.getCurrentState())) {
                if (transition.hasTransition(token)) {
                    return (PDATransition) transition;
                }
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
