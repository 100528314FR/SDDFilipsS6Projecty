import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.imageio.*;


public class  menu extends JFrame{
    static int rows = 1;
    static int columns;
    static String name;
    static Font font;
    static String difficulty;

    public menu() {
        new diff();
        if (rows >= 8) {
            new name();
        }
    }
    public class name extends Frame {
        public name() {
            JFrame fn = new JFrame();
            JLabel l = new JLabel("Enter your name:");
            JLabel l1 = new JLabel("Enter a valid name");
            JButton b = new JButton();
            JTextArea tf = new JTextArea();
            
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, new File("font.ttf"));
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
                public void actionPerformed(ActionEvent e){  
                            String test = tf.getText(); 
                            if (test.length() > 0) {
                                name = test;
                                new grid();
                                fn.dispose();
                                System.out.println(name);
                            } else {
                                fn.add(l1);
                                fn.pack();
                                fn.setSize(200,165);
                            }
                        }  
                    });  
            fn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fn.setLocationRelativeTo(null);
            
            fn.add(tf);
            fn.add(l);
            fn.add(b);
            fn.setSize(200,165);
            fn.setVisible(true);
            
        }
    }
    public class diff {
        public diff() {
            //UIManager.put("OptionPane.font", new Font(font.getName(), Font.PLAIN, 24));
            int dif = 1;
            JFrame fd = new JFrame();
            fd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fd.getContentPane().setBackground(new Color(189,189,189));
            fd.getRootPane().setBorder(BorderFactory.createRaisedBevelBorder());
            Object[] options = {"Easy", "Medium", "Hard"};
            dif = JOptionPane.showOptionDialog(fd, "Select a difficulty", "Difficulty",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options, 
                options[1] );

            if (dif == 0) {
             rows = 8;
             difficulty = "Easy";
            }
            else if (dif == 1) {
                rows = 12;
                difficulty = "Medium";
            }
            else if (dif == 2) {
                rows = 14;
                difficulty = "Hard";
            }
            columns = (rows + 4);
    
        }
    }


public static void main(String[] args) {
        new menu();
    }
}



    