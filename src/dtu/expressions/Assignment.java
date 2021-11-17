package dtu.expressions;

public class Assignment extends Expression{

    private String variableName;
    private int variableType;

    public Assignment(String aId, String aName, String variableName, int variableType, int startNode, int endNode)
    {
        super(aId, aName, startNode, endNode);
        this.variableName = variableName;
        this.variableType = variableType;
    }

    public int getVariableType() { return variableType;}

    public String getVariableName() { return variableName;}

}
