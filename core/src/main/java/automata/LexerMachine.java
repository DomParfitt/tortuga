package automata;

import utils.StringUtils;

public abstract class LexerMachine extends FiniteStateMachine<Character> {

    public final boolean parse(String input) {
        return super.parse(StringUtils.toCharacterList(input));
    }

    @Override
    public abstract LexerMachine copy();
}
