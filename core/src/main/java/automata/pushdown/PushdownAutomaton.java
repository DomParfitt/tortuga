package automata.pushdown;

import automata.State;
import automata.actions.StackAction;
import automata.actions.Transition;
import automata.finitestate.FiniteStateMachine;
import grammar.LexerGrammar;
import grammar.ParserGrammar;
import parser.Stack;
import parser.StackUnderflowError;

import java.util.List;
import java.util.Set;

public class PushdownAutomaton extends FiniteStateMachine<LexerGrammar> {

    protected Stack<LexerGrammar> stack;
//    protected Map<Transition<LexerGrammar>, StackAction> actions;

    public PushdownAutomaton() {
        //TODO: Implement
        super();
        this.stack = new Stack<>();
//        this.actions = new HashMap<>();
    }

    @Deprecated
    public PushdownAutomaton(List<State> states, Set<Transition<LexerGrammar>> transitions) {
        this();
        this.states = states;
        this.transitions = transitions;
    }

    @Override
    public boolean parse(List<LexerGrammar> tokens) {
        for(LexerGrammar token : tokens) {
            if(this.hasTransition(token)) {
                Transition<LexerGrammar> transition = this.getTransition(token);
                StackAction action = (StackAction) transition.getAction();

                //Do any required actions on the stack
                switch (action.getStackActionType()) {
                    case PUSH: {
                        this.stack.push(token);
                        break;
                    }
                    case POP: {
                        boolean isExpectedToken = this.popAndCompare(action.getTokenType());
                        if(!isExpectedToken) {
                            return false;
                        }
                        break;
                    }
                    case BOTH: {
                        boolean isExpectedToken = this.popAndCompare(action.getTokenType());
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
                this.setCurrentState(transition.getResultingState());
            }
        }

        return this.getCurrentState().isAcceptingState() && this.stack.isEmpty();
    }

    public PushdownAutomaton concatenate(PushdownAutomaton other) {
        return (PushdownAutomaton) super.concatenate(other);
//        for(Map.Entry<Transition<LexerGrammar>, StackAction> action : other.actions.entrySet()) {
//            for(LexerGrammar token : action.getKey().getInputSymbols()) {
//                Transition<LexerGrammar> transition = pda.getTransition(token);
//                pda.actions.put(transition, action.getValue());
//            }
////            pda.actions.put(action.getKey(), action.getValue());
//        }
//        return pda;
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

    public void addTransition(State fromState, State toState, LexerGrammar token, StackAction.StackActionType stackActionType, LexerGrammar actionToken) {
        StackAction stackAction = new StackAction(stackActionType, actionToken);
        Transition<LexerGrammar> transition = new Transition<>(token, fromState, toState, stackAction);
        super.addTransition(transition);
//        this.addAction(transition, stackActionType, actionToken);
    }

//    public void addAction(Transition<LexerGrammar> transition, StackAction.StackActionType stackActionType, LexerGrammar actionToken) {
//        StackAction stackAction = new StackAction(stackActionType, actionToken);
//        this.actions.put(transition, stackAction);
//
//    }

    @Override
    public PushdownAutomaton copy() {
        PushdownAutomaton copy = new PushdownAutomaton();
        //copy.stack
        copy.stateCounter = this.stateCounter;
        copy.terminalStateIndex = this.terminalStateIndex;
        copy.states = this.copyStates();
        copy.transitions = this.copyTransitions(copy.states);
//        copy.actions = this.actions; //TODO:May need to be made to copy


        return copy;
    }

    //TODO: This could be pushed up the hierarchy
    private Transition<LexerGrammar> getTransition(LexerGrammar token) {
        for(Transition<LexerGrammar> transition : this.transitions) {
            if (transition.getFromState().equals(this.getCurrentState())) {
                if (transition.hasTransition(token)) {
                    return transition;
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
