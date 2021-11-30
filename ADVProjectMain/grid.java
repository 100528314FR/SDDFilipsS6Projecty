
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
                        if (minef[i][j] == 9) {
                            num = 9;
                        } else {
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
   
       /*  public static void emptyRecu(boolean[][] revealed) {
            if (revealed[I][J] == true){
            for (int a = I - 1; a < I + 2; a++) {
                for (int b = J - 1; b < J + 2; b++) {
                    
                    revealed[a][b] = true;
                    
                }
                }
            }
        } */
    public void addButtons() {
        mineGen();
        counts();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                final int I = i;
                final int J = j;
                //if its a mine
                if (count[i][j] == 9) {
                    buttons[i][j] = new JButton();
                    try {
                        Image img = ImageIO.read(getClass().getResource("X.png"));
                        buttons[i][j].setIcon(new ImageIcon(img));
                       } catch (Exception ex) {
                        System.out.println(ex);
                       }
                    buttons[i][j].addMouseListener( new MouseAdapter() { 
                        boolean[][] revealed = new boolean[rows][columns];   
                        
                        public void mouseClicked(MouseEvent me) {

                            buttons[I][J].getModel().setArmed(true);
                            buttons[I][J].getModel().setPressed(true);
                            revealed[I][J] = true;
                            
                            if(SwingUtilities.isRightMouseButton(me)) {
                                try {
                                    Image img = ImageIO.read(getClass().getResource("F.jpg"));
                                    buttons[I][J].setIcon(new ImageIcon(img));
                               } catch (Exception ex) {
                                    System.out.println(ex);
                               }
                            } else {
                                for (int i = 0; i < rows; i++) {
                                    for (int j = 0; j < columns; j++) {
                                if (count[i][j] == 9) {
                                try {
                                    Image img = ImageIO.read(getClass().getResource("9.png"));
                                    buttons[i][j].setIcon(new ImageIcon(img));
                               } catch (Exception ex) {
                                    System.out.println(ex);
                                }
                            }
                            }
                        }
                                //new gameOver();
                            }       
                            try {
                                Image img = ImageIO.read(getClass().getResource("T9.png"));
                                buttons[I][J].setIcon(new ImageIcon(img));
                               } catch (Exception ex) {
                                System.out.println(ex);
                               }
                               
                        }
                    });
                    buttons[i][j].setPreferredSize(new Dimension(32, 32));
                } 
                // if its not a mine
                else {
                    buttons[i][j] = new JButton();
                    try {
                        Image img = ImageIO.read(getClass().getResource("X.png"));
                        buttons[i][j].setIcon(new ImageIcon(img));
                       } catch (Exception ex) {
                        System.out.println(ex);
                       }
                    buttons[i][j].addMouseListener( new MouseAdapter() {    
                        
                        boolean[][] revealed = new boolean[rows][columns];

                        public void mousePressed(MouseEvent e) {
                            buttons[I][J].getModel().setArmed(true);
                            buttons[I][J].getModel().setPressed(true);

                            if(SwingUtilities.isRightMouseButton(e)) {
                                try {
                                    Image img = ImageIO.read(getClass().getResource("F.jpg"));
                                    buttons[I][J].setIcon(new ImageIcon(img));
                               } catch (Exception ex) {
                                    System.out.println(ex);
                               }
                            }
                            else if(count[I][J] == 0) {
                                revealed[I][J] = true;
                                
                                if (revealed[I][J] == true){
                                for (int a = I - 1; a < I + 2; a++) {
                                    for (int b = J - 1; b < J + 2; b++) {
                                        
                                        revealed[a][b] = true;
                                        
                                    }
                                    }
                                }
                            
                                for(int i = 0; i < rows; i++) {
                                    for(int j = 0; j < columns; j++) {                                                
                                        if (revealed[i][j] == true) {
                                                    try {
                                                        Image img = ImageIO.read(getClass().getResource(count[i][j]+".png"));
                                                        buttons[i][j].setIcon(new ImageIcon(img));
                                                   } catch (Exception ex) {
                                                        System.out.println(ex);
                                                   }
                                                   
                                                }
                                            }
                                        }
                                            

                                    
                            } else if(count[I][J] > 0 && count[I][J] < 9) {
                                revealed[I][J] = true;
                                try {
                                            Image img = ImageIO.read(getClass().getResource(count[I][J]+".png"));
                                            buttons[I][J].setIcon(new ImageIcon(img));
                                       } catch (Exception ex) {
                                            System.out.println(ex);
                                       }
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

