package ADVProjectMain;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;

public class game {

    public static void main(String[] args) {

        new game();  
    } 

   // game game = new game();

    int rows = menu.rows;
    int columns = menu.columns;

    public void field() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {                   
                }

                JFrame f = new JFrame();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setLayout(new BorderLayout());
                f.add(new grid());
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }
        });

    }

    public class grid extends JPanel {
        public grid() {
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            for(int i = 0; i < rows; i++) {
                for(int j = 0; i < columns; j++) {
                    gbc.gridx = j;
                    gbc.gridy = i;

                    CellPane cp = new CellPane();
                    Border b = null;
                    if(i < rows - 1) {
                        if(j < columns - 1) {
                            b = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                        } else {
                            b = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                        }
                    } else  {
                        if(j < columns - 1) {
                            b = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                        } else {
                            b = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    }
                }
                cp.setBorder(b);
                add(cp, gbc);
            }
        }
    }
}

public class CellPane extends JPanel {

    private Color defaultBackground;

    public CellPane() {
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                defaultBackground = getBackground();
                setBackground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultBackground);
            }
        });
    }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(50,50);
        }
    }
}