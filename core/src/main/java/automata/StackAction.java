package automata;

import grammar.LexerGrammar;

public class StackAction {

    public enum StackActionType {
        PUSH, POP, BOTH, NONE
    }

    private StackActionType stackActionType;
    private LexerGrammar token;

    public StackAction(StackActionType stackActionType) {
        this(stackActionType, null);
    }

    public StackAction(StackActionType stackActionType, LexerGrammar token) {
        this.stackActionType = stackActionType;
        this.token = token;
    }

    public StackActionType getStackActionType() {
        return stackActionType;
    }

    public LexerGrammar getTokenType() {
        return token;
    }
}
