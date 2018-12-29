import java.util.Vector;

public class AStar {
    //jeśli rowzwiązanie składa się z n kwadratów
    //to solution zawiera 2n elementów
    //każde dwa to (i,j) następnego kwadratu
    //jeśli rozwiązanie jest zamknięte to pierwszy element jest na poczatku i końcu
    private Vector<Integer> solution = new Vector<>();
    public void solve()
    {

    }

    public void aStar(int start1, int start2)
    {

    }

    public void heuristic()
    {

    }

    public int getSolutionSize()
    {
        return this.solution.size();
    }
    public int getSolutionNext(int i)
    {
        try {
            return this.solution.elementAt(i);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public void testSolution()
    {
        //0,0 0,1 1,1 1,2
        solution.add(0);
        solution.add(0);
        solution.add(0);
        solution.add(1);
        solution.add(1);
        solution.add(1);
        solution.add(1);
        solution.add(2);
    }

}
