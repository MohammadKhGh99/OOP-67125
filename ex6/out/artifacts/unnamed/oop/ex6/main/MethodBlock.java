package oop.ex6.main;

import java.util.LinkedList;

import static oop.ex6.AllHelpingVariables.*;
import static oop.ex6.AllPatterns.*;

public class MethodBlock extends Block {

    /**
     * the constructor of method block, calling the constructor of Block class.
     *
     * @param globalVariables the global variables for this block.
     */
    public MethodBlock(LinkedList<Variable> globalVariables) {
        super(globalVariables);
    }

    /**
     * this method parses MethodBlock, check if it is legal.
     *
     * @param blockLines the lines for this block.
     * @throws IllegalFormationException if there is an error in parsing the block.
     */
    @Override
    public void parsingBlock(LinkedList<String> blockLines) throws Exception {
        Method currentMethod = parsingNewMethod(blockLines.getFirst());
        for (int i = 1; i < blockLines.size(); i++) {
            checkLine(currentMethod, blockLines, i);
            this.currentVariables.addAll(currentMethod.getLocalVariables());
        }
        GlobalBlock.addMethod(currentMethod);
    }

    /**
     * this method checks if the name of the method is legal ...
     *
     * @param line - the line of the method name.
     * @return - the name of the method if it is a legal name.
     * @throws IllegalFormationException - if there is error with name of the method.
     */
    private String checkMethodName(String line) throws IllegalFormationException {
        line = line.replace(VOID, EMPTY_LINE).trim();
        String methodName = line.substring(0, line.indexOf(OPEN_BRACKET));
        if (GlobalBlock.getGlobalVariables().size() > 0)
            for (Variable variable : GlobalBlock.getGlobalVariables())
                if (variable.getVarName().equals(methodName))
                    throw new IllegalFormationException(SAME_VARIABLE_NAME_MSG);
        if (GlobalBlock.getMethodsList().size() > 0)
            for (Method method : GlobalBlock.getMethodsList())
                if (method.getMethodName().equals(methodName))
                    throw new IllegalFormationException(SAME_METHOD_NAME_MSG);
        if (KEYWORDS.contains(methodName))
            throw new IllegalFormationException(RESERVED_KEYWORD_MSG);
        return methodName;
    }

    /**
     * this method is parsing new method if it is legal.
     *
     * @param line - the line of the new method.
     * @return - if the method is legal returns a new methodClass represent on this method, not legal
     * returns exception.
     * @throws IllegalFormationException - if the method is illegal returns exception with appropriate message.
     */
    private Method parsingNewMethod(String line) throws IllegalFormationException {
        LinkedList<Variable> methodParameters = new LinkedList<>();
        String methodName = checkMethodName(line);
        line = line.substring(line.indexOf(OPEN_BRACKET) + 1, line.lastIndexOf(CLOSE_BRACKET)).trim();
        if (whiteSpace.matcher(line).matches() || line.equals(EMPTY_LINE))
            return new Method(methodName, methodParameters, new LinkedList<>());
        String[] paramArray = line.split(COMMA);
        for (String param : paramArray) {
            String[] varSpecs = param.trim().split(WHITE_SPACE_REGEX);
            String type = DEFAULT_TYPE;
            String varName = DEFAULT_NAME;
            boolean isFinal = false;
            if (param.contains(FINAL)) {
                if (varSpecs.length != 3)
                    throw new IllegalFormationException(INAPPROPRIATE_METHOD);
                isFinal = true;
                type = varSpecs[1];
                varName = varSpecs[2];
            } else {
                if (varSpecs.length != 2)
                    throw new IllegalFormationException(INAPPROPRIATE_METHOD);
                type = varSpecs[0];
                varName = varSpecs[1];
            }
            for (Variable variable : methodParameters)
                if (variable.getVarName().equals(varName))
                    throw new IllegalFormationException(DUPLICATE_SAME_VARIABLE);
            for (Variable variable : GlobalBlock.getGlobalVariables())
                if (variable.getVarName().equals(varName))
                    throw new IllegalFormationException(DUPLICATE_SAME_VARIABLE);
            methodParameters.add(new Variable(type, varName, new Object(), isFinal, true, true));
        }
        return new Method(methodName, methodParameters, new LinkedList<>());
    }
}
