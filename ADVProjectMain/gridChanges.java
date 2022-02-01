import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class grid {

    int rows = menu.rows;
    int columns = menu.columns;
    String name = menu.name;
    int numMines = 0;
    int flaggedM = 0;
    Font font = menu.font;
    int[] sec = new int[] { 0, 0, 0 };
    boolean run = true;
    static int score = 0;

    JFrame fw = new JFrame();
    JFrame fl = new JFrame();
    JFrame f = new JFrame();
    JLayeredPane lPane = new JLayeredPane();
    JPanel mineP = new JPanel();
    JPanel infoP = new JPanel();
    JLabel tu = new JLabel(" ");
    JLabel tt = new JLabel(" ");
    JLabel th = new JLabel(" ");
    JButton reset = new JButton();
    JLabel nam = new JLabel(name);
    GridBagConstraints c = new GridBagConstraints();
    JButton[][] buttons = new JButton[rows][columns];
    JPanel flowPanel = new JPanel();
    JPanel timePane = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

    // 2D arrays for various properties of the tiles

    int[][] minef = new int[rows][columns];
    int[][] count = new int[rows][columns];
    boolean[][] flagged = new boolean[rows][columns];
    boolean[][] revealed = new boolean[rows][columns];
    int[][] clicked = new int[rows][columns];

    public grid() {
        run = true;
        Thread thread = new Thread(new Runnable() {

            public void run() {
                try {
                    Image img = ImageIO.read(getClass().getResource("t0.jpg"));
                    th.setIcon(new ImageIcon(img));
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                try {
                    Image img = ImageIO.read(getClass().getResource("t0.jpg"));
                    tt.setIcon(new ImageIcon(img));
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                try {
                    Image img = ImageIO.read(getClass().getResource("t0.jpg"));
                    tu.setIcon(new ImageIcon(img));
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                while (run == true) {
                    sec[2]++;
                    if (sec[2] > 9) {
                        sec[1]++;
                        sec[2] = 0;
                    }
                    if (sec[1] > 9) {
                        sec[0]++;
                        sec[1] = 0;
                    }
                    try {
                        Image img = ImageIO.read(getClass().getResource("t" + sec[0] + ".jpg"));
                        th.setIcon(new ImageIcon(img));
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    try {
                        Image img = ImageIO.read(getClass().getResource("t" + sec[1] + ".jpg"));
                        tt.setIcon(new ImageIcon(img));
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    try {
                        Image img = ImageIO.read(getClass().getResource("t" + sec[2] + ".jpg"));
                        tu.setIcon(new ImageIcon(img));
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println(Arrays.toString(sec));
                }
            }

        });
        thread.start();

        f.setLayout(new BorderLayout());
        try {
            Image img = ImageIO.read(getClass().getResource("Smile.png"));
            reset.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new grid();
                run = false;

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                f.dispose();
                fw.dispose();
                fl.dispose();
                f.setLocationRelativeTo(null);
                System.out.println(name);
            }
        });
        reset.setPreferredSize(new Dimension(32, 32));
        flowPanel.add(reset);
        timePane.add(th);
        timePane.add(tt);
        timePane.add(tu);
        nam.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 7));
        nam.setFont(font.deriveFont(Font.PLAIN, 9f));
        tu.setPreferredSize(new Dimension(28, 46));
        tt.setPreferredSize(new Dimension(28, 46));
        th.setPreferredSize(new Dimension(28, 46));
        timePane.setBorder(BorderFactory.createLoweredBevelBorder());
        infoP.setLayout(new BorderLayout());
        infoP.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(15, 3, 3, 3),
                BorderFactory.createLoweredBevelBorder()));
        infoP.add(timePane, BorderLayout.WEST);
        infoP.add(flowPanel, BorderLayout.CENTER);
        infoP.add(nam, BorderLayout.EAST);
        mineP.setLayout(new GridBagLayout());
        mineP.getInsets();
        mineP.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(15, 3, 3, 3),
                BorderFactory.createLoweredBevelBorder()));
        f.getRootPane().setBorder(BorderFactory.createRaisedBevelBorder());
        addButtons();
        f.add(mineP);
        f.add(infoP, BorderLayout.NORTH);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.pack();
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setBackground(new Color(189, 189, 189));
        mineP.setBackground(new Color(189, 189, 189));
        infoP.setBackground(new Color(189, 189, 189));
        flowPanel.setBackground(new Color(189, 189, 189));
    }

    public class gameOver {
        public gameOver() {
            run = false;
            JLabel l = new JLabel("YOU LOSE");
            fl.setLayout(null);
            fl.setBackground(new Color(189, 189, 189));
            l.setFont(font.deriveFont(Font.PLAIN, 10f));
            l.setBounds(18, 15, 100, 20);
            try {
                Image img = ImageIO.read(getClass().getResource("Sad.png"));
                reset.setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                System.out.println(ex);
            }
            fl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fl.getRootPane().setBorder(BorderFactory.createRaisedBevelBorder());
            fl.add(l);
            fl.setSize(100, 100);
            fl.setLocationRelativeTo(null);
            fl.setVisible(true);
            for (int i = 0; i < 3; i++) {
                score = (score * 10) + sec[i];
            }
            System.out.println(score);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (count[i][j] < 9 && flagged[i][j] == true) {
                        try {
                            Image img = ImageIO.read(getClass().getResource("WF.png"));
                            buttons[i][j].setIcon(new ImageIcon(img));
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    }
                }
            }
        }
    }

    public class win {
        public win() {
            run = false;
            for (int i = 0; i < 3; i++) {
                score = (score * 10) + sec[i];
            }
            new mineWrite();

            JLabel l = new JLabel("YOU WIN");
            fw.setLayout(null);
            fw.setBackground(new Color(189, 189, 189));
            fw.getRootPane().setBorder(BorderFactory.createRaisedBevelBorder());
            l.setFont(font.deriveFont(Font.PLAIN, 10f));
            l.setBounds(18, 15, 100, 20);
            try {
                Image img = ImageIO.read(getClass().getResource("Cool.png"));
                reset.setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                System.out.println(ex);
            }
            fw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fw.add(l);
            fw.setSize(100, 100);
            fw.setLocationRelativeTo(null);
            fw.setVisible(true);
            System.out.println(score);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (revealed[i][j] == false && count[i][j] < 9 && !flagged[i][j]) {
                        try {
                            Image img = ImageIO.read(getClass().getResource(count[i][j] + ".png"));
                            buttons[i][j].setIcon(new ImageIcon(img));
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    }
                }
            }
        }
    }

    // method for generating mines

    public int[][] mineGen() {
        int m;
        double r = 0;
        Random rand = new Random();
        // for every tile in the grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // generate a random real number 0 to 1.0 (r)
                r = rand.nextDouble();
                if (r <= 0.15)
                    m = 9;
                else
                    m = 0;
                // sets the current tile to either a mine or an empty
                minef[i][j] = m;

            }
        }
        return minef;
    }

    public int[][] counts() {
        // for each tile
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int num = 0;
                if (minef[i][j] == 9) {
                    num = 9;
                    numMines++;
                } else {
                    // for all neighbors of the current tile [i][j]
                    for (int a = i - 1; a < i + 2; a++) {
                        for (int b = j - 1; b < j + 2; b++) {
                            try {
                                // for every mine in the surrounding 8 tiles, add one to a counter
                                if (minef[a][b] == 9) {
                                    num = num + 1;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                continue;
                            }
                        }
                    }

                }
                // set the value of the current count[][] tile to the counters value
                count[i][j] = num;
            }
        }
        return count;
    }

    // recursive method for automatically revealing empty tiles
    public void reveal(int xPos, int yPos) {
        // exit condition
        if (count[xPos][yPos] != 0) {
            revealed[xPos][yPos] = true;
            clicked[xPos][yPos] = 1;
            return;
        } else if (count[xPos][yPos] == 0) {
            for (int a = xPos - 1; a < xPos + 2; a++) {
                for (int b = yPos - 1; b < yPos + 2; b++) {
                    try {
                        // if a tiles neighbor is 0, reveal it and recur again, starting from that tile
                        if (count[a][b] == 0 && a >= 0 && b >= 0 && a < rows && b < columns && !revealed[a][b]) {
                            revealed[a][b] = true;
                            clicked[a][b] = 1;
                            reveal(a, b);
                        } else {
                            // otherwise, just reveal and move on to the next tile
                            revealed[a][b] = true;
                            clicked[a][b] = 1;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        continue;
                    }
                }
            }
        }
    }

    public void addButtons() {
        mineGen();
        counts();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                final int I = i;
                final int J = j;
                // if its a mine

                if (count[i][j] == 9) {
                    buttons[i][j] = new JButton();
                    try {
                        Image img = ImageIO.read(getClass().getResource("X.png"));
                        buttons[i][j].setIcon(new ImageIcon(img));
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    buttons[i][j].addMouseListener(new MouseAdapter() {

                        public void mouseClicked(MouseEvent me) {

                            buttons[I][J].getModel().setArmed(true);
                            buttons[I][J].getModel().setPressed(true);

                            if (SwingUtilities.isRightMouseButton(me)) {
                                flaggedM++;
                                if (flagged[I][J] == true) {
                                    flagged[I][J] = false;
                                    flaggedM = flaggedM - 2;
                                    try {
                                        Image img = ImageIO.read(getClass().getResource("X.png"));
                                        buttons[I][J].setIcon(new ImageIcon(img));
                                    } catch (Exception ex) {
                                        System.out.println(ex);
                                    }
                                } else {
                                    flagged[I][J] = true;
                                    try {
                                        Image img = ImageIO.read(getClass().getResource("F.png"));
                                        buttons[I][J].setIcon(new ImageIcon(img));
                                    } catch (Exception ex) {
                                        System.out.println(ex);
                                    }
                                }
                                if (flaggedM == numMines) {
                                    new win();
                                }
                                System.out.println(flaggedM + " mines have been flagged out of " + numMines);
                            } else if (!flagged[I][J]) {

                                for (int i = 0; i < rows; i++) {
                                    for (int j = 0; j < columns; j++) {
                                        if (count[i][j] == 9) {

                                            revealed[i][j] = true;
                                            try {
                                                Image img = ImageIO.read(getClass().getResource("9.png"));
                                                buttons[i][j].setIcon(new ImageIcon(img));
                                            } catch (Exception ex) {
                                                System.out.println(ex);
                                            }

                                        }
                                    }
                                }
                                try {
                                    Image img = ImageIO.read(getClass().getResource("this9.png"));
                                    buttons[I][J].setIcon(new ImageIcon(img));
                                } catch (Exception ex) {
                                    System.out.println(ex);
                                }
                                new gameOver();
                            }
                        }

                    });
                    buttons[i][j].setPreferredSize(new Dimension(32, 32));
                }
                // if its not a mine
                else {
                    buttons[i][j] = new JButton();
                    try {
                        Image img = ImageIO.read(getClass().getResource("X.png"));
                        buttons[i][j].setIcon(new ImageIcon(img));
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    buttons[i][j].addMouseListener(new MouseAdapter() {

                        public void mousePressed(MouseEvent e) {

                            buttons[I][J].getModel().setArmed(true);
                            buttons[I][J].getModel().setPressed(true);

                            if (SwingUtilities.isRightMouseButton(e)) {
                                if (flagged[I][J] == true) {
                                    flagged[I][J] = false;
                                    try {
                                        Image img = ImageIO.read(getClass().getResource("X.png"));
                                        buttons[I][J].setIcon(new ImageIcon(img));
                                    } catch (Exception ex) {
                                        System.out.println(ex);
                                    }
                                } else {
                                    flagged[I][J] = true;
                                    try {
                                        Image img = ImageIO.read(getClass().getResource("F.png"));
                                        buttons[I][J].setIcon(new ImageIcon(img));
                                    } catch (Exception ex) {
                                        System.out.println(ex);
                                    }
                                }
                            } else if (count[I][J] == 0) {
                                revealed[I][J] = true;
                                reveal(I, J);

                            } else if (count[I][J] > 0 && count[I][J] < 9) {
                                if (flagged[I][J] == true) {
                                    return;
                                }

                                if (clicked[I][J] == 0) {
                                    revealed[I][J] = true;
                                } else if (clicked[I][J] > 0) {
                                    for (int a = I - 1; a < I + 2; a++) {
                                        for (int b = J - 1; b < J + 2; b++) {
                                            try {
                                                if (flagged[a][b] == false) {
                                                    if (count[a][b] == 9) {
                                                        new gameOver();
                                                        for (int i = 0; i < rows; i++) {
                                                            for (int j = 0; j < columns; j++) {
                                                                if (count[i][j] == 9) {
                                                                    revealed[i][j] = true;
                                                                }
                                                            }
                                                        }

                                                    } else if (count[a][b] > 0 && count[a][b] < 9) {
                                                        clicked[a][b] = 1;
                                                        revealed[a][b] = true;
                                                    } else if (count[a][b] == 0) {
                                                        reveal(a, b);
                                                    }
                                                } else if (flagged[a][b] == true) {
                                                    continue;
                                                }
                                            } catch (ArrayIndexOutOfBoundsException er) {
                                                continue;
                                            }
                                        }
                                    }
                                }
                                clicked[I][J]++;
                            }
                            // go through whole board, printing revealed tiles
                            for (int i = 0; i < rows; i++) {
                                for (int j = 0; j < columns; j++) {
                                    try {
                                        if (revealed[i][j] == true) {
                                            if (count[i][j] == 9) {
                                                if (clicked[i][j] == 0) {
                                                    try {
                                                        Image img = ImageIO.read(getClass().getResource("9.png"));
                                                        buttons[i][j].setIcon(new ImageIcon(img));
                                                    } catch (Exception ex) {
                                                        System.out.println(ex);
                                                    }
                                                } else if (clicked[i][j] > 0) {
                                                    try {
                                                        Image img = ImageIO.read(getClass().getResource("this9.png"));
                                                        buttons[i][j].setIcon(new ImageIcon(img));
                                                    } catch (Exception ex) {
                                                        System.out.println(ex);
                                                    }
                                                }

                                                // new gameOver();
                                            } else {
                                                try {
                                                    Image img = ImageIO
                                                            .read(getClass().getResource(count[i][j] + ".png"));
                                                    buttons[i][j].setIcon(new ImageIcon(img));
                                                } catch (Exception ex) {
                                                    System.out.println(ex);
                                                }
                                            }

                                        }
                                    } catch (ArrayIndexOutOfBoundsException err) {
                                        continue;
                                    }
                                }
                            }
                        }
                    });
                    buttons[i][j].setPreferredSize(new Dimension(32, 32));
                }

                c.gridx = j;
                c.gridy = i;
                mineP.add(buttons[i][j], c);

            }
        }
    }
}
