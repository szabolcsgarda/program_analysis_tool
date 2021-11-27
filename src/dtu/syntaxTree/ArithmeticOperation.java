package dtu.syntaxTree;

public class ArithmeticOperation {
    public enum OpA {ADD, SUB, MULT, DIV, MOD};

    public Primitive value1;
    public Value value2;
    public OpA operation;

    public ArithmeticOperation(Primitive value1, Value value2, OpA operation) {
        this.value1 = value1;
        this.value2 = value2;
        this.operation = operation;
    }
}
