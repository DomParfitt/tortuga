package grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        if (this.finiteStateMachines.isEmpty()) {
            this.initialState = machine.initialState;
        } else {
            //Get the last FSM in the list
            FiniteStateMachine last = this.finiteStateMachines.get(this.finiteStateMachines.size() - 1);

            //Get the final state of the last FSM
            State finalState = last.getFinalState();
            finalState.setIsAcceptingState(false);

            //Get all transitions from the new machine's initial state and add them to the
            //current last machine's final state
            for (Map.Entry<String, State> transition : machine.initialState.getTransitions().entrySet()) {
                finalState.addTransition(transition.getKey(), transition.getValue());
            }
        }

        //Add the new machine to the list
        this.finiteStateMachines.add(machine);
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
