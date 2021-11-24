package dtu.analysisP;

import dtu.ProgramGraph;
import dtu.expressions.Expression;

import java.util.ArrayList;

public abstract class WorkListAlgorithm {
    protected ProgramGraph mProgramGraphCopy;
    protected ArrayList<Integer> mUpdatedWorkList = new ArrayList<>();

    //called "extract" function in scientific papers
    public abstract Expression getNextExpression();

    //called "insert" function in scientific papers
    public abstract void feedbackChangedNodes (int aChangedNodeAssignment);

    public abstract void empty();
}
