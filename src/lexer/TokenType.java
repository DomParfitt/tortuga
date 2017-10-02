package lexer;

import grammar.*;

public enum TokenType {

    //Base
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

    //
    IDENTIFIER(new LoopingFSM(BaseType.LETTER.machine))
    ;

    //TODO: Figure a way to keep this separate
    private static enum BaseType {

        LETTER(new UnionFSM("abcdefghifjklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
//        NUMBER(new UnionFSM("0123456789"));

        private FiniteStateMachine machine;

        BaseType(FiniteStateMachine machine) {
            this.machine = machine;
        }
    }

    private String value; //TODO: Not sure if this is necessary
    private FiniteStateMachine machine;

    TokenType(FiniteStateMachine machine) {
        this.machine = machine;
    }

    public boolean parse(String input) {
        return this.machine.parse(input);
    }
}
