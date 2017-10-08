package lexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lexer {

    @Deprecated
    private String input;

    public Lexer() {}

    @Deprecated
    public Lexer(String input) {
        this.input = input;
    }

    @Deprecated
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        //Split the input string on whitespace
        List<String> stringTokens = this.splitInputOnWhiteSpace();
        int line = 0;
        int column = 0;
        for (String input : stringTokens) { //TODO: Doing this way requires unnecessary whitespace in source
            boolean matchFlag = false;
            for (TokenType tokenType : TokenType.values()) {
                if (tokenType.parse(input)) {
                    Token token = new Token(tokenType, input, 0, 0);
                    tokens.add(token);
                    matchFlag = true;
                    break;
                }
            }
            if (!matchFlag) {
                throw new TokenizeException("No valid token found on input string: " + input + " at column: " + column);
            }
            column += input.length();
        }
        return tokens;
    }

    /**
     * Parses a String character by character and splits it into valid tokens. Uses lookahead
     * to ensure that the longest valid match for a substring is used. If the substring is
     * ambiguous (i.e. matches more than a single token) then the Token with highest precedent
     * is used
     * @param input the String to tokenize
     * @return a List of Tokens
     * @throws TokenizeException upon encountering a substring that has no valid tokens
     */
    public List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        int line = 1;
        int column = 0;
        for (int i = 0; i < input.length(); ) {
            int lookahead = 1;

            //Get the character at the required position
            String sub = input.substring(i, i + lookahead);

            //If the character is whitespace we just skip it
            if (!this.isWhitespace(sub)) {

                //Get a list of tokens that the character matches
                List<TokenType> initialMatches = this.getMatchingTokenTypes(sub);

                //Create a list to hold matches from looking ahead
                List<TokenType> lookaheadMatches = new ArrayList<>();

                //Increment the lookahead offset
                lookahead++;

                //If there is another character to check then do so
                if (i + lookahead < input.length()) {

                    //Get the matching tokens from the substring up to the lookahead offset
                    lookaheadMatches = this.getMatchingTokenTypes(input.substring(i, i + lookahead));

                    //If the list is non-empty then we want to look ahead again, so increment offset
                    if (!lookaheadMatches.isEmpty()) {
                        lookahead++;
                    }
                }

                //Keeping doing a lookahead as long as the result isn't empty and there are characters to check
                while (!lookaheadMatches.isEmpty() && lookahead + i < input.length()) {

                    //Get the matching tokens from the substring up to the lookahead offset in a temp variable
                    List<TokenType> nextLookaheadMatches = this.getMatchingTokenTypes(input.substring(i, i + lookahead));

                    //If the temp variable is non-empty we can replace our original lookahead list with it and lookahead again
                    if (!nextLookaheadMatches.isEmpty()) {
                        lookaheadMatches = nextLookaheadMatches;
                        lookahead++;
                    } else {
                        break;
                    }
                }

                //Current lookahead offset had no matches so decrement
                lookahead--;

                if (initialMatches.isEmpty() && lookaheadMatches.isEmpty()) {
                    //Both lists are empty, so invalid input
                    throw new TokenizeException("No matching token");
                } else if (!lookaheadMatches.isEmpty()) {
                    //Prioritise tokens from lookahead over initial
                    TokenType type = lookaheadMatches.get(lookaheadMatches.size() - 1);
                    Token token = new Token(type, input.substring(i, i + lookahead), line, i - column);
                    tokens.add(token);
                } else if (!initialMatches.isEmpty()) {
                    //Nothing from lookahead so create token from initial character
                    TokenType type = initialMatches.get(initialMatches.size() - 1);
                    Token token = new Token(type, sub, line, i - column);
                    tokens.add(token);
                }

                //Increment i up to the successful lookahead poitn
                i += lookahead;
            } else {
                //Character was whitespace so just increment i
                i++;

                //Check if character is a newline and update line and column trackers as necessary
                if(sub.matches("\n") || sub.matches("\r") || sub.matches("\r\n")) {
                    line++;
                    column = i;
                }
            }
        }

        return tokens;
    }

    private boolean matchesTokenType(String input) {
        for (TokenType tokenType : TokenType.values()) {
            if (tokenType.parse(input)) {
                return true;
            }
        }

        return false;
    }

    private List<TokenType> getMatchingTokenTypes(String input) {
        List<TokenType> tokenTypes = new ArrayList<>();
        for (TokenType tokenType : TokenType.values()) {
            if (tokenType.parse(input)) {
                tokenTypes.add(tokenType);
            }
        }

        return tokenTypes;
    }

    private boolean isWhitespace(String input) {
        return input.matches("\\s+");
    }

    public List<String> splitInputOnWhiteSpace() {
        String[] splitInput = input.split("\\s+");
        return Arrays.asList(splitInput);
    }
}
