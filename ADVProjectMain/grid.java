
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class grid  {
    
    int rows = menu.rows;
    int columns = menu.columns;
    int numRev = 0;
    int numMines = 0;

    JFrame f = new JFrame();
    JPanel p = new JPanel();
    JButton[][]buttons = new JButton[rows][columns];
    
    int[][] minef = new int[rows][columns];
    int[][] count = new int[rows][columns];

    public grid() { 

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p.setLayout(new GridLayout(rows, columns));
        addButtons();
        f.add(p);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    public class gameOver {
    public gameOver() {
        JFrame fl = new JFrame();
        JLabel l = new JLabel("YOU LOSE");
        fl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fl.add(l);
        fl.setSize(100, 100);
        fl.setLocationRelativeTo(null);
        fl.setVisible(true);
    }
}

public class win {
    public win() {
        JFrame fw = new JFrame();
        JLabel l = new JLabel("YOU WIN");
        fw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fw.add(l);
        fw.setSize(100, 100);
        fw.setLocationRelativeTo(null);
        fw.setVisible(true);
    }
}

    public int[][] mineGen() {
            
            int m;
            double r = 0;
            Random rand = new Random();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    r = rand.nextDouble();
                    if (r <= 0.2) m = 9;
                    else m = 0;
                    minef[i][j] = m;
                    numMines++;
                }
            }
            return minef;
        }
    
        public int[][] counts() {
            
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                        int num = 0;
                        if (minef[i][j] != 9) {
                            try {
                            for (int a = i - 1; a < i + 2; a++) {
                                for (int b = j - 1; b < j + 2; b++) {
                                    if (minef[a][b] == 9) {
                                        num = num+1;
                                    }   
                                }
                            } 
                        } catch (ArrayIndexOutOfBoundsException e) {
                        continue;    
                        }
                    }
                    count [i][j] = num;
                }
            }
            return count;
        }
   
    
    public void addButtons() {
        mineGen();
        counts();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                final int I = i;
                final int J = j;
                if (minef[i][j] == 9) {
                    buttons[i][j] = new JButton();
                    try {
                        Image img = ImageIO.read(getClass().getResource("X.png"));
                        buttons[i][j].setIcon(new ImageIcon(img));
                       } catch (Exception ex) {
                        System.out.println(ex);
                       }
                    buttons[i][j].addActionListener( new ActionListener() {    
                        
                        public void actionPerformed(ActionEvent e) {
                            
                            
                               for (int i = 0; i < rows; i++) {
                                for ( int j = 0; j < columns; j++) {
                                    if (minef[i][j] == 9) {
                                        try {
                                            Image img = ImageIO.read(getClass().getResource("9.png"));
                                            buttons[i][j].setIcon(new ImageIcon(img));
                                           } catch (Exception ex) {
                                            System.out.println(ex);
                                           }
                                    }
                                }
                            }
                            try {
                                Image img = ImageIO.read(getClass().getResource("T9.png"));
                                buttons[I][J].setIcon(new ImageIcon(img));
                               } catch (Exception ex) {
                                System.out.println(ex);
                               }
                               //new gameOver();
                        }
                    });
                    buttons[i][j].setPreferredSize(new Dimension(32, 32));
                } else {
                    buttons[i][j] = new JButton();
                    try {
                        Image img = ImageIO.read(getClass().getResource("X.png"));
                        buttons[i][j].setIcon(new ImageIcon(img));
                       } catch (Exception ex) {
                        System.out.println(ex);
                       }
                    buttons[i][j].addActionListener( new ActionListener() {    
                        
                        public void actionPerformed(ActionEvent e) {
                            numRev++;
                            if (numRev >= (rows*columns)-numMines) {
                            try {
                                Image img = ImageIO.read(getClass().getResource(count[I][J]+".png"));
                                buttons[I][J].setIcon(new ImageIcon(img));
                               } catch (Exception ex) {
                                System.out.println(ex);
                               }
                            } else  if (numRev <= (rows*columns)-numMines) {
                                new win();
                            }
                        }
                    });
                    buttons[i][j].setPreferredSize(new Dimension(32, 32));
                }
                
                p.add(buttons[i][j]);
                
            }
        }   
    }
}

