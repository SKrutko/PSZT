import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import static java.lang.Math.round;
import static java.lang.System.out; //usunac po zrobieniu akcji przycisku

public class View extends JPanel implements MouseListener {
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



    int x, y;
    protected boolean canDraw = true;
    boolean canDrawAutomatic = true;
    private Vector<Integer> solutionX = new Vector<>();
    private Vector<Integer> solutionY = new Vector<>();

    int popUpWindowCheck = 0; //to exit popUpWindow


    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        squareSize = Window.gameboardSize / this.gameBoard.getSize();
        addMouseListener(this);
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


    private void popUpWindow(String message)
    {
        //popUpWindowCheck = 0;
        JFrame frame = new JFrame(message);


        // show a joptionpane dialog using showMessageDialog
        JOptionPane.showMessageDialog(frame, message, "Solution", JOptionPane.INFORMATION_MESSAGE);

    }

    private void clearAction(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                canDrawAutomatic = false;
                repaint();
                solutionX.clear();
                solutionY.clear();

                gameBoard.aStar.window.repaint();
                canDraw = true;

               //

            }
        });
    };

    private void revealAction(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canDraw = false;
                canDrawAutomatic = true;

                solutionX.clear();
                solutionY.clear();

                gameBoard.initSolve();

                repaint();

            }
        });
    };

    private void checkAction(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Vector <Integer> copyX, copyY;
                copyX = new Vector<>(solutionX);
                copyY = new Vector<>(solutionY);
                if (gameBoard.aStar.checkUserSolution(copyY, copyX))
                {
                    if(popUpWindowCheck == 0 && canDraw == true) {
                        popUpWindow("Congartulations!");
                        popUpWindowCheck = 1;
                        canDraw = true;
                    }
                }
                else
                {
                    if(popUpWindowCheck == 0 && canDraw == true) {
                        popUpWindow("It's bad solution :(");
                        popUpWindowCheck = 1;
                        canDraw = true;
                    }
                }
                repaint();

            }
        }
        );
    };

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBackground(g);
        paintGameboard(g);
        paintBorders(g);
        if(canDrawAutomatic == true)
             paintRoad(g);
        paintButton(clearButton, 10, 125);
        paintButton(checkButton, 10, 225);
        paintButton(revealButton, 10, 325);
        clearAction(clearButton);
        checkAction(checkButton);
        revealAction(revealButton);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.red);
        g2.setStroke(thickBorder);

      int x1, y1, x2, y2;
        for(int i = 0; i < solutionX.size() - 1;)//?
        {
            y1 = solutionY.elementAt(i);
            x1 = solutionX.elementAt(i);//gameBoard.aStar.getSolutionNext(i+1);
            y2 = solutionY.elementAt(i+1);//gameBoard.aStar.getSolutionNext(i +2);
            x2 = solutionX.elementAt(i+1);//gameBoard.aStar.getSolutionNext(i +3);
            i+=2;
            g2.drawLine(gameboardZero.x + x1 * squareSize + squareSize/2,
                    gameboardZero.y + y1 * squareSize + squareSize/2,
                    gameboardZero.x + x2 * squareSize + squareSize/2,
                    gameboardZero.y + y2 * squareSize +squareSize/2);
        }


    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        popUpWindowCheck = 0;
        if(canDraw == true)
        {
            x = e.getX();
            y = e.getY();

            for (int p = 1; p < gameBoard.getSize(); p++) {
                if (y > (gameboardZero.y + squareSize * p) - 20 && y < (gameboardZero.y + squareSize * p) + 20) {
                    for (int j = 1; j <= gameBoard.getSize(); j++) {
                        if (x > gameboardZero.x + (squareSize * j - squareSize / 2) - 20 &&
                                x < gameboardZero.x + (squareSize * j - squareSize / 2) + 20)
                        {
                            boolean canAdd = true;
                            for (int i = 0; i < solutionX.size(); i=i+2) {
                                if(solutionX.elementAt(i) == j-1 && solutionX.elementAt(i+1) == j-1 && solutionY.elementAt(i) == p-1
                                        && solutionY.elementAt(i+1) == p)
                                {
                                    solutionX.remove(i);
                                    solutionX.remove(i);
                                    solutionY.remove(i);
                                    solutionY.remove(i);
                                    canAdd = false;
                                    repaint();
                                }

                            }
                            if(canAdd)
                            {
                                solutionX.add(j - 1);
                                solutionY.add(p - 1);
                                solutionX.add(j - 1);
                                solutionY.add(p);
                                repaint();
                            }
                        }

                    }
                }
            }

           for (int p = 1; p <= gameBoard.getSize(); p++)
           {
                if ((y > (gameboardZero.y + squareSize * p - squareSize / 2) - 20)
                        && (y < (gameboardZero.y + squareSize * p - squareSize / 2) + 20))
                {
                    for (int w = 1; w < gameBoard.getSize(); w++)
                    {
                        if ((x > (gameboardZero.x + (squareSize * w) - 20)) &&
                                 (x < (gameboardZero.x + (squareSize * w) + 20)))
                            {
                                 {
                                     boolean canAdd = true;
                                       for (int i = 0; i < solutionX.size(); i=i+2)
                                       {
                                            if(solutionX.elementAt(i) == w-1 && solutionX.elementAt(i+1) == w
                                                    && solutionY.elementAt(i) == p-1
                                                    && solutionY.elementAt(i+1) == p-1)
                                            {
                                                solutionX.remove(i);
                                                solutionX.remove(i);
                                                solutionY.remove(i);
                                                solutionY.remove(i);
                                                canAdd = false;
                                                repaint();
                                            }

                                       }
                                       if(canAdd)
                                       {
                                            solutionX.add(w - 1);
                                            solutionY.add(p - 1);
                                            solutionX.add(w);
                                            solutionY.add(p-1);
                                            repaint();
                                       }
                                 }

                            }

                    }
                }

            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
