package dtu.syntaxTree;

public class Declaration extends Statement{
    String variableName;

    public Declaration(String variableName)
    {
        this.variableName = variableName;
    }
}
