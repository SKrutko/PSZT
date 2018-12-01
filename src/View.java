import javax.swing.*;
import javax.swing.text.Style;
import java.awt.*;

public class View extends JPanel {
    private Color backgroundColor = Color.CYAN;
    private Color gameboardColor = Color.WHITE;
    private Color borderColor = Color.BLACK;

    private Point gameboardZero = new Point(0, 0);

    private BasicStroke thickBorder = new BasicStroke(5);
    private BasicStroke thinBorder = new BasicStroke(2);



    //private JFrame frame;



    private void paintGameboard(Graphics g)
    {
        gameboardZero.setLocation((Window.frameWidth - Window.gameboardSize )/ 2, 0);
        g.setColor(gameboardColor);
        g.fillRect(gameboardZero.x , gameboardZero.y, Window.gameboardSize, Window.gameboardSize);
    }
    private void paintBackground(Graphics g)
    {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, Window.frameWidth, Window.frameHeight);
    }

    private void paintBorders(Graphics g)
    {
        g.setColor(borderColor);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(thickBorder);
        g2.drawRect(gameboardZero.x, gameboardZero.y, Window.gameboardSize, Window.gameboardSize);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBackground(g);
        paintGameboard(g);

        paintBorders(g);
    }

}
