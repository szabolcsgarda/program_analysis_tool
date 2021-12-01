package dtu.syntaxTree;

import dtu.analysisP.DetectionOfSigns;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class ArithmeticOperation extends Value{
    public enum OpA {ADD, SUB, MULT, DIV, MOD};

    public Primitive value1;
    public Value value2;
    public OpA operation;

    public ArithmeticOperation(Primitive value1, OpA operation, Value value2) {
        this.value1 = value1;
        this.value2 = value2;
        this.operation = operation;
    }

    public HashSet<Variable> getUsedVariables() {
        HashSet<Variable> usedVariables = (HashSet<Variable>)value1.getUsedVariables().clone();
        usedVariables.addAll(value2.getUsedVariables());
        return usedVariables;
    }

    public HashSet<DetectionOfSigns.Sign> aExp(HashMap<String, HashSet<DetectionOfSigns.Sign>> currentState)
    {
        HashSet<DetectionOfSigns.Sign> result = new HashSet<>();
        for(DetectionOfSigns.Sign i: value1.aExp(currentState))
        {
            int iInt;
            switch (i)
            {
                case neg:
                    iInt = 0;
                    break;
                case zero:
                    iInt = 1;
                    break;
                default:
                    iInt = 2;
            }
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
                    case ADD:
                        opInt = 0;
                        break;
                    case SUB:
                        opInt = 1;
                        break;
                    case MULT:
                        opInt = 2;
                        break;
                    case DIV:
                        opInt = 3;
                        break;
                    default:
                        opInt = 4;
                        break;
                }
                result.addAll(new HashSet<>(Arrays.asList(aExpResults[opInt][iInt][jInt])));
            }
        }
        return result;
    }

    private DetectionOfSigns.Sign[][][][] aExpResults = {
            {
                {{DetectionOfSigns.Sign.neg},{DetectionOfSigns.Sign.neg},{DetectionOfSigns.Sign.neg, DetectionOfSigns.Sign.zero, DetectionOfSigns.Sign.pos}}, //x = -
                {{DetectionOfSigns.Sign.neg},{DetectionOfSigns.Sign.zero},{DetectionOfSigns.Sign.pos}}, //x = 0
                {{DetectionOfSigns.Sign.neg, DetectionOfSigns.Sign.zero, DetectionOfSigns.Sign.pos},{DetectionOfSigns.Sign.pos},{DetectionOfSigns.Sign.pos}} //x = +
            },//add
            {
                {{DetectionOfSigns.Sign.neg, DetectionOfSigns.Sign.zero, DetectionOfSigns.Sign.pos},{DetectionOfSigns.Sign.pos},{DetectionOfSigns.Sign.pos}}, //x = -
                {{DetectionOfSigns.Sign.neg},{DetectionOfSigns.Sign.zero},{DetectionOfSigns.Sign.pos}}, //x = 0
                {{DetectionOfSigns.Sign.neg},{DetectionOfSigns.Sign.neg},{DetectionOfSigns.Sign.neg, DetectionOfSigns.Sign.zero, DetectionOfSigns.Sign.pos}} //x = +
            },//sub
            {
                {{DetectionOfSigns.Sign.pos},{DetectionOfSigns.Sign.zero},{DetectionOfSigns.Sign.neg}}, //x = -
                {{DetectionOfSigns.Sign.zero},{DetectionOfSigns.Sign.zero},{DetectionOfSigns.Sign.zero}}, //x = 0
                {{DetectionOfSigns.Sign.neg},{DetectionOfSigns.Sign.zero},{DetectionOfSigns.Sign.pos}} //x = +
            },//mult
            {
                {{DetectionOfSigns.Sign.pos},{},{DetectionOfSigns.Sign.neg}}, //x = -
                {{DetectionOfSigns.Sign.zero},{},{DetectionOfSigns.Sign.zero}}, //x = 0
                {{DetectionOfSigns.Sign.neg},{},{DetectionOfSigns.Sign.pos}} //x = +
            }, //div
            {
                {{DetectionOfSigns.Sign.zero},{},{DetectionOfSigns.Sign.zero}}, //x = -
                {{DetectionOfSigns.Sign.zero},{},{DetectionOfSigns.Sign.zero}}, //x = 0
                {{DetectionOfSigns.Sign.zero},{},{DetectionOfSigns.Sign.pos}} //x = +
            } //mod
    };
}
