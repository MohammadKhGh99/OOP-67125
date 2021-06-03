package oop.ex6.main;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static oop.ex6.AllPatterns.*;
import static oop.ex6.AllHelpingVariables.*;

/**
 * This GlobalBlock's class represent to the Global Variable of the file we want to research, all the method
 * and method member are static because there is one global block in each file and we want to add on them,
 * meaning we didn't want instance of them.
 */
public class GlobalBlock {

    // static linkedList of Variable's class indicates to global variables of file
    private static LinkedList<Variable> globalVariables = new LinkedList<>();

    // static linkedList of Method's class indicates to the method of the file
    private static LinkedList<Method> methodsList = new LinkedList<>();

    private static LinkedList<String> allLines = new LinkedList<>();

    public static void getAllLines(LinkedList<String> lines) {
        allLines = lines;
    }

    /**
     * this method reset the two linkedList (Global Variables, Method List)
     */
    public static void reset() {
        globalVariables = new LinkedList<>();
        methodsList = new LinkedList<>();
    }

    /**
     * This method is passing on the lines twice, the first passing is to add the legal global variable,
     * the second passing is on the method, checking the legal methods and add them to LinkedList of method
     * List, otherwise illegal comment... return exception
     *
     * @param blockLines   the blocks of all the global scope.
     * @param startIndexes the list that contains the starting indexes of all methods.
     * @param endIndexes   the list that contains the ending indexes of all methods.
     * @throws IllegalFormationException
     */
    public static void parsingBlock(LinkedList<String> blockLines, LinkedList<Integer> startIndexes,
                                    LinkedList<Integer> endIndexes) throws Exception {
        int i = 0;
        while (i < blockLines.size()) { // taking all the global variables, and checking illegal comments.
            String line = blockLines.get(i);
            checkIllegalComments(line);
            if (!legalComments.matcher(line).matches())
                if (illegalComments.matcher(line).matches())
                    throw new IllegalFormationException(ILLEGAL_COMMENTS_MSG);
                else if (!checkMethodIndexes(i, startIndexes, endIndexes)) {
                    if (checkNotVariableLine(line)) {
                        throw new IllegalFormationException(GLOBAL_ERROR);
                    } else {
                        VarLine varLine = new VarLine(GlobalBlock.getGlobalVariables());
                        GlobalBlock.addAllVariables(varLine.parsingLine(line));
                    }
                }
            i++;
        }

        for (int j = 0; j < startIndexes.size(); j++) {
            LinkedList<String> methodBlockLines = new LinkedList<>();
            MethodBlock methodBlock = new MethodBlock(GlobalBlock.getGlobalVariables());

            int index = startIndexes.get(j);
            String methLine = blockLines.get(index);
            methodBlockLines.add(methLine);
            index++;
            while (index <= endIndexes.get(j)) {
                methLine = blockLines.get(index);
                methodBlockLines.add(methLine);
                index++;
            }
            methodBlock.parsingBlock(methodBlockLines);
        }
    }

    public static int getIndexByLine(String line) {
        for (int i = 0; i < allLines.size(); i++) {
            if (allLines.get(i).equals(line))
                return i;
        }
        return -1;
    }

