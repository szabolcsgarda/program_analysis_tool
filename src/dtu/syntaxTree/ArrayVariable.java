package dtu.syntaxTree;

public class ArrayVariable extends Variable {
    public int length;

    public ArrayVariable(String variableName, int length)
    {
        super(variableName);
        this.length = length;
    }
}
