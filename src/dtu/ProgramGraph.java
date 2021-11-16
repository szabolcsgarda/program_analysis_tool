package dtu;

import dtu.expressions.Expression;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ProgramGraph {
    private HashMap<String, Expression> mExpressions;
    private String[][] mGraph; //[row/start][column/destination]

    public ProgramGraph(int aNumberOfNodes, HashMap<String, Expression> aExpressions)
    {
        mGraph = new String[aNumberOfNodes][aNumberOfNodes];
        mExpressions = new HashMap<>();
        mExpressions.putAll(aExpressions);
        generateGraph();
    }

    private void generateGraph()
    {
        //Reset graph / Invalidate old data
        for(int i = 0; i < mGraph.length; i++)
        {
            for(int k = 0; k < mGraph.length; k++)
            {
                mGraph[i][k] = "null";
            }
        }

        //Fill graph with expression ID-s
        Iterator it = mExpressions.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry element = (Map.Entry)it.next();
            Expression currentExpression = (Expression)element.getValue();
            mGraph[currentExpression.getStartNode()][currentExpression.getDestinationNode()] = currentExpression.getId();
        }
    }

    public void addExpression(String aExpressionId, Expression aExpression)
    {
        mExpressions.put(aExpressionId,aExpression);
        generateGraph();
    }

    public String[][] getProgramGraph()
    {
        return mGraph;
    }
}
