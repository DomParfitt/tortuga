package grammar;

import automata.FollowedByFSM;
import automata.LexerMachine;
import automata.UnionFSM;

/**
 * Factory class with static methods to get specific LexerMachines
 */
public class LexerGrammarFactory {

    public static LexerMachine getMachine(LexerGrammar grammar) {
        switch (grammar) {
            case WHITESPACE:
                return getWhitespaceMachine();
            case NEWLINE:
                return getNewlineMachine();
            case QUOTE:
                return getQuoteMachine();
            case LETTER:
                return getLetterMachine();
            case DIGIT:
                return getDigitMachine();
            case IDENTIFIER:
                return getIdentifierMachine();
            case OPEN_PAREN:
                return getOpenParenMachine();
            case CLOSE_PAREN:
                return getCloseParenMachine();
            case COMMA:
                return getCommaMachine();
            case PERIOD:
                return getPeriodMachine();
            case SEMICOLON:
                return getSemiColonMachine();
            case INT_LITERAL:
                return getIntLiteralMachine();
            case FLOAT_LITERAL:
                return getFloatLiteralMachine();
            case STRING_LITERAL:
                return getStringLiteralMachine();
            case BOOLEAN_LITERAL:
                return getBooleanLiteralMachine();
            case PLUS:
                return getPlusMachine();
            case MINUS:
                return getMinusMachine();
            case MULTIPLY:
                return getMultiplyMachine();
            case DIVIDE:
                return getDivideMachine();
            case ASSIGNMENT:
                return getAssignmentMachine();
            case EQUALITY:
                return getEqualityMachine();
            case GREATER_THAN:
                return getGreaterThanMachine();
            case GREATER_THAN_EQUALS:
                return getGreaterThanOrEqualsMachine();
            case LESS_THAN:
                return getLessThanMachine();
            case LESS_THAN_EQUALS:
                return getLessThanOrEqualsMachine();
            case INT:
                return getIntTypeMachine();
            case FLOAT:
                return getFloatTypeMachine();
            case BOOLEAN:
                return getBooleanTypeMachine();
            case IF:
                return getIfMachine();
            case ELSE:
                return getElseMachine();
            case WHILE:
                return getWhileMachine();
            case FOR:
                return getForMachine();
        }

        return null;
    }

    public static LexerMachine getWhitespaceMachine() {
        return new UnionFSM(" \n\t\r\f").loop();
    }

    public static LexerMachine getNewlineMachine() {
        return new UnionFSM("\n\r").loop();
    }

    public static LexerMachine getQuoteMachine() {
        return new FollowedByFSM("\"");
    }

    public static LexerMachine getLetterMachine() {
        return new UnionFSM("abcdefghifjklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    public static LexerMachine getDigitMachine() {
        return new UnionFSM("0123456789");
    }

    public static LexerMachine getIdentifierMachine() {
        // (letter|_)(letter|digit|_)*

        LexerMachine letterOrUnderscore = LexerGrammar.LETTER.union(new UnionFSM("_"));

        LexerMachine letterOrDigit = getLetterOrDigitMachine();

        LexerMachine letterOrDigitOrUnderscore = letterOrDigit.union(new UnionFSM("_"));

        LexerMachine loopOnLetterOrDigitOrUnderscore = letterOrDigitOrUnderscore.loop();

        return letterOrUnderscore.concatenate(loopOnLetterOrDigitOrUnderscore);
    }

    public static LexerMachine getOpenParenMachine() {
        return new UnionFSM("([{");
    }

    public static LexerMachine getCloseParenMachine() {
        return new UnionFSM(")]}");
    }

    public static LexerMachine getCommaMachine() {
        return new UnionFSM(",");
    }

    public static LexerMachine getPeriodMachine() {
        return new UnionFSM(".");
    }

    public static LexerMachine getSemiColonMachine() {
        return new UnionFSM(";");
    }

    public static LexerMachine getIntLiteralMachine() {
       return LexerGrammar.DIGIT.concatenate(LexerGrammar.DIGIT.loop()); //TODO: Is this form preferable?
    }

    public static LexerMachine getFloatLiteralMachine() {
//        LexerMachine intAndPeriod = new FollowedByFSM(getIntLiteralMachine(), getPeriodMachine());
//        return new FollowedByFSM(intAndPeriod, getIntLiteralMachine());
        LexerMachine intAndPeriod = LexerGrammar.INT_LITERAL.concatenate(LexerGrammar.PERIOD);
        return intAndPeriod.concatenate(LexerGrammar.INT_LITERAL);

    }

    public static LexerMachine getStringLiteralMachine() {
        //TODO: Fix implementation, how to allow any character between quotes?
        LexerMachine letterOrWhitespace = LexerGrammar.LETTER.union(LexerGrammar.WHITESPACE);
        LexerMachine lettersOrWhitespaces = letterOrWhitespace.loop();
        return LexerGrammar.QUOTE.concatenate(lettersOrWhitespaces).concatenate(LexerGrammar.QUOTE);
    }

    public static LexerMachine getBooleanLiteralMachine() {
        return new FollowedByFSM("true").union(new FollowedByFSM("false"));
    }

    public static LexerMachine getPlusMachine() {
        return new FollowedByFSM("+");
    }

    public static LexerMachine getMinusMachine() {
        return new FollowedByFSM("-");
    }

    public static LexerMachine getMultiplyMachine() {
        return new FollowedByFSM("*");
    }

    public static LexerMachine getDivideMachine() {
        return new FollowedByFSM("/");
    }

    public static LexerMachine getAssignmentMachine() {
        return new FollowedByFSM("=");
    }

    public static LexerMachine getEqualityMachine() {
        return new FollowedByFSM("==");
    }

    public static LexerMachine getGreaterThanMachine() {
        return new FollowedByFSM(">");
    }

    public static LexerMachine getGreaterThanOrEqualsMachine() {
        return new FollowedByFSM(">=");
    }

    public static LexerMachine getLessThanMachine() {
        return new FollowedByFSM("<");
    }

    public static LexerMachine getLessThanOrEqualsMachine() {
        return new FollowedByFSM("<=");
    }

    public static LexerMachine getIfMachine() {
        return new FollowedByFSM("if");
    }

    public static LexerMachine getElseMachine() {
        return new FollowedByFSM("else");
    }

    public static LexerMachine getWhileMachine() {
        return new FollowedByFSM("while");
    }

    public static LexerMachine getForMachine() {
        return  new FollowedByFSM("for");
    }

    public static LexerMachine getIntTypeMachine() {
        return new FollowedByFSM("int");
    }

    public static LexerMachine getFloatTypeMachine() {
        return new FollowedByFSM("float");
    }

    public static LexerMachine getBooleanTypeMachine() {
        return new FollowedByFSM("boolean");
    }

    private static LexerMachine getLetterOrDigitMachine() {
//        return new UnionFSM(getLetterMachine(), getDigitMachine());
        return LexerGrammar.LETTER.union(LexerGrammar.DIGIT);
    }
}
