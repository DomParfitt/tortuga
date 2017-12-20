package parser;

import automata.PushdownAutomaton;

public enum Grammar {

    STATEMENT(new PushdownAutomaton());

    PushdownAutomaton machine;

    Grammar(PushdownAutomaton machine) {
        this.machine = machine;
    }

    public PushdownAutomaton getMachine() {
        return machine;
    }
}
