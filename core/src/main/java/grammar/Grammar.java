package grammar;

import automata.finitestate.FiniteStateMachine;

import java.util.List;

/**
 * Interface for grammars
 * @param <T>
 */
public interface Grammar<T> {

//    public boolean parse(T input);

    /**
     * Parses a list of tokens
     * @param input a list of tokens of type T
     * @return true if the parse succeeds, false otherwise
     */
    boolean parse(List<T> input);

    /**
     * Gets the automata that represents the grammar
     * @return an automata
     */
    FiniteStateMachine<T> getMachine();
}
