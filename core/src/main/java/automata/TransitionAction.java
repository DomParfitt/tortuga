package automata;

public interface TransitionAction {

    <T> void doAction(FiniteStateMachine<T> machine);
}
