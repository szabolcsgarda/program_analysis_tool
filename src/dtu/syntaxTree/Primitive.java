package dtu.syntaxTree;

import java.util.HashSet;

public abstract class Primitive extends Value{

    public abstract HashSet<Variable> getUsedVariables();

    public abstract String getVariableName();
}
