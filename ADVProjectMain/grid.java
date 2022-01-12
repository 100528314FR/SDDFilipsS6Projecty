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
    GridBagConstraints c = new GridBagConstraints();
    JButton[][]buttons = new JButton[rows][columns];
    
    //2D arrays for various propertys of the tiles

    int[][] minef = new int[rows][columns];
    int[][] count = new int[rows][columns];
    boolean[][] flagged = new boolean[rows][columns];   
    boolean[][] revealed = new boolean[rows][columns];
    int[][] clicked = new int[rows][columns];
    

    public grid() { 

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p.setLayout(new GridBagLayout());
        addButtons();
        f.add(p);
        f.setSize(columns*32, rows*36);
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
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (count[i][j] > 0 && flagged[i][j] == true) {
                try {
                    Image img = ImageIO.read(getClass().getResource("WF.png"));
                    buttons[i][j].setIcon(new ImageIcon(img));
                   } catch (Exception ex) {
                    System.out.println(ex);
                   }
            }
        }
    }
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

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (revealed[i][j] == false && count[i][j] < 9 && flagged[i][j] == false) {
                    revealed[i][j] = true;
                }
            }
        }
    }
}

    //method for generating mines

    public int[][] mineGen() {
            int m;
            double r = 0;
            Random rand = new Random();
            //for every tile in the grid
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    //generate a random real number 0 to 1.0 (r)
                    r = rand.nextDouble();
                    if (r <= 0.15) m = 9;
                    else m = 0;
                    //sets the current tile to either a mine or an empty
                    minef[i][j] = m;
                    
                }
            }
            return minef;
        }
    
        public int[][] counts() {
            //for each tile
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                        int num = 0;
                        if (minef[i][j] == 9) {
                            num = 9;
                            numMines++;
                        } else {
                            //for all neighbors of the current tile [i][j]
                            for (int a = i - 1; a < i + 2; a++) {
                                for (int b = j - 1; b < j + 2; b++) {
                                    try {
                                    //for every mine in the surrounding 8 tiles, add one to a counter
                                    if (minef[a][b] == 9) {
                                        num = num+1;
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    continue; 
                                    }   
                                }
                            } 
                        
                    }
                    //set the value of the current count[][] tile to the counters value
                    count [i][j] = num;
                }
            }
            return count;
        }
   
        //recursive method for automatically revealing empty tiles
        public void reveal(int xPos, int yPos) {
            //exit condition
            if (count[xPos][yPos] != 0) {
                revealed[xPos][yPos] = true; 
                clicked[xPos][yPos] = 1;
                return;
            } else if (count[xPos][yPos] == 0) {
                for (int a = xPos - 1; a < xPos + 2; a++) {
                    for (int b = yPos - 1; b < yPos + 2;b++) {
                         try {
                             //if a tiles neighbor is 0, reveal it and recur again, starting from that tile
                        if (count[a][b] == 0 && a >= 0 && b >= 0 && a < rows && b < columns && !revealed[a][b]) {
                                revealed[a][b] = true;
                                clicked[a][b] = 1; 
                                System.out.println("I am revealed " + a + " " + b);
                                reveal(a, b);                           
                            } else {
                                //otherwise, just reveal and move on to the next tile
                                revealed[a][b] = true;
                                clicked[a][b] = 1; 
                            }
                        } catch (IndexOutOfBoundsException e) {
                            continue;
                        }
                    }
                }
            }            
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
                        
                        public void mouseClicked(MouseEvent me) {

                            buttons[I][J].getModel().setArmed(true);
                            buttons[I][J].getModel().setPressed(true);
                            
                            if(SwingUtilities.isRightMouseButton(me)) {
                                flaggedM++;
                                if (flagged[I][J] == true) {
                                    flagged[I][J] = false;
                                    flaggedM = flaggedM - 2;
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
                                if(flaggedM == numMines) {
                                    new win();
                                }
                                System.out.println(flaggedM + " mines have been flagged out of " + numMines);
                            } else if (!flagged[I][J]) {
                                
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
                            revealed[I][J] = true;
                            reveal(I, J);

                        } else if(count[I][J] > 0 && count[I][J] < 9) {
                            if (flagged[I][J] == true) {
                                return;
                            }
                            
                            if (clicked[I][J] == 0) {
                                revealed[I][J] = true;  
                            }  else  if (clicked[I][J] > 0) {
                                    for (int a = I - 1; a < I + 2; a++) {
                                        for (int b = J - 1; b < J + 2; b++) {
                                          try{   
                                            if(flagged[a][b] == false) {
                                                 if (count[a][b] == 9) {
                                                    for (int i = 0; i < rows; i++) {
                                                        for (int j = 0; j < columns; j++) {
                                                            if(count[i][j] == 9) {
                                                                revealed[i][j] = true;
                                                            }
                                                        }
                                                    }
                                                    new gameOver();
                                                } else if (count[a][b] > 0 && count[a][b] < 9) { 
                                                    clicked[a][b] = 1;
                                                    revealed[a][b] = true;
                                                } else if (count[a][b] == 0) {
                                                    reveal(a, b);
                                                }
                                            } else if (flagged[a][b] == true) {
                                                continue;
                                            }
                                        } catch (ArrayIndexOutOfBoundsException er) {
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
                
                c.gridx = j;
                c.gridy = i - 2;
                p.add(buttons[i][j], c);
                
            }
        }   
    }
}
