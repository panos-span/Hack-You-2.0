package highscoreTest;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        //for ( PlayerInfo playerInfo : HighScore.playerInfo)
        //    System.out.printf("%s : %d%n",playerInfo.getName(),playerInfo.getScore());
        new HighScore("Petros",34);
        SwingUtilities.invokeLater(HighScoreFrame::new);

    }


}
