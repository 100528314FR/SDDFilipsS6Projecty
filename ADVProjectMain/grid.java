import javax.swing.*;
import java.awt.*;

public class grid {
    
    int rows = menu.rows;
    int columns = menu.columns;
    
    JFrame f = new JFrame();
    JPanel p = new JPanel();
    JButton[][]buttons = new JButton[rows][columns];

    public grid() {
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);
        f.setSize(700, 600);
        p.setLayout(new GridLayout(rows, columns));
        
        addButtons();
        f.add(p);
    }
    public void addButtons() {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                buttons[i][j] = new JButton(i+1+", "+(j+1));
                p.add(buttons[i][j]);
            }
        }
    }
    public static void main (String[] args) {
        new grid();
    }
}
