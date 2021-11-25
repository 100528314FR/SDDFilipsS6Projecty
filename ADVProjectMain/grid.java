
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class grid  {
    
    int rows = menu.rows;
    int columns = menu.columns;
    boolean[][] rev = new boolean[rows][columns];

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
                //Icon test = new ImageIcon("C:\\gaming_SpriteSheet.jpg");
                if (minef[i][j] == 9) {
                    buttons[i][j] = new JButton();
                    try {
                        Image img = ImageIO.read(getClass().getResource("X.jpg"));
                        buttons[i][j].setIcon(new ImageIcon(img));
                       } catch (Exception ex) {
                        System.out.println(ex);
                       }
                    buttons[i][j].addActionListener( new ActionListener() {    
                        
                        public void actionPerformed(ActionEvent e) {
                            try {
                                Image img = ImageIO.read(getClass().getResource("9.jpg"));
                                buttons[I][J].setIcon(new ImageIcon(img));
                               } catch (Exception ex) {
                                System.out.println(ex);
                               }
                        }
                    });

                    
                    buttons[i][j].setPreferredSize(new Dimension(50, 50));
                } else {
                    buttons[i][j] = new JButton();
                    try {
                        Image img = ImageIO.read(getClass().getResource("X.jpg"));
                        buttons[i][j].setIcon(new ImageIcon(img));
                       } catch (Exception ex) {
                        System.out.println(ex);
                       }
                    buttons[i][j].addActionListener( new ActionListener() {    
                        
                        public void actionPerformed(ActionEvent e) {
                            try {
                                Image img = ImageIO.read(getClass().getResource(count[I][J]+".jpg"));
                                buttons[I][J].setIcon(new ImageIcon(img));
                               } catch (Exception ex) {
                                System.out.println(ex);
                               }
                        }
                    });

                    
                    buttons[i][j].setPreferredSize(new Dimension(50, 50));
                }
                
                p.add(buttons[i][j]);
                
            }
        }   
    }
    /*public class input implements ActionListener {
    public input() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (minef[i][j] == 9) {
                    buttons[i][j].addActionListener(this);
                } else {
                    buttons[i][j] = new JButton(count[i][j]+" ");
                    buttons[i][j].setPreferredSize(new Dimension(50, 50));
                }
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    } */
}

