package ADVProjectMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class menu extends JFrame{
    
    menu() {
    
    int dif = 0;
    JFrame f = new JFrame();

    //JDialog d = new JDialog(f, "Menu", true);
    
    JButton b1 = new JButton("Easy");
    JButton b2 = new JButton("Medium");
    JButton b3 = new JButton("Hard");
    JButton b = new JButton("EXIT");

    b1.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
                int dif = 8;
        }    
        });

    b2.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
                int dif = 14;
        }    
        });

    b3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int dif = 20;
        }    
        });

    b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
        }    
        });

    //ImageIcon i = new ImageIcon("/Users/pupil19/Desktop/mug.jpg");
    JLabel l = new JLabel("Select a difficulty:");
    f.add(l);
    f.add(b1);
    f.add(b2);
    f.add(b3);
    f.add(b);

    f.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 200));
    f.setSize(500,500);
    f.setVisible(true);
    //f.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

    return actionPerformed(dif);
}
    public static void main(String[] args) {
        new menu();
    }    
}
