package dtu.syntaxTree;

import dtu.analysisP.DetectionOfSigns;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class IntegerEvaluation extends BooleanEvaluation{
    public enum OpI {LT, LTE, GT, GTE, EQ, NEQ}
    public Value value1;
    public OpI operation;
    public Value value2;

    public IntegerEvaluation(Value value1, OpI operation, Value value2) {
        this.value1 = value1;
        this.operation = operation;
        this.value2 = value2;
    }

    public HashSet<Variable> getUsedVariables() {
        HashSet<Variable> usedVariables = (HashSet<Variable>) value1.getUsedVariables().clone();
        usedVariables.addAll(value2.getUsedVariables());
        return usedVariables;
    }

    public HashSet<DetectionOfSigns.Sign> Aboolean(HashMap<String, HashSet<DetectionOfSigns.Sign>> currentState)
    {
        HashSet<DetectionOfSigns.Sign> result = new HashSet<>();
        for(DetectionOfSigns.Sign j: value2.aExp(currentState))
        {
            int jInt;
            switch (j)
            {
                case neg:
                    jInt = 0;
                    break;
                case zero:
                    jInt = 1;
                    break;
                default:
                    jInt = 2;
            }
            int opInt;
            switch (operation)
            {
                case LT:
                    opInt = 0;
                    break;
                case LTE:
                    opInt = 1;
                    break;
                case EQ:
                    opInt = 2;
                    break;
                case GTE:
                    opInt = 3;
                    break;
                case GT:
                    opInt = 4;
                    break;
                default:
                    opInt = 5;
                    break;
            }
            result.addAll(new HashSet<>(Arrays.asList(aBooleanResults[jInt][opInt])));
        }
        return result;
    }

    private DetectionOfSigns.Sign[][][] aBooleanResults = {
            {{DetectionOfSigns.Sign.neg},{DetectionOfSigns.Sign.neg},{DetectionOfSigns.Sign.neg},{DetectionOfSigns.Sign.neg, DetectionOfSigns.Sign.zero, DetectionOfSigns.Sign.pos},{DetectionOfSigns.Sign.neg, DetectionOfSigns.Sign.zero, DetectionOfSigns.Sign.pos},{DetectionOfSigns.Sign.neg, DetectionOfSigns.Sign.zero, DetectionOfSigns.Sign.pos}}, //b = -
            {{DetectionOfSigns.Sign.neg},{DetectionOfSigns.Sign.neg, DetectionOfSigns.Sign.zero},{DetectionOfSigns.Sign.zero},{DetectionOfSigns.Sign.zero, DetectionOfSigns.Sign.pos},{DetectionOfSigns.Sign.pos},{DetectionOfSigns.Sign.neg, DetectionOfSigns.Sign.pos}}, //b = 0
            {{DetectionOfSigns.Sign.neg, DetectionOfSigns.Sign.zero, DetectionOfSigns.Sign.pos},{DetectionOfSigns.Sign.neg, DetectionOfSigns.Sign.zero, DetectionOfSigns.Sign.pos},{DetectionOfSigns.Sign.pos},{DetectionOfSigns.Sign.pos},{DetectionOfSigns.Sign.pos},{DetectionOfSigns.Sign.neg, DetectionOfSigns.Sign.zero, DetectionOfSigns.Sign.pos}}, //b = +
    };
}
