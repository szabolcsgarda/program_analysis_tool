package dtu;

import dtu.analysisP.*;
import dtu.expressions.AssignmentExpression;
import dtu.expressions.BooleanExpression;
import dtu.expressions.Expression;
import dtu.syntaxTree.*;

import java.util.*;

public class Main {
	public static void main(String[] args)
	{
		System.out.println("Initialization in progress");
		//TODO: Place decent graph builder here

//		Queue<Expression> dummyExpressions = new ArrayDeque<>();
//		dummyExpressions.add(new AssignmentExpression("edge1","", "x", 0, 1, new HashSet<>()));
//		dummyExpressions.add(new AssignmentExpression("edge2","", "y", 1, 2, new HashSet<>()));
//		dummyExpressions.add(new BooleanExpression("edge3","", 2, 3, new HashSet<>(Arrays.asList("x"))));
//		dummyExpressions.add(new BooleanExpression("edge4","", 2, 4, new HashSet<>(Arrays.asList("x"))));
//		dummyExpressions.add(new AssignmentExpression("edge5","", "y", 4, 5, new HashSet<>(Arrays.asList("x", "y"))));
//		dummyExpressions.add(new AssignmentExpression("edge6","", "x", 5, 2, new HashSet<>(Arrays.asList("x"))));
//		dummyExpressions.add(new AssignmentExpression("edge7","", "x", 3, 6, new HashSet<>()));
//
//		ProgramGraph mProgramGraph = new ProgramGraph(7, dummyExpressions);
//		ChaoticIteration chaoticIteration = new ChaoticIteration(mProgramGraph);
//		RoundRobin roundRobin = new RoundRobin(mProgramGraph);
//		SimpleWorklist firstInFirstOut = new SimpleWorklist(mProgramGraph, 0);
//		SimpleWorklist lastInFirstOut = new SimpleWorklist(mProgramGraph, 1);
//
//		long startTime;
//		long endTime;
//		System.out.println("Using chaotic iteration");
//		ReachingDefinitions rd1 = new ReachingDefinitions(mProgramGraph, chaoticIteration);
//		startTime = System.currentTimeMillis();
//		rd1.runAnalysis();
//		endTime = System.currentTimeMillis();
//		System.out.println("Execution time:" + (endTime-startTime) + " [ms]");
//
//
//		System.out.println("Using round robin");
//		ReachingDefinitions rd2 = new ReachingDefinitions(mProgramGraph, roundRobin);
//		startTime = System.currentTimeMillis();
//		rd2.runAnalysis();
//		endTime = System.currentTimeMillis();
//		System.out.println("Execution time:" + (endTime-startTime) + " [ms]");
//
//		System.out.println("Using FiFo WL");
//		ReachingDefinitions rd3 = new ReachingDefinitions(mProgramGraph, firstInFirstOut);
//		startTime = System.currentTimeMillis();
//		rd3.runAnalysis();
//		endTime = System.currentTimeMillis();
//		System.out.println("Execution time:" + (endTime-startTime) + " [ms]");
//
//		System.out.println("Using LIFO WL");
//		ReachingDefinitions rd4 = new ReachingDefinitions(mProgramGraph, lastInFirstOut);
//		startTime = System.currentTimeMillis();
//		rd4.runAnalysis();
//		endTime = System.currentTimeMillis();
//		System.out.println("Execution time:" + (endTime-startTime) + " [ms]");
//
//		System.out.println("Live Variables using Round Robin");
//		LiveVariables lv = new LiveVariables(mProgramGraph, new RoundRobin(mProgramGraph));
//		startTime = System.currentTimeMillis();
//		lv.runAnalysis();
//		endTime = System.currentTimeMillis();
//		System.out.println("Execution time:" + (endTime-startTime) + " [ms]");

		liveVariableExample();

	}

	public static void liveVariableExample()
	{
		Queue<Expression> dummyExpressions = new ArrayDeque<>();

		dummyExpressions.add(new BooleanExpression("edge1","", 0, 1, new BooleanOperation(
				new IntegerEvaluation(new Variable("x"), IntegerEvaluation.OpI.GTE, new ConstantValue(0)),
				BooleanOperation.OpB.AND,
				new IntegerEvaluation(new Variable("y"), IntegerEvaluation.OpI.GTE, new ConstantValue(0)))));
		dummyExpressions.add(new AssignmentExpression("edge2","",  1, 2, new Assignment(new Variable("q"), new ConstantValue(0))));
		dummyExpressions.add(new AssignmentExpression("edge3","", 2, 3, new Assignment(new Variable("r"), new Variable("x"))));
		dummyExpressions.add(new BooleanExpression("edge4","", 3, 4, new IntegerEvaluation(new Variable("r"), IntegerEvaluation.OpI.GTE, new Variable("y"))));
		dummyExpressions.add(new AssignmentExpression("edge5","", 4, 5, new Assignment(new Variable("r"), new ArithmeticOperation(new Variable("r"), ArithmeticOperation.OpA.SUB, new Variable("y")))));
		dummyExpressions.add(new AssignmentExpression("edge6","",5, 3, new Assignment(new Variable("q"), new ArithmeticOperation(new Variable("q"), ArithmeticOperation.OpA.ADD, new ConstantValue(1)))));
		dummyExpressions.add(new BooleanExpression("edge7","", 3, 6, new IntegerEvaluation(new Variable("r"), IntegerEvaluation.OpI.LT, new Variable("y"))));


		ProgramGraph mProgramGraph = new ProgramGraph(7, dummyExpressions);

		LiveVariables lv = new LiveVariables(mProgramGraph, new RoundRobin(mProgramGraph));
		System.out.println("Live Variables using Round Robin");
		long startTime = System.currentTimeMillis();
		lv.runAnalysis();
		long endTime = System.currentTimeMillis();
		System.out.println("Execution time:" + (endTime-startTime) + " [ms]");

		FaintVariables fv = new FaintVariables(mProgramGraph, new RoundRobin(mProgramGraph));
		System.out.println("Faint Variables using Round Robin");
		startTime = System.currentTimeMillis();
		fv.runAnalysis();
		endTime = System.currentTimeMillis();
		System.out.println("Execution time:" + (endTime-startTime) + " [ms]");
	}

}
