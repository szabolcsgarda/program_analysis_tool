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

    public ReachingDefinitions(Queue<Expression> expressionQueue, int[] nodes)
    {
        this.expressionQueue = expressionQueue;
        for (int i = 0; i < nodes.length; i++)
        {
            reachingDefinitions.put(nodes[i], new HashSet<>());
        }
    }

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
            Expression currentExpression = mWorkListAlgorithm.getNextExpression();
            if(currentExpression == null)
            {
                break;
            }
            ArrayList<Integer> feedbackNodes = new ArrayList<>();
            switch(currentExpression.getClass().getSimpleName()) {
                case "Assignment":
                    feedbackNodes = dealWithAssignment((Assignment)currentExpression);
                    break;
                case "VariableDeclaration":
                    feedbackNodes = dealWithDeclaration((VariableDeclaration)currentExpression);
                    break;
                case "ReadOperation":
                    feedbackNodes = dealWithReadOperation((ReadOperation)currentExpression);
                    break;
                case "BooleanEvaluation":
                    feedbackNodes = dealWithBooleanEvaluation((BooleanEvaluation)currentExpression);
                    break;
                default:
                    break;
            }
            mWorkListAlgorithm.feedbackChangedNodes(feedbackNodes);
        }
        prettyPrint();
        return result;
    }

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

    private ArrayList<Integer> dealWithBooleanEvaluation(BooleanEvaluation currentExpression) {
        ArrayList<Integer> touchedNodes = new ArrayList<>(); //TODO add which nodes's reaching definition triples have been modified
        HashSet<ReachingDefinitionTriple> startStateRD = (HashSet<ReachingDefinitionTriple>)reachingDefinitions.get(currentExpression.getStartNode()).clone();
        reachingDefinitions.get(currentExpression.getDestinationNode()).addAll(startStateRD);
        touchedNodes.add(currentExpression.getDestinationNode());
        return touchedNodes;
    }


    private ArrayList<Integer> dealWithAssignment(Assignment currentExpression) {
        ArrayList<Integer> touchedNodes = new ArrayList<>();
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
        return touchedNodes;
    }

    private ArrayList<Integer> dealWithDeclaration(VariableDeclaration currentExpression) {
        ArrayList<Integer> touchedNodes = new ArrayList<>();
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
        return touchedNodes;
    }

    private ArrayList<Integer> dealWithReadOperation(ReadOperation currentExpression) {
        ArrayList<Integer> touchedNodes = new ArrayList<>();
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
        return touchedNodes;
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
