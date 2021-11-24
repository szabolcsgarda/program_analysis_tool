package dtu.expressions;

import java.util.HashSet;

public class Assignment extends Expression{

    private String variableName;
    private int variableType;
    private HashSet<String> usedVariables;

    public Assignment(String aId, String aName, String variableName, int startNode, int endNode, HashSet<String> usedVariables)
    {
        super(aId, aName, startNode, endNode);
        this.variableName = variableName;
        this.variableType = Expression.VARIABLE_VARIABLE;
        this.usedVariables = usedVariables;
    }

    public Assignment(String aId, String aName, String arrayName, String accessedIndex, int startNode, int endNode, HashSet<String> usedVariables)
    {
        super(aId, aName, startNode, endNode);
        this.variableName = arrayName;
        this.variableType = Expression.VARIABLE_ARRAY;
        this.usedVariables = usedVariables;
        this.usedVariables.add(accessedIndex);
    }

    public int getVariableType() { return variableType;}

    public String getVariableName() { return variableName;}

    public HashSet<String> getUsedVariables(){ return usedVariables;}

}
