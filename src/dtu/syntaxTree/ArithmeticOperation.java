package dtu.syntaxTree;

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
}
