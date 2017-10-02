package lexer;

import grammar.*;

public enum TokenType {

    //Base
    LETTER(new UnionFSM("abcdefghifjklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")),
    NUMBER(new UnionFSM("0123456789")),

    //Keywords
    IF(new FollowedByFSM("if")),
    ELSE(new FollowedByFSM("else")),
    WHILE(new FollowedByFSM("while")),
    FOR(new FollowedByFSM("for")),

    //Operators
    PLUS(new FollowedByFSM("+")),
    MINUS(new FollowedByFSM("-")),
    MULTIPLY(new FollowedByFSM("*")),
    DIVIDE(new FollowedByFSM("/")),
    ASSIGNMENT(new FollowedByFSM("=")),
    EQUALITY(new FollowedByFSM("==")),

    //
    PARENTHESES(new UnionFSM("()[]{}")),
    IDENTIFIER(new LoopingFSM(LETTER.machine))
    ;

    private String value; //TODO: Not sure if this is necessary
    private FiniteStateMachine machine;

    TokenType(FiniteStateMachine machine) {
        this.machine = machine;
    }

    public boolean parse(String input) {
        return this.machine.parse(input);
    }
}
