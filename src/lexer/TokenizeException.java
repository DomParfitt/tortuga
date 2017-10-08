package lexer;

public class TokenizeException extends RuntimeException {

    public TokenizeException(String errorMessage) {
        super(errorMessage);
    }
}
