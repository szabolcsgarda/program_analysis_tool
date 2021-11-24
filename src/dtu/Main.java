package dtu;

import dtu.analysisP.*;
import dtu.expressions.Assignment;
import dtu.expressions.BooleanEvaluation;
import dtu.expressions.Expression;
import dtu.expressions.VariableDeclaration;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class Main {
	public static void main(String[] args)
	{
		System.out.println("Initialization in progress");
		//TODO: Place decent graph builder here
		Queue<Expression> dummyExpressions = new ArrayDeque<>();
		dummyExpressions.add(new Assignment("edge1","", "x", Expression.VARIABLE_VARIABLE, 0, 1));
		dummyExpressions.add(new Assignment("edge2","", "y", Expression.VARIABLE_VARIABLE, 1, 2));
		dummyExpressions.add(new BooleanEvaluation("edge3","", 2, 3));
		dummyExpressions.add(new BooleanEvaluation("edge4","", 2, 4));
		dummyExpressions.add(new Assignment("edge5","", "y", Expression.VARIABLE_VARIABLE, 4, 5));
		dummyExpressions.add(new Assignment("edge6","", "x", Expression.VARIABLE_VARIABLE, 5, 2));
		dummyExpressions.add(new Assignment("edge7","", "x", Expression.VARIABLE_VARIABLE, 3, 6));

		ProgramGraph mProgramGraph = new ProgramGraph(7, dummyExpressions);
		ChaoticIteration chaoticIteration = new ChaoticIteration(mProgramGraph);
		RoundRobin roundRobin = new RoundRobin(mProgramGraph);
		SimpleWorklist firstInFirstOut = new SimpleWorklist(mProgramGraph, 0);
		SimpleWorklist lastInFirstOut = new SimpleWorklist(mProgramGraph, 1);

		long startTime;
		long endTime;
		System.out.println("Using chaotic iteration");
		ReachingDefinitions rd1 = new ReachingDefinitions(mProgramGraph, chaoticIteration);
		startTime = System.currentTimeMillis();
		rd1.runAnalysis();
		endTime = System.currentTimeMillis();
		System.out.println("Execution time:" + (endTime-startTime) + " [ms]");


		System.out.println("Using round robin");
		ReachingDefinitions rd2 = new ReachingDefinitions(mProgramGraph, roundRobin);
		startTime = System.currentTimeMillis();
		rd2.runAnalysis();
		endTime = System.currentTimeMillis();
		System.out.println("Execution time:" + (endTime-startTime) + " [ms]");

		System.out.println("Using FiFo WL");
		ReachingDefinitions rd3 = new ReachingDefinitions(mProgramGraph, firstInFirstOut);
		startTime = System.currentTimeMillis();
		rd3.runAnalysis();
		endTime = System.currentTimeMillis();
		System.out.println("Execution time:" + (endTime-startTime) + " [ms]");

		System.out.println("Using LIFO WL");
		ReachingDefinitions rd4 = new ReachingDefinitions(mProgramGraph, lastInFirstOut);
		startTime = System.currentTimeMillis();
		rd4.runAnalysis();
		endTime = System.currentTimeMillis();
		System.out.println("Execution time:" + (endTime-startTime) + " [ms]");
		//reachingDefinitionsTest();

		//DFS test
		DFS dfs = new DFS(mProgramGraph);
		dfs.run();
    }

	private static void reachingDefinitionsTest() {
		int[] nodes = {0, 1, 2, 3, 4, 5, 6};
		Queue<Expression> expressions = new ArrayDeque<>();
		expressions.add(new Assignment("","", "x", Expression.VARIABLE_VARIABLE, 0, 1));
		expressions.add(new Assignment("","", "x", Expression.VARIABLE_VARIABLE, 0, 1));
		expressions.add(new Assignment("","", "y", Expression.VARIABLE_VARIABLE, 1, 2));
		expressions.add(new BooleanEvaluation("","", 2, 3));
		expressions.add(new BooleanEvaluation("","", 2, 4));
		expressions.add(new Assignment("","", "x", Expression.VARIABLE_VARIABLE, 3, 6));
		expressions.add(new Assignment("","", "y", Expression.VARIABLE_VARIABLE, 4, 5));
		expressions.add(new Assignment("","", "x", Expression.VARIABLE_VARIABLE, 5, 2));
		expressions.add(new Assignment("","", "y", Expression.VARIABLE_VARIABLE, 1, 2));
		expressions.add(new BooleanEvaluation("","", 2, 3));
		expressions.add(new BooleanEvaluation("","", 2, 4));
		expressions.add(new Assignment("","", "x", Expression.VARIABLE_VARIABLE, 3, 6));
		expressions.add(new Assignment("","", "y", Expression.VARIABLE_VARIABLE, 4, 5));
		expressions.add(new Assignment("","", "x", Expression.VARIABLE_VARIABLE, 5, 2));
		expressions.add(new BooleanEvaluation("","", 2, 3));
		expressions.add(new BooleanEvaluation("","", 2, 4));
		expressions.add(new Assignment("","", "x", Expression.VARIABLE_VARIABLE, 3, 6));
		expressions.add(new Assignment("","", "y", Expression.VARIABLE_VARIABLE, 4, 5));
		expressions.add(new Assignment("","", "x", Expression.VARIABLE_VARIABLE, 5, 2));
		expressions.add(new VariableDeclaration("","", "x", Expression.VARIABLE_VARIABLE, 0, 0));
		expressions.add(new VariableDeclaration("","", "y", Expression.VARIABLE_VARIABLE, 0, 0));
		ReachingDefinitions rd = new ReachingDefinitions(expressions, nodes);
		rd.run();
	}
}
