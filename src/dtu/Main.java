package dtu;

import dtu.analysisP.*;
import dtu.experiments.Experiments;
import dtu.expressions.Assignment;
import dtu.expressions.BooleanEvaluation;
import dtu.expressions.Expression;

import java.util.*;

public class Main {
	public static void main(String[] args)
	{
		System.out.println("Initialization in progress");
		//TODO: Place decent graph builder here

		/*Queue<Expression> dummyExpressions = new ArrayDeque<>();
		dummyExpressions.add(new Assignment("edge1","", "x", 0, 1, new HashSet<>()));
		dummyExpressions.add(new Assignment("edge2","", "y", 1, 2, new HashSet<>()));
		dummyExpressions.add(new BooleanEvaluation("edge3","", 2, 3, new HashSet<>(Arrays.asList("x"))));
		dummyExpressions.add(new BooleanEvaluation("edge4","", 2, 4, new HashSet<>(Arrays.asList("x"))));
		dummyExpressions.add(new Assignment("edge5","", "y", 4, 5, new HashSet<>(Arrays.asList("x", "y"))));
		dummyExpressions.add(new Assignment("edge6","", "x", 5, 2, new HashSet<>(Arrays.asList("x"))));
		dummyExpressions.add(new Assignment("edge7","", "x", 3, 6, new HashSet<>()));*/

		//ProgramGraph mProgramGraph = new ProgramGraph(7, dummyExpressions);

		ProgramGraph mProgramGraph = Experiments.matrixTranspose();



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

		System.out.println("Live Variables using Round Robin");
		LiveVariables lv = new LiveVariables(mProgramGraph, new RoundRobin(mProgramGraph));
		startTime = System.currentTimeMillis();
		lv.runAnalysis();
		endTime = System.currentTimeMillis();
		System.out.println("Execution time:" + (endTime-startTime) + " [ms]");

		liveVariableExample();

	}

	public static void liveVariableExample()
	{
		Queue<Expression> dummyExpressions = new ArrayDeque<>();

		dummyExpressions.add(new BooleanEvaluation("edge1","", 0, 1, new HashSet<>(Arrays.asList("x", "y"))));
		dummyExpressions.add(new Assignment("edge2","", "q", 1, 2, new HashSet<>()));
		dummyExpressions.add(new Assignment("edge3","", "r", 2, 3, new HashSet<>(Arrays.asList("x"))));
		dummyExpressions.add(new BooleanEvaluation("edge4","", 3, 4, new HashSet<>(Arrays.asList("r", "y"))));
		dummyExpressions.add(new Assignment("edge5","", "r", 4, 5, new HashSet<>(Arrays.asList("r", "y"))));
		dummyExpressions.add(new Assignment("edge6","", "q", 5, 3, new HashSet<>(Arrays.asList("q"))));
		dummyExpressions.add(new BooleanEvaluation("edge7","", 3, 6, new HashSet<>(Arrays.asList("r", "y"))));


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
