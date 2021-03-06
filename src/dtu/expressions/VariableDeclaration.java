package dtu.expressions;


public class VariableDeclaration extends Expression {

    private String variableName;
    private int variableType;

    public VariableDeclaration(String aId, String aName, String variableName, int variableType, int startNode, int endNode)
    {
        super(aId, aName, startNode, endNode);
        this.variableName = variableName;
        this.variableType = variableType;
    }

    public int getVariableType() { return variableType;}

    public String getVariableName() { return variableName;}
}
