package automata;

import lexer.Token;
import lexer.TokenType;

public class StackAction {

    public enum StackActionType {
        PUSH, POP, BOTH, NONE
    }

    private StackActionType stackActionType;
    private TokenType token;

    public StackAction(StackActionType stackActionType) {
        this(stackActionType, null);
    }

    public StackAction(StackActionType stackActionType, TokenType token) {
        this.stackActionType = stackActionType;
        this.token = token;
    }

    public StackActionType getStackActionType() {
        return stackActionType;
    }

    public TokenType getTokenType() {
        return token;
    }
}
