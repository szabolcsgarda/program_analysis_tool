package dtu.analysisP;

//import dtu.auxiliary.SetOfTriples;
import dtu.auxiliary.AnalysisAssignment;
import dtu.expressions.*;

import java.util.*;

public class ReachingDefinitions extends Analysis {

    private Queue<Expression> expressionQueue;
    private HashMap<Integer, HashSet<ReachingDefinitionTriple>> reachingDefinitions = new HashMap<>();

    public ReachingDefinitions(Queue<Expression> expressionQueue, int[] nodes)
    {
        this.expressionQueue = expressionQueue;
        for (int i = 0; i < nodes.length; i++)
        {
            reachingDefinitions.put(nodes[i], new HashSet<>());
        }
    }

    public AnalysisAssignment runAnalysis()
    {return null;}

    public void run()
    {
        //AnalysisAssignment result = new SetOfTriples();

        while (!expressionQueue.isEmpty())
        {
            Expression currentExpression = expressionQueue.poll();
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
        }
        prettyPrint();
    }

    private void dealWithBooleanEvaluation(BooleanEvaluation currentExpression) {
        HashSet<ReachingDefinitionTriple> startStateRD = (HashSet<ReachingDefinitionTriple>)reachingDefinitions.get(currentExpression.getStartNode()).clone();
        reachingDefinitions.get(currentExpression.getDestinationNode()).addAll(startStateRD);
    }


    private void dealWithAssignment(Assignment currentExpression) {
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
