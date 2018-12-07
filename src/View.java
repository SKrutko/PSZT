import javax.swing.*;
import javax.swing.text.Style;
import java.awt.*;

public class View extends JPanel {
    //colors used for painting
    private Color backgroundColor = Color.CYAN;
    private Color gameboardColor = Color.WHITE;
    private Color borderColor = Color.BLACK;

    private Point gameboardZero = new Point(0, 0);//location of the upper left corner of the gameboard

    //strokes
    private BasicStroke thickBorder = new BasicStroke(8);
    private BasicStroke thinBorder = new BasicStroke(2);

    private GameBoard gameBoard;

    int squareSize; // size of the smallest component of Gameoard

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        squareSize = Window.gameboardSize / this.gameBoard.getSize();
    }

    private void paintGameboard(Graphics g)//paints the game board background
    {
        gameboardZero.setLocation((Window.frameWidth - Window.gameboardSize )/ 2, 0);
        g.setColor(gameboardColor);
        g.fillRect(gameboardZero.x , gameboardZero.y, Window.gameboardSize, Window.gameboardSize);
    }
    private void paintBackground(Graphics g)//paints window background
    {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, Window.frameWidth, Window.frameHeight);
    }

    private void paintBorders(Graphics g)//paints game board borders and squares/countries inside of it
    {
        int i , j;
        g.setColor(borderColor);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(thickBorder);
        g2.drawRect(gameboardZero.x, gameboardZero.y, Window.gameboardSize, Window.gameboardSize);

        if(gameBoard.getSize() > 0) {
            //draw vertical edges
            for (i = 0; i < gameBoard.getSize(); i++) {
                for (j = 0; j < gameBoard.getSize() - 1; j++) {
                    try {
                        if (gameBoard.getRightEdgeType(i, j) == BorderType.EXTERNAL)
                            g2.setStroke(thickBorder);
                        else
                            g2.setStroke(thinBorder);
                    } catch (Exception e) {
                        g.setColor(Color.RED);
                    }

                    g2.drawLine(gameboardZero.x + (j + 1) * squareSize, gameboardZero.y + i * squareSize,
                            gameboardZero.x + (j + 1) * squareSize, gameboardZero.y + (i + 1) * squareSize);
                }
            }


            //draw horizontal edges
            for (i = 0; i < gameBoard.getSize() - 1; i++) {
                for (j = 0; j < gameBoard.getSize(); j++) {
                    try {
                        if (gameBoard.getBottomEdgeType(i, j) == BorderType.EXTERNAL)
                            g2.setStroke(thickBorder);
                        else
                            g2.setStroke(thinBorder);

                    } catch (Exception e) {
                        g.setColor(Color.RED);
                    }

                    g2.drawLine(gameboardZero.x + j * squareSize, gameboardZero.y + (i + 1) * squareSize,
                            gameboardZero.x + (j + 1) * squareSize, gameboardZero.y + (i + 1) * squareSize);
                }
            }


        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBackground(g);
        paintGameboard(g);

        paintBorders(g);
    }

}
