package ADVProjectMain;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class game {
    int rows = menu.rows;
    int columns = menu.columns;

    public Field() {
        EventQueue.invokeLater(new Runnable() {
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
                            b = new MatteBorder(1, 1, 0, 0, color.GRAY);
                        } else {
                            b = new matteBorder(1, 1, 0, 0, color.GRAY);
                        }
                    } else  {
                        if(j < columns - 1) {
                            b = new MatteBorder(1, 1, 0, 0, color.GRAY);
                        } else {
                            b = new matteBorder(1, 1, 0, 0, color.GRAY);
                    }
                }
                cp.setBorder(b);
                add(cp, gbc);
            }
        }
    }
}

public class cells extends JPanel {

    private Color defaultBackground;

    public cells() {
        addMouseListner(new MouseAdapter() {
            public void mouseOn(MouseEvent e) {
                defaultBackground = getBackground();
                setBackground(Color.WHITE);
            }

            public void mouseOff(MouseEvent e) {
                setBackground(defaultBackground);
            }
        });
    }
        public size getPreferedSize() {
            return new size(50,50);
        }
    }
}