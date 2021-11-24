package dtu.analysisP;

import dtu.ProgramGraph;
import dtu.expressions.Expression;
import dtu.expressions.ReadOperation;

import java.util.ArrayList;
import java.util.HashMap;

public class RoundRobin extends WorkListAlgorithm {
    private boolean mNewRoundNeeded;
    private ArrayList<Expression> mUnUsedEdgesOfCurrentNode; //Contains the unused edges of mLastSelectedNode
    private HashMap<Integer, Integer> mRPIndexing;
    DFS mDfs;

    public RoundRobin(ProgramGraph aProgramGraph)
    {
        mNewRoundNeeded = true;
        mUnUsedEdgesOfCurrentNode = new ArrayList<>();
        mRPIndexing = new HashMap<>();
        mProgramGraphCopy = new ProgramGraph(aProgramGraph);
        mDfs = new DFS(mProgramGraphCopy);

        for(int i = 0; i < mProgramGraphCopy.getNodeNumber(); i++)
        {
            mUpdatedWorkList.add(i);
        }
        mDfs.run();
        mRPIndexing = mDfs.getRP();
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
                    //currentNode = mUpdatedWorkList.remove(0);
                    currentNode = mUpdatedWorkList.remove(getNextNodeFromRP(mRPIndexing, mUpdatedWorkList));
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

    private int getNextNodeFromRP(HashMap<Integer, Integer> aRPIndexing, ArrayList<Integer> aWorklist)
    {
        int result = 0;
        for(int i = 0; i < aWorklist.size(); i++)
        {
            if(aRPIndexing.get(aWorklist.get(i)) < aRPIndexing.get(aWorklist.get(result)))
            {
                result = i;
            }
        }
        return result;
    }
}
