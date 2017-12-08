package lexer;

import automata.*;
import utils.StringUtils;

public enum TokenType {

    //Literals
    WHITESPACE(TokenCategory.LITERAL, new LoopingFSM(new UnionFSM(" \n\t\r\f"))),
    NEWLINE(TokenCategory.LITERAL, new LoopingFSM(new UnionFSM("\n\r"))),
    LETTER(TokenCategory.LITERAL, new UnionFSM("abcdefghifjklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")),
    DIGIT(TokenCategory.LITERAL, new UnionFSM("0123456789")),

    //Identifiers
    IDENTIFIER(TokenCategory.IDENTIFIER, FSMFactory.getIdentifierFSM()),

    //Separators
    OPENPAREN(TokenCategory.SEPARATOR, new UnionFSM("([{")),
    CLOSEPAREN(TokenCategory.SEPARATOR, new UnionFSM(")]}")),
    COMMA(TokenCategory.SEPARATOR, new UnionFSM(",")),
    SEMICOLON(TokenCategory.SEPARATOR, new UnionFSM(";")),

    //Operators
    PLUS(TokenCategory.OPERATOR, new FollowedByFSM("+")),
    MINUS(TokenCategory.OPERATOR, new FollowedByFSM("-")),
    MULTIPLY(TokenCategory.OPERATOR, new FollowedByFSM("*")),
    DIVIDE(TokenCategory.OPERATOR, new FollowedByFSM("/")),
    ASSIGNMENT(TokenCategory.OPERATOR, new FollowedByFSM("=")),
    EQUALITY(TokenCategory.OPERATOR, new FollowedByFSM("==")),
    GREATERTHAN(TokenCategory.OPERATOR, new FollowedByFSM(">")),
    GREATERTHANEQUALS(TokenCategory.OPERATOR, new FollowedByFSM(">=")),
    LESSTHAN(TokenCategory.OPERATOR, new FollowedByFSM("<")),
    LESSTHANEQUALS(TokenCategory.OPERATOR, new FollowedByFSM("<=")),

    //Keywords
    IF(TokenCategory.KEYWORD, new FollowedByFSM("if")),
    ELSE(TokenCategory.KEYWORD, new FollowedByFSM("else")),
    WHILE(TokenCategory.KEYWORD, new FollowedByFSM("while")),
    FOR(TokenCategory.KEYWORD, new FollowedByFSM("for"))
    ;

    private String value; //TODO: Not sure if this is necessary
    private TokenCategory category;
    private CharacterFSM machine;

    TokenType(TokenCategory category, CharacterFSM machine) {
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

    public TokenCategory getCategory() {
        return category;
    }
}
