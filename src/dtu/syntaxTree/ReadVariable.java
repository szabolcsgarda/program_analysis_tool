package dtu.syntaxTree;

import java.util.HashSet;

public class ReadVariable extends ReadExpression{

    private Variable variable;

    public HashSet<Variable> getUsedVariables() {
        return new HashSet<>();
    }

}
