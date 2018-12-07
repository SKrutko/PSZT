import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.System.out;

public class GameBoard {

    private int size;
    private Square [] [] squares;

    public GameBoard(int n) {
        size = n;
        squares = new Square[n][n];

    }

    public int getSize() {
        return size;
    }

    public BorderType getUpperEdgeType(int i, int j)
    {
        return squares[i][j].getUpperEdge();
    }
    public BorderType getBottomEdgeType(int i, int j)
    {
        return squares[i][j].getBottomEdge();
    }
    public BorderType getLeftEdgeType(int i, int j)
    {
        return squares[i][j].getLeftEdge();
    }
    public BorderType getRightEdgeType(int i, int j)
    {
        return squares[i][j].getRightEdge();
    }

    public void testView() // created for classes view and window testing only!!
    {
        for(int i = 0; i < size; i ++)
        {
            for(int j = 0; j < size; j++)
                squares[i][j] = new Square();
        }

        squares[0][0].setRightEdge(BorderType.INTERNAL); //0
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


    }
}
