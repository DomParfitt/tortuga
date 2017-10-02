package lexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lexer {

    private String input;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        //Split the input string on whitespace
        List<String> stringTokens = this.splitInputOnWhiteSpace();
        int line = 0;
        int column = 0;
        for (String input : stringTokens) {
            boolean matchFlag = false;
            for (TokenType tokenType : TokenType.values()) {
                if (tokenType.parse(input)) {
                    Token token = new Token(tokenType, input, 0, 0);
                    tokens.add(token);
                    matchFlag = true;
                    break;
                }
            }
            if(!matchFlag) {
                throw new TokenizeException("No valid token found on input string: " + input + " at column: " + column);
            }
            column += input.length();
        }
        return tokens;
    }

    public List<String> splitInputOnWhiteSpace() {
        String[] splitInput = input.split("\\s+");
        return Arrays.asList(splitInput);
    }
}
