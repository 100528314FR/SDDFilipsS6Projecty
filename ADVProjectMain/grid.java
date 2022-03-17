import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class grid {

    // various required variables
    int rows = menu.rows;
    int columns = menu.columns;
    String name = menu.name;
    String dif = menu.difficulty;
    int numMines = 0;
    int flaggedM = 0;
    Font font = menu.font;
    // an array for timer, each unit value, hundreds, tens and units, used for
    // incrementation and score assigning
    int[] sec = new int[] { 0, 0, 0 };
    boolean run = true;
    static int score = 0;
    int firstClick = 0;

    // gui components
    JFrame f = new JFrame();
    JFrame fl = new JFrame();
    JFrame fw = new JFrame();
    JLayeredPane lPane = new JLayeredPane();
    JPanel mineP = new JPanel();
    JPanel infoP = new JPanel();
    JLabel tu = new JLabel(" ");
    JLabel tt = new JLabel(" ");
    JLabel th = new JLabel(" ");
    JButton reset = new JButton();
    JLabel nam = new JLabel(name, SwingConstants.RIGHT);
    BoxLayout boxlayout = new BoxLayout(infoP, BoxLayout.X_AXIS);
    GridBagConstraints c = new GridBagConstraints();
    JButton[][] buttons = new JButton[rows][columns];
    JPanel timePane = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

    // 2D arrays for various properties of the tiles

    int[][] minef = new int[rows][columns];
    int[][] count = new int[rows][columns];
    //boolean array for flagged tiles, used in unflagging and win/lose printing
    boolean[][] flagged = new boolean[rows][columns];
    //boolean array used by methods to tell the print method what to print
    boolean[][] revealed = new boolean[rows][columns];
    //used for when you click on a mine and for revealing a revealed tiles neighbors when clicked again
    int[][] clicked = new int[rows][columns];

    public grid() {
        run = true;
        // start a new thread for timer in background
        Thread thread = new Thread(new Runnable() {

            public void run() {
                // set each unit as an image initially 0
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
                // wait one second
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                // timer only stops when game ends (win or lose)
                while (run == true) {
                    // increment each place, staring with seconds
                    sec[2]++;
                    if (sec[2] > 9) {
                        // when a unit reaches 9, increment the higher value and reset to 0
                        sec[1]++;
                        sec[2] = 0;
                    }
                    if (sec[1] > 9) {
                        sec[0]++;
                        sec[1] = 0;
                    }
                    // every second, each image is updated to the vlaues found above
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
                    // wait a second
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
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
                f.dispose();
                fw.dispose();
                fl.dispose();
                f.setLocationRelativeTo(null);
            }
        });
        reset.setMaximumSize(new Dimension(32, 32));
        reset.setPreferredSize(new Dimension(32, 32));
        timePane.add(th);
        timePane.add(tt);
        timePane.add(tu);
        nam.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 7));
        nam.setFont(font.deriveFont(Font.PLAIN, 9f));
        tu.setPreferredSize(new Dimension(28, 46));
        tt.setPreferredSize(new Dimension(28, 46));
        th.setPreferredSize(new Dimension(28, 46));
        timePane.setBorder(BorderFactory.createLoweredBevelBorder());
        timePane.setMaximumSize(new Dimension(84, 46));
        infoP.setLayout(new BorderLayout());
        infoP.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(15, 3, 3, 3),
                BorderFactory.createLoweredBevelBorder()));
        infoP.setLayout(boxlayout);
        infoP.add(timePane);
        infoP.add(Box.createHorizontalGlue());
        infoP.add(reset);
        infoP.add(nam);
        mineP.setLayout(new GridBagLayout());
        mineP.getInsets();
        mineP.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(15, 3, 3, 3),
                BorderFactory.createLoweredBevelBorder()));
        f.getRootPane().setBorder(BorderFactory.createRaisedBevelBorder());
        buttonBehaviour();
        f.add(mineP);
        f.add(infoP, BorderLayout.NORTH);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.pack();
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setBackground(new Color(189, 189, 189));
        Rectangle r = f.getBounds();
        nam.setMinimumSize(new Dimension(((int) Math.round(r.width / 2.3565)), 12));
        nam.setPreferredSize(new Dimension(((int) Math.round(r.width / 2.3565)), 12));
        mineP.setBackground(new Color(189, 189, 189));
        infoP.setBackground(new Color(189, 189, 189));
    }

    public void gameOver() {
        // read the database, mainly to get its size
        new mineRead();
        // stop the timer
        run = false;
        for (int i = 0; i < 3; i++) {
            // get the score by checking each value in the sec array and moving it along in value (times 10)
            score = (score * 10) + sec[i];
        }

        JLabel l = new JLabel("YOU LOSE");
        fl.setLayout(null);
        fl.setBackground(new Color(189, 189, 189));
        l.setFont(font.deriveFont(Font.PLAIN, 10f));
        l.setBounds(18, 15, 100, 20);
        // change the reset button to be sad
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
        // for every tile
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (minef[i][j] != 9 && flagged[i][j]) {
                    // reveal all the incorrectly flagged tiles (a non mine flag) as wrong
                    try {
                        Image img = ImageIO.read(getClass().getResource("WF.png"));
                        buttons[i][j].setIcon(new ImageIcon(img));
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                } else if (minef[i][j] == 9 && flagged[i][j]) {
                    // used to make sure the correctly flagged mines remain flagged after previous
                    // check
                    try {
                        Image img = ImageIO.read(getClass().getResource("F.png"));
                        buttons[i][j].setIcon(new ImageIcon(img));
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        }
    }

    public void win() {
        // read the database
        new mineRead();
        // creates a new result record wiht the values from this game (name, time/score and difficulty)
        results newResult = new results(name, score, dif);
        // a fodder record for sorting
        results temp = new results(null, 0, null);
        // creates a new array of records, one bigger than however many games are already recorded so that the current game can be added
        results[] resultArr = new results[mineRead.count++];
        //sets the last (latest) game to the record of this game
        try {
           resultArr[mineRead.count++] = newResult;
        } catch (IndexOutOfBoundsException e) {

        }

        boolean swap = true;
        int n = resultArr.length;
        while (swap && n >= 0) {
            for (int i = 0; i < n - 2; i++) {
                swap = false;
                if (resultArr[i].score > resultArr[i + 1].score) {
                    temp = resultArr[i];
                    resultArr[i] = resultArr[i + 1];
                    resultArr[i + 1] = temp;
                    swap = true;
                }
            }
            n--;
        }
        run = false;
        //the same score creator as game over
        for (int i = 0; i < 3; i++) {
            score = (score * 10) + sec[i];
        }
        //write the winning game to database
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
        //for all tiles
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (revealed[i][j] == false && count[i][j] < 9 && flagged[i][j] == false) {
                    //once you win, reveales all unrevealed non-mine tiles that havent been flagged 
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
                //a 15% chance that any given tiles becomes a mine
                if (r <= 0.15) {
                    m = 9;
                    //count the mines for win condition
                    numMines++;
                } else {
                    m = 0;
                }
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
                                //if its counting a tile on the edge and tries to count a non-existant neighbor, just skip it
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
        // exit condition (once there are no 0 neighbors)
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
    //method for printing tiles mainly used for recursive method, when lots of tiles are being revealed at once
    public void printTiles() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                //if any method has set the current tile to have been revealed at soem point 
                if (revealed[i][j] == true) {
                    //if its a mine
                    if (minef[i][j] == 9) {
                        //if it was revealed by a method other than the player directly clicking on it
                        if (clicked[i][j] == 0) {
                            try {
                                Image img = ImageIO.read(getClass().getResource("9.png"));
                                buttons[i][j].setIcon(new ImageIcon(img));
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                            //if the player directly clicked and revealed this bomb
                        } else if (clicked[i][j] > 0) {
                            try {
                                Image img = ImageIO.read(getClass().getResource("this9.png"));
                                buttons[i][j].setIcon(new ImageIcon(img));
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                        }
                        //if it wasnt a mine, just print the count
                    } else {
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

    // generates the grid of buttons and the behaviour, depending on their value, of each when clicked
    public void buttonBehaviour() {
        //calls the mine generation and count to be used to determine the behaviour of buttons
        mineGen();
        counts();
        //large 2d loop, going through and adding each button to a grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                //uneditable versions of array counters for use in embedded loops
                final int I = i;
                final int J = j;

                // if its a mine
                if (count[i][j] == 9) {
                    buttons[i][j] = new JButton();
                    //to begin, every tile looks the same
                    try {
                        Image img = ImageIO.read(getClass().getResource("X.png"));
                        buttons[i][j].setIcon(new ImageIcon(img));
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    //what happens when clicked
                    buttons[i][j].addMouseListener(new MouseAdapter() {

                        public void mouseClicked(MouseEvent me) {

                            buttons[I][J].getModel().setArmed(true);
                            buttons[I][J].getModel().setPressed(true);
                            //if its a right click (flag)
                            if (SwingUtilities.isRightMouseButton(me)) {
                                //a counter of flagged mines (only increments when a mine is flagged, not empty tiles) used for win condition
                                flaggedM++;
                                //if its already flagged
                                if (flagged[I][J] == true) {
                                    //unflag it
                                    flagged[I][J] = false;
                                    //reduce the amount of flagged mines
                                    flaggedM = flaggedM - 2;
                                    try {
                                        Image img = ImageIO.read(getClass().getResource("X.png"));
                                        buttons[I][J].setIcon(new ImageIcon(img));
                                    } catch (Exception ex) {
                                        System.out.println(ex);
                                    }
                                    //if its not aready flagged
                                } else {
                                    flagged[I][J] = true;
                                    try {
                                        Image img = ImageIO.read(getClass().getResource("F.png"));
                                        buttons[I][J].setIcon(new ImageIcon(img));
                                    } catch (Exception ex) {
                                        System.out.println(ex);
                                    }
                                }
                                //once every mine has been flagged, win 
                                if (flaggedM == numMines) {
                                    win();
                                }
                            } else if (!flagged[I][J]) {
                                //for use when the very first click would reveal a bomb and lose the game, with no information for the platyer, this would be unfair
                                //if this is the first click of the game and a bomb has been clicked
                                if (firstClick == 0) {
                                    //change the bomb to an empty tile
                                    minef[I][J] = 0;
                                    count[I][J] = 0;
                                    //recount numbers, for the tiles around the ex-bomb and the new empty tile itself if it had bomb neighbors
                                    counts();
                                    //print the new tiles count
                                    try {
                                        Image img = ImageIO.read(getClass().getResource(count[I][J] + ".png"));
                                        buttons[I][J].setIcon(new ImageIcon(img));
                                    } catch (Exception ex) {
                                        System.out.println(ex);
                                    }
                                    //if the tile has at least one bomb neighbor
                                    if (count[I][J] > 0) {
                                        for (int a = I - 1; a < I + 2; a++) {
                                            for (int b = J - 1; b < J + 2; b++) {
                                                //reveal all its neighbors, skipping any bombs
                                                //this gives the player a few empty tiles to start solving with, instead of just one tile
                                                try {
                                                    if (minef[a][b] == 0 && count[a][b] > 0) {
                                                        clicked[a][b] = 1;
                                                        revealed[a][b] = true;
                                                        //if the tile had no bomb neighbors (its a 0), start recursive method
                                                    } else if (count[a][b] == 0) {
                                                        reveal(a, b);

                                                    } else {
                                                        continue;
                                                    }
                                                } catch (IndexOutOfBoundsException e) {
                                                    return;
                                                }
                                            }
                                        }
                                        //calls the print method for all the newly revealed tiles
                                        printTiles();
                                    } else if (count[I][J] == 0) {
                                        //if its a 0, act normally
                                        reveal(I, J);
                                        printTiles();
                                    }
                                    //remove one mine from the counter (the one that was clicked) and increment first click, so every click after this (mine or not) acts normally
                                    numMines--;
                                    firstClick++;
                                    //after the first click
                                    //if youve clicked on a mine
                                } else if (firstClick > 0 && minef[I][J] == 9) {
                                    for (int i = 0; i < rows; i++) {
                                        for (int j = 0; j < columns; j++) {
                                            //goes through an prints all the mines
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
                                    //the mine you clicked looks different
                                    try {
                                        Image img = ImageIO.read(getClass().getResource("this9.png"));
                                        buttons[I][J].setIcon(new ImageIcon(img));
                                    } catch (Exception ex) {
                                        System.out.println(ex);
                                    }
                                    //clicking on a mine ends the game in a loss
                                    gameOver();
                                }
                            }
                        }

                    });
                    buttons[i][j].setPreferredSize(new Dimension(32, 32));
                }
                // if they havent clicked one mine
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
                            //if they right clicked (flagged)
                            if (SwingUtilities.isRightMouseButton(e)) {
                                //win();
                                //if its already flagged, unflag it
                                //the flaggedM counter is not incremented as a mine has not been flagged, win condition can only be met by correctly flagging mines
                                if (flagged[I][J] == true) {
                                    flagged[I][J] = false;
                                    if(clicked[I][J] < 1) {
                                        try {
                                            Image img = ImageIO.read(getClass().getResource("X.png"));
                                            buttons[I][J].setIcon(new ImageIcon(img));
                                        } catch (Exception ex) {
                                            System.out.println(ex);
                                        }
                                    } else {
                                    try {
                                        Image img = ImageIO.read(getClass().getResource(count[I][J] + ".png"));
                                        buttons[I][J].setIcon(new ImageIcon(img));
                                    } catch (Exception ex) {
                                        System.out.println(ex);
                                    }
                                }
                                    //if it isnt already flagged, flag it
                                } else {
                                    flagged[I][J] = true;
                                    try {
                                        Image img = ImageIO.read(getClass().getResource("F.png"));
                                        buttons[I][J].setIcon(new ImageIcon(img));
                                    } catch (Exception ex) {
                                        System.out.println(ex);
                                    }
                                }
                                //if they didnt right click
                                //if its a 0
                            } else if (count[I][J] == 0) {
                                //increment first click, so any mines clicked now will end the game
                                firstClick = 1;
                                revealed[I][J] = true;
                                //call recursive method starting from the tile that was clicked
                                reveal(I, J);
                                //once recursion has ended, go back and print all the tiles that it revealed
                                printTiles();
                                //if they havent clicked on a 0
                            } else if (count[I][J] > 0 && count[I][J] < 9) {
                                //if this is the first click
                                if (firstClick == 0) {
                                    //subsequent clicks act normally
                                    firstClick = 1;
                                    //reveal this tiles neighbors, skipping mines, this avoids situations where they only have one revealed tile, so they have no information to solve with
                                    for (int a = I - 1; a < I + 2; a++) {
                                        for (int b = J - 1; b < J + 2; b++) {
                                            try {
                                                if (minef[a][b] == 0 && count[a][b] > 0) {
                                                    clicked[a][b] = 1;
                                                    revealed[a][b] = true;
                                                    //if a neighbor is a 0, recursive method is called
                                                } else if (count[a][b] == 0) {
                                                    reveal(a, b);

                                                } else {
                                                    continue;
                                                }
                                            } catch (IndexOutOfBoundsException ex) {
                                                return;
                                            }
                                        }
                                    }
                                } else {
                                    //any click after the first
                                    //you cannot reveal flagged tiles
                                    if (flagged[I][J] == true) {
                                        return;
                                    }
                                    //if a tile is already revealed and is clicked again, all of its neighbors are revealed, allowing for faster gameplay for player
                                    //if this tile has not already been clicked (or revealed by any other method) then just reveal it
                                    if (clicked[I][J] == 0) {
                                        revealed[I][J] = true;
                                        //if this tile has been revealed already
                                    } else if (clicked[I][J] > 0) {
                                        //for each of its neighbors
                                        for (int a = I - 1; a < I + 2; a++) {
                                            for (int b = J - 1; b < J + 2; b++) {
                                                try {
                                                    //if you click again and have not flagged the surrounding mines, reveal it and end in loss
                                                    if (flagged[a][b] == false) {
                                                        if (count[a][b] == 9) {
                                                            clicked[a][b]++;
                                                            gameOver();
                                                            for (int i = 0; i < rows; i++) {
                                                                for (int j = 0; j < columns; j++) {
                                                                    if (count[i][j] == 9) {
                                                                        revealed[i][j] = true;
                                                                    }
                                                                }
                                                            }
                                                            //if the neighbor is empty and not flagged, reveal it (recur if 0)
                                                        } else if (count[a][b] > 0 && count[a][b] < 9) {
                                                            clicked[a][b] = 1;
                                                            revealed[a][b] = true;
                                                        } else if (count[a][b] == 0) {
                                                            reveal(a, b);
                                                        }
                                                        //if a neighbor is flagged, skip it, keeping it as flagged, mine or otherwise
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
                                //print all the tiles revealed by this
                                printTiles();

                            }
                        }
                    });
                    buttons[i][j].setPreferredSize(new Dimension(32, 32));
                }

                c.gridx = j;
                c.gridy = i;
                //add the button to the grid 
                mineP.add(buttons[i][j], c);

            }
        }
    }
}
