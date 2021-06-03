package oop.ex6.main;

import java.util.*;

import static oop.ex6.AllHelpingVariables.*;
import static oop.ex6.AllPatterns.*;

public class Parsing {

    /**
     * parsing the file, put the method in hash map with the line of start method and finish
     *
     * @param codeLines - the lines of the file
     * @throws IllegalFormationException - Exception >> illegal things with appropriate message
     */
    public static void parsingFile(LinkedList<String> codeLines) throws Exception {
        HashMap<Integer, Integer> methodsLines = getMethodsLines(codeLines);
        LinkedList<Integer> startIndexes = new LinkedList<>(methodsLines.keySet());
        LinkedList<Integer> endIndexes = new LinkedList<>(methodsLines.values());
        GlobalBlock.parsingBlock(codeLines, startIndexes, endIndexes);
    }

    /**
     * this method parses on the files searching on the methods of the file, put them in hash map with two
     * Integers indicates the line of the start of specific method and the end of it.
     *
     * @param codeLines - the lines of the file
     * @return - HashMap contains the methods of the file
     * @throws IllegalFormationException - Exception if the method not illegal like no return...
     */
    private static HashMap<Integer, Integer> getMethodsLines(LinkedList<String> codeLines) throws IllegalFormationException {
        HashMap<Integer, Integer> methodsLinesNums = new HashMap<>();
        int i = 0;
        while (i < codeLines.size()) {
            int methodLinesCount = 0;
            String line = codeLines.get(i);
            if (methodPattern.matcher(line).matches()) {
                int methodStart = i;
                methodLinesCount++;
                int parenthesesCount = 1;
                i++;
                while (parenthesesCount > 0 && i < codeLines.size()) {
                    line = codeLines.get(i);
                    methodLinesCount++;
                    if (methodPattern.matcher(line).matches())
                        throw new IllegalFormationException(METHOD_IN_METHOD);
                    if (ifPattern.matcher(line).matches() || whilePattern.matcher(line).matches()) {
                        parenthesesCount++;
                    } else if (illegalBlockStartP.matcher(line).matches()) {
                        throw new IllegalFormationException(NOT_IF_OR_WHILE);
                    } else if (blockEnd.matcher(line).matches()) {
                        if (parenthesesCount == 1) {
                            String returnLine = codeLines.get(i - 1);
                            if (!returnPattern.matcher(returnLine).matches())
                                throw new IllegalFormationException(RETURN_ERROR);
                        }
                        parenthesesCount--;
                    }
                    i++;
                }
                if (parenthesesCount == 0)
                    methodsLinesNums.put(methodStart, methodStart + methodLinesCount - 1);
                else throw new IllegalFormationException(BRACKETS_PROBLEM_MSG);
            } else i++;
        }
        return methodsLinesNums;
    }
}
