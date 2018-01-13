package grammar;

import automata.finitestate.FiniteStateMachine;

import java.util.List;

public interface Grammar<T> {

//    public boolean parse(T input);

    boolean parse(List<T> input);

    FiniteStateMachine<T> getMachine();
}
