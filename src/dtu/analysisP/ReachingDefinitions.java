package dtu.analysisP;

//import dtu.auxiliary.SetOfTriples;
import dtu.ProgramGraph;
import dtu.auxiliary.AnalysisAssignment;
import dtu.expressions.*;

import java.util.*;

public class ReachingDefinitions extends Analysis {

    private Queue<Expression> expressionQueue;
    private HashMap<Integer, HashSet<ReachingDefinitionTriple>> reachingDefinitions = new HashMap<>();

    private WorkListAlgorithm mWorkListAlgorithm;
    private ProgramGraph mProgram;


    public ReachingDefinitions(ProgramGraph aProgram, WorkListAlgorithm aWorkListAlgorithm)
    {
        mWorkListAlgorithm = aWorkListAlgorithm;
        mProgram = aProgram;
        for (int i = 0; i < mProgram.getNodeNumber(); i++)
        {
            reachingDefinitions.put(i, new HashSet<>());
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
            HashSet<ReachingDefinitionTriple> oldTriples = (HashSet<ReachingDefinitionTriple>)reachingDefinitions.get(currentExpression.getDestinationNode()).clone();
            switch(currentExpression.getClass().getSimpleName()) {
                case "AssignmentExpression":
                    dealWithAssignment((AssignmentExpression)currentExpression);
                    break;
                case "VariableDeclaration":
                    dealWithDeclaration((VariableDeclaration)currentExpression);
                    break;
                case "ReadOperation":
                    dealWithReadOperation((ReadOperation)currentExpression);
                    break;
                case "BooleanExpression":
                    dealWithBooleanEvaluation((BooleanExpression)currentExpression);
                    break;
                default:
                    break;
            }
            if(!oldTriples.equals(reachingDefinitions.get(currentExpression.getDestinationNode())))
            {
                mWorkListAlgorithm.feedbackChangedNodes(currentExpression.getDestinationNode());
            }
        }
        prettyPrint();
        return result;
    }

    private void dealWithBooleanEvaluation(BooleanExpression currentExpression) {
        HashSet<ReachingDefinitionTriple> startStateRD = (HashSet<ReachingDefinitionTriple>)reachingDefinitions.get(currentExpression.getStartNode()).clone();
        reachingDefinitions.get(currentExpression.getDestinationNode()).addAll(startStateRD);
    }


    private void dealWithAssignment(AssignmentExpression currentExpression) {
        HashSet<ReachingDefinitionTriple> startStateRD = (HashSet<ReachingDefinitionTriple>)reachingDefinitions.get(currentExpression.getStartNode()).clone();
        if (currentExpression.getVariableType() == Expression.VARIABLE_VARIABLE)
        {
            //kill function
            for (Iterator<ReachingDefinitionTriple> i = startStateRD.iterator(); i.hasNext();)
            {
                ReachingDefinitionTriple triple = i.next();
                if (triple.variableName == currentExpression.getVariableName())
                    i.remove();
            }
        }
        startStateRD.add(
                new ReachingDefinitionTriple(currentExpression.getVariableName(),
                        currentExpression.getStartNode(),
                        currentExpression.getDestinationNode()));

        HashSet<ReachingDefinitionTriple> currentStateRD = reachingDefinitions.get(currentExpression.getDestinationNode());
        currentStateRD.addAll(startStateRD);
        //reachingDefinitions.put(currentExpression.getDestinationNode(), currentStateRD);
    }

    private void dealWithDeclaration(VariableDeclaration currentExpression) {
        HashSet<ReachingDefinitionTriple> startStateRD = reachingDefinitions.get(currentExpression.getStartNode());
//        startStateRD.add(
//                new ReachingDefinitionTriple(currentExpression.getVariableName(),
//                        currentExpression.getStartNode(),
//                        currentExpression.getDestinationNode()));
//        reachingDefinitions.put(currentExpression.getDestinationNode(), startStateRD);
        for (Integer i : reachingDefinitions.keySet())
        {
            boolean hasVariableAssignment = false;
            HashSet<ReachingDefinitionTriple> currSet = reachingDefinitions.get(i);
            for (ReachingDefinitionTriple triple : currSet)
            {
                if (triple.variableName == currentExpression.getVariableName())
                    hasVariableAssignment = true;
            }
            if (!hasVariableAssignment)
            {
                currSet.add(new ReachingDefinitionTriple(currentExpression.getVariableName(), -1, 0));
                reachingDefinitions.put(i, currSet);
            }
        }
    }

    private void dealWithReadOperation(ReadOperation currentExpression) {
        HashSet<ReachingDefinitionTriple> startStateRD = (HashSet<ReachingDefinitionTriple>)reachingDefinitions.get(currentExpression.getStartNode()).clone();
        if (currentExpression.getVariableType() == Expression.VARIABLE_VARIABLE)
        {
            //kill function
            for (Iterator<ReachingDefinitionTriple> i = startStateRD.iterator(); i.hasNext();)
            {
                ReachingDefinitionTriple triple = i.next();
                if (triple.variableName == currentExpression.getVariable().getVariableName())
                    i.remove();
            }
        }
        startStateRD.add(
                new ReachingDefinitionTriple(currentExpression.getVariable().getVariableName(),
                        currentExpression.getStartNode(),
                        currentExpression.getDestinationNode()));

        HashSet<ReachingDefinitionTriple> currentStateRD = reachingDefinitions.get(currentExpression.getDestinationNode());
        currentStateRD.addAll(startStateRD);
        //reachingDefinitions.put(currentExpression.getDestinationNode(), currentStateRD);
    }

    private void prettyPrint()
    {
        for (Map.Entry<Integer, HashSet<ReachingDefinitionTriple>> entry: this.reachingDefinitions.entrySet())
        {
            StringBuilder l = new StringBuilder("Node: " + entry.getKey().toString() + "     RD: ");
            for (ReachingDefinitionTriple triple: entry.getValue())
            {
                l.append("(").append(triple.variableName).append(", ").append(triple.startNode).append(", ").append(triple.endNode).append("), ");
            }
            System.out.println(l);
        }
    }


    private class ReachingDefinitionTriple {
        private String variableName;
        private int startNode;
        private int endNode;

        public ReachingDefinitionTriple(String variableName, int startNode, int endNode)
        {
            this.variableName = variableName;
            this.startNode = startNode;
            this.endNode = endNode;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ReachingDefinitionTriple that = (ReachingDefinitionTriple) o;
            return startNode == that.startNode && endNode == that.endNode && variableName.equals(that.variableName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(variableName, startNode, endNode);
        }
    }

}
