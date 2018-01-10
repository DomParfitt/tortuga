package automata;

import grammar.LexerGrammar;
import grammar.ParserGrammar;

@Deprecated
public class PDAFactory {

    /*
     *  CONCATENATE
     */

    public static PushdownAutomaton concatenate(PushdownAutomaton first, PushdownAutomaton second) {
        PushdownAutomaton pda = new PushdownAutomaton();
        PushdownAutomaton firstCopy = first.copy();
        PushdownAutomaton secondCopy = second.copy();
        pda.initialise(firstCopy);

        State secondInitial = secondCopy.getInitialState();
        //Add each state of secondCopy except initial
        boolean addedState = false;
        for (State state : secondCopy.states) {
            if (!state.equals(secondInitial)) {
                int newNumber = pda.addState(state);
                addedState = true;

                //Find any transitions referencing that state and update their number
                for (Transition<LexerGrammar> transition : secondCopy.transitions) {
                    if (transition.fromState.equals(state)) {
                        transition.fromState.setNumber(newNumber);
                    }

                    if (transition.toState.equals(state)) {
                        transition.toState.setNumber(newNumber);
                    }
                }

            }
        }
        //Update secondCopy transitions with initial to use this's terminal
        //Add transitions
        //Add all transitions from secondCopy into this, replacing initial and terminal states with this's
        for (Transition<LexerGrammar> transition : secondCopy.transitions) {
            if (transition.fromState.equals(secondInitial)) {
                transition.fromState = pda.getTerminalState();
            }

            if (transition.toState.equals(secondInitial)) {
                transition.toState = pda.getTerminalState();
            }

            pda.addTransition(transition);
        }

        //Mark terminal state of this as non-terminal if new states have been added
        if (addedState) {
            pda.getTerminalState().setAcceptingState(false);
        }

        //Update terminal index of this
        pda.terminalStateIndex = pda.stateCounter - 1;

        return pda;
    }

    public static PushdownAutomaton concatenate(ParserGrammar first, ParserGrammar second) {
        return concatenate(first.getMachine(), second.getMachine());
    }

    public static PushdownAutomaton concatenate(ParserGrammar first, PushdownAutomaton second) {
        return concatenate(first.getMachine(), second);
    }

    public static PushdownAutomaton concatenate(PushdownAutomaton first, ParserGrammar second) {
        return concatenate(first, second.getMachine());
    }

    /*
     *  UNION
     */

    public static PushdownAutomaton union(PushdownAutomaton first, PushdownAutomaton second) {
        return null;
    }

    public static PushdownAutomaton union(ParserGrammar first, ParserGrammar second) {
        return union(first.getMachine(), second.getMachine());
    }

    public static PushdownAutomaton union(ParserGrammar first, PushdownAutomaton second) {
        return union(first.getMachine(), second);
    }

    public static PushdownAutomaton union(PushdownAutomaton first, ParserGrammar second) {
        return union(first, second.getMachine());
    }

    /*
     *   LOOP
     */

    public static PushdownAutomaton loop(PushdownAutomaton pda) {
        return null;
    }

    public static PushdownAutomaton loop(ParserGrammar grammar) {
        return loop(grammar.getMachine());
    }
}
