package dtu.syntaxTree;

import java.util.HashSet;

public abstract class ReadExpression {

    public abstract HashSet<Variable> getUsedVariables();
}
