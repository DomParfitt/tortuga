package lexer;

import grammar.FiniteStateMachine;

public enum TokenType {

    //Keywords
    IF("if"),
    ELSE("else"),
    WHILE("while"),
    FOR("for"),

    //Operators
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),

    //
    IDENTIFIER("id")
    ;

    private String value;
    private FiniteStateMachine machine;

    TokenType(String value) {
        this.value = value;
    }

    public boolean parse(String input) {
        return this.machine.parse(input);
    }
}
