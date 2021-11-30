package dtu.analysisP;

import dtu.ProgramGraph;
import dtu.expressions.Expression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ChaoticIteration extends WorkListAlgorithm {
    private Random mRand = new Random();
    private int mLastSelectedNode; //-1 if we can go for the another node
    private ArrayList<Expression> mUnUsedEdgesOfCurrentNode; //Contains the unused edges of mLastSelectedNode

    public ChaoticIteration(ProgramGraph aProgramGraph)
    {
        mLastSelectedNode = -1;
        mUnUsedEdgesOfCurrentNode = new ArrayList<>();
        mProgramGraphCopy = new ProgramGraph(aProgramGraph);
        for(int i = 0; i < mProgramGraphCopy.getNodeNumber(); i++)
        {
            mUpdatedWorkList.add(i);
        }
    }

    @Override
    public Expression getNextExpression()
    {
        mIterationCounter++;
        //Set up currentNode
        int randomNodeIndex = 0; // index of the selected node
        int randomEdgeIndex = 0; // index of the selected edge
        if(mLastSelectedNode == -1)
        {
            boolean validNode = false; //The selected node is invalid if no edge starts from there
            while(!validNode)
            {
                //If the worklist is empty
                if(mUpdatedWorkList.size() == 0)
                {
                    return null;
                }

                randomNodeIndex = mRand.nextInt(mUpdatedWorkList.size());
                mLastSelectedNode = mUpdatedWorkList.get(randomNodeIndex);
                mUnUsedEdgesOfCurrentNode = mProgramGraphCopy.getExpressionsFromNode(mLastSelectedNode);
                if(mUnUsedEdgesOfCurrentNode.size() > 0)
                {
                    validNode = true;
                }
                else
                {
                    mUpdatedWorkList.remove(randomNodeIndex);
                }
            }
        }
        //Select and expression from the node expressions
        randomEdgeIndex = mRand.nextInt(mUnUsedEdgesOfCurrentNode.size());

        Expression result = mUnUsedEdgesOfCurrentNode.get(randomEdgeIndex);

        //Update class variables
        mUnUsedEdgesOfCurrentNode.remove(randomEdgeIndex);
        if(mUnUsedEdgesOfCurrentNode.size() == 0)
        {
            mUpdatedWorkList.remove(mUpdatedWorkList.indexOf(mLastSelectedNode));
            mLastSelectedNode = -1;
        }
        return result;
    }

    @Override
    public void feedbackChangedNodes (int aChangedNodeAssignment)
    {
        if(!mUpdatedWorkList.contains(aChangedNodeAssignment))
        {
            mUpdatedWorkList.add(aChangedNodeAssignment);
        }
    }

    @Override
    public void empty ()
    {

    }

    @Override
    public int getIterationNumber()
    {
        return mIterationCounter;
    }
}
