package lexer;

import automata.*;
import utils.StringUtils;

public enum LexerGrammar implements Grammar {

    //TokenTypes defined in order of precedence, from lowest to highest

    //Literals
    WHITESPACE(TokenType.LITERAL, new LoopingFSM(new UnionFSM(" \n\t\r\f"))),
    NEWLINE(TokenType.LITERAL, new LoopingFSM(new UnionFSM("\n\r"))),
    QUOTE(TokenType.LITERAL, new FollowedByFSM("\"")),
    LETTER(TokenType.LITERAL, new UnionFSM("abcdefghifjklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")),
    DIGIT(TokenType.LITERAL, new UnionFSM("0123456789")),

    //Primitive literals
    INT(TokenType.LITERAL, new LoopingFSM(DIGIT.getMachine())),
    STRING(TokenType.LITERAL, new FollowedByFSM(new FollowedByFSM(QUOTE.getMachine(), new LoopingFSM(LETTER.getMachine())), QUOTE.getMachine())),
    //TODO: String doesn't work

    //Identifiers
    IDENTIFIER(TokenType.IDENTIFIER, FSMFactory.getIdentifierFSM()),

    //Separators
    OPENPAREN(TokenType.SEPARATOR, new UnionFSM("([{")),
    CLOSEPAREN(TokenType.SEPARATOR, new UnionFSM(")]}")),
    COMMA(TokenType.SEPARATOR, new UnionFSM(",")),
    SEMICOLON(TokenType.SEPARATOR, new UnionFSM(";")),

    //Operators
    PLUS(TokenType.OPERATOR, new FollowedByFSM("+")),
    MINUS(TokenType.OPERATOR, new FollowedByFSM("-")),
    MULTIPLY(TokenType.OPERATOR, new FollowedByFSM("*")),
    DIVIDE(TokenType.OPERATOR, new FollowedByFSM("/")),
    ASSIGNMENT(TokenType.OPERATOR, new FollowedByFSM("=")),
    EQUALITY(TokenType.OPERATOR, new FollowedByFSM("==")),
    GREATERTHAN(TokenType.OPERATOR, new FollowedByFSM(">")),
    GREATERTHANEQUALS(TokenType.OPERATOR, new FollowedByFSM(">=")),
    LESSTHAN(TokenType.OPERATOR, new FollowedByFSM("<")),
    LESSTHANEQUALS(TokenType.OPERATOR, new FollowedByFSM("<=")),

    //Keywords
    IF(TokenType.KEYWORD, new FollowedByFSM("if")),
    ELSE(TokenType.KEYWORD, new FollowedByFSM("else")),
    WHILE(TokenType.KEYWORD, new FollowedByFSM("while")),
    FOR(TokenType.KEYWORD, new FollowedByFSM("for"))
    ;

    private String value; //TODO: Not sure if this is necessary
    private TokenType category;
    private CharacterFSM machine;

    LexerGrammar(TokenType category, CharacterFSM machine) {
        this.category = category;
        this.machine = machine;
    }

    public boolean parse(String input) {
        return this.machine.parse(StringUtils.toCharacterList(input));
    }

    public boolean parse(Character input) {
        return this.machine.parse(input);
    }

    public CharacterFSM getMachine() {
        return machine;
    }

    public TokenType getCategory() {
        return category;
    }
}
