import javax.swing.*;
import java.awt.*;

public class Window {
    private JFrame  mainWindow;
    private View view;


    public static int frameWidth = 800;
    public static int frameHeight = 600;

    public static int gameboardSize;

    public Window()
    {
        //set basic info about main window
        mainWindow = new JFrame("Country Road");
        mainWindow.setVisible(true);
        mainWindow.setSize(frameWidth, frameHeight);
        mainWindow.setResizable(false);
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setLocationRelativeTo(null);

        mainWindow.add(view = new View());

        gameboardSize = frameWidth < frameHeight ? frameWidth : frameHeight - 100;
        view.repaint();
    }

    public void setGameBoard(GameBoard gameBoard) {
        view.setGameBoard(gameBoard);
    }

    public void repaint(){
        view.repaint();
    }


}
