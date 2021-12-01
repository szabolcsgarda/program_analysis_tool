package dtu.expressions;

import dtu.syntaxTree.BooleanEvaluation;
import dtu.syntaxTree.Variable;

import java.util.HashSet;

public class BooleanExpression extends Expression{

    private BooleanEvaluation evaluation;

    public BooleanExpression(String aId, String aName, int startNode, int endNode, BooleanEvaluation evaluation)
    {
        super(aId, aName, startNode, endNode);
        this.evaluation = evaluation;
    }

    public HashSet<Variable> getUsedVariables(){ return evaluation.getUsedVariables();}

    public BooleanEvaluation getEvaluation() {return evaluation;}
}
