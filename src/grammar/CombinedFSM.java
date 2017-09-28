package grammar;

import java.util.List;

public class CombinedFSM extends FiniteStateMachine {

    List<FiniteStateMachine> finiteStateMachines;

    public CombinedFSM(List<FiniteStateMachine> finiteStateMachines) {
        super("");
        this.finiteStateMachines = finiteStateMachines;
    }
}
