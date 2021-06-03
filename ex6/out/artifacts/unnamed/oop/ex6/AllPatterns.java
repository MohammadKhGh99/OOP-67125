package oop.ex6;

import java.util.regex.Pattern;

import static oop.ex6.AllHelpingVariables.*;

public class AllPatterns {

    public static Pattern legalComments=Pattern.compile(LEGAL_COMMENTS_REGEX);
    public static Pattern illegalComments=Pattern.compile(ILLEGAL_COMMENTS);
    public static Pattern booleanTypes=Pattern.compile(BOOLEAN_TYPES);
    public static Pattern isInt=Pattern.compile(IS_INT);
    public static Pattern isDouble=Pattern.compile(IS_DOUBLE);
    public static Pattern isChar=Pattern.compile(IS_CHAR);
    public static Pattern isString=Pattern.compile(IS_STRING);
    public static Pattern isBoolean=Pattern.compile(IS_BOOLEAN);
    public static Pattern whiteSpace=Pattern.compile(WHITE_SPACE_REGEX);
    public static Pattern varNamePattern=Pattern.compile(VAR_NAME_REGEX);
    public static Pattern blockEnd=Pattern.compile(BLOCK_END);
    public static Pattern typesPattern=Pattern.compile(TYPES);
    public static Pattern assign2Value=Pattern.compile(ASSIGN_2VALUE);
    public static Pattern variablesAssign=Pattern.compile(VARIABLES_REGEX);
    public static Pattern assign2Variable=Pattern.compile(ASSIGN_2VARIABLE);
    public static Pattern globalVariablesPattern=Pattern.compile(GLOBAL_VARIABLE);
    public static Pattern methodPattern=Pattern.compile(METHOD_REGEX);
    public static Pattern methodCallPattern=Pattern.compile(METHOD_CALL);
    public static Pattern ifPattern=Pattern.compile(IF_REGEX);
    public static Pattern whilePattern=Pattern.compile(WHILE_REGEX);
    public static Pattern returnPattern=Pattern.compile(RETURN_REGEX);
    public static Pattern isConstantPattern=Pattern.compile(IS_CONSTANT);
    public static Pattern variableAssignWithout=Pattern.compile(VARIABLE_ASSIGN_WITHOUT);
    public static Pattern variableAssignWith=Pattern.compile(VARIABLE_ASSIGN);
    public static Pattern illegalBlockStartP=Pattern.compile(ILLEGAL_BLOCK_START);
}
