package automata;

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
}
