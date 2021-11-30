package dtu.analysisP;

import dtu.ProgramGraph;
import dtu.expressions.Expression;

import java.util.ArrayList;
import java.util.HashMap;

//Class to produce Depth-First Spamming tree and Reverse Postorder numbering from a Program Graph input
public class DFS {
    private ProgramGraph mProgramGraphCopy;
    private String[][] mDfsTree; //[Start/End][column/destination]
    private HashMap<Integer, Integer> mRpNumbering;

    public DFS(ProgramGraph aProgramGraph)
    {
        mProgramGraphCopy = new ProgramGraph(aProgramGraph);
        mDfsTree = new String[mProgramGraphCopy.getNodeNumber()][mProgramGraphCopy.getNodeNumber()];
        mRpNumbering = new HashMap<>();

        //Reset graph / Invalidate old data
        for(int i = 0; i < mDfsTree.length; i++)
        {
            for(int k = 0; k < mDfsTree.length; k++)
            {
                mDfsTree[i][k] = "null";
            }
        }
    }

    //Run algorithm before using the getter functions
    public void run()
    {
        //Constructing DFS tree
        int k = mProgramGraphCopy.getNodeNumber() -1;
        ArrayList<Integer> visitedNodes = new ArrayList<>();
        int currentNode = 0;

        while(currentNode < mProgramGraphCopy.getNodeNumber())
        {
            visitedNodes.add(currentNode);
            ArrayList<Expression> expressions = mProgramGraphCopy.getExpressionsFromNode(currentNode);
            for(Expression currentExpression:expressions)
            {
                if(!visitedNodes.contains(currentExpression.getDestinationNode()))
                {
                    mDfsTree[currentNode][currentExpression.getDestinationNode()] = currentExpression.getId();
                }
            }
            mRpNumbering.put(currentNode, k);
            k--;
            currentNode ++;
        }

        /*System.out.println("Program graph");

        for(int i = 0; i < mDfsTree.length; i++)
        {
            for(int k1 = 0; k1 < mDfsTree.length; k1++)
            {
                System.out.print((mProgramGraphCopy.getProgramGraph())[i][k1] + "\t");
            }
            System.out.print("\n");
        }

        System.out.println("DFS tree");
        for(int i = 0; i < mDfsTree.length; i++)
        {
            for(int k1 = 0; k1 < mDfsTree.length; k1++)
            {
                System.out.print(mDfsTree[i][k1]);
                System.out.print("\t");
            }
            System.out.print("\n");
        }

        System.out.println("RP indexing");
        for(int i = 0; i<mProgramGraphCopy.getNodeNumber(); i++)
        {
            System.out.println(i + "->" + mRpNumbering.get(i));
        }*/
    }

    // Get Depth-First Spamming tree
    public String[][] getDFS()
    {
        return mDfsTree;
    }

    //Get node indexes in Reverse Postorder
    //Returns by map (Node ID -> Index in Reverse Postorder)
    public HashMap<Integer, Integer> getRP()
    {
        return mRpNumbering;
    }
}