    /**
     * this method counts how many times the given string appears in the given text.
     *
     * @param text       the text to search in.
     * @param searchChar the string to search for.
     * @return the number of times that the given string appears in the given text.
     */
    private static int countCharOccurrence(String text, String searchChar) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            String ch = text.charAt(i) + "";
            if (ch.equals(searchChar))
                count++;
        }
        return count;
    }

    /**
     * this method checks if there is multiple variables in the given line.
     *
     * @param variablesLine the given line to check in.
     * @return true if there is assigning for multiple variables, false otherwise.
     * @throws IllegalFormationException
     */
    private static boolean checkMultiVariables(String variablesLine) throws IllegalFormationException {
        Pattern pattern = Pattern.compile("\\s*(?:" + FINAL + " )?\\s*" + TYPES + "\\s+.+");
        Matcher matcher = pattern.matcher(variablesLine);
        if (matcher.matches()) {
            variablesLine = variablesLine.replace(variablesLine.split(WHITE_SPACE_REGEX)[0], EMPTY_LINE).trim();
            variablesLine = variablesLine.replace(SEMI_COLON, EMPTY_LINE);
            String[] allVariables = variablesLine.split(COMMA);
            int commaCounter = countCharOccurrence(variablesLine, COMMA);
            if (allVariables.length != (commaCounter + 1))
                throw new IllegalFormationException(ERROR_ASSIGNING);
            for (String line : allVariables) {
                boolean condition1 = assign2Value.matcher(line).matches();
                boolean condition2 = line.matches(VAR_NAME_REGEX + EQUAL + VAR_NAME_REGEX);
                boolean condition3 = varNamePattern.matcher(line).matches();
                if (condition1 || condition2 || condition3) return true;
            }
        }
        return false;
    }

    /**
     * this method using the above method to check the given line if it is not variable line.
     *
     * @param line the given line to check.
     * @return true if the line not a variable line, false otherwise.
     * @throws IllegalFormationException
     */
    private static boolean checkNotVariableLine(String line) throws IllegalFormationException {
        boolean condition = !variablesAssign.matcher(line).matches() &&
                !variableAssignWith.matcher(line).matches() &&
                !globalVariablesPattern.matcher(line).matches() &&
                !line.matches(ASSIGN_2VARIABLE + SEMI_COLON + "\\s*");
        if (condition) {
            if (!checkMultiVariables(line))
                return true;
        }
        return false;
    }

    /**
     * this method checks if the given index is in the indexes of the methods indexes.
     *
     * @param lineIndex    the required index to check.
     * @param startIndexes linked list that contains when all the methods start.
     * @param endIndexes   linked list that contains when all the methods ends.
     * @return true if the index in the methods indexes, false otherwise.
     */
    private static boolean checkMethodIndexes(int lineIndex, LinkedList<Integer> startIndexes,
                                              LinkedList<Integer> endIndexes) {
        for (int i = 0; i < startIndexes.size(); i++) {
            int startIndex = startIndexes.get(i);
            int endIndex = endIndexes.get(i);
            if (lineIndex >= startIndex && lineIndex <= endIndex) return true;
        }
        return false;
    }

    /**
     * this method checks illegal comments.
     *
     * @param commentLine - the line of the comment.
     * @throws IllegalFormationException - if the comment illegal returns exception with appropriate message.
     */
    private static void checkIllegalComments(String commentLine) throws IllegalFormationException {
        if (commentLine.matches(ILLEGAL_COMMENTS))
            throw new IllegalFormationException(ILLEGAL_COMMENTS_MSG);
    }

    /**
     * this method checks if the string contains in global variables
     *
     * @param name - the string wants to check
     * @return - true if it is global variable, otherwise false
     */
    public static boolean contains(String name) {
        for (Variable variable : globalVariables) {
            if (variable.getVarName().equals(name))
                return true;
        }
        return false;
    }

    /**
     * this method returns the appropriate variable in the global block by its name.
     *
     * @param varName the name of the variable.
     * @return the appropriate variable in the global block by its name.
     */
    public static Variable getVariableByName(String varName) {
        for (Variable variable : globalVariables) {
            if (variable.getVarName().equals(varName))
                return variable;
        }
        return null;
    }

    /**
     * this method adds all the global variables to the linked list
     *
     * @param variables - the legal variables want to add
     */
    public static void addAllVariables(LinkedList<Variable> variables) {
        globalVariables.addAll(variables);
    }

    /**
     * this method adds method to the method linked list
     *
     * @param method - the legal method wants to add
     */
    public static void addMethod(Method method) {
        methodsList.add(method);
    }

    /**
     * this method gets the linkedList of global variables
     *
     * @return - the global variables
     */
    public static LinkedList<Variable> getGlobalVariables() {
        return globalVariables;
    }

    /**
     * this method gets the linkedList of methodList
     *
     * @return - the method list
     */
    public static LinkedList<Method> getMethodsList() {
        return methodsList;
    }
}
