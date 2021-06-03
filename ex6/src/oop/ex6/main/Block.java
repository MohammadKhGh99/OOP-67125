package oop.ex6.main;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static oop.ex6.AllHelpingVariables.*;
import static oop.ex6.AllPatterns.*;

/**
 * this class describes each block of methods, if \ while conditions.
 */
public abstract class Block {

    /**
     * the global variables for this block.
     */
    protected LinkedList<Variable> globalVariables;

    /**
     * the current variables of this block.
     */
    protected LinkedList<Variable> currentVariables;

    /**
     * the current method if this block, if it is if \ while block.
     */
    protected Method currentMethod;


    /**
     * the default constructor of this class.
     */
    public Block() {
        this.currentVariables = new LinkedList<>();
    }

    /**
     * the constructor to use in defining the methods blocks.
     *
     * @param globalVariables the global variables for the current block.
     */
    public Block(LinkedList<Variable> globalVariables) {
        this.globalVariables = globalVariables;
        this.currentVariables = new LinkedList<>();
    }

    /**
     * the constructor to use for if \ while blocks.
     *
     * @param globalVariables the global variables for this block.
     * @param currentMethod   the current method which the if \ while blocks are in.
     */
    public Block(LinkedList<Variable> globalVariables, Method currentMethod) {
        this(globalVariables);
        this.currentMethod = currentMethod;
    }

    /**
     * this method parses the block, check if it is legal.
     *
     * @param blockLines the lines for this block.
     * @throws IllegalFormationException if there is an error in parsing the block.
     */
    public abstract void parsingBlock(LinkedList<String> blockLines) throws Exception;


    /**
     * this method parses the line to get the variables from it.
     *
     * @param currentMethod the current method that this block found in.
     * @param line          the line to parse.
     * @throws IllegalFormationException
     */
    protected void variableLine(Method currentMethod, String line) throws Exception {
        VarLine varLine = new VarLine(GlobalBlock.getGlobalVariables());
        currentMethod.addAllVariables(varLine.parsingLine(line));
    }

    /**
     * this method adds the variables in the current method and the global to one linked list.
     *
     * @param currentMethod the current method that the block found in.
     * @return all the variables in current method and global.
     */
    private LinkedList<Variable> allVariables(Method currentMethod) {
        LinkedList<Variable> allVariables = new LinkedList<>(GlobalBlock.getGlobalVariables());
        allVariables.addAll(currentMethod.getLocalVariables());
        return allVariables;
    }

    /**
     * this method checks what the line means, variable definition, if block, while block, method call,
     * return line.
     *
     * @param currentMethod the method that we are in.
     * @param blockLines    the block lines of the parent block that this block in.
     * @param index         the index that we are in.
     * @throws IllegalFormationException
     */
    protected void checkLine(Method currentMethod, LinkedList<String> blockLines, int index)
            throws Exception {
        String line = blockLines.get(index);
        if (variablesAssign.matcher(line).matches() || variableAssignWith.matcher(line).matches()
                || globalVariablesPattern.matcher(line).matches()) {
            variableLine(currentMethod, line);
        } else if (ifPattern.matcher(line).matches()) {
            ifLine(blockLines, index, currentMethod);
        } else if (whilePattern.matcher(line).matches()) {
            whileLine(blockLines, index, currentMethod);
        } else if (methodCallPattern.matcher(line).matches()) {
            checkMethodCall(currentMethod, line);
        } else if (returnPattern.matcher(line).matches()) {
        } else if (blockEnd.matcher(line).matches()) {
        } else if (legalComments.matcher(line).matches()) {
        } else throw new IllegalFormationException(SYNTAX_ERROR);

    }

    /**
     * this method checks if the line is a method calling line.
     *
     * @param line          the line to check.
     * @param currentMethod the current method that we are in.
     * @throws IllegalFormationException if the calling method doesn't take as number of parameters as the
     *                                   reserved method, if the number of parameters in the calling method
     *                                   line not as number of parameters in the original method.
     */
    private static void checkMethodCall(Method currentMethod, String line) throws IllegalFormationException {
        line = line.trim();
        String methodName = line.substring(0, line.indexOf(OPEN_BRACKET)).replaceAll(SPACE, EMPTY_LINE);
        if (KEYWORDS.contains(methodName)) throw new IllegalFormationException(RESERVED_KEYWORD_MSG);
        boolean legal = true;
        for (Method method : GlobalBlock.getMethodsList()) {
            if (method.getMethodName().equals(methodName)) {
                String betweenBracketsVariables = line.substring(line.indexOf(OPEN_BRACKET) + 1,
                        line.lastIndexOf(CLOSE_BRACKET));
                String[] methodParams = betweenBracketsVariables.split(COMMA);
                int methodParamLength = method.getLocalParameters().size();
                int methodLocalLength = method.getLocalVariables().size();
                if (betweenBracketsVariables.equals(EMPTY_LINE) && (methodLocalLength > 0
                        || methodParamLength > 0))
                    throw new IllegalFormationException(METHOD_CALL_ERROR1);
                if (methodParamLength != methodParams.length && methodLocalLength != methodParams.length)
                    throw new IllegalFormationException(METHOD_CALL_ERROR1);
                for (int i = 0; i < methodParams.length; i++) {
                    String param = methodParams[i].trim();
                    if (method.getLocalVariables().size() > 0 || method.getLocalParameters().size() > 0) {
                        Variable variable = method.getLocalParameters().get(i);
                        boolean isConstant = isConstantPattern.matcher(param).matches();
                        boolean isVariable = varNamePattern.matcher(param).matches();
                        boolean isGlobalVariable = GlobalBlock.contains(variable.getVarName());
                        boolean isNull = variable.getVarValue() == null;
                        boolean isMethodVariable = currentMethod.contains(param);
                        if (isConstant)
                            if (!checkValue(variable.getType(), param)) {
                                legal = false;
                                break;
                            } else continue;
                        if (isVariable) {
                            if (isGlobalVariable) {
                                Variable globalVar = GlobalBlock.getVariableByName(param);
                                if (globalVar != null && !globalVar.getType().equals(variable.getType())) {
                                    legal = false;
                                    break;
                                }
                            } else if (isMethodVariable) {
                                Variable methodVar = currentMethod.getVariableByName(param);
                                if (methodVar != null && methodVar.getType().equals(variable.getType()))
                                    if (!methodVar.getIsInit() && methodVar.getVarValue() == null) {
                                        legal = false;
                                        break;
                                    } else return;
                            }
                        }
                        if (!isConstant && isVariable && !isGlobalVariable && isNull && !isMethodVariable) {
                            legal = false;
                            break;
                        }
                    } else
                        throw new IllegalFormationException(METHOD_CALL_ERROR2);
                }
            }
        }
        if (!legal)
            throw new IllegalFormationException(METHOD_CALL_VALUES_ERROR);
    }

