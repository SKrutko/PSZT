import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.System.out;

public class GameBoard {

    private int size;
    private int [][] vertical;
    private int [][] horizontal;

    public GameBoard() {

        try {
            FileReader fr = new FileReader("pierwsza_plansza.txt");
            BufferedReader br = new BufferedReader(fr);

            int n; // size of game board
            n = Integer.parseInt(br.readLine());
            this.size = n;

            String str;
            while((str = br.readLine() )!= null)
            {
                int i = 0, j = 0;
                out.println(str);
                String [] line = str.split(" ");
                for (String s:line
                     ) {
                    
                }
            }
            br.close();
        } catch (IOException e) {
            out.println("Error while opening file");
        }
        out.println(size);
    }
}
