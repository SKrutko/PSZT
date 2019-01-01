import javax.xml.soap.Node;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Vector;

public class AStar {
    private GameBoard gameboard;
    private Window window;

    //jeśli rowzwiązanie składa się z n kwadratów
    //to solution zawiera 2n elementów
    //każde dwa to (i,j) następnego kwadratu
    //jeśli rozwiązanie jest zamknięte to pierwszy element jest na poczatku i końcu
    private Vector<Integer> solution = new Vector<>();

    private boolean solved = false;
    AStar(GameBoard gameBoard, Window window)
    {
        this.gameboard = gameBoard;
        this.window = window;
    }
    public void solve()
    {
        int startI, startJ; //A* needs start and end node, for country road this are the same points
        startI = startJ = 0; //start with 0,0 and if this point belongs to solution it will be found
        long timeStart, timeStop;
        timeStart= System.currentTimeMillis();
        while(!solved && startI < gameboard.getSize())
        {
            aStar(startI, startJ++);
            if(startJ >= gameboard.getSize())
            {
                startI++;
                startJ = 0;
            }
        }
        timeStop = System.currentTimeMillis();
        System.out.println("time = " + (timeStop - timeStart));

    }
    //
    //
    //
    //

    private void aStar(int startI, int startJ)//, Vector<Integer> [] visitedPaths)
    {
        int currentI, currentJ; //(i,j) of current Square
        Vector<Integer> visitedCountries = new Vector<>(); //indexes of countries that have been visited
        int currentCountryIndex;
        Vector<Cell> openSquares = new Vector<>(); // squares that have been visited but haven't been been closed

        Cell parentCell = new Cell(startI, startJ, -1, -1);
        parentCell.setCostC(0);
        parentCell.setCostCh(0);
        parentCell.setIJ(startI, startJ);
        parentCell.setStartCell(true);
        openSquares.add(parentCell);

        while(openSquares.size() > 0 && !checkSolution(visitedCountries.size()))
        {
            int index = pickNext(openSquares);//picks node with the smallest cost = C + h and returns its index
            Cell current = openSquares.elementAt(index);
            openSquares.remove(index);
            currentI = current.getI();
            currentJ = current.getJ();
            currentCountryIndex = gameboard.squares[currentI][currentJ].getCountryIndex();
            try {
                //there are up to four squares that can be accessed from current
                //if any of them can belong to solution it is added to openSquares
                if (currentJ > 0 && checkNextCell(currentI, currentJ - 1,
                        current, currentCountryIndex,startI, startJ))
                    addToOpenSquares(current, startI, startJ, currentI, currentJ - 1, openSquares);

                if (currentI > 0 && checkNextCell(currentI - 1, currentJ,
                        current, currentCountryIndex, startI, startJ))
                    addToOpenSquares(current, startI, startJ, currentI - 1, currentJ, openSquares);

                if (currentJ < gameboard.getSize() - 1
                        && checkNextCell(currentI, currentJ + 1,
                        current, currentCountryIndex,  startI, startJ))
                    addToOpenSquares(current, startI, startJ, currentI, currentJ + 1, openSquares);

                if (currentI < gameboard.getSize() - 1
                        && checkNextCell( currentI + 1, currentJ,
                        current, currentCountryIndex, startI, startJ))
                    addToOpenSquares(current, startI, startJ, currentI + 1, currentJ, openSquares);

                visitedCountries.removeAllElements();
                solution = reversePath(current, startI, startJ, visitedCountries);
                //Thread.sleep(100);
                window.repaint();
            }
            catch (Exception e)
            {
                System.out.println("Exceptioon in astar: " + e.getMessage());
            }
        }
    }
    //is used to check if a square that is next to current is according to rules of the game and can be a part of solution
    private boolean checkNextCell( int i, int j,
                                  Cell current, int currentCountryIndex, int startI, int startJ)
    {
        Vector<Integer> visitedCountries = new Vector<>();

          if(current.getParentI()== i && current.getParentJ() == j)
              return false; // is the square we came from
          if(belongsToPath(i, j, reversePath(current, startI, startJ, visitedCountries)))
              return false; //already belongs to this path
          if((gameboard.squares[i][j].getCountryIndex() != currentCountryIndex
                && visitedCountries.contains(gameboard.squares[i][j].getCountryIndex())
                && gameboard.squares[i][j].getCountryIndex() != visitedCountries.elementAt(0)))
              return false; // belongs to country that has been visited

          return true;
    }

    //checks if square (i,j) belongs to given path
    private boolean belongsToPath(int i, int j, Vector<Integer>path)
    {
        for(int a = 2; a < path.size() - 1; a+=2)
        {
            if(path.elementAt(a) == i && path.elementAt(a+1) == j)
                return true;
        }
        return false;
    }

