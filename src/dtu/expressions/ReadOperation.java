package dtu.expressions;

public class ReadOperation extends Expression{

    private String variableName;
    private int variableType;

    public ReadOperation(String aId, String aName, String variableName, int variableType)
    {
        super();
        this.variableName = variableName;
        this.variableType = variableType;
    }

    public int getVariableType() { return variableType;}

    public String getVariableName() { return variableName;}

}
