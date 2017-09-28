package grammar;

public abstract class FiniteStateMachine {

    protected State initialState;

    public FiniteStateMachine() {
        this.initialState = new State(false);
    }

    public boolean parse(String input) {
        State currentState = this.initialState;
        for(int i = 0; i < input.length(); i++) {
          String character = input.substring(i, 1);
          if(currentState.hasTransition(character)) {
              currentState = currentState.getResultingState(character);
          }
        }

        return currentState.isAcceptingState();
    }

}
