package grammar;

import automata.FiniteStateMachine;

import java.util.List;

public interface Grammar<T> {

//    public boolean parse(T input);

    public boolean parse(List<T> input);

    public FiniteStateMachine<T> getMachine();
}
