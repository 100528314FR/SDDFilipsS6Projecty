import javax.swing.*;
import java.awt.*;
import java.util.*;

public class grid {
    
    int rows = menu.rows;
    int columns = menu.columns;
    
    JFrame f = new JFrame();
    JPanel p = new JPanel();
    JButton[][]buttons = new JButton[rows][columns];
    
    int[][] minef = new int[rows][columns];
    int[][] count = new int[rows][columns];


    public grid() {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(700, 600);
        p.setLayout(new GridLayout(rows, columns));
        addButtons();
        f.add(p);
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
                
                if (minef[i][j] == 9) {
                    buttons[i][j] = new JButton(9+" ");
                } else {
                    buttons[i][j] = new JButton(count[i][j]+" ");
                }
                
                p.add(buttons[i][j]);
            }
        }   
    }
}
