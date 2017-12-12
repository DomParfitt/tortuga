package ast;

import lexer.Token;

import java.util.ArrayList;
import java.util.List;

public class AbstractSyntaxTree {

    private Token token;
    private AbstractSyntaxTree parent, left, right;
    //Not sure if it will ever need more than two branches
    private List<AbstractSyntaxTree> branches;

    public AbstractSyntaxTree() {

    }

    public AbstractSyntaxTree(Token token) {
        this.token = token;
        this.branches = new ArrayList<>();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public AbstractSyntaxTree getParent() {
        return this.parent;
    }

    public void setParent(AbstractSyntaxTree parent) {
        this.parent = parent;
    }

    public AbstractSyntaxTree getLeftChild() {
        return this.left;
    }

    public AbstractSyntaxTree getRightChild() {
        return this.right;
    }

    public void setLeftChild(AbstractSyntaxTree left) {
        left.parent = this;
        if (this.left != null) {
            this.left.parent = left;
        }
        left.left = this.left;
        this.left = left;
    }

    public void setRightChild(AbstractSyntaxTree right) {
        right.parent = this;
        if (this.right != null) {
            this.right.parent = right;
        }
        right.right = this.right;
        this.right = right;
    }

    private void addChild(AbstractSyntaxTree branch) {

    }
}
