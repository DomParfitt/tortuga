package automata;

import lexer.Token;

public class StackAction {

    public enum StackActionType {
        PUSH, POP, BOTH, NONE
    }

    private StackActionType stackActionType;
    private Token token;

    public StackAction(StackActionType stackActionType) {
        this(stackActionType, null);
    }

    public StackAction(StackActionType stackActionType, Token token) {
        this.stackActionType = stackActionType;
        this.token = token;
    }

    public StackActionType getStackActionType() {
        return stackActionType;
    }

    public Token getToken() {
        return token;
    }
}
