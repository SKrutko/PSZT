import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.System.out;

public class Main {
    private Window window;

    public static void main(String[] args) {
        Main main = new Main();
        main.ReadFile();

    }

    private Main() {
        try {
            window = new Window();
            //this.view = new View();
            //view.setVisible(true);
        }
        catch(Exception e)
        {
            out.println("exception in Main()");
        }
    }

    private void ReadFile()
    {
        try {
            FileReader fr = new FileReader("pierwsza_plansza.txt");
            BufferedReader br = new BufferedReader(fr);

            int n; // size of game board
            n = Integer.parseInt(br.readLine());
            GameBoard MainBoard = new GameBoard(n);
            window.setGameBoard(MainBoard);

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

            try {
                MainBoard.testView();//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!testing View
            }
            catch (Exception e) {
                out.println("ERROR WHILE TESTING VIEW");
            }

            br.close();
        } catch (IOException e) {
            out.println("Error while opening file");
        }
    }
}
