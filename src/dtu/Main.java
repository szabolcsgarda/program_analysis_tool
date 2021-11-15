package dtu;

import dtu.expressions.Expression;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
	System.out.println("Initialization in progress");

	//TODO: Place decent graph builder here
	HashMap<String, Expression> dummyExpressions = new HashMap<>();


	ProgramGraph mProgramGraph = new ProgramGraph(5, dummyExpressions);

    }
}
