package dtu.syntaxTree;

import dtu.analysisP.DetectionOfSigns;

import java.lang.reflect.Array;
import java.util.HashMap;
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

    @Override
    public HashSet<DetectionOfSigns.Sign> aExp(HashMap<String, HashSet<DetectionOfSigns.Sign>> currentState) {
        return currentState.get(array.getVariableName());
    }
}
