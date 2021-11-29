package dtu.syntaxTree;

import java.util.HashSet;

public class Assignment extends Statement{
    Primitive variable;
    Value value;

    public Assignment(Primitive variable, Value value)
    {
        this.variable = variable;
        this.value = value;
    }

    public HashSet<Variable> getUsedVariables() {
        return value.getUsedVariables();
    }

    public Primitive getVariable() {
        return variable;
    }
}
