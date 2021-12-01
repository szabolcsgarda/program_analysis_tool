package dtu.syntaxTree;

import dtu.analysisP.DetectionOfSigns;

import java.util.HashMap;
import java.util.HashSet;

public abstract class Value {

    public abstract HashSet<Variable> getUsedVariables();

    public abstract HashSet<DetectionOfSigns.Sign> aExp(HashMap<String, HashSet<DetectionOfSigns.Sign>> currentState);
}
