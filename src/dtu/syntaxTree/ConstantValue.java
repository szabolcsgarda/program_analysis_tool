package dtu.syntaxTree;

import java.util.HashSet;

public class ConstantValue extends Primitive{
    int value;

    public ConstantValue(int value)
    {
        this.value = value;
    }

    @Override
    public HashSet<Variable> getUsedVariables() {
        return new HashSet<>();
    }

    @Override
    public String getVariableName() {
        return "";
    }
}
