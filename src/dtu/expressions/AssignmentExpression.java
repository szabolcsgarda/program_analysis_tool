package dtu.expressions;

import dtu.syntaxTree.ArrayValue;
import dtu.syntaxTree.Assignment;
import dtu.syntaxTree.Variable;

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
                Variable v = (Variable)assignment.getVariable();
                return v.getUsedVariables();
            case "ArrayValue":
                ArrayValue a = (ArrayValue) assignment.getVariable();
                return a.getUsedVariables();
        }
        return new HashSet<>();
    }

}
