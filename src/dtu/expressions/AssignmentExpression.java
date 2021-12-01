package dtu.expressions;

import dtu.analysisP.DetectionOfSigns;
import dtu.syntaxTree.ArrayValue;
import dtu.syntaxTree.Assignment;
import dtu.syntaxTree.Variable;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;

public class AssignmentExpression extends Expression{

    private Assignment assignment;

    public AssignmentExpression(String aId, String aName, int startNode, int endNode, Assignment assignment)
    {
        super(aId, aName, startNode, endNode);
        this.assignment = assignment;
    }

    public int getVariableType() {
        switch (assignment.getVariable().getClass().getSimpleName())
        {
            case "Variable":
                Variable v = (Variable)assignment.getVariable();
                return v.getVariableType();
            case "ArrayValue":
                return Expression.VARIABLE_ARRAY;
        }
        return -1;
    }

    public String getVariableName() {
        switch (assignment.getVariable().getClass().getSimpleName())
        {
            case "Variable":
                Variable v = (Variable)assignment.getVariable();
                return v.getVariableName();
            case "ArrayValue":
                ArrayValue a = (ArrayValue) assignment.getVariable();
                return a.array.getVariableName();
        }
        return "";
    }

    public HashSet<Variable> getUsedVariables(){
        switch (assignment.getVariable().getClass().getSimpleName())
        {
            case "Variable":
                return assignment.getUsedVariables();
            case "ArrayValue":
                HashSet<Variable> usedVariables = assignment.getUsedVariables();
                ArrayValue a = (ArrayValue)assignment.getVariable();
                usedVariables.addAll(a.getUsedVariables());
                return usedVariables;
        }
        return new HashSet<>();
    }

    public HashSet<DetectionOfSigns.Sign> aExp(HashMap<String, HashSet<DetectionOfSigns.Sign>> currentState)
    {
        return assignment.aExp(currentState);
    }

}
