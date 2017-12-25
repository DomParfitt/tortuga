package grammar;

import automata.LexerGrammarFactory;
import automata.LexerMachine;
import lexer.TokenType;
import utils.StringUtils;

import java.util.List;

public enum LexerGrammar implements Grammar<Character> {

    //TokenTypes defined in order of precedence, from lowest to highest

    //Literals - these should be lowest precedence, i.e. they are mostly used to form other entries rather
    //than existing as valid tokens in a program
    WHITESPACE(TokenType.LITERAL),
    NEWLINE(TokenType.LITERAL),
    QUOTE(TokenType.LITERAL),
    LETTER(TokenType.LITERAL),
    DIGIT(TokenType.LITERAL),

    //Identifiers - must be lowest precedence of valid tokens within the program to ensure that any uses of
    //valid keywords (such as if, else, while, etc.) are lexed as keywords and not identifiers
    IDENTIFIER(TokenType.IDENTIFIER),

    //Separators
    OPEN_PAREN(TokenType.SEPARATOR),
    CLOSE_PAREN(TokenType.SEPARATOR),
    COMMA(TokenType.SEPARATOR),
    PERIOD(TokenType.SEPARATOR),
    SEMICOLON(TokenType.SEPARATOR),

    //Primitive literals
    INT_LITERAL(TokenType.LITERAL),
    FLOAT_LITERAL(TokenType.LITERAL),
    STRING_LITERAL(TokenType.LITERAL),
    BOOLEAN_LITERAL(TokenType.LITERAL),

    //Operators
    PLUS(TokenType.OPERATOR),
    MINUS(TokenType.OPERATOR),
    MULTIPLY(TokenType.OPERATOR),
    DIVIDE(TokenType.OPERATOR),
    ASSIGNMENT(TokenType.OPERATOR),
    EQUALITY(TokenType.OPERATOR),
    GREATER_THAN(TokenType.OPERATOR),
    GREATER_THAN_EQUALS(TokenType.OPERATOR),
    LESS_THAN(TokenType.OPERATOR),
    LESS_THAN_EQUALS(TokenType.OPERATOR),

    //Types
    //TODO: Add types
    INT(TokenType.KEYWORD),
    FLOAT(TokenType.KEYWORD),
    BOOLEAN(TokenType.KEYWORD),

    //Keywords
    IF(TokenType.KEYWORD),
    ELSE(TokenType.KEYWORD),
    WHILE(TokenType.KEYWORD),
    FOR(TokenType.KEYWORD)
    ;

//    private String value; //TODO: Not sure if this is necessary
    private TokenType tokenType;
//    private LexerMachine machine;

    LexerGrammar(TokenType tokenType) {
        this.tokenType = tokenType;
//        this.machine = LexerGrammarFactory.getMachine(this);
    }

//    LexerGrammar(TokenType tokenType, LexerMachine machine) {
//        this.tokenType = tokenType;
//        this.machine = machine;
//    }

    public boolean parse(String input) {
        return this.getMachine().parse(StringUtils.toCharacterList(input));
    }

//    @Override
//    public boolean parse(Character input) {
//        return this.machine.parse(input);
//    }

    @Override
    public boolean parse(List<Character> input) {
        String stringInput = "";
        for(Character character : input) {
            stringInput += character;
        }
        return this.getMachine().parse(stringInput);
    }

    @Override
    public synchronized LexerMachine getMachine() {
//        return machine;
        return LexerGrammarFactory.getMachine(this);
    }

    public TokenType getTokenType() {
        return tokenType;
    }
}
