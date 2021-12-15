package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

/**
 * Κλάση που περιγράφει το παιχνίδι και τη "πλοκή"
 *
 * @author Team Hack-You
 */

//TODO(Mallikoko): Φτιάξε καλύτερα την εμφάνιση του κειμένου
public class Description extends UtilityFrame {

    Menu menu;
    JTextArea textArea = new JTextArea();
    JScrollPane scrollPane;

    public Description(Menu menu) {
        super("Description", 800, 600);
        this.menu = menu;
        super.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menu.description.setEnabled(true);
                Description.super.closeFrame();
            }
        });

        textArea.setBounds(100, 0, 600, 800);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setForeground(Color.black);
        textArea.setFont(new Font("Calibri", Font.BOLD, 20));
        textArea.setEditable(false);

        try {
            super.load("src/main/resources/Mythos.txt", textArea);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        scrollPane = super.createScrollPane(textArea, 600, 500);

        frame.getContentPane().add(scrollPane);
        frame.add(backgroundLabel);
    }


}