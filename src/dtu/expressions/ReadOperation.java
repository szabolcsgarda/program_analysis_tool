package dtu.expressions;

import java.util.HashSet;

public class ReadOperation extends Expression{

    private String variableName;
    private int variableType;
    private HashSet<String> usedVariables = new HashSet<>();

    public ReadOperation(String aId, String aName, String variableName)
    {
        super();
        this.variableName = variableName;
        this.variableType = Expression.VARIABLE_VARIABLE;
    }

    public ReadOperation(String aId, String aName, String variableName, String accessedIndex)
    {
        super();
        this.variableName = variableName;
        this.variableType = Expression.VARIABLE_ARRAY;
        this.usedVariables.add(accessedIndex);
    }

    public int getVariableType() { return variableType;}

    public String getVariableName() { return variableName;}

    public HashSet<String> getUsedVariables(){ return usedVariables;}

}
