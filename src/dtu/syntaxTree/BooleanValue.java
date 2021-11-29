package dtu.syntaxTree;

import java.util.HashSet;

public class BooleanValue extends BooleanEvaluation{
    public boolean value;

    public BooleanValue(boolean value)
    {
        this.value = value;
    }

    @Override
    public HashSet<Variable> getUsedVariables() {
        return new HashSet<>();
    }
}
