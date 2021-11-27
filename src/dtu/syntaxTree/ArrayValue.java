package dtu.syntaxTree;

import java.lang.reflect.Array;

public class ArrayValue extends Primitive{
    public ArrayVariable array;
    public Value index;

    public ArrayValue(ArrayVariable array, Value index) {
        this.array = array;
        this.index = index;
    }

}
