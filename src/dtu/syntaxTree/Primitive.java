package dtu.syntaxTree;

import dtu.analysisP.DetectionOfSigns;

import java.util.HashMap;
import java.util.HashSet;

public abstract class Primitive extends Value{

    public abstract HashSet<Variable> getUsedVariables();

    public abstract String getVariableName();

    public abstract HashSet<DetectionOfSigns.Sign> aExp(HashMap<String, HashSet<DetectionOfSigns.Sign>> currentState);
}
