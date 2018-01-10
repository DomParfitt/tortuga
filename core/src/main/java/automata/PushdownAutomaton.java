package automata;

import grammar.LexerGrammar;
import grammar.ParserGrammar;
import parser.Stack;
import parser.StackUnderflowError;

import java.util.List;
import java.util.Set;

public class PushdownAutomaton extends FiniteStateMachine<LexerGrammar> {

    protected Stack<LexerGrammar> stack;

    public PushdownAutomaton() {
        //TODO: Implement
        super();
        this.stack = new Stack<>();
    }

    public PushdownAutomaton(List<State> states, Set<Transition<LexerGrammar>> transitions) {
        this();
        this.states = states;
        this.transitions = transitions;
    }

    @Override
    public boolean parse(List<LexerGrammar> tokens) {
        for(LexerGrammar token : tokens) {
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
    }

    public PushdownAutomaton concatenate(PushdownAutomaton other) {
        return (PushdownAutomaton) super.concatenate(other);
    }

    public PushdownAutomaton concatenate(ParserGrammar grammar) {
        return this.concatenate(grammar.getMachine());
    }

    public PushdownAutomaton union(PushdownAutomaton other) {
        return (PushdownAutomaton) super.union(other);
    }

    public PushdownAutomaton union(ParserGrammar grammar) {
        return this.union(grammar.getMachine());
    }

    public PushdownAutomaton loop() {
        return (PushdownAutomaton) super.loop();
    }

    public void addTransition(State fromState, State toState, LexerGrammar token, StackAction stackAction) {
      super.addTransition(new PDATransition(token, stackAction, fromState, toState));
    }

    public void addTransition(int fromStateNumber, int toStateNumber, LexerGrammar token, StackAction stackAction) {
//        this.addTransition(this.s);
        //TODO: Implement
    }

    public void addStackAction(State fromState, State toState, LexerGrammar token, StackAction stackAction) {
        PDATransition transition = this.getTransition(fromState, toState, token);
        transition.stackAction = stackAction;

    }

    @Override
    public PushdownAutomaton copy() {
        PushdownAutomaton copy = new PushdownAutomaton();
        //copy.stack
        copy.stateCounter = this.stateCounter;
        copy.terminalStateIndex = this.terminalStateIndex;
        copy.states = this.copyStates();
        copy.transitions = this.copyTransitions(copy.states);

        return copy;
    }

    //TODO: Rename this to getResultingState and resolve Transition to a single type
    private PDATransition getTransition(LexerGrammar token) {
        for(Transition<LexerGrammar> transition : this.transitions) {
            if (transition.getFromState().equals(this.getCurrentState())) {
                if (transition.hasTransition(token)) {
                    return (PDATransition) transition;
                }
            }
        }

        return null;
    }

    private PDATransition getTransition(State fromState, State toState, LexerGrammar token) {
        for(Transition<LexerGrammar> transition : this.transitions) {
            if (transition.getFromState().equals(fromState) && transition.getToState().equals(toState)) {
                if (transition.hasTransition(token)) {
                    return (PDATransition) transition;
                }
            }
        }

        return null;
    }

    private boolean popAndCompare(LexerGrammar expected) {
        try {
            return this.stack.pop().equals(expected);
        } catch (StackUnderflowError e) {
            return false;
        }
    }



}
