package dtu.experiments;

import dtu.ProgramGraph;
import dtu.expressions.Assignment;
import dtu.expressions.BooleanEvaluation;
import dtu.expressions.Expression;
import dtu.expressions.WriteOperation;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;

public class Experiments {
    public static void Expression()
    {

    }

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
    static public void worklisttest1(ProgramGraph aGraph)
    {
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

        aGraph = new ProgramGraph(nodeNumber, expressions);
    }

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
    public static ProgramGraph worklisttest2()
    {
        int nodeNumber = 12;

        Queue<Expression> expressions = new ArrayDeque<>();
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
        return new ProgramGraph(nodeNumber, expressions);
    }

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
    public static ProgramGraph modulo1()
    {
        int nodeNumber = 8;

        Queue<Expression> expressions = new ArrayDeque<>();
        expressions.add(new BooleanEvaluation("edge1","", 0, 7, new HashSet<>(Arrays.asList("x","y"))));
        expressions.add(new BooleanEvaluation("edge2","", 0, 1, new HashSet<>(Arrays.asList("x","y"))));
        expressions.add(new Assignment("edge3","", "q", 1, 2, new HashSet<>()));
        expressions.add(new Assignment("edge4","", "r", 2, 3, new HashSet<>(Arrays.asList("x"))));
        expressions.add(new BooleanEvaluation("edge5","", 3, 5, new HashSet<>(Arrays.asList("r","y"))));
        expressions.add(new BooleanEvaluation("edge6","", 3, 4, new HashSet<>(Arrays.asList("r","y"))));
        expressions.add(new Assignment("edge7","", "r", 5, 6, new HashSet<>(Arrays.asList("r","y"))));
        expressions.add(new Assignment("edge8","", "q", 6, 3, new HashSet<>(Arrays.asList("q"))));
        expressions.add(new WriteOperation("edge9","", 4, 7, "r"));
        return new ProgramGraph(nodeNumber, expressions);
    }

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
    public static ProgramGraph matrixTranspose()
    {
        int nodeNumber = 10;

        Queue<Expression> expressions = new ArrayDeque<>();
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
        return new ProgramGraph(nodeNumber, expressions);
    }
}
