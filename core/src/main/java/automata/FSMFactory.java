package automata;

import lexer.LexerGrammar;

/**
 * Factory class with static methods to get specific compound FSMs
 */
public class FSMFactory {

    private static CharacterFSM letterOrDigit;

    private static CharacterFSM identifierFSM;

    public static CharacterFSM getIdentifierFSM() {
        // (letter|_)(letter|digit|_)*

        //TODO: Implementation of singleton, is this necessary?
        if (FSMFactory.identifierFSM == null) {

            CharacterFSM letterOrUnderscore = new UnionFSM(LexerGrammar.LETTER.getMachine(), new UnionFSM("_"));
//            System.out.println("Initialised letterOrUnderscore");

            CharacterFSM letterOrDigit = FSMFactory.getLetterOrDigitFSM();
//            System.out.println("Initialised letterOrDigit");

            CharacterFSM letterOrDigitOrUnderscore = new UnionFSM(letterOrDigit, new UnionFSM("_"));
//            System.out.println("Initialised letterOrDigitOrUnderscore");

            CharacterFSM loopOnLetterOrDigitOrUnderscore = new LoopingFSM(letterOrDigitOrUnderscore);
//            System.out.println("Initialised loopOnLetterOrDigitOrUnderscore");

            FSMFactory.identifierFSM = new FollowedByFSM(letterOrUnderscore, loopOnLetterOrDigitOrUnderscore);
//            System.out.println("Initialised identifier using letterOrUnderscore followed by letterOrDigit");
        }

        return FSMFactory.identifierFSM;
    }

    public static CharacterFSM getLetterOrDigitFSM() {
        if(FSMFactory.letterOrDigit == null) {
            FSMFactory.letterOrDigit = new UnionFSM(LexerGrammar.LETTER.getMachine(), LexerGrammar.DIGIT.getMachine());
        }

        return FSMFactory.letterOrDigit;
    }
}
