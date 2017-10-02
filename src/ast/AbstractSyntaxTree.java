package ast;

import lexer.Token;

import java.util.ArrayList;
import java.util.List;

public class AbstractSyntaxTree {

    private Token token;
    private List<AbstractSyntaxTree> branches;

    public AbstractSyntaxTree(Token token) {
        this.token = token;
        this.branches = new ArrayList<>();
    }
}
