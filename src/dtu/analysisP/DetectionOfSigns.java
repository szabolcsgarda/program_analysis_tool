package dtu.analysisP;

import dtu.ProgramGraph;
import dtu.auxiliary.AnalysisAssignment;
import dtu.expressions.*;
import dtu.syntaxTree.BooleanOperation;
import dtu.syntaxTree.IntegerEvaluation;
import dtu.syntaxTree.Primitive;
import dtu.syntaxTree.Variable;

import java.util.*;

public class DetectionOfSigns extends Analysis{

    public enum Sign {neg, zero, pos};

    private HashMap<Integer, HashMap<String, HashSet<Sign>>> detectionOfSigns = new HashMap<>();

    private WorkListAlgorithm mWorkListAlgorithm;
    private ProgramGraph mProgram;


    public DetectionOfSigns(ProgramGraph aProgram, WorkListAlgorithm aWorkListAlgorithm, String[] variables)
    {
        mWorkListAlgorithm = aWorkListAlgorithm;
        mProgram = aProgram;
        HashMap<String, HashSet<Sign>> startingState = new HashMap<>();
        for (String v: variables)
        {
            startingState.put(v, new HashSet<>(Arrays.asList(Sign.neg, Sign.zero, Sign.pos)));
        }
        detectionOfSigns.put(0, startingState);
        for (int i = 1; i < mProgram.getNodeNumber(); i++)
        {
            detectionOfSigns.put(i, new HashMap<>());
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
            HashMap<String, HashSet<Sign>> oldRecords = (HashMap<String, HashSet<Sign>>)detectionOfSigns.get(currentExpression.getDestinationNode()).clone();
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
            if(!oldRecords.equals(detectionOfSigns.get(currentExpression.getDestinationNode())))
            {
                mWorkListAlgorithm.feedbackChangedNodes(currentExpression.getDestinationNode());
            }
        }
        prettyPrint();
        return result;
    }

    private void dealWithBooleanEvaluation(BooleanExpression currentExpression) {
        HashMap<String, HashSet<Sign>> startStateDS = deepMapCopy(detectionOfSigns.get(currentExpression.getStartNode()));
        HashMap<String, HashSet<Sign>> currentStateDS = (HashMap<String, HashSet<Sign>>)detectionOfSigns.get(currentExpression.getDestinationNode()).clone();
        if (currentExpression.getEvaluation().getClass().getSimpleName().equals("BooleanOperation"))
        {
            BooleanOperation castedParent = (BooleanOperation)currentExpression.getEvaluation();
            if (castedParent.booleanEvaluation1.getClass().getSimpleName().equals("IntegerEvaluation"))
            {
                IntegerEvaluation castedCurr = (IntegerEvaluation)castedParent.booleanEvaluation1;
                if (castedCurr.value1.getClass().getSimpleName().equals("Variable")) {
                    Variable castedVar = (Variable)castedCurr.value1;
                    HashSet<Sign> signFilter = castedCurr.Aboolean(startStateDS);
                    if (startStateDS.containsKey(castedVar.getVariableName()))
                        startStateDS.get(castedVar.getVariableName()).retainAll(signFilter);
                }
            }
            if (castedParent.booleanEvaluation2.getClass().getSimpleName().equals("IntegerEvaluation"))
            {
                IntegerEvaluation castedCurr = (IntegerEvaluation)castedParent.booleanEvaluation2;
                if (castedCurr.value1.getClass().getSimpleName().equals("Variable")) {
                    Variable castedVar = (Variable)castedCurr.value1;
                    HashSet<Sign> signFilter = castedCurr.Aboolean(startStateDS);
                    if (startStateDS.containsKey(castedVar.getVariableName()))
                        startStateDS.get(castedVar.getVariableName()).retainAll(signFilter);
                }
            }
        }
        if (currentExpression.getEvaluation().getClass().getSimpleName().equals("IntegerEvaluation"))
        {
            IntegerEvaluation castedCurr = (IntegerEvaluation)currentExpression.getEvaluation();
            if (castedCurr.value1.getClass().getSimpleName().equals("Variable")) {
                Variable castedVar = (Variable)castedCurr.value1;
                HashSet<Sign> signFilter = castedCurr.Aboolean(startStateDS);
                if (startStateDS.containsKey(castedVar.getVariableName()))
                    startStateDS.get(castedVar.getVariableName()).retainAll(signFilter);
            }
        }
        for (Map.Entry<String, HashSet<Sign>> startE: startStateDS.entrySet())
        {
            if (currentStateDS.containsKey(startE.getKey()))
            {
                if (currentStateDS.get(startE.getKey()).addAll(startE.getValue()));
            }
            else currentStateDS.put(startE.getKey(), startE.getValue());
        }
        detectionOfSigns.get(currentExpression.getDestinationNode()).putAll(currentStateDS);
    }


