package dtu.analysisP;

import dtu.ProgramGraph;
import dtu.expressions.Expression;

import java.util.ArrayList;

public abstract class WorkListAlgorithm {

    public abstract ArrayList<Expression> generateWorkList(ProgramGraph aProgramGraph);

}
