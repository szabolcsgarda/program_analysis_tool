package dtu.syntaxTree;

import java.util.HashSet;

public class BooleanOperation extends BooleanEvaluation{
    public enum OpB {AND, OR, EQ, NEQ}

    public BooleanEvaluation booleanEvaluation1;
    public OpB operation;
    public BooleanEvaluation booleanEvaluation2;

    public BooleanOperation(BooleanEvaluation booleanEvaluation1, OpB operation, BooleanEvaluation booleanEvaluation2) {
        this.booleanEvaluation1 = booleanEvaluation1;
        this.operation = operation;
        this.booleanEvaluation2 = booleanEvaluation2;
    }

    @Override
    public HashSet<Variable> getUsedVariables() {
        HashSet<Variable> usedVariables = (HashSet<Variable>)booleanEvaluation1.getUsedVariables().clone();
        usedVariables.addAll(booleanEvaluation2.getUsedVariables());
        return usedVariables;
    }
}