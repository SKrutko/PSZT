import java.util.Vector;

import static java.lang.Math.abs;

public class AStar {
    private GameBoard gameboard;
    protected Window window;

    //jeśli rowzwiązanie składa się z n kwadratów
    //to solution zawiera 2n elementów
    //każde dwa to (i,j) następnego kwadratu
    //jeśli rozwiązanie jest zamknięte to pierwszy element jest na poczatku i końcu

    protected Vector<Integer> solution = new Vector<>();
    protected Vector<Integer> userSolution;

    private boolean solved = false;

    AStar(GameBoard gameBoard, Window window) {
        this.gameboard = gameBoard;
        this.window = window;
    }

    public boolean checkUserSolution(Vector<Integer> solutionI, Vector<Integer> solutionJ) {
        try {
            userSolution = new Vector<>();
            if (solutionI.size() < 2 || solutionJ.size() < 2) {
                return false;
            }
            userSolution.add(solutionI.elementAt(0));
            userSolution.add(solutionJ.elementAt(0));
            userSolution.add(solutionI.elementAt(1));
            userSolution.add(solutionJ.elementAt(1));

            solutionI.remove(0);
            solutionJ.remove(0);
            solutionI.remove(0);
            solutionJ.remove(0);


            for (int i = 0; i < solutionI.size(); i++) {
                if (solutionI.elementAt(i) == userSolution.elementAt(0) && solutionJ.elementAt(i) == userSolution.elementAt(1)) {
                    if (i % 2 == 0) {
                        userSolution.add(0, solutionJ.elementAt(i + 1));
                        userSolution.add(0, solutionI.elementAt(i + 1));
                        solutionI.remove(i);
                        solutionI.remove(i);
                        solutionJ.remove(i);
                        solutionJ.remove(i);
                    } else {
                        userSolution.add(0, solutionJ.elementAt(i - 1));
                        userSolution.add(0, solutionI.elementAt(i - 1));
                        solutionI.remove(i - 1);
                        solutionI.remove(i - 1);
                        solutionJ.remove(i - 1);
                        solutionJ.remove(i - 1);
                    }
                    i = -1;
                } else if (solutionI.elementAt(i) == userSolution.elementAt(userSolution.size() - 2)
                        && solutionJ.elementAt(i) == userSolution.elementAt(userSolution.size() - 1)) {
                    if (i % 2 == 0) {
                        userSolution.add(solutionJ.elementAt(i + 1));
                        userSolution.add(solutionI.elementAt(i + 1));
                        solutionI.remove(i);
                        solutionI.remove(i);
                        solutionJ.remove(i);
                        solutionJ.remove(i);
                    } else {

                        userSolution.add(solutionJ.elementAt(i - 1));
                        userSolution.add(solutionI.elementAt(i - 1));
                        solutionI.remove(i - 1);
                        solutionI.remove(i - 1);
                        solutionJ.remove(i - 1);
                        solutionJ.remove(i - 1);

                    }
                    i = -1;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        if (!solutionI.isEmpty()) {
            return false;
        }


        if (abs(userSolution.elementAt(0) - userSolution.elementAt(userSolution.size() - 2))
                + abs(userSolution.elementAt(1) - userSolution.elementAt(userSolution.size() - 1)) <= 1) {
            return false;
        }
        boolean[] visitedCountries = new boolean[gameboard.getNumberOfCountries()];
        for (int i = 0; i < gameboard.getNumberOfCountries(); i++) {
            visitedCountries[i] = false;
        }

        for (int i = 0; i < userSolution.size() - 1; i += 2) {
            visitedCountries[gameboard.squares[userSolution.elementAt(i)][userSolution.elementAt(i + 1)].getCountryIndex()] = true;
        }
        int numberOfVisitedCountries = countVisitedCountries(visitedCountries);
        userSolution.add(userSolution.elementAt(0));
        userSolution.add(userSolution.elementAt(1));

        return checkSolution(numberOfVisitedCountries, userSolution);
    }

    public void solve() {
        int startI, startJ; //A* needs start and end node, for country road this are the same points
        startI = startJ = 0; //start with 0,0 and if this point belongs to solution it will be found
        long timeStart, timeStop;
        timeStart = System.currentTimeMillis();
        while (!solved && startI < gameboard.getSize()) {
            aStar(startI, startJ++);
            if (startJ >= gameboard.getSize() - 1) {
                startI++;
                startJ = 0;
            }
        }
        timeStop = System.currentTimeMillis();
        if ((timeStop - timeStart) != 0)
            System.out.println("time = " + (timeStop - timeStart));


    }

    private void aStar(int startI, int startJ)//, Vector<Integer> [] visitedPaths)
    {
        int currentI, currentJ; //(i,j) of current Square
        boolean[] visitedCountries = new boolean[gameboard.getNumberOfCountries()];
        for (int i = 0; i < gameboard.getNumberOfCountries(); i++)
            visitedCountries[i] = false;
        int currentCountryIndex;
        Vector<Cell> openSquares = new Vector<>(); // squares that have been visited but haven't been been closed

        Cell parentCell = new Cell(startI, startJ, -1, -1);
        parentCell.setCostC(0);
        parentCell.setCostCh(0);
        parentCell.setIJ(startI, startJ);
        parentCell.setStartCell(true);

        addToOpenSquares(parentCell, startI, startJ, parentCell.getI(), parentCell.getJ() + 1, openSquares);

        while (openSquares.size() > 0 && !checkSolution(countVisitedCountries(visitedCountries), this.solution)) {
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
                        current, currentCountryIndex, startI, startJ))
                    addToOpenSquares(current, startI, startJ, currentI, currentJ - 1, openSquares);

                if (currentI > 0 && checkNextCell(currentI - 1, currentJ,
                        current, currentCountryIndex, startI, startJ))
                    addToOpenSquares(current, startI, startJ, currentI - 1, currentJ, openSquares);

                if (currentJ < gameboard.getSize() - 1
                        && checkNextCell(currentI, currentJ + 1,
                        current, currentCountryIndex, startI, startJ))
                    addToOpenSquares(current, startI, startJ, currentI, currentJ + 1, openSquares);

                if (currentI < gameboard.getSize() - 1
                        && checkNextCell(currentI + 1, currentJ,
                        current, currentCountryIndex, startI, startJ))
                    addToOpenSquares(current, startI, startJ, currentI + 1, currentJ, openSquares);

                clearVisitedCountries(visitedCountries);
                solution = reversePath(current, startI, startJ, visitedCountries);
                window.repaint();
            } catch (Exception e) {
                System.out.println("Exceptioon in astar: " + e.getMessage());
            }
        }
    }

