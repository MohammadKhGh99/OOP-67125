package oop.ex6.main;

import java.util.LinkedList;

import static oop.ex6.AllHelpingVariables.*;

/**
 * Method's Class represents on each method, it is mean each method in the file has a object from this class
 * and this class has the characters of the method
 */
public class Method {

    private String methodName;
    private LinkedList<Variable> localVariables;
    private LinkedList<Variable> localParameters;

    public Method(String methodName, LinkedList<Variable> localParameters, LinkedList<Variable> localVariable) {
        this.localParameters = localParameters;
        this.localVariables = localVariable;
        this.methodName = methodName;
    }


    public LinkedList<Variable> getLocalVariables() {
        return this.localVariables;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void addAllVariables(LinkedList<Variable> variables) throws IllegalFormationException {
        LinkedList<Variable> temp = new LinkedList<>();
        if (this.localVariables != null) {
            for (Variable variable : variables) {
                if (this.localVariables.size() > 0) {
                    for (Variable variable1 : this.localVariables)
                        if (variable.equals(variable1))
                            throw new IllegalFormationException(DUPLICATE_SAME_VARIABLE);
                        else {
                            temp.add(variable);
                        }
                }
            }
        } else {
            this.localVariables = new LinkedList<>();
        }
        if (temp.size() > 0) this.localVariables.addAll(temp);
        else this.localVariables.addAll(variables);

    }

//    public void deletingCheckedLines(LinkedList<String> lines){
//        LinkedList<String> temp=new LinkedList<>();
//        for (String line:)
//    }

    public boolean contains(String varName) {
        for (Variable var : this.localVariables) {
            if (var.getVarName().equals(varName))
                return true;
        }
        for (Variable variable : this.localParameters) {
            if (variable.getVarName().equals(varName))
                return true;
        }
        return false;
    }

    public LinkedList<Variable> getLocalParameters() {
        return this.localParameters;
    }

    public Variable getVariableByName(String varName) {
        for (Variable var : this.localVariables) {
            if (var.getVarName().equals(varName))
                return var;
        }
        for (Variable variable : this.localParameters) {
            if (variable.getVarName().equals(varName))
                return variable;
        }
        return null;
    }
}
