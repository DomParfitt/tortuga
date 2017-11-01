package visualiser;

import automata.FiniteStateMachine;
import automata.State;
import automata.Transition;

import java.util.List;
import java.util.Set;

public class FiniteStateMachinePOJO {

    private List<State> states;
    private Set<Transition> transitions;

    public FiniteStateMachinePOJO(List<State> states, Set<Transition> transitions) {
        this.states = states;
        this.transitions = transitions;
    }

    public FiniteStateMachinePOJO(FiniteStateMachine fsm) {
        this.states = fsm.getStates();
        this.transitions = fsm.getTransitions();
    }

    public List<State> getStates() {
        return this.states;
    }

    public Set<Transition> getTransitions() {
        return this.transitions;
    }
}
