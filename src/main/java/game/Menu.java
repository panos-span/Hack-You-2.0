package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Κλάση για StartMenu
 *
 * @author Team Hack-You
 */
public class Menu implements ActionListener {

    /**
     * Initialize μεταβλητών διαστάσεων
     */
    private final int X = 380;
    private final int Y = 200;
    private final int WIDTH = 200;
    private final int HEIGHT = 50;
    private int counter = 0;
    private ImageIcon icon3;

    JFrame frame = new JFrame();
    JButton start = new JButton("Start Game");
    JButton how2play = new JButton("How to Play");
    JButton credits = new JButton("Show Credits");
    JButton description = new JButton("Game Description");
    JLabel label = new JLabel();
    JLabel backgroundLabel = new JLabel();

    //Αρχικοποίηση εξαρτημένων παραθύρων
    Guide guide;
    Credits creditsFrame;
    Description descriptionFrame;
    UtilityFrame[] utilityFrames = {guide, creditsFrame, descriptionFrame};

    public Menu() {
        // Εξατομίκευση παραθύρου
        frame.setTitle("Menu"); //setTitle of frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(970, 850);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setIconImage(Main.icon.getImage());
        //Για να εμφανίζεται στο κέντρο της οθόνης του χρήστη
        frame.setLocationRelativeTo(null);

        setButton(start, Y);
        setButton(how2play, Y + 100);
        setButton(credits, Y + 200);
        setButton(description, Y + 300);

        frame.add(start);
        frame.add(how2play);
        frame.add(credits);
        frame.add(description);
        frame.add(label);
        //-------test changes------//
        backgroundLabel.setIcon(Main.background);
        backgroundLabel.setBounds(0, 0, 1000, 1000);
        frame.add(backgroundLabel);
        //-------test changes end------//
    }

    /**
     * Μέθοδος δημιουργίας Κουμπιών
     */
    private void setButton(JButton button, int y) {
        counter++;
        button.setBounds(X, y, WIDTH, HEIGHT);
        button.setFocusable(false);
        if (counter % 2 == 1) {
            icon3 = new ImageIcon("src/main/resources/buttons/wood2.png");
        } else {
            icon3 = new ImageIcon("src/main/resources/buttons/wood1.png");
        }
        button.setIcon(icon3);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setFont(new Font("Calibri", Font.BOLD, 20));
        button.setForeground(Color.black);
        //button.setHorizontalAlignment(JButton.CENTER);
        button.addActionListener(this);
        //button.setFont(new Font("Calibri",Font.ITALIC,16));
    }

    /**
     * Ενέργεια όταν κάνουμε κλικ στο κουμπί
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            new Levels();
            frame.dispose();
            //Έλεγχος για το αν υπάρχουν ανοιχτά utilityFrames πριν την έναρξη του παιχνιδιού
            for (UtilityFrame utilityFrame : utilityFrames) {
                if (utilityFrame == null)
                    continue;
                if (utilityFrame.getIsOpen())
                    utilityFrame.closeFrame();
            }
        } else if (e.getSource() == how2play) {
            how2play.setEnabled(false);
            utilityFrames[0] = new Guide(this);
        } else if (e.getSource() == credits) {
            credits.setEnabled(false);
            utilityFrames[1] = new Credits(this);
        } else {
            description.setEnabled(false);
            utilityFrames[2] = new Description(this);
        }
    }
}