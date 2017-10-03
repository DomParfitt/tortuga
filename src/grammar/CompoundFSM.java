package grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Deprecated
public class CompoundFSM extends FiniteStateMachine {

    List<FiniteStateMachine> finiteStateMachines;

    public CompoundFSM(FiniteStateMachine machine) {
        super();
        this.finiteStateMachines = new ArrayList<>();
        this.addFiniteStateMachine(machine);
    }

    public CompoundFSM(List<FiniteStateMachine> finiteStateMachines) {
        super();
        this.finiteStateMachines = new ArrayList<>();
        for(FiniteStateMachine machine : finiteStateMachines) {
            this.addFiniteStateMachine(machine);
        }
    }

    public void addFiniteStateMachine(FiniteStateMachine machine) {
        FiniteStateMachine copy = machine.copy();
        if (this.finiteStateMachines.isEmpty()) {
            this.initialState = copy.initialState;
        } else {
            //Get the last FSM in the list
            FiniteStateMachine last = this.finiteStateMachines.get(this.finiteStateMachines.size() - 1);

            //Get the final state of the last FSM
            State finalState = last.getFinalState();
            finalState.setIsAcceptingState(false);

            //Get all transitions from the new machine's initial state and add them to the
            //current last machine's final state
            for (Map.Entry<Character, State> transition : copy.initialState.getTransitions().entrySet()) {
                finalState.addTransition(transition.getKey(), transition.getValue());
            }
        }

        //Add the new machine to the list
        this.finiteStateMachines.add(copy);
    }

    @Override
    public FiniteStateMachine copy() {
        return new CompoundFSM(this.finiteStateMachines);
    }

    @Override
    public String toString() {
        String output = "";
        for (FiniteStateMachine machine : this.finiteStateMachines) {
            output += "(" + machine + ")";
        }
        return output;
    }
}
