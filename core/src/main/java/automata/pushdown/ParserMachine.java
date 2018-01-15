package automata.pushdown;

import grammar.LexerGrammar;
import grammar.ParserGrammar;

public class ParserMachine extends PushdownAutomaton<LexerGrammar> {

    //TODO: Need to update the stack reference when concatenating (and unioning and probably looping)
    public ParserMachine concatenate(ParserMachine automata) {
        return (ParserMachine) super.concatenate(automata);
    }

    public ParserMachine concatenate(ParserGrammar grammar) {
        return (ParserMachine) super.concatenate(grammar);
    }

    public ParserMachine union(ParserMachine automata) {
        return (ParserMachine) super.union(automata);
    }

    public ParserMachine union(ParserGrammar grammar) {
        return  (ParserMachine) super.union(grammar);
    }

    public ParserMachine loop() {
        return (ParserMachine) super.loop();
    }

    @Override
    public ParserMachine copy() {
        ParserMachine copy = new ParserMachine();
        copy.stateCounter = this.stateCounter;
        copy.terminalStateIndex = this.terminalStateIndex;
        copy.states = this.copyStatesWithActions();
        copy.stack = this.stack;//.copy();
        return copy;
    }
}