    private Vector <Integer> reversePath(Cell cell, int startI, int startJ, Vector <Integer> visitedCountries)
    {
        if(cell.getI() == startI && cell.getJ() == startJ && cell.isStartCell())
        {
            Vector <Integer> path = new Vector <>();
            path.add(cell.getI());
            path.add(cell.getJ());
            visitedCountries.add(gameboard.squares[cell.getI()][cell.getJ()].getCountryIndex());
            return path;
        }
        Vector<Integer> path = reversePath(cell.getParent(), startI, startJ, visitedCountries);
        path.add(cell.getI());
        path.add(cell.getJ());
        if(!visitedCountries.contains(gameboard.squares[cell.getI()][cell.getJ()].getCountryIndex()))
            visitedCountries.add(gameboard.squares[cell.getI()][cell.getJ()].getCountryIndex());
        return path;
    }

    private int pickNext(Vector<Cell>openSquares)
    {
        int minCost = openSquares.firstElement().getCostCh();
        int minIndex = 0;
        for(int i = 1; i < openSquares.size(); i++)
        {
            if(openSquares.elementAt(i).getCostCh() < minCost)
            {
                minCost = openSquares.elementAt(i).getCostCh();
                minIndex = i;
            }
        }
        return minIndex;
    }
    private void addToOpenSquares(Cell current, int start1, int start2, int i, int j, Vector<Cell>openSquares)
    {
        int cost = current.getCostC() + 1;// +
        Cell newCell = new Cell(i, j, current.getI(), current.getJ());
        newCell.setCostC(cost);
        cost += heuristic(start1, start2, i,j, gameboard.squares[i][j].getCountryIndex(), gameboard.squares[current.getI()][ current.getJ()].getCountryIndex());
        newCell.setCostCh(cost);
        newCell.setIJ(i, j);
        newCell.setParent(current);
        openSquares.add(newCell);
    }

    public int heuristic(int startI, int startJ, int i, int j, int countryIndex, int prevCountryIndex)
    {
        int cost = Math.abs(i - startI) + Math.abs(j - startJ);

        /*if(gameboard.squares[i][j].getUpperEdge() != BorderType.EXTERNAL)
            cost +=5;
        if(gameboard.squares[i][j].getRightEdge() != BorderType.EXTERNAL)
            cost +=5;
        if(gameboard.squares[i][j].getBottomEdge() != BorderType.EXTERNAL)
            cost +=5;
        if(gameboard.squares[i][j].getLeftEdge() != BorderType.EXTERNAL)
            cost +=5;*/

        //if(countryIndex != prevCountryIndex)
          //  cost+=5;

        return cost;
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

    public boolean checkSolution(int numberOfVisitedCountries)
    {
        try {
            //check if path is closed
            if(solution.size() <= 2
                    || solution.elementAt(0) != solution.elementAt(solution.size() - 2)
                    || solution.elementAt(1) != solution.elementAt(solution.size() - 1)) {
                return false;
            }
            //check if all countries have been visited
            if(gameboard.getNumberOfCountries() != numberOfVisitedCountries)
                return false;

            //set every square belongstosolution to false - clear previous solution
            for (int i = 0; i < gameboard.getSize(); i++) {
                for (int j = 0; j < gameboard.getSize(); j++)
                    gameboard.squares[i][j].setBelongsToSolution(false);
            }
            //mark solution
            for (int i = 0; i < solution.size()-1; i += 2) {
                gameboard.squares[solution.elementAt(i)][solution.elementAt(i + 1)].setBelongsToSolution(true);
            }
            //check verticals
            for (int i = 0; i < gameboard.getSize(); i++) {
                for (int j = 0; j < gameboard.getSize() - 1; j++) {
                    if (gameboard.squares[i][j].getRightEdge() == BorderType.EXTERNAL
                            && !gameboard.squares[i][j].getBelongsToSolution()
                            && !gameboard.squares[i][j + 1].getBelongsToSolution()) {
                        return false;
                    }

                }
            }
            //check horizontals
            for (int i = 0; i < gameboard.getSize() - 1; i++) {
                for (int j = 0; j < gameboard.getSize(); j++) {
                    if (gameboard.squares[i][j].getBottomEdge() == BorderType.EXTERNAL
                            && !gameboard.squares[i][j].getBelongsToSolution()
                            && !gameboard.squares[i + 1][j].getBelongsToSolution()) {
                        return false;
                    }

                }
            }


        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("ArrayIndexOutOfBoundsException in check Solution "+ e.getMessage());
            return false;
        }
        catch (Exception e)
        {
            System.out.println("Exception in check Solution "+ e.getMessage());
            return false;
        }
        System.out.println("Solution is correct");
        solved = true;
        return true;
    }

}
