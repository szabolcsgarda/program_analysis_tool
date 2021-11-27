package dtu.syntaxTree;

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
}
