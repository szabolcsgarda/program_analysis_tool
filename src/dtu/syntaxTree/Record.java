package dtu.syntaxTree;

public class Record extends Variable{
    public int fst;
    public int snd;

    public Record(String variableName, int fst, int snd)
    {
        super(variableName);
        this.fst = fst;
        this.snd = snd;
    }
}
