package dtu.analysisP;

//import dtu.auxiliary.SetOfTriples;
import dtu.ProgramGraph;
import dtu.auxiliary.AnalysisAssignment;
import dtu.expressions.*;

import java.util.*;

public class FaintVariables extends Analysis {

    private Queue<Expression> expressionQueue;
    private HashMap<Integer, HashSet<String>> faintVariables = new HashMap<>();

    private WorkListAlgorithm mWorkListAlgorithm;
    private ProgramGraph mProgram;


    public FaintVariables(ProgramGraph aProgram, WorkListAlgorithm aWorkListAlgorithm)
    {
        mWorkListAlgorithm = aWorkListAlgorithm;
        mProgram = aProgram;
        for (int i = 0; i < mProgram.getNodeNumber(); i++)
        {
            faintVariables.put(i, new HashSet<>());
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
            HashSet<String> oldFaintVariables = (HashSet<String>) faintVariables.get(currentExpression.getStartNode()).clone();
            switch(currentExpression.getClass().getSimpleName()) {
                case "Assignment":
                    dealWithAssignment((AssignmentExpression)currentExpression);
                    break;
                case "VariableDeclaration":
                    dealWithDeclaration((VariableDeclaration)currentExpression);
                    break;
                case "ReadOperation":
                    dealWithReadOperation((ReadOperation)currentExpression);
                    break;
                case "BooleanEvaluation":
                    dealWithBooleanEvaluation((BooleanExpression)currentExpression);
                    break;
                default:
                    break;
            }

            if(!oldFaintVariables.equals(faintVariables.get(currentExpression.getStartNode())))
            {
                mWorkListAlgorithm.feedbackChangedNodes(currentExpression.getStartNode());
            }
        }
        prettyPrint();
        return result;
    }

    private void dealWithBooleanEvaluation(BooleanExpression currentExpression) {
        HashSet<String> startStateFV = (HashSet<String>) faintVariables.get(currentExpression.getDestinationNode()).clone();
        currentExpression.getUsedVariables().forEach(x -> startStateFV.add(x.getVariableName()));
        faintVariables.get(currentExpression.getStartNode()).addAll(startStateFV);
    }


    private void dealWithAssignment(AssignmentExpression currentExpression) {
        HashSet<String> startStateFV = (HashSet<String>) faintVariables.get(currentExpression.getDestinationNode()).clone();
        boolean assigningToLiveVariable = startStateFV.contains(currentExpression.getVariableName());
        if (currentExpression.getVariableType() == Expression.VARIABLE_VARIABLE)
        {
            //kill function
            for (Iterator<String> i = startStateFV.iterator(); i.hasNext();)
            {
                String variableName = i.next();
                if (variableName == currentExpression.getVariableName())
                    i.remove();
            }
        }
        if (assigningToLiveVariable)
            currentExpression.getUsedVariables().forEach(x -> startStateFV.add(x.getVariableName())); //for arrays used variable already includes the accessed index
        faintVariables.get(currentExpression.getStartNode()).addAll(startStateFV);
    }

    private void dealWithDeclaration(VariableDeclaration currentExpression) {
        HashSet<String> startStateFV = faintVariables.get(currentExpression.getDestinationNode());
        faintVariables.get(currentExpression.getStartNode()).addAll(startStateFV);
    }

    private void dealWithReadOperation(ReadOperation currentExpression) {
        HashSet<String> startStateFV = (HashSet<String>) faintVariables.get(currentExpression.getStartNode()).clone();
        boolean assigningToLiveVariable = startStateFV.contains(currentExpression.getVariable().getVariableName());
        if (currentExpression.getVariableType() == Expression.VARIABLE_VARIABLE)
        {
            //kill function
            for (Iterator<String> i = startStateFV.iterator(); i.hasNext();)
            {
                String variableName = i.next();
                if (variableName == currentExpression.getVariable().getVariableName())
                    i.remove();
            }
        } else if (currentExpression.getVariableType() == Expression.VARIABLE_ARRAY)
            if (assigningToLiveVariable) currentExpression.getUsedVariables().forEach(x -> startStateFV.add(x.getVariableName()));
        faintVariables.get(currentExpression.getStartNode()).addAll(startStateFV);
    }

    private void prettyPrint()
    {
        for (Map.Entry<Integer, HashSet<String>> entry: this.faintVariables.entrySet())
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
