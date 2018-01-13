package parser;

import ast.AbstractSyntaxTree;
import grammar.LexerGrammar;
import grammar.ParserGrammar;

import java.util.List;

public class Parser {

    public Parser() {

    }

    public AbstractSyntaxTree parse(List<LexerGrammar> tokens) {
        AbstractSyntaxTree ast = new AbstractSyntaxTree();

        for(ParserGrammar parserGrammar : ParserGrammar.values()) {

        }

        return ast;
    }
}
