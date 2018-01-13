package automata.actions;

import automata.State;
import automata.finitestate.FiniteStateMachine;

/**
 * Interface for actions within an automaton
 * @param <T> the type of object that the action acts upon
 */
public interface AutomataAction<T> {

    /**
     * Checks whether the action applies to the given token
     * @param token the token
     * @return true if this action consumes the given token, false otherwise
     */
    boolean appliesTo(T token);

    /**
     * Performs the defined action on the given automaton
     * @param machine the automaton to perform the action on
     * @return true if the action succeeded, false otherwise
     */
    boolean doAction(FiniteStateMachine<T> machine);

    /**
     * Gets the state that results from performing this action (may be the current state)
     * @return the resulting state
     */
    State getResultingState();

    /**
     * Set the state that results from performing this action
     * @param resultingState the resulting state
     */
    void setResultingState(State resultingState);

//    AutomataAction<T> copy();
}
