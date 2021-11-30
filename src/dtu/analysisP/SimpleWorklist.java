package dtu.analysisP;

import dtu.ProgramGraph;
import dtu.expressions.Expression;

import java.util.ArrayList;

public class SimpleWorklist extends WorkListAlgorithm{

    //SimpleWorklist Settings
    public static final int FIRST_IN_FIRST_OUT = 0;
    public static final int LAST_IN_FIRST_OUT = 1;

    private int mOutputSetting;
    private int mLastSelectedNode;
    private ArrayList<Expression> mUnUsedEdgesOfCurrentNode; //Contains the unused edges of mLastSelectedNode

    public SimpleWorklist(ProgramGraph aProgramGraph, int aOutputSetting)
    {
        mProgramGraphCopy = new ProgramGraph(aProgramGraph);
        mOutputSetting = aOutputSetting;
        mLastSelectedNode = -1;
        mUnUsedEdgesOfCurrentNode = new ArrayList<>();
        for(int i = 0; i < mProgramGraphCopy.getNodeNumber(); i++)
        {
            mUpdatedWorkList.add(i);
        }
    }

    @Override
    public Expression getNextExpression()
    {
        mIterationCounter++;
        Expression result = null;
        //int selectedNode = 0;
        int selectedEdge = 0;
        boolean validNode = false;

        //Select the next edge if there is no unhandled edge from the last node
        if(mLastSelectedNode == -1)
        {
            while(!validNode)
            {
                //If the worklist is empty
                if(mUpdatedWorkList.size() == 0)
                {
                    return null;
                }

                switch (mOutputSetting)
                {
                    case FIRST_IN_FIRST_OUT:
                        mLastSelectedNode = mUpdatedWorkList.get(0);
                        break;

                    case LAST_IN_FIRST_OUT:
                        mLastSelectedNode = mUpdatedWorkList.get(mUpdatedWorkList.size()-1);
                        break;
                }
                mUnUsedEdgesOfCurrentNode = mProgramGraphCopy.getExpressionsFromNode(mLastSelectedNode);
                if(mUnUsedEdgesOfCurrentNode.size() > 0)
                {
                    validNode = true;
                }
                else
                {
                    mUpdatedWorkList.remove(mUpdatedWorkList.indexOf(mLastSelectedNode));
                }
            }
        }

        //Select and expression from the node expressions
        result = mUnUsedEdgesOfCurrentNode.remove(0);
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
    public void empty()
    {

    }

    @Override
    public int getIterationNumber()
    {
        return mIterationCounter;
    }
}
