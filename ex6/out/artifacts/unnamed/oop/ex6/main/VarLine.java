package oop.ex6.main;


import java.util.LinkedList;
import java.util.regex.Pattern;

import static oop.ex6.AllHelpingVariables.*;
import static oop.ex6.AllPatterns.*;

/**
 * this class describes the Variable line methods.
 */
public class VarLine {

    /**
     * if the variable is final or not.
     */
    private boolean isFinal;

    /**
     * the type of the variable.
     */
    private String type;


    /**
     * the pattern to check the shape of variable assignment.
     */
    private Pattern variablePattern = Pattern.compile(VARIABLE_ASSIGN_WITHOUT);

    private LinkedList<Variable> globalVariables;
    private LinkedList<Variable> currentVariables;

    /**
     * the constructor for this class.
     */
    public VarLine(LinkedList<Variable> globalVariables) {
        this.globalVariables = globalVariables;
        this.isFinal = false;
        this.type = DEFAULT_TYPE;
    }

    /**
     * this method reads the line and extracts the wanted Strings from it.
     *
     * @param line the required line to read.
     * @return LinkedList that contains the wanted items from this line.
     * @throws IllegalFormationException if there is a problem with values or types or anything else.
     */
    public LinkedList<Variable> parsingLine(String line) throws Exception {
        String varName = DEFAULT_NAME;
        Object varValue = null;
        line = line.replace(SEMI_COLON, EMPTY_LINE).trim();
        String[] varSpecs = line.split(WHITE_SPACE_REGEX);

        if (!variableAssignWith.matcher(line).matches()) {
            // deleting "white spaces" to check if there is "final" keyword.
            varSpecs = checkFinal(varSpecs, line);

            deletingWhiteSpaces(varSpecs);
        }
        for (String currentVar : varSpecs) { // getting all the variables in the line.
            String[] varArray = currentVar.split(EQUAL);
            for (int i = 0; i < varArray.length; i++) varArray[i] = varArray[i].trim();
            if (type.equals(DEFAULT_TYPE)) {
                varName = checkVariableAssigning(varName, varArray);
            } else if (isFinal) { // if the variable is final.
                Object[] array = checkFinalVariable(currentVar, varArray);
                varName = (String) array[0];
                varValue = array[1];
            } else { // if the variable is not final.
                if (varArray.length == 2)
                    varValue = checkType(type, varArray[1]);
                varName = varArray[0];
            }
            if (currentVariables != null)
                varValue = checkExistence(varName, varArray);
            else currentVariables = new LinkedList<>();
            boolean isInit = true;
            if (varValue == null) isInit = false;
            currentVariables.add(new Variable(type, varName, varValue, isFinal, false, isInit));
        }
        return currentVariables;
    }

    /**
     * this method check the existence of variable name
     * @param varName - the name want to check
     * @param varArray - array of string have the names of the variables
     * @return - the object of this name if exist, otherwise null
     * @throws IllegalFormationException - if there is a problem with variable like duplicate name of
     * inappropriate name.
     */
    private Object checkExistence(String varName, String[] varArray) throws Exception {
        for (Variable var : currentVariables) {
            if (var.getVarName().equals(varName))
                if (!type.equals(DEFAULT_TYPE))
                    throw new IllegalFormationException(DUPLICATE_SAME_VARIABLE);
                else if (checkValue(var.getType(), varArray[1]))
                    return checkType(var.getType(), varArray[1]);
                else throw new IllegalFormationException(INAPPROPRIATE_TYPE_MSG);
        }
        return null;
    }

    /**
     * this method checking if the receive variable final or not
     * @param currentVar - the name of the variable we want to change
     * @param varArray -  array of string have the names of the variables
     * @return - array of object contains the name of the variable and the value of the variable.
     * @throws IllegalFormationException - if there is a problem with variable related to if is final.
     */
    private Object[] checkFinalVariable(String currentVar, String[] varArray) throws Exception {
        if (!currentVar.contains(EQUAL)) // the final variable has no value!
            throw new IllegalFormationException(FINAL_NO_VALUE_MSG);
        else if (variablePattern.matcher(currentVar).matches()) {
            return new Object[]{varArray[0], checkType(type, varArray[1])};
        } else throw new IllegalFormationException(FINAL_INITIALIZATION);
    }

