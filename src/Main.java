import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.ReadFile();


    }
    private void ReadFile()
    {
        try {
            FileReader fr = new FileReader("pierwsza_plansza.txt");
            BufferedReader br = new BufferedReader(fr);

            int n; // size of game board
            n = Integer.parseInt(br.readLine());
            GameBoard MainBoard = new GameBoard(n);

            String str;
            while((str = br.readLine() )!= null)
            {
                int i = 0, j = 0;
                out.println(str);
                String [] line = str.split(" ");
                for (String s:line
                        ) {
                out.println("elo");
                }
            }
            br.close();
        } catch (IOException e) {
            out.println("Error while opening file");
        }
    }
}
