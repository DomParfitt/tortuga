package automata;

import utils.StringUtils;

public abstract class CharacterFSM extends FiniteStateMachine<Character> {

    public final boolean parse(String input) {
        return super.parse(StringUtils.toCharacterList(input));
    }

    @Override
    public abstract CharacterFSM copy();
}
