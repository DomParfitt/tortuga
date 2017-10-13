package parser;

public class StackUnderflowError extends RuntimeException {
    public StackUnderflowError() {
        super("Tried to pop from an empty stack");
    }
}