package dtu.expressions;

import java.util.HashSet;

public class BooleanExpression extends Expression{

    private HashSet<String> usedVariables;

    public BooleanExpression(String aId, String aName, int startNode, int endNode, HashSet<String> usedVariables)
    {
        super(aId, aName, startNode, endNode);
        this.usedVariables = usedVariables;
    }

    public HashSet<String> getUsedVariables(){ return usedVariables;}
}
