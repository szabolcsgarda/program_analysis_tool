package dtu;

import dtu.analysisP.ReachingDefinitions;
import dtu.expressions.Expression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

public class ProgramGraph{
    private Queue<Expression> mExpressions ;
    private String[][] mGraph; //[row/start][column/destination]

    public ProgramGraph(int aNumberOfNodes, Queue<Expression> aExpressions)
    {
        mGraph = new String[aNumberOfNodes][aNumberOfNodes];
        mExpressions = aExpressions;
        generateGraph();
    }

    public ProgramGraph(ProgramGraph aProgramGraph)
    {
        mExpressions = aProgramGraph.mExpressions;
        mGraph = new String[aProgramGraph.mGraph.length][aProgramGraph.mGraph.length];
        mGraph = aProgramGraph.mGraph;
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
        for (Iterator<Expression> it = mExpressions.iterator(); it.hasNext();)
        {
            Expression currentExpression = it.next();
            mGraph[currentExpression.getStartNode()][currentExpression.getDestinationNode()] = currentExpression.getId();
        }
    }

    public void addExpression(Expression aExpression)
    {
        mExpressions.add(aExpression);
        generateGraph();
    }

    public int getNodeNumber()
    {
        return mGraph.length;
    }

    public String[][] getProgramGraph()
    {
        return mGraph;
    }

    public ArrayList<Expression> getExpressionsFromNode(int aNode) {
        ArrayList<Expression> result = new ArrayList<>();
        for(int i = 0; i < mGraph.length; i++)
        {
            if(!mGraph[aNode][i].equals("null"))
            {
                result.add(getExpressionById(mGraph[aNode][i]));
            }
        }
        return result;
    }

    public Expression getExpressionById(String aId) {
        for (Iterator<Expression> it = mExpressions.iterator(); it.hasNext();)
        {
            Expression currentExpression = it.next();
            if(currentExpression.getId().equals(aId))
            {
                return currentExpression;
            }
        }
        return null;
    }

    public void prettyPrint()
    {

    }

}
