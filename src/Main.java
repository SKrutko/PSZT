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
            main.ReadFile();
            //main.MainBoard.aStar.testSolution();
            main.MainBoard.aStar.solve();
            main.window.repaint();
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

    private void ReadFile()
    {
        try {
            FileReader fr = new FileReader("druga_plansza.txt");
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
           //while((str = br.readLine() )!= null)
            for(; x<n+n; x++)
            {
                str = br.readLine();
                //out.println(str);
                String [] line = str.split(" ");

                for (String s:line
                        ) {
                    int currentBottomType = Integer.parseInt(s);
                    MainBoard.HorizontalBorder(i, j, previousLine[j], currentBottomType);
                    out.println( j + "  " + previousLine[j] + " " + currentBottomType);
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
                    out.println( "Number of country. Square" + "[" + i + "][" + j + "]" + "=" + MainBoard.getNumberOfCountryIndex(i,j,index));
                    j++;

                    if(MaxNumberOfCountry < index)
                        MaxNumberOfCountry = index;
                }
                j = 0;
                i++;
            }

            MainBoard.setNumberOfCountries(MaxNumberOfCountry + 1);
            //out.println( MainBoard.getNumberOfCountries()); //check if number of coutries in MainBoard is proper

            for(j = 0; j < n; j++)
            {
                MainBoard.HorizontalBorder(n - 1, j, previousLine[j], -1);
            }

            try {
                MainBoard.testView();//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!testing View
            }
            catch (Exception e) {
                out.println("ERROR WHILE TESTING VIEW");
            }


            br.close();
        //    MainBoard.HorizontalEdge(n);
        } catch (IOException e) {
            out.println("Error while opening file");
        }
        catch (Exception e)
        {
            out.println("Exception in readfile() " + e.toString());
        }

    }
}
