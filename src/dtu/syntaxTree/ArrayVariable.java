package dtu.syntaxTree;

import dtu.expressions.Expression;

public class ArrayVariable extends Variable {
    public int length;

    public ArrayVariable(String variableName, int length)
    {
        super(variableName);
        this.length = length;
    }

    public int getVariableType(){return Expression.VARIABLE_ARRAY;}
}
