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
		testName.add("Program1 -> RD with FIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist firstInFirstOut11 = new SimpleWorklist(mProgramGraphTest1,1);
		ReachingDefinitions rd11 = new ReachingDefinitions(mProgramGraphTest1, firstInFirstOut11);
		rd11.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(firstInFirstOut11.getIterationNumber());

		testName.add("Program1 -> RD with LIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist lastInFirstOut11 = new SimpleWorklist(mProgramGraphTest1, 1);
		ReachingDefinitions rd12 = new ReachingDefinitions(mProgramGraphTest1, lastInFirstOut11);
		rd12.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(lastInFirstOut11.getIterationNumber());

		testName.add("Program2 -> RD with FIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist firstInFirstOut12 = new SimpleWorklist(mProgramGraphTest2, 0);
		ReachingDefinitions rd21 = new ReachingDefinitions(mProgramGraphTest2, firstInFirstOut12);
		rd21.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(firstInFirstOut12.getIterationNumber());

		testName.add("Program2 -> RD with LIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist lastInFirstOut12 = new SimpleWorklist(mProgramGraphTest2, 1);
		ReachingDefinitions rd22 = new ReachingDefinitions(mProgramGraphTest2, lastInFirstOut12);
		rd22.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(lastInFirstOut12.getIterationNumber());

		testName.add("Program3 -> RD with FIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist firstInFirstOut13 = new SimpleWorklist(mProgramGraphTest3, 0);
		ReachingDefinitions rd31 = new ReachingDefinitions(mProgramGraphTest3, firstInFirstOut13);
		rd31.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(firstInFirstOut13.getIterationNumber());

		testName.add("Program3 -> RD with LIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist lastInFirstOut13 = new SimpleWorklist(mProgramGraphTest3, 1);
		ReachingDefinitions rd32 = new ReachingDefinitions(mProgramGraphTest3, lastInFirstOut13);
		rd32.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(lastInFirstOut13.getIterationNumber());

		testName.add("Program4 -> RD with FIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist firstInFirstOut14 = new SimpleWorklist(mProgramGraphTest4, 0);
		ReachingDefinitions rd41 = new ReachingDefinitions(mProgramGraphTest4, firstInFirstOut14);
		rd41.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(firstInFirstOut14.getIterationNumber());

		testName.add("Program4 -> RD with LIFO");
		startTime.add(System.nanoTime());
		SimpleWorklist lastInFirstOut14 = new SimpleWorklist(mProgramGraphTest4, 1);
		ReachingDefinitions rd42 = new ReachingDefinitions(mProgramGraphTest4, lastInFirstOut14);
		rd42.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(lastInFirstOut14.getIterationNumber());




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








		System.out.println("Running tests for Reverse Postorder (meeting9)");

		testName.add("Program1 -> LV with RP");
		startTime.add(System.nanoTime());
		RoundRobin rr1 = new RoundRobin(mProgramGraphTest1);
		LiveVariables lv51 = new LiveVariables(mProgramGraphTest1, rr1);
		lv51.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(rr1.getIterationNumber());


		testName.add("Program2 -> LV with RP");
		startTime.add(System.nanoTime());
		RoundRobin rr2 = new RoundRobin(mProgramGraphTest2);
		LiveVariables lv52 = new LiveVariables(mProgramGraphTest2, rr2);
		lv52.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(rr2.getIterationNumber());

		testName.add("Program3 -> LV with RP");
		startTime.add(System.nanoTime());
		RoundRobin rr3 = new RoundRobin(mProgramGraphTest3);
		LiveVariables lv53 = new LiveVariables(mProgramGraphTest3, rr3);
		lv53.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(rr3.getIterationNumber());


		testName.add("Program4 -> LV with RP");
		startTime.add(System.nanoTime());
		RoundRobin rr4 = new RoundRobin(mProgramGraphTest4);
		LiveVariables lv54 = new LiveVariables(mProgramGraphTest4, rr4);
		lv54.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(rr4.getIterationNumber());


		testName.add("Program1 -> FV with RP");
		startTime.add(System.nanoTime());
		RoundRobin rr5 = new RoundRobin(mProgramGraphTest1);
		FaintVariables fv51 = new FaintVariables(mProgramGraphTest1, rr5);
		fv51.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(rr5.getIterationNumber());


		testName.add("Program2 -> FV with RP");
		startTime.add(System.nanoTime());
		RoundRobin rr6 = new RoundRobin(mProgramGraphTest2);
		FaintVariables fv52 = new FaintVariables(mProgramGraphTest2, rr6);
		fv52.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(rr6.getIterationNumber());

		testName.add("Program3 -> FV with RP");
		startTime.add(System.nanoTime());
		RoundRobin rr7 = new RoundRobin(mProgramGraphTest3);
		FaintVariables fv53 = new FaintVariables(mProgramGraphTest3, rr7);
		fv53.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(rr7.getIterationNumber());

		testName.add("Program4 -> FV with RP");
		startTime.add(System.nanoTime());
		RoundRobin rr8 = new RoundRobin(mProgramGraphTest4);
		FaintVariables fv54 = new FaintVariables(mProgramGraphTest4, rr8);
		fv54.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(rr8.getIterationNumber());



		testName.add("Program1 -> RD with RP");
		startTime.add(System.nanoTime());
		RoundRobin rr9 = new RoundRobin(mProgramGraphTest1);
		ReachingDefinitions rd51 = new ReachingDefinitions(mProgramGraphTest1, rr9);
		rd51.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(rr9.getIterationNumber());


		testName.add("Program2 -> RD with RP");
		startTime.add(System.nanoTime());
		RoundRobin rr10 = new RoundRobin(mProgramGraphTest2);
		ReachingDefinitions rd52 = new ReachingDefinitions(mProgramGraphTest2, rr10);
		rd52.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(rr10.getIterationNumber());

		testName.add("Program3 -> RD with RP");
		startTime.add(System.nanoTime());
		RoundRobin rr11= new RoundRobin(mProgramGraphTest3);
		ReachingDefinitions rd53 = new ReachingDefinitions(mProgramGraphTest3, rr11);
		rd53.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(rr11.getIterationNumber());


		testName.add("Program4 -> RD with RP");
		startTime.add(System.nanoTime());
		RoundRobin rr12 = new RoundRobin(mProgramGraphTest4);
		ReachingDefinitions rd54 = new ReachingDefinitions(mProgramGraphTest4, rr12);
		rd54.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(rr12.getIterationNumber());









		System.out.println("Running tests for Chaotic Iteration (CI)(meeting9)");

		testName.add("Program1 -> LV with CI");
		startTime.add(System.nanoTime());
		ChaoticIteration ci1 = new ChaoticIteration(mProgramGraphTest1);
		LiveVariables lv61 = new LiveVariables(mProgramGraphTest1, ci1);
		lv61.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(ci1.getIterationNumber());


		testName.add("Program2 -> LV with CI");
		startTime.add(System.nanoTime());
		ChaoticIteration ci2 = new ChaoticIteration(mProgramGraphTest2);
		LiveVariables lv62 = new LiveVariables(mProgramGraphTest2, ci2);
		lv62.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(ci2.getIterationNumber());

		testName.add("Program3 -> LV with CI");
		startTime.add(System.nanoTime());
		ChaoticIteration ci3 = new ChaoticIteration(mProgramGraphTest3);
		LiveVariables lv63 = new LiveVariables(mProgramGraphTest3, ci3);
		lv53.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(ci3.getIterationNumber());


		testName.add("Program4 -> LV with CI");
		startTime.add(System.nanoTime());
		ChaoticIteration ci4 = new ChaoticIteration(mProgramGraphTest4);
		LiveVariables lv64 = new LiveVariables(mProgramGraphTest4, ci4);
		lv64.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(ci4.getIterationNumber());


		testName.add("Program1 -> FV with CI");
		startTime.add(System.nanoTime());
		ChaoticIteration ci5 = new ChaoticIteration(mProgramGraphTest1);
		FaintVariables fv61 = new FaintVariables(mProgramGraphTest1, ci5);
		fv61.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(ci5.getIterationNumber());


		testName.add("Program2 -> FV with CI");
		startTime.add(System.nanoTime());
		ChaoticIteration ci6 = new ChaoticIteration(mProgramGraphTest2);
		FaintVariables fv62 = new FaintVariables(mProgramGraphTest2, ci6);
		fv62.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(ci6.getIterationNumber());

		testName.add("Program3 -> FV with CI");
		startTime.add(System.nanoTime());
		ChaoticIteration ci7 = new ChaoticIteration(mProgramGraphTest3);
		FaintVariables fv63 = new FaintVariables(mProgramGraphTest3, ci7);
		fv63.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(ci7.getIterationNumber());

		testName.add("Program4 -> FV with CI");
		startTime.add(System.nanoTime());
		ChaoticIteration ci8 = new ChaoticIteration(mProgramGraphTest4);
		FaintVariables fv64 = new FaintVariables(mProgramGraphTest4, ci8);
		fv64.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(ci8.getIterationNumber());



		testName.add("Program1 -> RD with CI");
		startTime.add(System.nanoTime());
		ChaoticIteration ci9 = new ChaoticIteration(mProgramGraphTest1);
		ReachingDefinitions rd61 = new ReachingDefinitions(mProgramGraphTest1, ci9);
		rd61.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(ci9.getIterationNumber());


		testName.add("Program2 -> RD with CI");
		startTime.add(System.nanoTime());
		ChaoticIteration ci10 = new ChaoticIteration(mProgramGraphTest2);
		ReachingDefinitions rd62 = new ReachingDefinitions(mProgramGraphTest2, ci10);
		rd62.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(ci10.getIterationNumber());

		testName.add("Program3 -> RD with CI");
		startTime.add(System.nanoTime());
		ChaoticIteration ci11= new ChaoticIteration(mProgramGraphTest3);
		ReachingDefinitions rd63 = new ReachingDefinitions(mProgramGraphTest3, ci11);
		rd63.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(ci11.getIterationNumber());


		testName.add("Program4 -> RD with CI");
		startTime.add(System.nanoTime());
		ChaoticIteration ci12 = new ChaoticIteration(mProgramGraphTest4);
		ReachingDefinitions rd64 = new ReachingDefinitions(mProgramGraphTest4, ci12);
		rd64.runAnalysis();
		endTime.add(System.nanoTime());
		iterationNumber.add(ci12.getIterationNumber());


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
