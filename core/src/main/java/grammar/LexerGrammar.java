package grammar;

import automata.*;
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
    INT(TokenType.LITERAL),
    FLOAT(TokenType.LITERAL),
    STRING(TokenType.LITERAL),
    BOOLEAN(TokenType.LITERAL),
    //TODO: String doesn't work

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

    //Keywords
    IF(TokenType.KEYWORD),
    ELSE(TokenType.KEYWORD),
    WHILE(TokenType.KEYWORD),
    FOR(TokenType.KEYWORD)
    ;

    private String value; //TODO: Not sure if this is necessary
    private TokenType tokenType;
    private LexerMachine machine;

    LexerGrammar(TokenType tokenType) {
        this.tokenType = tokenType;
        this.machine = LexerMachineFactory.getMachine(this);
    }

    LexerGrammar(TokenType tokenType, LexerMachine machine) {
        this.tokenType = tokenType;
        this.machine = machine;
    }

    public boolean parse(String input) {
        return this.machine.parse(StringUtils.toCharacterList(input));
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
        return this.machine.parse(stringInput);
    }

    @Override
    public synchronized LexerMachine getMachine() {
        return machine;
    }

    public TokenType getTokenType() {
        return tokenType;
    }
}
