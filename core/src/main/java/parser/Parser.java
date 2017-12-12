package parser;

import ast.AbstractSyntaxTree;
import lexer.Token;
import lexer.TokenType;

import java.util.List;

public class Parser {

    public Parser() {

    }

    public AbstractSyntaxTree parse(List<Token> tokens) {
        AbstractSyntaxTree ast = new AbstractSyntaxTree();
        AbstractSyntaxTree currentNode = ast;
        for (Token token : tokens) {
            switch (token.getTokenCategory()) {

                case IDENTIFIER:
                    break;
                case KEYWORD:
                    break;
                case SEPARATOR:
                    if (token.getTokenType() == TokenType.OPENPAREN) {
                        AbstractSyntaxTree newNode = new AbstractSyntaxTree();
                        currentNode.setLeftChild(newNode);
                        currentNode = newNode;
                    } else if (token.getTokenType() == TokenType.CLOSEPAREN) {
                        if (currentNode.getParent() != null) {

                            currentNode = currentNode.getParent();
                        } else {
                            AbstractSyntaxTree parent = new AbstractSyntaxTree();
                            currentNode.setParent(parent);
                            parent.setLeftChild(currentNode);
                            currentNode = parent;
                        }
                    }
                    break;
                case OPERATOR:
                    currentNode.setToken(token);
                    AbstractSyntaxTree newNode = new AbstractSyntaxTree();
                    currentNode.setRightChild(newNode);
                    currentNode = newNode;
                    break;
                case LITERAL:
                    currentNode.setToken(token);
                    currentNode = currentNode.getParent();
                    break;
                case COMMENT:
                    break;
            }
        }
        return currentNode;
    }
}
