package lexer;

import grammar.LexerGrammar;

public class Token {

    private LexerGrammar type;
    private String value;
    private int line;
    private int column;

    public Token(LexerGrammar type, String value, int line, int column) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    public LexerGrammar getTokenType() {
        return this.type;
    }

    public TokenType getTokenCategory() {
        return this.getTokenType().getTokenType();
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
