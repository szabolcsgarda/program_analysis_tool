package dtu.syntaxTree;

import dtu.expressions.Expression;

public class Record extends Variable{
    public int fst;
    public int snd;

    public Record(String variableName, int fst, int snd)
    {
        super(variableName);
        this.fst = fst;
        this.snd = snd;
    }

    @Override
    public int getVariableType() {return Expression.VARIABLE_RECORD;}
}
