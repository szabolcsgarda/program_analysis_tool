package dtu;

import dtu.analysisP.ChaoticIteration;
import dtu.analysisP.LiveVariables;
import dtu.analysisP.ReachingDefinitions;
import dtu.expressions.Assignment;
import dtu.expressions.BooleanEvaluation;
import dtu.expressions.Expression;
import dtu.expressions.VariableDeclaration;

import java.util.*;

public class Main {
	public static void main(String[] args)
	{
		System.out.println("Initialization in progress");
		//TODO: Place decent graph builder here

		Queue<Expression> dummyExpressions = new ArrayDeque<>();
		dummyExpressions.add(new Assignment("edge1","", "x", 0, 1, new HashSet<>()));
		dummyExpressions.add(new Assignment("edge2","", "y", 1, 2, new HashSet<>()));
		dummyExpressions.add(new BooleanEvaluation("edge3","", 2, 3, new HashSet<>(Arrays.asList("x"))));
		dummyExpressions.add(new BooleanEvaluation("edge4","", 2, 4, new HashSet<>(Arrays.asList("x"))));
		dummyExpressions.add(new Assignment("edge5","", "y", 4, 5, new HashSet<>(Arrays.asList("x", "y"))));
		dummyExpressions.add(new Assignment("edge6","", "x", 5, 2, new HashSet<>(Arrays.asList("x"))));
		dummyExpressions.add(new Assignment("edge7","", "x", 3, 6, new HashSet<>()));

		ProgramGraph mProgramGraph = new ProgramGraph(7, dummyExpressions);
		ChaoticIteration chaoticIteration = new ChaoticIteration(mProgramGraph);
//		ReachingDefinitions rd = new ReachingDefinitions(mProgramGraph, chaoticIteration);
//		rd.runAnalysis();

		LiveVariables lv = new LiveVariables(mProgramGraph, chaoticIteration);
		lv.runAnalysis();
		//reachingDefinitionsTest();

    }

//	private static void reachingDefinitionsTest() {
//		int[] nodes = {0, 1, 2, 3, 4, 5, 6};
//		Queue<Expression> expressions = new ArrayDeque<>();
//		expressions.add(new Assignment("","", "x", Expression.VARIABLE_VARIABLE, 0, 1));
//		expressions.add(new Assignment("","", "x", Expression.VARIABLE_VARIABLE, 0, 1));
//		expressions.add(new Assignment("","", "y", Expression.VARIABLE_VARIABLE, 1, 2));
//		expressions.add(new BooleanEvaluation("","", 2, 3));
//		expressions.add(new BooleanEvaluation("","", 2, 4));
//		expressions.add(new Assignment("","", "x", Expression.VARIABLE_VARIABLE, 3, 6));
//		expressions.add(new Assignment("","", "y", Expression.VARIABLE_VARIABLE, 4, 5));
//		expressions.add(new Assignment("","", "x", Expression.VARIABLE_VARIABLE, 5, 2));
//		expressions.add(new Assignment("","", "y", Expression.VARIABLE_VARIABLE, 1, 2));
//		expressions.add(new BooleanEvaluation("","", 2, 3));
//		expressions.add(new BooleanEvaluation("","", 2, 4));
//		expressions.add(new Assignment("","", "x", Expression.VARIABLE_VARIABLE, 3, 6));
//		expressions.add(new Assignment("","", "y", Expression.VARIABLE_VARIABLE, 4, 5));
//		expressions.add(new Assignment("","", "x", Expression.VARIABLE_VARIABLE, 5, 2));
//		expressions.add(new BooleanEvaluation("","", 2, 3));
//		expressions.add(new BooleanEvaluation("","", 2, 4));
//		expressions.add(new Assignment("","", "x", Expression.VARIABLE_VARIABLE, 3, 6));
//		expressions.add(new Assignment("","", "y", Expression.VARIABLE_VARIABLE, 4, 5));
//		expressions.add(new Assignment("","", "x", Expression.VARIABLE_VARIABLE, 5, 2));
//		expressions.add(new VariableDeclaration("","", "x", Expression.VARIABLE_VARIABLE, 0, 0));
//		expressions.add(new VariableDeclaration("","", "y", Expression.VARIABLE_VARIABLE, 0, 0));
//		ReachingDefinitions rd = new ReachingDefinitions(expressions, nodes);
//		rd.run();
//	}
}
