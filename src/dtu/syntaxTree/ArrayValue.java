package dtu.syntaxTree;

import java.lang.reflect.Array;
import java.util.HashSet;

public class ArrayValue extends Primitive{
    public ArrayVariable array;
    public Value index;

    public ArrayValue(ArrayVariable array, Value index) {
        this.array = array;
        this.index = index;
    }

    @Override
    public HashSet<Variable> getUsedVariables() {
        return index.getUsedVariables();
    }

    @Override
    public String getVariableName() {
        return array.getVariableName();
    }
}
