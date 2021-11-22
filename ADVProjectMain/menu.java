
import javax.swing.*;
import java.util.Random;

public class  menu extends JFrame{
    static int rows;
    static int columns;
    
    menu() {
    int dif = 1;
    JFrame f = new JFrame();
    Object[] options = {"Easy", "Medium", "Hard"};
    dif = JOptionPane.showOptionDialog(null, "Select a difficulty", "Difficulty",
          JOptionPane.YES_NO_CANCEL_OPTION,
          JOptionPane.QUESTION_MESSAGE,
          null,
          options, 
          options[1] );


    //f.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

    if (dif == 0) {
        rows = 8;
    }
    else if (dif == 1) {
        rows = 14;
    }
    else rows = 20;

    columns = (rows + 2);
    Random rand = new Random();
        
        //Randomly generate mines in set size
    

        int [][] mine = new int[rows][columns];
        int m;
        double r = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                r = rand.nextDouble();
                if (r <= 0.3) m = 1;
                else m = 0;
                mine[i][j] = m; 
            
            }
            
        }

        for (int[] rows: mine) {
            if(rows != null) {
                for( int cols: rows) {
                    System.out.print(cols + " ");
                }
            }
            System.out.println();
        }


}
    public static void main(String[] args) {
        new menu();
        new grid();
        
    }    
}
