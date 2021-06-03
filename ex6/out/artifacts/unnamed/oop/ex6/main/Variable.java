package oop.ex6.main;

import static oop.ex6.AllHelpingVariables.*;
import static oop.ex6.AllPatterns.*;

/**
 * Variable's Class represents on each variable, it is mean each variable in the file has a object
 * from this class and this class has the characters of the method.
 */
public class Variable {
    private String type,varName;
    private Object varValue;
    private boolean isFinal;
    private boolean isInit,isParam;

    /**
     * This constructor method
     * @param type - type of this variable
     * @param varName - name of this variable
     * @param varValue - value of this variable
     * @param isFinal - the situation of this variable is final or not
     * @param isParam - if the variable is a parameter of method or not.
     * @param isInit - the situation of this variable is initializes or not
     * @throws IllegalFormationException - Exception if wrong value or type
     */
    public Variable(String type, String varName, Object varValue, boolean isFinal,boolean isParam,boolean isInit)
            throws IllegalFormationException{
        if (typesPattern.matcher(type).matches())
            this.type=type;
        else throw new IllegalFormationException(TYPE_MSG);
        if (varNamePattern.matcher(varName).matches() && !KEYWORDS.contains(varName))
            this.varName=varName;
        else throw new IllegalFormationException(VALUE_MSG);
        this.isInit=isInit;
        this.isParam=isParam;
        this.varValue=varValue;
        this.isFinal=isFinal;
    }

    public boolean getIsInit(){return this.isInit;}

    public String getType() {
        return type;
    }
    public String getVarName() {
        return varName;
    }
    public void setVarValue(Object varValue) {
        this.varValue = varValue;
    }

    public Object getVarValue() {
        return varValue;
    }

    public boolean getIsFinal() {
        return isFinal;
    }

    /**
     * this method checks if the given variable is equals to this variable.
     * @param variable the given variable to check with.
     * @return true if the given variable is equals to this variable.
     */
    public boolean equals(Variable variable){
        if (!variable.varName.equals(this.varName)) return false;
        if (!variable.type.equals(this.type)) return false;
        if (!variable.isParam==this.isParam) return false;
        if (!variable.isInit==this.isInit) return false;
        if (!variable.isFinal==this.isFinal) return false;
        if (!(variable.varValue==null && variable.varValue==this.varValue))return false;
        return true;
    }
}
