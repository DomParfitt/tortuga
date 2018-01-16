package visualiser;

import automata.State;
import automata.actions.AutomataAction;
import automata.finitestate.FiniteStateMachine;
import grammar.Grammar;

import java.io.FileWriter;
import java.util.Map;
import java.util.Set;

public class Visualiser {

    FileWriter writer;

    public Visualiser() {

    }

    public <T> void render(Grammar<T> grammar) {
        System.out.println(this.stringify(grammar));
    }

//    private <T> File getFile(Grammar<T> grammar) {
//        File file = new File(".");
//    }

    private <T> String stringify(Grammar<T> grammar) {
        FiniteStateMachine<T> automata = grammar.getMachine();
        String output = "@startuml\n";
        output += "digraph G {\n";
        output += "rankdir=LR;";
        output += "node [shape=circle];\n";

        for (State state : automata.getStates()) {
            if (!state.isAcceptingState()) {
                output += "\"" + state.getNumber() + "\"\n";
            }
        }

        State terminal = automata.getTerminalState();
        output += "node [shape=doublecircle];\n";
        output += "\"" + terminal.getNumber() + "\"\n";

        for (Map.Entry<State, Set<AutomataAction<T>>> stateActions : automata.getStatesWithActions().entrySet()) {
            State state = stateActions.getKey();
            Set<AutomataAction<T>> actions = stateActions.getValue();
            for (AutomataAction<T> action : actions) {
                output += "\"" + action.getFromState().getNumber() + "\" -> \"" + action.getResultingState().getNumber() + "\" ";
                output += "[label= \"";
                String prefix = "";
                for(T token : action.getInputSymbols()) {
                    output += prefix + token;
                    prefix = "|";
                }
                output += "\"];\n";
            }
        }


        output += "}\n";
        output += "@enduml";
        return output;
    }
}
