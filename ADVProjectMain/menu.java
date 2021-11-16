package ADVProjectMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

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
                System.out.println(dif);
        }    
        });

    b2.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
                int dif = 14;
                System.out.println(dif);
        }    
        });

    b3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int dif = 20;
                System.out.println(dif);
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

    int rows = dif;
    int columns = (rows + 2);
    Random rand = new Random();
        
        //Randomly generate mines in set size
    
        int m = 0;
        int [][] mine = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double r = rand.nextDouble();
                if (r < rows/40) m = 1;
                else m = 0;
            
                mine[i][j] = m; 
            }
        }
}
    public static void main(String[] args) {
        new menu();
        
    }    
}
