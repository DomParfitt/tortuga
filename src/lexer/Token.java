package lexer;

public class Token {

    private TokenType type;
    private String value;
    private int line;
    private int column;

    public Token(TokenType type, String value, int line, int column) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return "[" + this.type + ": " + this.value + "]";
    }
}