    private void dealWithAssignment(AssignmentExpression currentExpression) {
        HashMap<String, HashSet<Sign>> startStateDS = (HashMap<String, HashSet<Sign>>)detectionOfSigns.get(currentExpression.getStartNode()).clone();

        startStateDS.put(currentExpression.getVariableName(), currentExpression.aExp(startStateDS));

        HashMap<String, HashSet<Sign>> currentStateDS = detectionOfSigns.get(currentExpression.getDestinationNode());
        for (Map.Entry<String, HashSet<Sign>> startE: startStateDS.entrySet())
        {
            if (currentStateDS.containsKey(startE.getKey()))
            {
                currentStateDS.get(startE.getKey()).addAll(startE.getValue());
            }
            else currentStateDS.put(startE.getKey(), startE.getValue());
        }
        detectionOfSigns.get(currentExpression.getDestinationNode()).putAll(currentStateDS);
    }

    private void dealWithDeclaration(VariableDeclaration currentExpression) {
        HashMap<String, HashSet<Sign>> startStateDS = (HashMap<String, HashSet<Sign>>)detectionOfSigns.get(currentExpression.getStartNode()).clone();
        startStateDS.put(currentExpression.getVariableName(), new HashSet<>(Arrays.asList(Sign.neg, Sign.zero, Sign.pos)));
        HashMap<String, HashSet<Sign>> currentStateDS = (HashMap<String, HashSet<Sign>>)detectionOfSigns.get(currentExpression.getDestinationNode()).clone();
        for (Map.Entry<String, HashSet<Sign>> startE: startStateDS.entrySet())
        {
            if (currentStateDS.containsKey(startE.getKey()))
            {
                currentStateDS.get(startE.getKey()).addAll(startE.getValue());
            }
            else currentStateDS.put(startE.getKey(), startE.getValue());
        }
        detectionOfSigns.get(currentExpression.getDestinationNode()).putAll(currentStateDS);

    }

    private void dealWithReadOperation(ReadOperation currentExpression) {
        HashMap<String, HashSet<Sign>> startStateDS = (HashMap<String, HashSet<Sign>>)detectionOfSigns.get(currentExpression.getStartNode()).clone();
        startStateDS.put(currentExpression.getVariableName(), new HashSet<>(Arrays.asList(Sign.neg, Sign.zero, Sign.pos)));
        HashMap<String, HashSet<Sign>> currentStateDS = (HashMap<String, HashSet<Sign>>)detectionOfSigns.get(currentExpression.getDestinationNode()).clone();
        for (Map.Entry<String, HashSet<Sign>> startE: startStateDS.entrySet())
        {
            if (currentStateDS.containsKey(startE.getKey()))
            {
                currentStateDS.get(startE.getKey()).addAll(startE.getValue());
            }
            else currentStateDS.put(startE.getKey(), startE.getValue());
        }
        detectionOfSigns.get(currentExpression.getDestinationNode()).putAll(currentStateDS);
    }

    private void prettyPrint()
    {
        for (Map.Entry<Integer, HashMap<String, HashSet<Sign>>> entry: this.detectionOfSigns.entrySet())
        {
            StringBuilder l = new StringBuilder("Node: " + entry.getKey().toString() + "     DS: ");
            for (Map.Entry<String, HashSet<Sign>> record : entry.getValue().entrySet())
            {
                l.append(record.getKey()).append(" -> {");
                for (Sign sign: record.getValue()) {
                    switch (sign) {
                        case neg:
                            l.append("-, ");
                            break;
                        case zero:
                            l.append("0, ");
                            break;
                        case pos:
                            l.append("+, ");
                            break;
                    }
                }
                l.append("}, ");
            }
            System.out.println(l);
        }
    }

    private HashMap<String, HashSet<Sign>> deepMapCopy(HashMap<String, HashSet<Sign>> original)
    {
        HashMap<String, HashSet<Sign>> copy = new HashMap<>();
        for (Map.Entry<String, HashSet<Sign>> entry: original.entrySet())
        {
            HashSet<Sign> copyValue = new HashSet<>();
            for (Sign s: entry.getValue())
            {
                copyValue.add(s);
            }
            copy.put(entry.getKey(), copyValue);
        }
        return copy;
    }
}

