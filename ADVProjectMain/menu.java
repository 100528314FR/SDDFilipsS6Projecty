import javax.swing.*;


public class  menu extends JFrame{
    static int rows = 1;
    static int columns;
    
    public menu() {
    int dif = 1;
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Object[] options = {"Easy", "Medium", "Hard"};
    dif = JOptionPane.showOptionDialog(null, "Select a difficulty", "Difficulty",
          JOptionPane.YES_NO_CANCEL_OPTION,
          JOptionPane.QUESTION_MESSAGE,
          null,
          options, 
          options[1] );
    String name = "";
    JLabel l = new JLabel("Enter your name");
    f.add(l);




    if (dif == 0) {
        rows = 8;
    }
    else if (dif == 1) {
        rows = 12;
    }
    else if (dif == 2) {
        rows = 14;
    }
    columns = (rows + 4);
    }
    public static void main(String[] args) {
        new menu();
        if (rows >= 8) {
        new grid();
        }
    }
}

    