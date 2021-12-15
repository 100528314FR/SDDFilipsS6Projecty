import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class grid  {
    
    int rows = menu.rows;
    int columns = menu.columns;
    int numMines = 0;
    int flaggedM = 0;

    
    JFrame f = new JFrame();
    JPanel p = new JPanel();
    JButton[][]buttons = new JButton[rows][columns];
    
    int[][] minef = new int[rows][columns];
    int[][] count = new int[rows][columns];
    boolean[][] flagged = new boolean[rows][columns];   
    boolean[][] revealed = new boolean[rows][columns];
    int[][] clicked = new int[rows][columns];
    

    public grid() { 

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p.setLayout(new GridLayout(rows, columns));
        addButtons();
        f.add(p);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setResizable(false);
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
                            
                            for (int a = i - 1; a < i + 2; a++) {
                                for (int b = j - 1; b < j + 2; b++) {
                                    try {
                                    if (minef[a][b] == 9) {
                                        num = num+1;
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    continue; 
                                    }   
                                }
                            } 
                        
                    }
                    count [i][j] = num;
                }
            }
            return count;
        }
   
        public void reveal(int yPos, int xPos) {
            try {
                
                for (int a = xPos - 1; a < xPos + 2; a++) {
                    for (int b = yPos - 1; b < yPos + 2;b++) {
                        if (count[a][b] != 0) {
                            revealed[a][b] = true;
                        } else if (count[a][b] == 0) {
                            revealed[a][b] = true;
                            reveal(a, b);
                        }
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                return;
            }
    /* if (xPos <0 || xPos > rows-1 || yPos<0 || yPos > columns-1) {
        return;
    }
         if(count[yPos][xPos]!=0){
            System.out.println("revealing xPos = " + xPos + " yPos = " + yPos);
            revealed[yPos][xPos] = true;
            return;   
         } else  {
           System.out.println("revealing the current tile xPos = " + xPos + " yPos = " + yPos);
            revealed[yPos][xPos] = true;
             for (int a = (xPos - 1); a < (xPos + 2); a++) {
                    for (int b = (yPos - 1); b < (yPos + 2); b++) { 
                        if(a>=0 && a< rows && b>=0 && b < columns && a!=xPos && b!=yPos) {
                            revealed[a][b] = true;
                            reveal(a, b);
                        } else {
                            return;
                        }
                    }
                }
            } */
             
    }  
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
                            
                            
                            if(SwingUtilities.isRightMouseButton(me)) {
                                  try {
                                    Image img = ImageIO.read(getClass().getResource("F.png"));
                                    buttons[I][J].setIcon(new ImageIcon(img));
                               } catch (Exception ex) {
                                    System.out.println(ex);
                               }  
                               flagged[I][J] = true;
                               for (int i = 0; i < rows; i++) {
                                for (int j = 0; j < columns; j++) {
                                    if(count[i][j] == 9) {
                                    numMines++;
                                    } 
                                }
                            }
                            if(flaggedM == numMines) {
                                new win();
                            } else {
                                flaggedM = flaggedM + 1;
                            }

                            } else if (flagged[I][J] != true) {
                                
                                    for (int i = 0; i < rows; i++) {
                                        for (int j = 0; j < columns; j++) {
                                            if (count[i][j] == 9) {

                                                revealed[i][j] = true;
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
                                new gameOver();
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
                        
                        public void mousePressed(MouseEvent e) {
 
                            buttons[I][J].getModel().setArmed(true);
                            buttons[I][J].getModel().setPressed(true);

                            if(SwingUtilities.isRightMouseButton(e)) {
                                if (flagged[I][J] == true) {
                                    flagged[I][J] = false;
                                    try {
                                        Image img = ImageIO.read(getClass().getResource("X.png"));
                                        buttons[I][J].setIcon(new ImageIcon(img));
                                       } catch (Exception ex) {
                                        System.out.println(ex);
                                       }
                                } else { flagged[I][J] = true;
                                    try {
                                        Image img = ImageIO.read(getClass().getResource("F.png"));
                                        buttons[I][J].setIcon(new ImageIcon(img));
                                   } catch (Exception ex) {
                                        System.out.println(ex);
                                   }  
                                    }
                        } else if(count[I][J] == 0) {
                                reveal(I, J);

                        } else if(count[I][J] > 0 && count[I][J] < 9) {
                            if (flagged[I][J] == true) {
                                return;
                            }
                            
                            if (clicked[I][J] == 0) {
                                System.out.println("revealing a tile");
                                revealed[I][J] = true;  
                            }  else  if (clicked[I][J] > 0) {
                                System.out.println("revealing a tiles surroundings");
                                    for (int a = I - 1; a < I + 2; a++) {
                                        for (int b = J - 1; b < J + 2; b++) {
                                          try{   
                                            if(flagged[a][b] == false) {
                                                
                                                 if (count[a][b] == 9) {
                                                     new gameOver();
                                                    for (int i = 0; i < rows; i++) {
                                                        for (int j = 0; j < columns; j++) {
                                                            if(count[i][j] == 9) {
                                                                revealed[i][j] = true;
                                                            }
                                                        }
                                                    }
                                                } else { 
                                                    clicked[a][b] = 1;
                                                    revealed[a][b] = true;
                                                }
                                            } else if (flagged[a][b] == true) {
                                        continue;
                                    }
                                } 
                                    catch (ArrayIndexOutOfBoundsException er) {
                                                continue;
                                        }
                                }
                                } 
                                    
                                } 
                                    clicked[I][J]++;
                            }
                            //go through whole board, printing revealed tiles    
                            for(int i = 0; i < rows; i++) {
                                    for(int j = 0; j < columns; j++) {
                                        try {                                                   
                                        if (revealed[i][j] == true) {
                                            if(count[i][j] == 9) {
                                                if (clicked[i][j] == 0) {
                                                try {
                                                    Image img = ImageIO.read(getClass().getResource("9.png"));
                                                    buttons[i][j].setIcon(new ImageIcon(img));
                                               } catch (Exception ex) {
                                                    System.out.println(ex);
                                               } 
                                            } else if (clicked[i][j] > 0) {
                                                try {
                                                    Image img = ImageIO.read(getClass().getResource("T9.png"));
                                                    buttons[i][j].setIcon(new ImageIcon(img));
                                               } catch (Exception ex) {
                                                    System.out.println(ex);
                                               } 
                                            }

                                               //new gameOver();
                                            } else {
                                                    try {
                                                        Image img = ImageIO.read(getClass().getResource(count[i][j]+".png"));
                                                        buttons[i][j].setIcon(new ImageIcon(img));
                                                   } catch (Exception ex) {
                                                        System.out.println(ex);
                                                   }
                                                }
                                                   
                                                }
                                            } catch (ArrayIndexOutOfBoundsException err) {
                                                continue;
                                            }
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