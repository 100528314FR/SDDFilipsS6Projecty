import javax.swing.*;


public class  menu extends JFrame{
    static int rows = 1;
    static int columns;
    
    public menu() {
        new diff();
        if (rows >= 8) {
            new name();
        }
    }
    public class name {
        public name() {
            String name = "";
            JFrame fn = new JFrame();
            JLabel l = new JLabel("Enter your name");
            JTextField tf = new JTextField();
            tf.setBounds();
            fn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fn.add(l);
            fn.add(tf);
            fn.setSize(150,100);
            fn.setVisible(true);
            fn.setLocationRelativeTo(null);
        }
    }
    public class diff {
        public diff() {

            int dif = 1;
            JFrame fd = new JFrame();
            fd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Object[] options = {"Easy", "Medium", "Hard"};
            dif = JOptionPane.showOptionDialog(null, "Select a difficulty", "Difficulty",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options, 
                options[1] );

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
    }


public static void main(String[] args) {
        new menu();
        if (rows >= 8) {
        new grid();
        }
    }
}



    