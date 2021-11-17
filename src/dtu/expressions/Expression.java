package dtu.expressions;

public class Expression {

    //VARIABLE TYPES
    public static int VARIABLE_VARIABLE = 0;
    public static int VARIABLE_ARRAY = 1;
    public static int VARIABLE_RECORD = 2;

    private String mId;
    private String mName;
    private int mStartNode;
    private int mDestinationNode;

    public Expression(String aId, String aName, int aStartNode, int aDestinationNode)
    {
        mId = aId;
        mName = aName;
        mStartNode = aStartNode;
        mDestinationNode = aDestinationNode;
    }
    public Expression()
    {

    }

    public String getId () {
        return mId;
    }

    public String getName () {
        return mName;
    }

    public int getStartNode () {
        return mStartNode;
    }

    public int getDestinationNode () {
        return mDestinationNode;
    }
}
