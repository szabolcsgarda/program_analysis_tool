package dtu.syntaxTree;

import dtu.analysisP.DetectionOfSigns;

import java.util.HashMap;
import java.util.HashSet;

public class ConstantValue extends Primitive{
    int value;

    public ConstantValue(int value)
    {
        this.value = value;
    }

    @Override
    public HashSet<Variable> getUsedVariables() {
        return new HashSet<>();
    }

    @Override
    public String getVariableName() {
        return "";
    }

    @Override
    public HashSet<DetectionOfSigns.Sign> aExp(HashMap<String, HashSet<DetectionOfSigns.Sign>> currentState) {
        HashSet<DetectionOfSigns.Sign> result = new HashSet<>();
        if (value > 0) result.add(DetectionOfSigns.Sign.pos);
        else if (value == 0) result.add(DetectionOfSigns.Sign.zero);
        else result.add(DetectionOfSigns.Sign.neg);
        return result;
    }
}
