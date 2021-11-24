package dtu.expressions;

import java.util.HashSet;

public class WriteOperation extends Expression{

    private HashSet<String> usedVariables = new HashSet<>();

    public WriteOperation(String aId, String aName, int startNode, int endNode, String variableName)
    {
        super(aId, aName, startNode, endNode);
        this.usedVariables.add(variableName);
    }

    public WriteOperation(String aId, String aName, int startNode, int endNode, String arrayName, String accessedIndex)
    {
        super(aId, aName, startNode, endNode);
        this.usedVariables.add(arrayName);
        this.usedVariables.add(accessedIndex);
    }

    public HashSet<String> getUsedVariables(){ return usedVariables;}


}
