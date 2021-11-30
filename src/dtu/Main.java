package dtu;

import dtu.analysisP.*;
import dtu.experiments.Experiments;
import dtu.expressions.Assignment;
import dtu.expressions.BooleanEvaluation;
import dtu.expressions.Expression;
import dtu.expressions.WriteOperation;

import java.util.*;

public class Main {
	public static void main(String[] args)
	{
		Queue<Expression> dummyExpressions = new ArrayDeque<>();
		dummyExpressions.add(new Assignment("edge1","", "x", 0, 1, new HashSet<>()));
		dummyExpressions.add(new Assignment("edge2","", "y", 1, 2, new HashSet<>()));
		dummyExpressions.add(new BooleanEvaluation("edge3","", 2, 3, new HashSet<>(Arrays.asList("x"))));
		dummyExpressions.add(new BooleanEvaluation("edge4","", 2, 4, new HashSet<>(Arrays.asList("x"))));
		dummyExpressions.add(new Assignment("edge5","", "y", 4, 5, new HashSet<>(Arrays.asList("x", "y"))));
		dummyExpressions.add(new Assignment("edge6","", "x", 5, 2, new HashSet<>(Arrays.asList("x"))));
		dummyExpressions.add(new Assignment("edge7","", "x", 3, 6, new HashSet<>()));

		ProgramGraph mProgramGraph = new ProgramGraph(7, dummyExpressions);

		System.out.println("Generating Program Graphs...");

		ProgramGraph mProgramGraphTest1 = null;
		ProgramGraph mProgramGraphTest2 = null;
		ProgramGraph mProgramGraphTest3 = null;
		ProgramGraph mProgramGraphTest4 = null;

		// Example program with 2 nested loops:
   		/*
   		int x = 0;
   		int k = 0;
   		while(x < 10)
   		{
   		    x += 1;
   		    y = 0;
   		    while(y < 10)
   		    {
   		       y++;
   		       k = k + x *y;
   		    }
   		}
   		 */
		int nodeNumber = 9;
		Queue<Expression> expressions = new ArrayDeque<>();
		expressions.add(new Assignment("edge1","", "x", 0, 1, new HashSet<>()));
		expressions.add(new Assignment("edge2","", "q", 1, 2, new HashSet<>()));
		expressions.add(new BooleanEvaluation("edge3","", 2, 3, new HashSet<>(Arrays.asList("x"))));
		expressions.add(new BooleanEvaluation("edge4","", 2, 8, new HashSet<>(Arrays.asList("x"))));
		expressions.add(new Assignment("edge5","", "x", 3, 4, new HashSet<>(Arrays.asList("x"))));
		expressions.add(new Assignment("edge6","", "y", 4, 5, new HashSet<>()));
		expressions.add(new BooleanEvaluation("edge7","", 5, 6, new HashSet<>(Arrays.asList("y"))));
		expressions.add(new BooleanEvaluation("edge8","", 5, 2, new HashSet<>(Arrays.asList("y"))));
		expressions.add(new Assignment("edge9","", "y", 6, 7, new HashSet<>(Arrays.asList("y"))));
		expressions.add(new Assignment("edge10","", "k", 7, 5, new HashSet<>(Arrays.asList("k","x","y"))));
		mProgramGraphTest1 = new ProgramGraph(nodeNumber, expressions);


		// Example program with 3 nested loops:
    	/*
    	int x = 0;
    	int q = 0;
    	while(x < 10)
    	{
    	    x += 1;
    	    y = 0;
    	    while(y < 10)
    	    {
    	       y++;
    	       k = 0;
    	       while(k <10)
    	       {
    	            k++;
    	            q = q + x *y *k;
    	       }
    	    }
    	}
    	 */
		nodeNumber = 12;
		expressions = new ArrayDeque<>();
		expressions.add(new Assignment("edge1","", "x", 0, 1, new HashSet<>()));
		expressions.add(new Assignment("edge2","", "q", 1, 2, new HashSet<>()));
		expressions.add(new BooleanEvaluation("edge3","", 2, 3, new HashSet<>(Arrays.asList("x"))));
		expressions.add(new BooleanEvaluation("edge4","", 2, 11, new HashSet<>(Arrays.asList("x"))));
		expressions.add(new Assignment("edge5","", "x", 3, 4, new HashSet<>(Arrays.asList("x"))));
		expressions.add(new Assignment("edge6","", "y", 4, 5, new HashSet<>()));
		expressions.add(new BooleanEvaluation("edge7","", 5, 6, new HashSet<>(Arrays.asList("y"))));
		expressions.add(new BooleanEvaluation("edge8","", 5, 2, new HashSet<>(Arrays.asList("y"))));
		expressions.add(new Assignment("edge9","", "y", 6, 7, new HashSet<>(Arrays.asList("y"))));
		expressions.add(new Assignment("edge10","", "k", 7, 8, new HashSet<>()));
		expressions.add(new BooleanEvaluation("edge11","", 8, 9, new HashSet<>(Arrays.asList("k"))));
		expressions.add(new BooleanEvaluation("edge12","", 8, 5, new HashSet<>(Arrays.asList("k"))));
		expressions.add(new Assignment("edge13","", "k", 9, 10, new HashSet<>(Arrays.asList("k"))));
		expressions.add(new Assignment("edge14","", "q", 10, 8, new HashSet<>(Arrays.asList("k","x","y","k"))));
		mProgramGraphTest2 = new ProgramGraph(nodeNumber, expressions);

		//Modulo function
    	/*
    	if(x>=0&y>0)
    	{
    	    q = 0;
    	    r = x;
    	    while(r>=y)
    	    {
    	        r=r-y;
    	        q=q+1;
    	    }
    	    write r;
    	}
    	 */
		nodeNumber = 8;
		expressions = new ArrayDeque<>();
		expressions.add(new BooleanEvaluation("edge1","", 0, 7, new HashSet<>(Arrays.asList("x","y"))));
		expressions.add(new BooleanEvaluation("edge2","", 0, 1, new HashSet<>(Arrays.asList("x","y"))));
		expressions.add(new Assignment("edge3","", "q", 1, 2, new HashSet<>()));
		expressions.add(new Assignment("edge4","", "r", 2, 3, new HashSet<>(Arrays.asList("x"))));
		expressions.add(new BooleanEvaluation("edge5","", 3, 5, new HashSet<>(Arrays.asList("r","y"))));
		expressions.add(new BooleanEvaluation("edge6","", 3, 4, new HashSet<>(Arrays.asList("r","y"))));
		expressions.add(new Assignment("edge7","", "r", 5, 6, new HashSet<>(Arrays.asList("r","y"))));
		expressions.add(new Assignment("edge8","", "q", 6, 3, new HashSet<>(Arrays.asList("q"))));
		expressions.add(new WriteOperation("edge9","", 4, 7, "r"));
		mProgramGraphTest3 = new ProgramGraph(nodeNumber, expressions);

		//Matrix transpose
    	/*
    	 int i = 0;
    	 while(i<n)
    	 {
    	    j=0;
    	    while(j<m)
    	    {
    	        u=(i*m)+j;
    	        t=(j*n)+i;
    	        B[t]=A[u];
    	        j=j+1;
    	    }
    	    i=i+1;
    	 }
    	 */

		nodeNumber = 10;
		expressions = new ArrayDeque<>();
		expressions.add(new Assignment("edge1","", "i", 0, 1, new HashSet<>()));
		expressions.add(new BooleanEvaluation("edge2","", 1, 2, new HashSet<>(Arrays.asList("i","n"))));
		expressions.add(new BooleanEvaluation("edge3","", 1, 9, new HashSet<>(Arrays.asList("i","n"))));
		expressions.add(new Assignment("edge4","", "j", 2, 3, new HashSet<>()));
		expressions.add(new BooleanEvaluation("edge5","", 3, 5, new HashSet<>(Arrays.asList("j","m"))));
		expressions.add(new BooleanEvaluation("edge6","", 3, 4, new HashSet<>(Arrays.asList("j","m"))));
		expressions.add(new Assignment("edge7","", "u", 5, 6, new HashSet<>(Arrays.asList("i","m","j"))));
		expressions.add(new Assignment("edge8","", "t", 6, 7, new HashSet<>(Arrays.asList("j","n","i"))));
		expressions.add(new Assignment("edge9","", "B","t", 7, 8, new HashSet<>(Arrays.asList("t","A","u"))));
		expressions.add(new Assignment("edge10","", "j", 8, 3, new HashSet<>(Arrays.asList("j"))));
		expressions.add(new Assignment("edge11","", "i", 4, 1, new HashSet<>(Arrays.asList("i"))));
		mProgramGraphTest4 = new ProgramGraph(nodeNumber, expressions);


		ArrayList<String>testName = new ArrayList<>();
		ArrayList<Long>startTime = new ArrayList<Long>();
		ArrayList<Long>endTime = new ArrayList<Long>();
		ArrayList<Integer>iterationNumber = new ArrayList<Integer>();

		System.out.println("Starting tests");
		System.out.println("Running Reaching Definition tests using lifo and fifo WL");

		//No prints during tests to avoid effect on execution times
		/*testName.add("Program1 -> RD with FIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist firstInFirstOut11 = new SimpleWorklist(mProgramGraphTest1,1);
		ReachingDefinitions rd11 = new ReachingDefinitions(mProgramGraphTest1, firstInFirstOut11);
		rd11.run();
		endTime.add(System.nanoTime());
		iterationNumber.add(firstInFirstOut11.getIterationNumber());

		testName.add("Program1 -> RD with LIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist lastInFirstOut11 = new SimpleWorklist(mProgramGraphTest1, 1);
		ReachingDefinitions rd12 = new ReachingDefinitions(mProgramGraphTest1, lastInFirstOut11);
		rd12.run();
		endTime.add(System.nanoTime());
		iterationNumber.add(lastInFirstOut11.getIterationNumber());

		testName.add("Program2 -> RD with FIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist firstInFirstOut12 = new SimpleWorklist(mProgramGraphTest2, 0);
		ReachingDefinitions rd21 = new ReachingDefinitions(mProgramGraphTest2, firstInFirstOut12);
		rd21.run();
		endTime.add(System.nanoTime());
		iterationNumber.add(firstInFirstOut12.getIterationNumber());

		testName.add("Program2 -> RD with LIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist lastInFirstOut12 = new SimpleWorklist(mProgramGraphTest2, 1);
		ReachingDefinitions rd22 = new ReachingDefinitions(mProgramGraphTest2, lastInFirstOut12);
		rd22.run();
		endTime.add(System.nanoTime());
		iterationNumber.add(lastInFirstOut12.getIterationNumber());

		testName.add("Program3 -> RD with FIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist firstInFirstOut13 = new SimpleWorklist(mProgramGraphTest3, 0);
		ReachingDefinitions rd31 = new ReachingDefinitions(mProgramGraphTest3, firstInFirstOut13);
		rd31.run();
		endTime.add(System.nanoTime());
		iterationNumber.add(firstInFirstOut13.getIterationNumber());

		testName.add("Program3 -> RD with LIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist lastInFirstOut13 = new SimpleWorklist(mProgramGraphTest3, 1);
		ReachingDefinitions rd32 = new ReachingDefinitions(mProgramGraphTest3, lastInFirstOut13);
		rd32.run();
		endTime.add(System.nanoTime());
		iterationNumber.add(lastInFirstOut13.getIterationNumber());

		testName.add("Program4 -> RD with FIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist firstInFirstOut14 = new SimpleWorklist(mProgramGraphTest4, 0);
		ReachingDefinitions rd41 = new ReachingDefinitions(mProgramGraphTest4, firstInFirstOut14);
		rd41.run();
		endTime.add(System.nanoTime());
		iterationNumber.add(firstInFirstOut14.getIterationNumber());

		testName.add("Program4 -> RD with LIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist lastInFirstOut14 = new SimpleWorklist(mProgramGraphTest4, 1);
		ReachingDefinitions rd42 = new ReachingDefinitions(mProgramGraphTest4, lastInFirstOut14);
		rd42.run();
		endTime.add(System.nanoTime());
		iterationNumber.add(lastInFirstOut14.getIterationNumber());*/




		System.out.println("Running Live Variables tests using lifo and fifo WL");
		testName.add("Program1 -> LV with FIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist firstInFirstOut21 = new SimpleWorklist(mProgramGraphTest1,1);
		LiveVariables lv11 = new LiveVariables(mProgramGraphTest1, firstInFirstOut21);
		lv11.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(firstInFirstOut21.getIterationNumber());

		testName.add("Program1 -> LV with LIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist lastInFirstOut21 = new SimpleWorklist(mProgramGraphTest1, 1);
		LiveVariables lv12 = new LiveVariables(mProgramGraphTest1, lastInFirstOut21);
		lv12.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(lastInFirstOut21.getIterationNumber());

		testName.add("Program2 -> LV with FIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist firstInFirstOut22 = new SimpleWorklist(mProgramGraphTest2, 0);
		LiveVariables lv21 = new LiveVariables(mProgramGraphTest2, firstInFirstOut22);
		lv21.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(firstInFirstOut22.getIterationNumber());

		testName.add("Program2 -> LV with LIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist lastInFirstOut22 = new SimpleWorklist(mProgramGraphTest2, 1);
		LiveVariables lv22 = new LiveVariables(mProgramGraphTest2, lastInFirstOut22);
		lv22.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(lastInFirstOut22.getIterationNumber());

		testName.add("Program3 -> LV with FIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist firstInFirstOut23 = new SimpleWorklist(mProgramGraphTest3, 0);
		LiveVariables lv31 = new LiveVariables(mProgramGraphTest3, firstInFirstOut23);
		lv31.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(firstInFirstOut23.getIterationNumber());

		testName.add("Program3 -> LV with LIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist lastInFirstOut23 = new SimpleWorklist(mProgramGraphTest3, 1);
		LiveVariables lv32 = new LiveVariables(mProgramGraphTest3, lastInFirstOut23);
		lv32.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(lastInFirstOut23.getIterationNumber());

		testName.add("Program4 -> LV with FIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist firstInFirstOut24 = new SimpleWorklist(mProgramGraphTest4, 0);
		LiveVariables lv41 = new LiveVariables(mProgramGraphTest4, firstInFirstOut24);
		lv41.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(firstInFirstOut24.getIterationNumber());

		testName.add("Program4 -> LV with LIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist lastInFirstOut24 = new SimpleWorklist(mProgramGraphTest4, 1);
		LiveVariables lv42 = new LiveVariables(mProgramGraphTest4, lastInFirstOut24);
		lv42.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(lastInFirstOut24.getIterationNumber());






		System.out.println("Running Faint Variables tests using lifo and fifo WL");
		testName.add("Program1 -> FV with FIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist firstInFirstOut31 = new SimpleWorklist(mProgramGraphTest1,1);
		FaintVariables fv11 = new FaintVariables(mProgramGraphTest1, firstInFirstOut31);
		fv11.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(firstInFirstOut31.getIterationNumber());

		testName.add("Program1 -> FV with LIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist lastInFirstOut31 = new SimpleWorklist(mProgramGraphTest1, 1);
		FaintVariables fv12 = new FaintVariables(mProgramGraphTest1, lastInFirstOut31);
		fv12.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(lastInFirstOut31.getIterationNumber());

		testName.add("Program2 -> FV with FIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist firstInFirstOut32 = new SimpleWorklist(mProgramGraphTest2, 0);
		FaintVariables fv21 = new FaintVariables(mProgramGraphTest2, firstInFirstOut32);
		fv21.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(firstInFirstOut32.getIterationNumber());

		testName.add("Program2 -> FV with LIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist lastInFirstOut32 = new SimpleWorklist(mProgramGraphTest2, 1);
		FaintVariables fv22 = new FaintVariables(mProgramGraphTest2, lastInFirstOut32);
		fv22.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(lastInFirstOut32.getIterationNumber());

		testName.add("Program3 -> FV with FIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist firstInFirstOut33 = new SimpleWorklist(mProgramGraphTest3, 0);
		FaintVariables fv31 = new FaintVariables(mProgramGraphTest3, firstInFirstOut33);
		fv31.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(firstInFirstOut33.getIterationNumber());

		testName.add("Program3 -> FV with LIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist lastInFirstOut33 = new SimpleWorklist(mProgramGraphTest3, 1);
		FaintVariables fv32 = new FaintVariables(mProgramGraphTest3, lastInFirstOut33);
		fv32.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(lastInFirstOut33.getIterationNumber());

		testName.add("Program4 -> FV with FIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist firstInFirstOut34 = new SimpleWorklist(mProgramGraphTest4, 0);
		FaintVariables fv41 = new FaintVariables(mProgramGraphTest4, firstInFirstOut34);
		lv41.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(firstInFirstOut34.getIterationNumber());

		testName.add("Program4 -> FV with LIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist lastInFirstOut34 = new SimpleWorklist(mProgramGraphTest4, 1);
		FaintVariables fv42 = new FaintVariables(mProgramGraphTest4, lastInFirstOut34);
		fv42.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(lastInFirstOut34.getIterationNumber());


		for(int i=0;i<testName.size();i++)
		{
			System.out.println(testName.get(i) + "\t" + ((endTime.get(i)-startTime.get(i))/1000) +"\t Iteration number:" + iterationNumber.get(i));
		}
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
