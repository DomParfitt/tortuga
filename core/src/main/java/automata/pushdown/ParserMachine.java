package automata.pushdown;

import grammar.LexerGrammar;
import grammar.ParserGrammar;

public class ParserMachine extends PushdownAutomaton<LexerGrammar> {

    public ParserMachine concatenate(ParserGrammar grammar) {
        return (ParserMachine) super.concatenate(grammar);
    }

    public ParserMachine union(ParserGrammar grammar) {
        return  (ParserMachine) super.union(grammar);
    }

    @Override
    public ParserMachine copy() {
        ParserMachine copy = new ParserMachine();
        copy.stateCounter = this.stateCounter;
        copy.terminalStateIndex = this.terminalStateIndex;
        copy.states = this.copyStatesWithActions();
        copy.stack = this.stack.copy();
        return copy;
    }
}
