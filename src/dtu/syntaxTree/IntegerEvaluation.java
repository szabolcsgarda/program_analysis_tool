package dtu.syntaxTree;

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
}
