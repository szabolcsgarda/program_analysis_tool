package dtu;

import dtu.analysisP.ReachingDefinitions;
import dtu.expressions.Assignment;
import dtu.expressions.BooleanEvaluation;
import dtu.expressions.Expression;
import dtu.expressions.VariableDeclaration;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class Main {
	public static void main(String[] args)
	{
		System.out.println("Initialization in progress");
		//TODO: Place decent graph builder here

		HashMap<String, Expression> dummyExpressions = new HashMap<>();
		ProgramGraph mProgramGraph = new ProgramGraph(5, dummyExpressions);

		reachingDefinitionsTest();

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
