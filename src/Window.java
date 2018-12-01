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
        mainWindow = new JFrame("Country Road");
        mainWindow.setVisible(true);
        mainWindow.setSize(frameWidth, frameHeight);
        mainWindow.setResizable(false);
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setLocationRelativeTo(null);

        mainWindow.add(view = new View());
        //getContentPane().setBackground(backgroundColor);

        gameboardSize = frameWidth < frameHeight ? frameWidth : frameHeight - 100;
        view.repaint();
    }


}
