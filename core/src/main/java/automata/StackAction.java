package automata;

import grammar.LexerGrammar;

public class StackAction implements TransitionAction {

    public enum StackActionType {
        PUSH, POP, BOTH, NONE
    }

    private StackActionType stackActionType;
    private LexerGrammar token;
//    private Transition<LexerGrammar> transition;

    public StackAction(StackActionType stackActionType) {
        this.stackActionType = stackActionType;
//        this.transition = transition;
    }

    public StackAction(StackActionType stackActionType, LexerGrammar token) {
        this(stackActionType);
        this.token = token;
    }

    public StackActionType getStackActionType() {
        return stackActionType;
    }

    public LexerGrammar getTokenType() {
        return token;
    }

    @Override
    public <T> void doAction(FiniteStateMachine<T> machine) {
        switch(this.getStackActionType()) {

            case PUSH:
                break;
            case POP:
                break;
            case BOTH:
                break;
            case NONE:
                break;
        }
    }

//    public boolean appliesTo(Transition<LexerGrammar> transition) {
//        return this.transition.equals(transition);
//    }
}
