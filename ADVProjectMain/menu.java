import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.imageio.*;
import javax.swing.plaf.ColorUIResource;


public class  menu extends JFrame{
    //assigns a value for rows to allow for exiting before a difficulty is chosen
    static int rows = 1;
    static int columns;
    static String name;
    static Font font;
    static String difficulty;

    public menu() {
        //call method to get desired difficulty
        new diff();
        //only move onto name input if a difficulty has been selected (lets player close difficulty window)
        if (rows >= 8) {
            new name();
        }
    }
    public class name extends Frame {
        //method for getting a name
        public name() {
            //gui
            JFrame fn = new JFrame();
            JLabel l = new JLabel("Enter your name:");
            JLabel l1 = new JLabel("Enter a valid name");
            JButton b = new JButton();
            JTextArea tf = new JTextArea();
            //sets a custom font
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("font.ttf"));
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(font);
            } catch (IOException|FontFormatException e) {
                e.printStackTrace();
            }
            
            fn.setLayout(null);
            fn.getContentPane().setBackground(new Color(189,189,189));
            fn.getRootPane().setBorder(BorderFactory.createRaisedBevelBorder());
            l.setBounds(13, 10, 200, 40);
            l.setFont(font.deriveFont(Font.PLAIN, 10f));
            l1.setBounds(20, 65, 200, 20);
            l1.setFont(font.deriveFont(Font.PLAIN, 8f));
            tf.setBounds(20, 45, 150, 20);
            tf.setBorder(BorderFactory.createLoweredBevelBorder());
            b.setBounds(125, 90, 50, 25);
            try {
                Image img = ImageIO.read(getClass().getResource("tick.png"));
                b.setIcon(new ImageIcon(img));
               } catch (Exception ex) {
                System.out.println(ex);
               }
            b.addActionListener(new ActionListener(){  
                //when the confirm button is clicked
                public void actionPerformed(ActionEvent e){  
                            //a string is set to the text input in order to validate string
                            String test = tf.getText(); 
                            if (test.length() < 1 || test.matches(".*[!£$%^&*()_+-=//[//]{}'#@~,.<>;:/?`¬].*")) {
                                //if the test string is empty (length < 1) or has any special characters, tell the player to input a valid name
                                fn.add(l1);
                                fn.pack();
                                fn.setSize(200,165);
                            } else {
                                //if validation is passed, set the name to the test, which is what was input
                                name = test;
                                //all pre-game infor has been collected at this point, so closes current name window and calls main game method
                                new grid();
                                fn.dispose();
                            }
                        }  
                    });  
                    //more gui
            fn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fn.setResizable(false);
            fn.add(tf);
            fn.add(l);
            fn.add(b);
            fn.setSize(200,165);
            fn.setVisible(true);
            fn.setLocationRelativeTo(null);
        }
    }
    public class diff {
        //method for determining difficulty (size of field)
        public diff() {
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("font.ttf"));
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(font);
            } catch (IOException|FontFormatException e) {
                e.printStackTrace();
            }
            //an int (0-2) determined by option panel
            int dif;
            //gui
            ImageIcon img = new ImageIcon("9.png");
            JFrame fd = new JFrame();
            UIManager.put("OptionPane.background",new ColorUIResource(189,189,189));
            UIManager.put("OptionPane.border", BorderFactory.createRaisedBevelBorder());
            UIManager.put("OptionPane.messageFont", font.deriveFont(Font.PLAIN, 10f));
            UIManager.put("OptionPane.buttonFont", font.deriveFont(Font.PLAIN, 8f));
            UIManager.put("Panel.background",new ColorUIResource(189,189,189));
            fd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Object[] options = {"Easy", "Medium", "Hard"};
            
            dif = JOptionPane.showOptionDialog(fd, "Select a difficulty", "Difficulty",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                img,
                options, 
                options[1] );
            fd.getRootPane().setBorder(BorderFactory.createRaisedBevelBorder());
            //easy = 0, medium = 1 & hard = 2 
            //the difficulty string is used in game record in database
            if (dif == 0) {
             rows = 8;
             difficulty = "Easy";
            }
            else if (dif == 1) {
                rows = 12;
                difficulty = "Medium";
            }
            else if (dif == 2) {
                rows = 16;
                difficulty = "Hard";
            }
            //grid is wider than it is tall
            columns = (rows + 4);
    
        }
    }


public static void main(String[] args) {
        new menu();
    }
}



    