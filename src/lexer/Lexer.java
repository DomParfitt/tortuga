package lexer;

import java.util.Arrays;
import java.util.List;

public class Lexer {

    private String input;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        //Split the input string on whitespace
        List<String> stringTokens = this.splitInputOnWhiteSpace();
        return null;
    }

    public List<String> splitInputOnWhiteSpace() {
        String[] splitInput = input.split("\\s+");
        return Arrays.asList(splitInput);
    }
}
