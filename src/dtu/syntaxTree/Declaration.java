package dtu.syntaxTree;

public class Declaration extends Statement{
    private Variable variable;

    public Declaration(Variable variable)
    {
        this.variable = variable;
    }

    public Variable getVariable()
    {
        return variable;
    }
}
