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
        if(!this.isEmpty()) {
            return this.stack.remove(this.getTopIndex());
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

    /**
     * Peeks at the top entry on the stack without removing it
     * @return the value on the top of the stack
     */
    public T peek() {
        if(!this.isEmpty()) {
            return this.stack.get(this.getTopIndex());
        } else {
            throw new StackUnderflowError();
        }
    }

    /**
     * Checks whether the stack is empty or not
     * @return true, if the stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return !(this.stack.size() > 0);
    }

    /**
     * Returns the index of the topmost element on the stack, i.e. the next
     * element to be popped
     * @return the index of the topmost element on the stack
     */
    private int getTopIndex() {
        return this.stack.size() - 1;
    }

}
