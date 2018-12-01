import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.System.out;

public class GameBoard {

   // private int size;
    private Square [] [] squares;

    public GameBoard(int n) {
        squares = new Square[n][n];

    }

}
