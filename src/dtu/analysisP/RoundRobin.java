package dtu.analysisP;

import dtu.ProgramGraph;
import dtu.expressions.Expression;
import dtu.expressions.ReadOperation;

import java.util.ArrayList;

public class RoundRobin extends WorkListAlgorithm {
    private boolean mNewRoundNeeded;
    private ArrayList<Expression> mUnUsedEdgesOfCurrentNode; //Contains the unused edges of mLastSelectedNode

    public RoundRobin(ProgramGraph aProgramGraph)
    {
        mNewRoundNeeded = true;
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
        int currentNode = -1;
        Expression result = null;

        //Check if we have unused edge from the last visited node
        if(mUnUsedEdgesOfCurrentNode.size() > 0)
        {
            result = mUnUsedEdgesOfCurrentNode.remove(0);
        }
        else
        {
            //Selecting the next valid node
            while(true)
            {
                //If the worklist is empty fill it if new round is needed
                if(mUpdatedWorkList.size() == 0 && mNewRoundNeeded)
                {
                    for(int i = 0; i < mProgramGraphCopy.getNodeNumber(); i++)
                    {
                        mUpdatedWorkList.add(i);
                    }
                    mNewRoundNeeded = false;
                }
                //Selecting the next node if the worklist is not empty
                if(mUpdatedWorkList.size() > 0)
                {
                    currentNode = mUpdatedWorkList.remove(0);
                    mUnUsedEdgesOfCurrentNode.addAll(mProgramGraphCopy.getExpressionsFromNode(currentNode));
                    if(mUnUsedEdgesOfCurrentNode.size() > 0)
                    {
                        result = mUnUsedEdgesOfCurrentNode.remove(0);
                        break;
                    }
                }
                else
                {
                    result = null;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public void feedbackChangedNodes (int aChangedNodeAssignment)
    {
        mNewRoundNeeded = true;
    }

    @Override
    public void empty ()
    {

    }
}
