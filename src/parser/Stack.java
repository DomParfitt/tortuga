package parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple implementation of a generic stack data structure
 * @param <T> the type of object held on the stack
 */
public class Stack<T> {

    private List<T> stack;

    /**
     * Intitialises an empty stack
     */
    public Stack() {
        this.stack = new ArrayList<>();
    }

    /**
     * Pop the top element from the stack
     * @return the top element of the stack
     */
    public T pop() {
        if(this.stack.size() > 0) {
            return this.stack.remove(this.stack.size() - 1);
        } else {
            throw new StackUnderflowError();
        }
    }

    /**
     * Pushes a new element onto the top of the stack
     * @param item the element to push to the stack
     */
    public void push(T item) {
        this.stack.add(item);
    }

}