    /**
     * This method checks Variable assigning, if it legal returns the name of the variable,otherwise exception
     * @param varName - the name of the variable we want to check
     * @param varArray - array of the variable
     * @return - if it legal returns the name of the variable,otherwise exception
     * @throws IllegalFormationException - Exception if the variable not initialized ,final or wrong value
     */
    private String checkVariableAssigning(String varName, String[] varArray) throws IllegalFormationException {
        boolean thereIS = false;
        if (this.globalVariables == null)
            throw new IllegalFormationException(NOT_INITIALIZED);
        for (Variable variable : globalVariables) {
            if (variable.getVarName().equals(varArray[0])) {
                if (variable.getIsFinal())
                    throw new IllegalFormationException(FINAL_INITIALIZATION);
                thereIS = true;
                type = variable.getType();
                varName = variable.getVarName();
                isFinal = false;
                break;
            }
        }
        if (!thereIS || varArray.length == 1) throw new IllegalFormationException(VALUE_MSG);
        return varName;
    }

    /**
     * this method deletes the white spaces from the current line.
     *
     * @param varSpecs the array of the variables assignments (if there are!)
     * @throws IllegalFormationException if there is a problem with the variable assignment.
     */
    private void deletingWhiteSpaces(String[] varSpecs) throws IllegalFormationException {
        for (int i = 0; i < varSpecs.length; i++) {
            boolean withoutValue = varSpecs[i].matches(VAR_NAME_REGEX);
            boolean withValue = variableAssignWithout.matcher(varSpecs[i]).matches();
            if (withValue || withoutValue || varSpecs[i].matches(ASSIGN_2VARIABLE))
                varSpecs[i] = varSpecs[i].trim(); // deleting "white spaces".
            else throw new IllegalFormationException(VALUE_MSG);
        }
    }

    /**
     * this method checks if there is final keyword.
     *
     * @param varSpecs the array of the words in the current line.
     * @param line     the current line.
     * @return true if there is final keyword, false otherwise.
     */
    private String[] checkFinal(String[] varSpecs, String line) {
        if (varSpecs[0].equals(FINAL)) {
            line = line.replace(FINAL, "");
            varSpecs = line.trim().split(WHITE_SPACE_REGEX);
            isFinal = true;
        }
        type = varSpecs[0];
        line = line.replace(type, ""); // deleting the type from the line.
        return line.trim().split(","); // deleting "," if there are more than one var.

    }

    /**
     * this method checks the given value if it is appropriate for the given type.
     *
     * @param type  the type of the value.
     * @param value the value to check.
     * @return int value if the type is int, double value if the type is double, String value if the type is
     * String, char value if the type is char, boolean value if the type is boolean.
     * @throws IllegalFormationException if there is problem with the given value (inappropriate), or if the given type is
     *                                   not supported.
     */
    private Object checkType(String type, String value) throws Exception {
        value = value.trim();
        Variable variable = GlobalBlock.getVariableByName(value);
        if (variable != null) {
            if (type.equals(BOOLEAN)) {
                if (booleanTypes.matcher(variable.getType()).matches() && variable.getIsInit())
                    return variable.getVarValue();
            }else if (type.equals(DOUBLE) && (variable.getType().equals(INT) || variable.getType().equals(DOUBLE))) {
                return variable.getVarValue();
            }else{
                    if (variable.getType().equals(type) && variable.getIsInit())
                        return variable.getVarValue();
                }
            }
        switch (type) {
            case INT:
                if (isInt.matcher(value).matches()) return Integer.parseInt(value);
                else throw new IllegalFormationException(NOT_INT_MSG);
            case DOUBLE:
                if (isDouble.matcher(value).matches()) return Double.parseDouble(value);
                else throw new IllegalFormationException(NOT_DOUBLE_MSG);
            case STRING:
                if (isString.matcher(value).matches()) return value;
                else throw new IllegalFormationException(NOT_STRING_MSG);
            case CHAR:
                if (isChar.matcher(value).matches()) return value;
                else throw new IllegalFormationException(NOT_CHAR_MSG);
            case BOOLEAN:
                if (isBoolean.matcher(value).matches())
                    if (value.equals(TRUE)) return true;
                    else if (value.equals(FALSE)) return false;
                    else if (value.matches(IS_INT)) return Integer.parseInt(value);
                    else if (value.matches(IS_DOUBLE)) return Double.parseDouble(value);
                    else throw new IllegalFormationException(NOT_BOOLEAN_MSG);
        }
        throw new IllegalFormationException(NOT_SUPPORTED_TYPE_MSG);
    }
}
