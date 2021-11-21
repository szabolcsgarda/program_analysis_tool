package dtu.expressions;

import java.util.HashSet;

public class BooleanEvaluation extends Expression{

    private HashSet<String> usedVariables;

    public BooleanEvaluation(String aId, String aName, int startNode, int endNode, HashSet<String> usedVariables)
    {
        super(aId, aName, startNode, endNode);
        this.usedVariables = usedVariables;
    }

    public HashSet<String> getUsedVariables(){ return usedVariables;}
}
