import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.Style;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.System.out; //usunac po zrobieniu akcji przycisku

public class View extends JPanel {
    //colors used for painting
    private Color backgroundColor = Color.CYAN;
    private Color gameboardColor = Color.WHITE;
    private Color borderColor = Color.BLACK;
    private Color roadrColor = Color.MAGENTA;

    private Point gameboardZero = new Point(0, 0);//location of the upper left corner of the gameboard

    //strokes
    private BasicStroke thickBorder = new BasicStroke(8);
    private BasicStroke thinBorder = new BasicStroke(2);

    private GameBoard gameBoard;

    int squareSize; // size of the smallest component of Gameoard

    JButton clearButton = new JButton("clear");
    JButton checkButton = new JButton("check");
    JButton revealButton = new JButton("reveal");

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
    private void paintRoad(Graphics g)
    {
        int x1, x2, y1, y2;
        for(int i = 0; i < gameBoard.aStar.getSolutionSize() - 2;)//?
        {
            y1 = gameBoard.aStar.getSolutionNext(i);
            x1 = gameBoard.aStar.getSolutionNext(i+1);
            y2 = gameBoard.aStar.getSolutionNext(i +2);
            x2 = gameBoard.aStar.getSolutionNext(i +3);
            i+=2;
            g.setColor(roadrColor);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(thickBorder);
            g2.drawLine(gameboardZero.x + x1 * squareSize + squareSize/2,
                    gameboardZero.y + y1 * squareSize + squareSize/2,
                    gameboardZero.x + x2 * squareSize + squareSize/2,
                    gameboardZero.y + y2 * squareSize +squareSize/2);

        }
    }

    private void paintButton(JButton button, int x, int y){
        button.setBounds(x,y,100,50); // set size of button
        button.setVisible(true); // set visibility
        //button.setBackground(Color.white);
        button.setBorder(new LineBorder(Color.BLACK));
        add(button);
    };

    private void clearAction(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                out.println("clear");
            }
        });
    }; //zmodyfikowac jak bedziemy mialy heurystyke; narazie wypisuje tekst do konsoli

    private void revealAction(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                out.println("reveal");
            }
        });
    };

    private void checkAction(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                out.println("check");
            }
        });
    };

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBackground(g);
        paintGameboard(g);
        paintBorders(g);
        paintRoad(g);
        paintButton(clearButton, 10, 125);
        paintButton(checkButton, 10, 225);
        paintButton(revealButton, 10, 325);
        clearAction(clearButton);
        checkAction(checkButton);
        revealAction(revealButton);
    }

}
