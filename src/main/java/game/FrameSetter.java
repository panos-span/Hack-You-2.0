package game;

import javax.swing.*;
import java.awt.*;

public class FrameSetter {

    public static void setFrame(JFrame frame,String title,int width,int height){
        frame.setTitle(title); //setTitle of frame
        frame.setResizable(false);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setIconImage(Main.icon.getImage());
        frame.setLocationRelativeTo(null);
    }

    public static void scaleBackground(JLabel label,int width,int height){
        Image img = Main.background.getImage();
        Image temp = img.getScaledInstance(width-15, height, Image.SCALE_SMOOTH);
        ImageIcon back = new ImageIcon(temp);
        label.setIcon(back);
        label.setBounds(0, 0, width, height);
    }
}
