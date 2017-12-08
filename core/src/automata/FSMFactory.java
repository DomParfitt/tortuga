package automata;

import lexer.TokenType;

/**
 * Factory class with static methods to get specific compound FSMs
 */
public class FSMFactory {

    private static FiniteStateMachine<Character> letterOrDigit;

    private static FiniteStateMachine<Character> identifierFSM;

    public static FiniteStateMachine<Character> getIdentifierFSM() {
        // (letter|_)(letter|digit|_)*

        //TODO: Implementation of singleton, is this necessary?
        if (FSMFactory.identifierFSM == null) {

            FiniteStateMachine letterOrUnderscore = new UnionFSM(TokenType.LETTER.getMachine(), new UnionFSM("_"));
//            System.out.println("Initialised letterOrUnderscore");

            FiniteStateMachine letterOrDigit = FSMFactory.getLetterOrDigitFSM();
//            System.out.println("Initialised letterOrDigit");

            FiniteStateMachine letterOrDigitOrUnderscore = new UnionFSM(letterOrDigit, new UnionFSM("_"));
//            System.out.println("Initialised letterOrDigitOrUnderscore");

            FiniteStateMachine loopOnLetterOrDigitOrUnderscore = new LoopingFSM(letterOrDigitOrUnderscore);
//            System.out.println("Initialised loopOnLetterOrDigitOrUnderscore");

            FSMFactory.identifierFSM = new FollowedByFSM(letterOrUnderscore, loopOnLetterOrDigitOrUnderscore);
//            System.out.println("Initialised identifier using letterOrUnderscore followed by letterOrDigit");
        }

        return FSMFactory.identifierFSM;
    }

    public static FiniteStateMachine<Character> getLetterOrDigitFSM() {
        if(FSMFactory.letterOrDigit == null) {
            FSMFactory.letterOrDigit = new UnionFSM(TokenType.LETTER.getMachine(), TokenType.DIGIT.getMachine());
        }

        return FSMFactory.letterOrDigit;
    }
}
