package grammar;

import lexer.Token;
import lexer.TokenType;

/**
 * Factory class with static methods to get specific compound FSMs
 */
public class FSMFactory {

    private static FiniteStateMachine identifierFSM;

    public static FiniteStateMachine getIdentifierFSM() {
        // (letter|_)(letter|digit|_)*

        //TODO: Implementation of singleton, is this necessary?
        if (FSMFactory.identifierFSM == null) {

            FiniteStateMachine letterOrUnderscore = new UnionFSM(TokenType.LETTER.getMachine(), new UnionFSM("_"));
//            System.out.println("Initialised letterOrUnderscore");

            FiniteStateMachine letterOrDigit = new UnionFSM(TokenType.LETTER.getMachine(), TokenType.DIGIT.getMachine());
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
}
