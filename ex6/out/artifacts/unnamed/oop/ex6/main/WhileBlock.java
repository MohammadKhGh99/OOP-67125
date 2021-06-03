package oop.ex6.main;

import java.util.LinkedList;

/**
 * This class describe the While block extends Block class, parsing the while block...
 */
public class WhileBlock extends Block{

    /**
     * the constructor of while block, calling the constructor of Block class.
     * @param globalVariables the global variables for this block.
     * @param currentMethod   the current method which the if \ while blocks are in.
     */
    public WhileBlock(LinkedList<Variable> globalVariables,Method currentMethod) {
        super(globalVariables,currentMethod);

    }

    /**
     * this method parses WhileBlock, check if it is legal.
     * @param blockLines the lines for this block.
     * @throws IllegalFormationException if there is an error in parsing the block.
     */
    @Override
    public void parsingBlock(LinkedList<String> blockLines) throws Exception {
        String whileLine=blockLines.get(0);
        checkingCondition(whileLine);
        for (int i=1;i<blockLines.size();i++){
            checkLine(this.currentMethod,blockLines,i);
        }
        this.currentVariables.addAll(this.currentMethod.getLocalVariables());
    }
}
