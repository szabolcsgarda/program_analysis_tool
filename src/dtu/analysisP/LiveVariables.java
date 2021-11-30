package dtu.analysisP;

//import dtu.auxiliary.SetOfTriples;
import dtu.ProgramGraph;
import dtu.auxiliary.AnalysisAssignment;
import dtu.expressions.*;

import java.util.*;

public class LiveVariables extends Analysis {

    private Queue<Expression> expressionQueue;
    private HashMap<Integer, HashSet<String>> liveVariables = new HashMap<>();

    private WorkListAlgorithm mWorkListAlgorithm;
    private ProgramGraph mProgram;


    public LiveVariables(ProgramGraph aProgram, WorkListAlgorithm aWorkListAlgorithm)
    {
        mWorkListAlgorithm = aWorkListAlgorithm;
        mProgram = aProgram;
        for (int i = 0; i < mProgram.getNodeNumber(); i++)
        {
            liveVariables.put(i, new HashSet<>());
        }
    }

    public AnalysisAssignment runAnalysis()
    {
        AnalysisAssignment result = new AnalysisAssignment();
        while(true)
        {
            //HashMap<Integer, HashSet<ReachingDefinitionTriple>> localCopy = new HashMap<Integer, HashSet<ReachingDefinitionTriple>>(reachingDefinitions);
            Expression currentExpression = mWorkListAlgorithm.getNextExpression();
            if(currentExpression == null)
            {
                break;
            }
            HashSet<String> oldLiveVariables = (HashSet<String>)liveVariables.get(currentExpression.getStartNode()).clone();
            switch(currentExpression.getClass().getSimpleName()) {
                case "Assignment":
                    dealWithAssignment((Assignment)currentExpression);
                    break;
                case "VariableDeclaration":
                    dealWithDeclaration((VariableDeclaration)currentExpression);
                    break;
                case "ReadOperation":
                    dealWithReadOperation((ReadOperation)currentExpression);
                    break;
                case "BooleanEvaluation":
                    dealWithBooleanEvaluation((BooleanEvaluation)currentExpression);
                    break;
                default:
                    break;
            }

            if(!oldLiveVariables.equals(liveVariables.get(currentExpression.getStartNode())))
            {
                mWorkListAlgorithm.feedbackChangedNodes(currentExpression.getStartNode());
            }
        }
        //prettyPrint();
        return result;
    }

    private void dealWithBooleanEvaluation(BooleanEvaluation currentExpression) {
        HashSet<String> startStateLV = (HashSet<String>)liveVariables.get(currentExpression.getDestinationNode()).clone();
        startStateLV.addAll(currentExpression.getUsedVariables());
        liveVariables.get(currentExpression.getStartNode()).addAll(startStateLV);
    }


    private void dealWithAssignment(Assignment currentExpression) {
        HashSet<String> startStateLV = (HashSet<String>)liveVariables.get(currentExpression.getDestinationNode()).clone();
        if (currentExpression.getVariableType() == Expression.VARIABLE_VARIABLE)
        {
            //kill function
            for (Iterator<String> i = startStateLV.iterator(); i.hasNext();)
            {
                String variableName = i.next();
                if (variableName == currentExpression.getVariableName())
                    i.remove();
            }
        }
        startStateLV.addAll(
                currentExpression.getUsedVariables()); //for arrays used variable already includes the accessed index
        liveVariables.get(currentExpression.getStartNode()).addAll(startStateLV);
    }

    private void dealWithDeclaration(VariableDeclaration currentExpression) {
        HashSet<String> startStateLV = liveVariables.get(currentExpression.getDestinationNode());
        liveVariables.get(currentExpression.getStartNode()).addAll(startStateLV);
    }

    private void dealWithReadOperation(ReadOperation currentExpression) {
        HashSet<String> startStateLV = (HashSet<String>)liveVariables.get(currentExpression.getStartNode()).clone();
        if (currentExpression.getVariableType() == Expression.VARIABLE_VARIABLE)
        {
            //kill function
            for (Iterator<String> i = startStateLV.iterator(); i.hasNext();)
            {
                String variableName = i.next();
                if (variableName == currentExpression.getVariableName())
                    i.remove();
            }
        } else if (currentExpression.getVariableType() == Expression.VARIABLE_ARRAY)
            startStateLV.addAll(currentExpression.getUsedVariables());
        liveVariables.get(currentExpression.getStartNode()).addAll(startStateLV);
    }

    private void prettyPrint()
    {
        for (Map.Entry<Integer, HashSet<String>> entry: this.liveVariables.entrySet())
        {
            StringBuilder l = new StringBuilder("Node: " + entry.getKey().toString() + "     LV: ");
            for (String variable: entry.getValue())
            {
                l.append("(").append(variable).append("), ");
            }
            System.out.println(l);
        }
    }



}
