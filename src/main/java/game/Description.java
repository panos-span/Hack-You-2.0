package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Κλάση που περιγράφει το παιχνίδι και τη "πλοκή"
 *
 * @author Team Hack-You
 */

//TODO(Mallikoko): Φτιάξε καλύτερα την εμφάνιση του κειμένου
public class Description extends UtilityFrame {

    Menu menu;
    //private ArrayList<String> description = new ArrayList<>();
    JTextArea textArea = new JTextArea();

    public Description(Menu menu) {
        super("Description",800,800);
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
            super.load("src/main/resources/Mythos.txt",textArea);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        frame.add(textArea);
        frame.add(backgroundLabel);
    }



}