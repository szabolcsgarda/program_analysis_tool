package dtu.syntaxTree;

import java.util.HashSet;

public class ReadArray extends ReadExpression{

    public ArrayValue accessedArray;

    public HashSet<Variable> getUsedVariables() {
        return accessedArray.getUsedVariables();
    }
}
