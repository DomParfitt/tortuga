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

    public TokenType getTokenType() {
        return this.type;
    }

    public TokenCategory getTokenCategory() {
        return this.getTokenType().getCategory();
    }

    @Override
    public String toString() {
        return "[" + this.type + ": \"" + this.value + "\"; line: " + this.line + "; col: " + this.column +"]";
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Token)) {
            return false;
        }

        Token otherToken = (Token) other;

        return (this.type == otherToken.type) && (this.value == otherToken.value);
    }
}
