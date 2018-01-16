package visualiser;

import grammar.LexerGrammar;

public class Runner {

    public static void main(String[] args) {
        Visualiser visualiser = new Visualiser();
        visualiser.render(LexerGrammar.WHILE);
    }
}
