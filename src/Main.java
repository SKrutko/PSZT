import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.System.out;

public class Main {
    private Window window;
    private GameBoard MainBoard;

    public static void main(String[] args) {
        try {
            Main main = new Main();
            String[] namesOfFiles = {"pierwsza_plansza.txt", "druga_plansza.txt", "trzecia_plansza.txt", "czwarta_plansza.txt",
                    "piata_plansza.txt", "szosta_plansza", "siodma_plansza"};

            if(!args[0].equals(namesOfFiles[0]) &&  !args[0].equals(namesOfFiles[1]) &&  !args[0].equals(namesOfFiles[2])  &&  !args[0].equals(namesOfFiles[3]) &&
            !args[0].equals(namesOfFiles[4]) && !args[0].equals(namesOfFiles[5]) && !args[0].equals(namesOfFiles[6])) {
                System.out.println("Name of file is wrong!");
                System.exit(-1);

            }

            if(!args[1].equals("1") && !args[1].equals("2"))
            {
                    System.out.println("State of game is wrong!");
                    System.exit(-1);
            }
            main.ReadFile(args[0]);
            if(args[1].equals("1")) {
                main.MainBoard.aStar.solve();
                main.window.canDraw();
            }

            //main.MainBoard.aStar.solve();

            main.window.repaint();

            //main.MainBoard.aStar.testSolution();


        }
        catch (ArrayIndexOutOfBoundsException arrayE)
        {
            out.println("Exception 01 in main");
        }
        catch (Exception e)
        {
            out.println("Exception 02 in main");
        }


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

    private void ReadFile(String game)
    {
        try {
           // FileReader fr = new FileReader("pierwsza_plansza.txt");
            FileReader fr = new FileReader(game);
            BufferedReader br = new BufferedReader(fr);

            int n; // size of game board
            n = Integer.parseInt(br.readLine());
            MainBoard = new GameBoard(n, window);
            window.setGameBoard(MainBoard);

            String str;
            int i , j =0;
            for (i = 0; i < n; i++){
            str = br.readLine();
                int left = -1;
             String [] line = str.split(" ");
             for (String s:line
                        ) {
                 int right = Integer.parseInt(s);
                 MainBoard.VerticalBorder(i, j, left, right);
                 left = right;
                 j++;
                }
                j = 0;
            }

            int x = i+1;

            i = 0;
            j = 0;
            int [] previousLine = new int[n];
            for (int s = 0; s<n; s++){
                previousLine[s] = -1;
            }

            for(; x<n+n; x++)
            {
                str = br.readLine();

                String [] line = str.split(" ");

                for (String s:line
                        ) {
                    int currentBottomType = Integer.parseInt(s);
                    MainBoard.HorizontalBorder(i, j, previousLine[j], currentBottomType);
                    previousLine[j] = currentBottomType;

                    j++;
                }
                j = 0;
                i++;
            }

            i = 0;
            j = 0;

            int MaxNumberOfCountry = 0;
            while((str = br.readLine() )!= null)
            {
                String [] line = str.split(" ");

                for (String s:line
                        ) {
                    int index = Integer.parseInt(s);
                    MainBoard.countCountries(i,j,index);
                    j++;

                    if(MaxNumberOfCountry < index)
                        MaxNumberOfCountry = index;
                }
                j = 0;
                i++;
            }

            MainBoard.setNumberOfCountries(MaxNumberOfCountry + 1);

            for(j = 0; j < n; j++)
            {
                MainBoard.HorizontalBorder(n - 1, j, previousLine[j], -1);
            }

            br.close();

        } catch (IOException e) {
            out.println("Error while opening file");
        }
        catch (Exception e)
        {
            out.println("Exception in readfile() " + e.toString());
        }

    }
}
