package oop.ex6;

import java.util.Arrays;
import java.util.LinkedList;

public class AllHelpingVariables {

    public static final String ERROR_ASSIGNING="ERROR: problem with assigning multiple variables!";
    public static final String ILLEGAL_FORMATION = "1", IO_EXCEPTION_RESULT = "2", LEGAL = "0";
    public static final String ILLEGAL_COMMENTS_MSG = "ERROR: Illegal comments!";
    public static final String FINAL_NO_VALUE_MSG = "ERROR: the final variable has no value!";
    public static final String FINAL_INITIALIZATION = "ERROR: there is a problem with the final variable " +
            "initialization!";
    public static final String DUPLICATE_SAME_VARIABLE = "ERROR: duplicate same name variables!";
    public static final String INAPPROPRIATE_TYPE_MSG = "ERROR: inappropriate type!";
    public static final String NOT_INT_MSG = "ERROR: the given value is not int!";
    public static final String NOT_DOUBLE_MSG = "ERROR: the given value is not double!";
    public static final String NOT_STRING_MSG = "ERROR: the given value is not String!";
    public static final String NOT_CHAR_MSG = "ERROR: the given value is not char!";
    public static final String NOT_BOOLEAN_MSG = "ERROR: the given value is not boolean!";
    public static final String NOT_SUPPORTED_TYPE_MSG = "ERROR: the given type is not supported or not legal!";
    public static final String BRACKETS_PROBLEM_MSG = "ERROR: the number of '{' and the number of '}' " +
            "are not the same!";
    public static final String SAME_METHOD_NAME_MSG = "ERROR: there is a method like this method name before!";
    public static final String SAME_VARIABLE_NAME_MSG = "ERROR: you can't name a method with a name of an existing " +
            "global variable!";
    public static final String RESERVED_KEYWORD_MSG = "ERROR: you can't name a method with a reserved keyword!";
    public static final String FILE_TYPE_MSG = "ERROR: the file should be from .sjavac type!";
    public static final String METHOD_CALL_ERROR1 = "ERROR: not same number of parameters!";
    public static final String METHOD_CALL_ERROR2 = "ERROR: the method should not take any parameter!";
    public static final String METHOD_IN_METHOD = "ERROR: you can't make a method in a method!";
    public static final String NOT_IF_OR_WHILE = "ERROR: not an if line or a while line in method!";
    public static final String INAPPROPRIATE_METHOD = "ERROR: inappropriate method shape!";
    public static final String SEMI_COLON = ";";
    public static final String EMPTY_LINE = "";
    public static final String NOT_INITIALIZED = "ERROR: the variable has not been initialized yet!";
    public static final String IF_ERROR = "ERROR: there is a problem with the if condition!";
    public static final String WHILE_ERROR = "ERROR: there is a problem with the while condition!";
    public static final String GLOBAL_ERROR = "ERROR: you can just make global variables and methods in Global";
    public static final String METHOD_CALL_VALUES_ERROR = "ERROR: the values in calling method is " +
            "inappropriate!";
public static final String SYNTAX_ERROR="ERROR: illegal syntax in method!";

    public static final String LEGAL_COMMENTS_REGEX = "//.*";
    /**
     * illegal comments regex.
     */
    public static final String ILLEGAL_COMMENTS = "\\s*(?:/\\*|/\\*\\*|.+//).*\\s*";

    public static final String ILLEGAL_BLOCK_START = ".*\\{.*";

//    public static final String ILLEGAL_END_MSG = "ERROR: the end bracket should be alone in a line!";

    /**
     * the primitive types of variables.
     */
    public static final String INT = "int", DOUBLE = "double", STRING = "String", CHAR = "char",
            BOOLEAN = "boolean";
    public static final String BOOLEAN_TYPES = BOOLEAN + "|" + INT + "|" + DOUBLE;
    public static final String NOT_BOOLEAN_ERROR = "ERROR: the variable is not boolean!";
    /**
     * other keywords to check.
     */
    public static final String VOID = "void", FINAL = "final", IF = "if", WHILE = "while", TRUE = "true",
            FALSE = "false", RETURN = "return";

    public static final String IS_INT = "-?\\d+", IS_DOUBLE = "-?(\\d+.?\\d*)|(\\d*.\\d+)",IS_CHAR = "'.'"
            , IS_STRING = "\".*\"", IS_BOOLEAN = IS_INT + "|" + IS_DOUBLE + "|" + TRUE + "|" + FALSE;

    public static final String EQUAL = "=", DEFAULT_TYPE = "", DEFAULT_NAME = "";

    /**
     * searching fro whitespaces regex.
     */
    public static final String WHITE_SPACE_REGEX = "\\s+";

    /**
     * variable name and value regex.
     */
    public static final String VAR_NAME_REGEX = "\\s*(?:_\\w+|[a-zA-Z]+\\w*)\\s*";
    public static final String VAR_VALUE_REGEX = "(?:(?:" + IS_INT + ")|(?:" + IS_DOUBLE + ")|(?:" + IS_CHAR +
            ")|(?:" + IS_STRING + ")|" + TRUE + "|" + FALSE + ")";
    public static final String BLOCK_START = ".+\\{\\s*", BLOCK_END = "\\s*}\\s*";

