package dtu.syntaxTree;

import java.util.HashSet;

public class RecordAssignment {
    public Record record;
    public Assignment assignment1;
    public Assignment assignment2;

    public RecordAssignment(Record record, Value value1, Value value2) {
        this.record = record;
        this.assignment1 = new Assignment(new ConstantValue(record.fst), value1);
        this.assignment2 = new Assignment(new ConstantValue(record.snd), value2);
    }
    public HashSet<Variable> getUsedVariables() {
        HashSet<Variable> usedVariables = (HashSet<Variable>)assignment1.getUsedVariables().clone();
        usedVariables.addAll(assignment2.getUsedVariables());
        return usedVariables;
    }
}
