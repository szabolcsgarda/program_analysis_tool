package dtu.expressions;

import dtu.syntaxTree.ArrayValue;
import dtu.syntaxTree.Primitive;
import dtu.syntaxTree.ReadArray;
import dtu.syntaxTree.Variable;

import java.util.HashSet;

public class ReadOperation extends Expression{

    private Primitive variable;
    private int variableType;
    private HashSet<String> usedVariables = new HashSet<>();

    public ReadOperation(String aId, String aName, Variable variable)
    {
        super();
        this.variable = variable;
        variableType = variable.getVariableType();
    }

    public ReadOperation(String aId, String aName, ArrayValue variable)
    {
        super();
        this.variable = variable;
        variableType = Expression.VARIABLE_ARRAY;
    }

    public int getVariableType() { return variableType;}

    public Primitive getVariable() { return variable;}

    public HashSet<Variable> getUsedVariables(){ return variable.getUsedVariables();}

}
