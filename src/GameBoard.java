import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.out;

public class GameBoard {

    private int size;
    private Window window;
    public Square[][] squares;
    private int numberOfCountries;
    public AStar aStar;
    private int[] countrySquaresCounter;
    private int [] [] countryNeighbours;


    public GameBoard(int n, Window window) {
        size = n;
        squares = new Square[n][n];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                squares[i][j] = new Square();
        }
        this.window = window;
        this.aStar = new AStar(this, window);
    }

    public int getSize() {
        return size;
    }

    public void setNumberOfCountries(int numberOfCountries) {

        this.numberOfCountries = numberOfCountries;
        countrySquaresCounter = new int[numberOfCountries];
        countryNeighbours = new int [numberOfCountries][numberOfCountries];
        for (int i = 0; i < numberOfCountries; i++) {
            countrySquaresCounter[i] = 0;
            for (int j = 0; j < numberOfCountries; j++) {
                countryNeighbours[i][j] = 0;
            }
        }
        countrySquaresCount();
        fillCountryNeighbours();

    }

    private void countrySquaresCount() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                countrySquaresCounter[squares[i][j].getCountryIndex()]++;
            }
        }
    }
    private void fillCountryNeighbours()
    {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize() - 1; j++) {
                if(squares[i][j].getRightEdge() == BorderType.EXTERNAL)
                {
                    countryNeighbours[squares[i][j].getCountryIndex()][squares[i][j + 1].getCountryIndex()] = 1;
                    countryNeighbours[squares[i][j + 1].getCountryIndex()][squares[i][j].getCountryIndex()] = 1;
                }
            }
        }
        for (int i = 0; i < getSize() - 1; i++) {
            for (int j = 0; j < getSize(); j++) {
                if(squares[i][j].getRightEdge() == BorderType.EXTERNAL)
                {
                    countryNeighbours[squares[i][j].getCountryIndex()][squares[i + 1][j].getCountryIndex()] = 1;
                    countryNeighbours[squares[i + 1][j].getCountryIndex()][squares[i][j].getCountryIndex()] = 1;
                }
            }
        }
    }

    public int getNumberOfCountries() {
        return numberOfCountries;
    }

    public void HorizontalBorder(int i, int j, int upperEdge, int bottomEdge) {
        if (upperEdge == -1)
            squares[i][j].setUpperEdge(BorderType.BORDER);
        else if (upperEdge == 0)
            squares[i][j].setUpperEdge(BorderType.INTERNAL);
        else
            squares[i][j].setUpperEdge(BorderType.EXTERNAL);

        if (bottomEdge == -1)
            squares[i][j].setBottomEdge(BorderType.BORDER);
        else if (bottomEdge == 0)
            squares[i][j].setBottomEdge(BorderType.INTERNAL);
        else
            squares[i][j].setBottomEdge(BorderType.EXTERNAL);

    }

    public void VerticalBorder(int i, int j, int leftEdge, int rightEdge) {
        if (leftEdge == -1)
            squares[i][j].setLeftEdge(BorderType.BORDER);
        else if (leftEdge == 0)
            squares[i][j].setLeftEdge(BorderType.INTERNAL);
        else
            squares[i][j].setLeftEdge(BorderType.EXTERNAL);

        if (rightEdge == -1)
            squares[i][j].setRightEdge(BorderType.BORDER);
        else if (rightEdge == 0)
            squares[i][j].setRightEdge(BorderType.INTERNAL);
        else
            squares[i][j].setRightEdge(BorderType.EXTERNAL);

    }

    public BorderType getUpperEdgeType(int i, int j) {
        return squares[i][j].getUpperEdge();
    }

    public BorderType getBottomEdgeType(int i, int j) {
        return squares[i][j].getBottomEdge();
    }

    public BorderType getLeftEdgeType(int i, int j) {
        return squares[i][j].getLeftEdge();
    }

    public BorderType getRightEdgeType(int i, int j) {
        return squares[i][j].getRightEdge();
    }

    public void testView() // created for classes view and window testing only!!
    {


        /*squares[0][0].setRightEdge(BorderType.INTERNAL); //0
        squares[0][1].setRightEdge(BorderType.INTERNAL); //0
        squares[0][2].setRightEdge(BorderType.EXTERNAL); //1
        squares[0][3].setRightEdge(BorderType.INTERNAL); //0
        squares[0][4].setRightEdge(BorderType.BORDER);//-1

        squares[1][0].setRightEdge(BorderType.INTERNAL);//0
        squares[1][1].setRightEdge(BorderType.INTERNAL);//0
        squares[1][2].setRightEdge(BorderType.EXTERNAL);//1
        squares[1][3].setRightEdge(BorderType.EXTERNAL);//1
        squares[1][4].setRightEdge(BorderType.BORDER);

        squares[2][0].setRightEdge(BorderType.EXTERNAL);//1
        squares[2][1].setRightEdge(BorderType.INTERNAL);//0
        squares[2][2].setRightEdge(BorderType.INTERNAL);//0
        squares[2][3].setRightEdge(BorderType.INTERNAL);//0
        squares[2][4].setRightEdge(BorderType.BORDER);

        squares[3][0].setRightEdge(BorderType.EXTERNAL);//1
        squares[3][1].setRightEdge(BorderType.EXTERNAL);//1
        squares[3][2].setRightEdge(BorderType.EXTERNAL);//1
        squares[3][3].setRightEdge(BorderType.INTERNAL);//0
        squares[3][4].setRightEdge(BorderType.BORDER);

        squares[4][0].setRightEdge(BorderType.INTERNAL);//0
        squares[4][1].setRightEdge(BorderType.EXTERNAL);//1
        squares[4][2].setRightEdge(BorderType.INTERNAL);//0
        squares[4][3].setRightEdge(BorderType.INTERNAL);//0
        squares[4][4].setRightEdge(BorderType.BORDER);

        squares[0][0].setBottomEdge(BorderType.EXTERNAL);//1
        squares[0][1].setBottomEdge(BorderType.EXTERNAL);//1
        squares[0][2].setBottomEdge(BorderType.EXTERNAL);//1
        squares[0][3].setBottomEdge(BorderType.INTERNAL);//0
        squares[0][4].setBottomEdge(BorderType.EXTERNAL);//1

        squares[1][0].setBottomEdge(BorderType.INTERNAL);//0
        squares[1][1].setBottomEdge(BorderType.EXTERNAL);//1
        squares[1][2].setBottomEdge(BorderType.EXTERNAL);//1
        squares[1][3].setBottomEdge(BorderType.EXTERNAL);//1
        squares[1][4].setBottomEdge(BorderType.INTERNAL);//0

        squares[2][0].setBottomEdge(BorderType.INTERNAL);//0
        squares[2][1].setBottomEdge(BorderType.INTERNAL);//0
        squares[2][2].setBottomEdge(BorderType.EXTERNAL);//1
        squares[2][3].setBottomEdge(BorderType.EXTERNAL);//1
        squares[2][4].setBottomEdge(BorderType.EXTERNAL);//1

        squares[3][0].setBottomEdge(BorderType.EXTERNAL);//1
        squares[3][1].setBottomEdge(BorderType.EXTERNAL);//1
        squares[3][2].setBottomEdge(BorderType.INTERNAL);//0
        squares[3][3].setBottomEdge(BorderType.EXTERNAL);//1
        squares[3][4].setBottomEdge(BorderType.EXTERNAL);//1

        squares[4][0].setBottomEdge(BorderType.BORDER);
        squares[4][1].setBottomEdge(BorderType.BORDER);
        squares[4][2].setBottomEdge(BorderType.BORDER);
        squares[4][3].setBottomEdge(BorderType.BORDER);
        squares[4][4].setBottomEdge(BorderType.BORDER);
*/

    }

    public int getNumberOfCountryIndex(int i, int j, int index) //to check in console if values are proper
    {
        return squares[i][j].getCountryIndex();
    }

    public void countCountries(int i, int j, int index) {

        squares[i][j].setCountryIndex(index);
    }

    public int getCountrySize(int countryIndex)
    {
        return countrySquaresCounter[countryIndex];
    }
    public boolean areNeighbours(int firstCountry, int secondCountry)
    {
        if(countryNeighbours[firstCountry][secondCountry] > 0)
            return true;
        return false;
    }


}
