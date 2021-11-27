package dtu.syntaxTree;

public class Assignment extends Statement{
    Primitive variable;
    Value value;

    public Assignment(Primitive variable, Value value)
    {
        this.variable = variable;
        this.value = value;
    }
}