    public static final String VARIABLE_NAME = "\\s*([a-zA-Z]|_\\w)+\\w*\\s*";

    public static final String TYPES = "(?:int|double|String|char|boolean)";

    public static final String ASSIGN_2VALUE = VAR_NAME_REGEX + EQUAL + "\\s*" + VAR_VALUE_REGEX + "\\s*";
    public static final String VARIABLES_REGEX = "\\s*(final )?\\s*" + TYPES + "\\s+" + ASSIGN_2VALUE
            + SEMI_COLON + "\\s*";

//    public static final String JUST_VARIABLE =
//            "\\s*" + TYPES + WHITE_SPACE_REGEX + VAR_NAME_REGEX + "\\s*" + SEMI_COLON + "\\s*";

    public static final String ASSIGN_2VARIABLE ="\\s*"+
            TYPES+"?"+VAR_NAME_REGEX + EQUAL + VAR_NAME_REGEX;
//    public static final String MULTI_VARIABLE_DEF=
//            "\\s*(final )?\\s*"+TYPES+"\\s+"+"(?:(?:"+ASSIGN_2VALUE+")|(?:"+VAR_NAME_REGEX+EQUAL+VAR_NAME_REGEX+")|(?:"+


    private static String[] words = new String[]{INT, DOUBLE, STRING, CHAR, BOOLEAN, VOID, FINAL, IF, WHILE,
            TRUE, FALSE, RETURN};
    public static final LinkedList<String> KEYWORDS = new LinkedList<>(Arrays.asList(words));
    public static final String GLOBAL_VARIABLE = "\\s*" + TYPES + " +" + VAR_NAME_REGEX + "\\s*;\\s*";


    public static final String METHOD_REGEX = "\\s*" + VOID + " +[a-zA-Z]\\w* *\\(.*\\) *\\{\\s*";
    public static final String METHOD_CALL = "\\s*[a-zA-Z]\\w* *\\(.*\\) *;\\s*";

//    public static final String INT_REGEX = "\\d+", DOUBLE_REGEX = "\\d*\\.?\\d*";

    public static final String IF_REGEX = "\\s*" + IF + " *\\( *.+ *\\) *\\{\\s*";
    public static final String WHILE_REGEX = "\\s*" + WHILE + " *\\( *.+ *\\) *\\{\\s*";

    public static final String COMMA = ",";
    public static final String OPEN_BRACKET = "(";
    public static final String CLOSE_BRACKET = ")";
    public static final String SPACE = " ";
    public static final String CONDITION_SPLITTER = "(&{2}|\\|{2})+";

//    public static boolean checkBooleanExpression(String expression) throws Exception{
//        String[] expParts=expression.split(CONDITION_SPLITTER);
//        for (String part:expParts){
//
//            if (part.split(" ").length!=1) throw new ValueException(VALUE_MSG);
//
//        }
//    }

//    private boolean checkCondition(String condition){
//        condition=condition.trim();
//        if (condition.matches(BOOLEAN_CHECK))
//    }


    /**
     * the message to print when there is a TypeException.
     */
    public static final String TYPE_MSG = "ERROR: there is a problem with the type!!!";

    /**
     * the message to print when there is a ValueException.
     */
    public static final String VALUE_MSG = "ERROR: wrong value to current type!!!";

    /**
     * the message to print if the IOException has been thrown.
     */
    public static final String IO_EXCEPTION_MSG = "ERROR: can't read the file !!!";


    public static final String RETURN_REGEX = "\\s*" + RETURN + "\\s*;\\s*";
    public static final String RETURN_ERROR = "ERROR: the method should end with return!";

    public static final String IS_CONSTANT = IS_DOUBLE + "|" + IS_STRING + "|" + IS_INT + "|" + IS_CHAR + "|" + TRUE + "|" + FALSE;

    public static boolean checkValue(String type, String valueToCheck) {
        switch (type) {
            case INT:
                if (valueToCheck.matches(IS_INT)) return true;
                else break;
            case DOUBLE:
                if (valueToCheck.matches(IS_DOUBLE)) return true;
                else break;
            case CHAR:
                if (valueToCheck.matches(IS_CHAR)) return true;
                else break;
            case STRING:
                if (valueToCheck.matches(IS_STRING)) return true;
                else break;
            case BOOLEAN:
                if (valueToCheck.matches(IS_BOOLEAN)) return true;
                else break;
        }
        return false;
    }

    public static final String VARIABLE_ASSIGN = VAR_NAME_REGEX + EQUAL + VAR_VALUE_REGEX + "\\s*;\\s*";
    public static final String VARIABLE_ASSIGN_WITHOUT = VAR_NAME_REGEX + EQUAL + "\\s*" + VAR_VALUE_REGEX +
            "\\s*";
}
