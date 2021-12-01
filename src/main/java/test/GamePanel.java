package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements Runnable, ActionListener {

    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    TileManager tileM = new TileManager(this);
    private final int FPS = 60;

    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    Player player = new Player(this, keyH);
    public CollisionCheck collisionCheck = new CollisionCheck(this);

    //Δηλώνω static το frame έτσι ώστε να μπορεί να ανοιγοκλείνει από τα Options
    protected static JFrame frame;
    static JProgressBar bar;
    JButton start = new JButton("start");
    //-------test changes end------//

    //Μεταβλητές χρήσιμες για τη λειτουργία του progressBar
    protected static boolean go = true; // Για το αν συνεχίζει το παιχνίδι ή βρίσκεται σε pause
    private int pause_count = 0; //Για το πόσες φορές έχει πατήσει το spacebar
    private boolean hasStarted = false; // Για το αν έχει αρχίσει το παιχνίδι

    //Μεταβλητές για πόσο χρόνο ο παίκτης θα κερδίζει χάνει ανάλογα με την απάντησή του στις ερωτήσεις
    protected static int for_correct;
    protected static int for_wrong;
    //Πόσο χρόνο σε seconds θα έχει ο παίκτης
    private static int time;

    //Δηλώνω static και τα Threads ώστε να κλείνουν μαζί με το frame
    private static Thread fill_bar;


    //Κατάσταση παιχνιδιού
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    private void createBar() {
        bar = new JProgressBar(0, time);
        bar.setValue(time);
        bar.setStringPainted(true);
        //Θέτω το μέγεθος της progressBar
        bar.setPreferredSize(new Dimension(600, 50));
        bar.setFont(new Font("Arial", Font.BOLD, 25));
        bar.setForeground(Color.red);
        bar.setBackground(Color.black);
        bar.setVisible(false);
    }

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.setLayout(new BorderLayout());
        createBar();
        this.add(bar, BorderLayout.NORTH);
        setButton(start, 500);
        this.add(bar, BorderLayout.SOUTH);
        this.validate(); // to invoke the layout manager
        this.repaint(); // to repaint the components
        startGameThread();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        gameState = playState;
    }

    /**
     * Game loop ώστε να τρέχει με 60 fps
     */
    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            //UPDATE && DRAW
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                this.revalidate(); // to invoke the layout manager
                this.repaint();
            }
        }
    }

    /**
     * Μέθοδος λειτουργίας progressBar
     */
    private static void fill(int flg) {
        int counter = flg;

        while (counter > 0) {
            if (!go) {
                go = true;
                return;
            }
            bar.setString(String.format("%d seconds left", counter));
            bar.setValue(counter);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "A problem has occurred", "Error", JOptionPane.ERROR_MESSAGE);
            }
            counter--;
        }
        bar.setString("Game Over");
        //SwingUtilities.invokeLater(DeathFrame::new);
        frame.dispose();
    }

    private void setButton(JButton button, int y) {
        button.setBounds(250, y, 100, 50);
        button.setFocusable(false);
        button.setHorizontalAlignment(JButton.CENTER);
        button.setFont(new Font("Calibri", Font.ITALIC, 20));
        button.addActionListener(this);
    }

    public void update() {

        if (gameState == playState)
            player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);
        player.draw(g2);

        if (gameState == pauseState) {
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
            String text = "ΠΑΥΣΗ";
            int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = screenWidth / 2 - length / 2;
            int y= screenHeight/2;
            g2.drawString(text,x,y);
        }

        g2.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            bar.setVisible(true);
            Thread fill_bar = new Thread(() -> fill(time));
            fill_bar.start();
            start.setEnabled(false);
            hasStarted = true;

        }

    }

}