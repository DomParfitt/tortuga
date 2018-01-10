package automata;

import grammar.LexerGrammar;
import utils.StringUtils;

public class LexerMachine extends FiniteStateMachine<Character> {

    public final boolean parse(String input) {
        return super.parse(StringUtils.toCharacterList(input));
    }

    @Override
    public LexerMachine copy() {
        LexerMachine copy = new LexerMachine();
        copy.stateCounter = this.stateCounter;
        copy.terminalStateIndex = this.terminalStateIndex;
        copy.states = this.copyStates();
        copy.transitions = this.copyTransitions(copy.states);

        return copy;
    }

    public LexerMachine concatenate(LexerMachine other) {
        return (LexerMachine) super.concatenate(other);
    }

    public LexerMachine concatenate(LexerGrammar grammar) {
        return this.concatenate(grammar.getMachine());
    }

    public LexerMachine union(LexerMachine other) {
        return (LexerMachine) super.union(other);
    }

    public LexerMachine union(LexerGrammar grammar) {
        return this.union(grammar.getMachine());
    }

    public LexerMachine loop() {
        return (LexerMachine) super.loop();
    }

}
