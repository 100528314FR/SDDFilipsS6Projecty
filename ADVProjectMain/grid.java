package ADVProjectMain;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class grid {
    
    int rows = menu.rows;
    int columns = menu.columns;
    
    JFrame f = new JFrame();
    JPanel p = new JPanel();
    JButton[][]buttons = new JButton[rows][columns];
    
    public grid() {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(700, 600);
        p.setLayout(new GridLayout(rows, columns));
        
        addButtons();
        f.add(p);
        f.setVisible(true);
    }

    public class mines {
        
        int[][] mines = new int[rows][columns];

        public int[][] mineGen() {
            int m;
            double r = 0;
            Random rand = new Random();
    
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    r = rand.nextDouble();
                    if (r <= 0.2) m = 9;
                    else m = 0;
                    mines[i][j] = m;
                }
            }
            return mines;
        }
    
        public int[][] counts() {
            int[][] count = new int[3][3];
            int num = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                        if (mines[i][j] != 9) {
                            for (int a = i - 1; a < i + 1; a++) {
                                for (int b = j + 1; b < j - 1; b++) {
                                    if (mines[a][b] == 9) {
                                        num = num+1;
                                }   
                            }
                        }
                    }
                    count [i][j] = num;
                }
            }
            return count;
        }
    }
    
    

    public void addButtons() {
        new mines();
        new counts();
        /*int m;
        double r = 0;
        Random rand = new Random(); */

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                //r = rand.nextDouble();
                //if (r <= 0.2) m = 9;
                //else m = 0;
                //mines[i][j] = m; 
                
                if (mines[i][j] == 9) {
                    buttons[i][j] = new JButton(9+" ");
                } else {
                    buttons[i][j] = new JButton(count[i][j]+" ");
                }
                
                p.add(buttons[i][j]);
            }
        }
    }

    public static void main (String[] args) {
        new grid();
    }
}