package dtu;

import dtu.expressions.Expression;

import java.util.HashMap;

public class ProgramGraph {
    private HashMap<String, Expression> mExpressions;
    private String[][] mGraph;

    public ProgramGraph(int aNumberOfNodes, HashMap<String, Expression> aExpressions)
    {
        mGraph = new String[aNumberOfNodes][aNumberOfNodes];
        mExpressions = new HashMap<>();
        mExpressions.putAll(aExpressions);
    }
}
