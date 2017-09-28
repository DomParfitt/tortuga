package lexer;

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

    TokenType(String value) {
        this.value = value;
    }
}
