package ADVProjectMain;

import javax.swing.*;

public class menu extends JFrame{

    static int rows;
    static int columns;
    static int[][] mine;
    
    public menu() {
    int dif = 1;
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Object[] options = {"Hard", "Medium", "Easy"};
    dif = JOptionPane.showOptionDialog(null, "Select a difficulty", "Difficulty",
          JOptionPane.YES_NO_CANCEL_OPTION,
          JOptionPane.QUESTION_MESSAGE,
          null,
          options, 
          options[1] );
    if (dif == 2) {
        rows = 8;
    }
    else if (dif == 1) {
        rows = 14;
    }
    else rows = 20;
    columns = (rows + 4);
    }
    public static void main(String[] args) {
        new menu();
        new grid();
    }    
}
