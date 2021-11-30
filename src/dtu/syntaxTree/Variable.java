package dtu.syntaxTree;

import dtu.expressions.Expression;

import java.util.Arrays;
import java.util.HashSet;

public class Variable extends Primitive{
    private String variableName;

    public Variable(String variableName)
    {
        this.variableName = variableName;
    }

    @Override
    public HashSet<Variable> getUsedVariables() {
        return new HashSet<Variable>(Arrays.asList(this));
    }

    public int getVariableType(){return Expression.VARIABLE_VARIABLE;}

    public String getVariableName(){return variableName;}
}