    /**
     * this method checks the if line condition.
     *
     * @param blockLines    the block lines that the if in.
     * @param index         the index that we are in.
     * @param currentMethod the method that we are in.
     * @throws IllegalFormationException
     */
    private void ifLine(LinkedList<String> blockLines, int index, Method currentMethod) throws Exception {
        IfBlock ifBlock = new IfBlock(allVariables(currentMethod), currentMethod);
        LinkedList<String> ifBlockLines = getConditionBlocks(blockLines, index);

        ifBlock.parsingBlock(ifBlockLines);
    }

    /**
     * this method checks the while line condition.
     *
     * @param blockLines    the block lines that the if in.
     * @param index         the index that we are in.
     * @param currentMethod the method that we are in.
     * @throws IllegalFormationException
     */
    private void whileLine(LinkedList<String> blockLines, int index, Method currentMethod) throws Exception {
        WhileBlock whileBlock = new WhileBlock(allVariables(currentMethod), currentMethod);
        LinkedList<String> whileBlockLines = getConditionBlocks(blockLines, index);
        whileBlock.parsingBlock(whileBlockLines);
    }

    /**
     * this method take the lines of the condition block, if or while.
     *
     * @param blockGlobalLines the blocks of the parent block.
     * @param index            the index that we are in.
     * @return condition block line.
     * @throws IllegalFormationException
     */
    private LinkedList<String> getConditionBlocks(LinkedList<String> blockGlobalLines, int index)
            throws IllegalFormationException {
        LinkedList<String> blockLines = new LinkedList<>();
        int parenthesisCounter = 1;
        String line = blockGlobalLines.get(index);
        blockLines.add(line);
        index++;
        while (index < blockGlobalLines.size() && parenthesisCounter > 0) {
            line = blockGlobalLines.get(index);
            if (ifPattern.matcher(line).matches() || whilePattern.matcher(line).matches()) {
                parenthesisCounter++;
            } else if (illegalBlockStartP.matcher(line).matches()) {
                throw new IllegalFormationException(NOT_IF_OR_WHILE);
            } else {
                if (blockEnd.matcher(line).matches())
                    parenthesisCounter--;
            }
            blockLines.add(line);
            index++;
        }
        return blockLines;
    }

    /**
     * this method checks if the condition is legal or not.
     *
     * @param line the condition line to check.
     * @throws IllegalFormationException
     */
    protected void checkingCondition(String line) throws IllegalFormationException {
        Pattern pattern = Pattern.compile(".+\\{\\s*");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) throw new IllegalFormationException(IF_ERROR);
        line = line.substring(line.indexOf(OPEN_BRACKET) + 1, line.lastIndexOf(CLOSE_BRACKET)).trim();
        String[] ifConditions = line.split(CONDITION_SPLITTER);
        checkBoolean(ifConditions);

    }

    /**
     * checks if the given array of conditions are boolean or not.
     *
     * @param ifConditions the array of conditions.
     * @throws IllegalFormationException
     */
    protected void checkBoolean(String[] ifConditions) throws IllegalFormationException {
        for (String condition : ifConditions) {
            condition = condition.trim();
            if (!isBoolean.matcher(condition).matches())
                if (!varNamePattern.matcher(condition).matches())
                    throw new IllegalFormationException(IF_ERROR);
                else checkBooleanVariable(condition);
        }
    }

    /**
     * this method checks if the given variable name is boolean or not.
     *
     * @param varName the variable name to check.
     * @throws IllegalFormationException
     */
    protected void checkBooleanVariable(String varName) throws IllegalFormationException {
        boolean found = false;
        for (Variable variable : this.globalVariables)
            if (variable.getVarName().equals(varName) && variable.getIsInit()) {
                found = true;
                if (!booleanTypes.matcher(variable.getType()).matches())
                    throw new IllegalFormationException(NOT_BOOLEAN_ERROR);
            }
        if (!found)
            throw new IllegalFormationException(NOT_BOOLEAN_MSG);
    }
}
