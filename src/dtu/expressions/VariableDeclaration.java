package dtu.expressions;


import dtu.syntaxTree.Declaration;

public class VariableDeclaration extends Expression {

    private String variableName;
    private int variableType;
    private Declaration declaration;

    public VariableDeclaration(String aId, String aName, String variableName, int variableType, int startNode, int endNode)
    {
        super(aId, aName, startNode, endNode);
        this.variableName = variableName;
        this.variableType = variableType;
    }

    public VariableDeclaration(String aId, String aName, int startNode, int endNode, Declaration declaration)
    {
        super(aId, aName, startNode, endNode);
        this.declaration = declaration;
    }

    public int getVariableType() { return declaration.getVariable().getVariableType();}

    public String getVariableName() { return declaration.getVariable().getVariableName();}
}