    private void clearVisitedCountries(boolean[] visitedCountries) {
        for (int i = 0; i < gameboard.getNumberOfCountries(); i++)
            visitedCountries[i] = false;
    }

    private int countVisitedCountries(boolean[] visitedCountries) {
        int count = 0;
        for (boolean n : visitedCountries
                ) {
            if (n)
                count++;
        }
        return count;
    }

    //is used to check if a square that is next to current is according to rules of the game and can be a part of solution
    private boolean checkNextCell(int i, int j,
                                  Cell current, int currentCountryIndex, int startI, int startJ) {
        try {
            boolean[] visitedCountries = new boolean[gameboard.getNumberOfCountries()];


            if (current.getParentI() == i && current.getParentJ() == j)
                return false; // is the square we came from
            if (belongsToPath(i, j, reversePath(current, startI, startJ, visitedCountries)))
                return false; //already belongs to this path
            if ((gameboard.squares[i][j].getCountryIndex() != currentCountryIndex
                    && visitedCountries[gameboard.squares[i][j].getCountryIndex()]//visitedCountries.contains(gameboard.squares[i][j].getCountryIndex())
                    && gameboard.squares[i][j].getCountryIndex() != gameboard.squares[startI][startJ].getCountryIndex()))//visitedCountries.elementAt(0)))
                return false; // belongs to country that has been visited

        } catch (Exception e) {
            System.out.println("checknextcell " + e.getMessage());
            return false;
        }

        return true;
    }

    //checks if square (i,j) belongs to given path
    private boolean belongsToPath(int i, int j, Vector<Integer> path) {
        for (int a = 2; a < path.size() - 1; a += 2) {
            if (path.elementAt(a) == i && path.elementAt(a + 1) == j)
                return true;
        }
        return false;
    }

    private Vector<Integer> reversePath(Cell cell, int startI, int startJ, boolean[] visitedCountries)//Vector <Integer> visitedCountries)
    {
        if (cell.getI() == startI && cell.getJ() == startJ && cell.isStartCell()) {
            Vector<Integer> path = new Vector<>();
            path.add(cell.getI());
            path.add(cell.getJ());
            visitedCountries[gameboard.squares[cell.getI()][cell.getJ()].getCountryIndex()] = true;
            return path;
        }
        Vector<Integer> path = reversePath(cell.getParent(), startI, startJ, visitedCountries);
        path.add(cell.getI());
        path.add(cell.getJ());
        visitedCountries[gameboard.squares[cell.getI()][cell.getJ()].getCountryIndex()] = true;
        return path;
    }

