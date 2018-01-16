package visualiser;

import grammar.LexerGrammar;
import grammar.ParserGrammarFactory;

public class Runner {

    public static void main(String[] args) {
        Visualiser visualiser = new Visualiser();
        visualiser.render(ParserGrammarFactory.getMathematicalOperator());
    }
}
