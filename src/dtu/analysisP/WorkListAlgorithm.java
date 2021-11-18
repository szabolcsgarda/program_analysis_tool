package dtu.analysisP;

import dtu.ProgramGraph;
import dtu.expressions.Expression;

import java.util.ArrayList;

public abstract class WorkListAlgorithm {
    protected ProgramGraph mProgramGraphCopy;
    protected ArrayList<Integer> mUpdatedWorkList = new ArrayList<>();

    public abstract Expression getNextExpression();

    public abstract void feedbackChangedNodes (ArrayList<Integer> aChangedNodeAssignments);

    public abstract void feedbackChangedNodes (int aChangedNodeAssignment);

}
