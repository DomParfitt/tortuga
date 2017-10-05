package lexer;

import grammar.*;

public enum TokenType {

    //Literals
    LETTER(TokenCategory.LITERAL, new UnionFSM("abcdefghifjklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")),
    DIGIT(TokenCategory.LITERAL, new UnionFSM("0123456789")),

    //Separators
    PARENTHESES(TokenCategory.SEPARATOR, new UnionFSM("()[]{}")),
    COMMA(TokenCategory.SEPARATOR, new UnionFSM(",")),
    SEMICOLON(TokenCategory.SEPARATOR, new UnionFSM(";")),

    //Keywords
    IF(TokenCategory.KEYWORD, new FollowedByFSM("if")),
    ELSE(TokenCategory.KEYWORD, new FollowedByFSM("else")),
    WHILE(TokenCategory.KEYWORD, new FollowedByFSM("while")),
    FOR(TokenCategory.KEYWORD, new FollowedByFSM("for")),

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

    //Identifiers
    IDENTIFIER(TokenCategory.IDENTIFIER, FSMFactory.getIdentifierFSM())
    ;

    private String value; //TODO: Not sure if this is necessary
    private TokenCategory category;
    private FiniteStateMachine machine;

    TokenType(TokenCategory category, FiniteStateMachine machine) {
        this.category = category;
        this.machine = machine;
    }

    public boolean parse(String input) {
        return this.machine.parse(input);
    }

    public FiniteStateMachine getMachine() {
        return machine;
    }
}