    private int pickNext(Vector<Cell> openSquares) {
        int minCost = openSquares.firstElement().getCostCh();
        int minIndex = 0;
        for (int i = 1; i < openSquares.size(); i++) {
            if (openSquares.elementAt(i).getCostCh() < minCost) {
                minCost = openSquares.elementAt(i).getCostCh();
                minIndex = i;
            }
        }
        return minIndex;
    }

    private void addToOpenSquares(Cell current, int start1, int start2, int i, int j, Vector<Cell> openSquares) {
        int cost = current.getCostC() + 1;// +
        Cell newCell = new Cell(i, j, current.getI(), current.getJ());
        newCell.setCostC(cost);
        cost += heuristic(start1, start2, i, j, gameboard.squares[i][j].getCountryIndex(), gameboard.squares[current.getI()][current.getJ()].getCountryIndex(), current);
        newCell.setCostCh(cost);
        newCell.setIJ(i, j);
        newCell.setParent(current);
        openSquares.add(newCell);
    }

    public int heuristic(int startI, int startJ, int i, int j, int countryIndex, int prevCountryIndex, Cell parentCell) {
        boolean[] visitedCountries = new boolean[gameboard.getNumberOfCountries()];
        int cost = 0;
        double solutionLength = (solution.size() / 2) / ((4 / 5) * Math.pow(gameboard.getSize(), 2));

        cost += solutionLength * (abs(i - startI) + abs(j - startJ));

        if (gameboard.squares[i][j].getUpperEdge() != BorderType.EXTERNAL ||
                belongsToPath(i - 1, j, reversePath(parentCell, startI, startJ, visitedCountries))
                || visitedCountries[gameboard.squares[i - 1][j].getCountryIndex()])
            cost += gameboard.getSize();
        if (gameboard.squares[i][j].getRightEdge() != BorderType.EXTERNAL ||
                belongsToPath(i, j + 1, reversePath(parentCell, startI, startJ, visitedCountries))
                || visitedCountries[gameboard.squares[i][j + 1].getCountryIndex()])
            cost += gameboard.getSize();
        if (gameboard.squares[i][j].getBottomEdge() != BorderType.EXTERNAL ||
                belongsToPath(i + 1, j, reversePath(parentCell, startI, startJ, visitedCountries))
                || visitedCountries[gameboard.squares[i + 1][j].getCountryIndex()])
            cost += gameboard.getSize();
        if (gameboard.squares[i][j].getLeftEdge() != BorderType.EXTERNAL ||
                belongsToPath(i, j - 1, reversePath(parentCell, startI, startJ, visitedCountries))
                || visitedCountries[gameboard.squares[i][j - 1].getCountryIndex()])
            cost += gameboard.getSize();
        cost -= gameboard.getSize();

        if (countryIndex != prevCountryIndex)
            cost += gameboard.getCountrySize(prevCountryIndex);

        return cost;
    }

    public int getSolutionSize() {
        return this.solution.size();
    }

    public int getSolutionNext(int i) {
        try {
            return this.solution.elementAt(i);
        } catch (Exception e) {
        }
        return 0;
    }

    public boolean checkSolution(int numberOfVisitedCountries, Vector<Integer> solution) {
        try {
            //check if path is closed
            if (solution.size() <= 2
                    || solution.elementAt(0) != solution.elementAt(solution.size() - 2)
                    || solution.elementAt(1) != solution.elementAt(solution.size() - 1)) {
                return false;
            }
            //check if all countries have been visited
            if (gameboard.getNumberOfCountries() != numberOfVisitedCountries)
                return false;

            //set every square belongstosolution to false - clear previous solution
            for (int i = 0; i < gameboard.getSize(); i++) {
                for (int j = 0; j < gameboard.getSize(); j++)
                    gameboard.squares[i][j].setBelongsToSolution(false);
            }
            //mark solution

            for (int i = 0; i < solution.size() - 1; i += 2) {
                gameboard.squares[(int) (solution.elementAt(i))][(int) solution.elementAt(i + 1)].setBelongsToSolution(true);
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


        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ArrayIndexOutOfBoundsException in check Solution " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Exception in check Solution " + e.getMessage());
            return false;
        }
        solved = true;
        return true;
    }

}
