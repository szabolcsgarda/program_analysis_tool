package dtu.syntaxTree;

import java.util.HashSet;

public abstract class Value {

    public abstract HashSet<Variable> getUsedVariables();
}
