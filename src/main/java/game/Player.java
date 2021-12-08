package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 16;

        setDefaultValues();
        getImage();
    }

    /**
     * Προετοιμασία των animation
     */
    private void getImage() {
        try {
            setMovement(up, "thiseaswalkingup");
            setMovement(down, "thiseaswalkingdown");
            setMovement(right, "thiseaswalkingright");
            setMovement(left, "thiseaswalkingleft");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Διάβασμα αρχείων για τη φόρτωση των animation
     *
     * @param images : ο πίνακας εικόνων κινήσεων
     * @param move   : καθορίζει την κατηγορία κίνησης
     * @throws IOException
     */
    private void setMovement(BufferedImage[] images, String move) throws IOException {
        for (int i = 0; i < images.length; i++)
            images[i] = ImageIO.read(getClass().getResourceAsStream(String.format("/thiseas2/%s%d.png", move, i + 1)));
    }

    /**
     * Μέθοδος καθορισμού αρχικής θέσης παίκτη
     */
    private void setDefaultValues() {
        x = 50;
        y = 500;
        speed = 2;
        direction = "up";
    }

    /**
     * Μέθοδος ανανέωσης κίνησης παίκτη
     */
    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else {
                direction = "right";
            }

            collisionOn = false;
            gp.collisionCheck.checkTile(this);
            int objIndex = gp.collisionCheck.checkObject(this, true);
            interact(objIndex);

            //If collision is false only then can player move on
            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        y -= speed;
                        break;
                    case "down":
                        //if (y < 520)
                        y += speed;
                        break;
                    case "left":
                        x -= speed;
                        break;
                    case "right":
                        x += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 5) {
                if (spriteNum < 9) {
                    spriteNum++;
                } else {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    /**
     * Μέθοδος σταθεροποίησης κίνησης παίκτη
     */
    public void stabilizePlayer() {
        keyH.upPressed = false;
        keyH.downPressed = false;
        keyH.rightPressed = false;
        keyH.leftPressed = false;
    }

    /**
     * Μέθοδος που είναι υπεύθυνη για τη διαχείριση interactions του παίκτη με αντικείμενα μέσα στο παιχνίδι
     *
     * @param index
     */
    private void interact(int index) {
        if (index != 999) {
            String objectName = gp.obj[index].name;
            if (Objects.equals(objectName, "Question")) {
                //Για να μην κολλήσει το progressBar και η ροή του παιχνιδιού
                //stabilizePlayer();
                LabyrinthFrame.stopBar();
                gp.gameState = gp.pauseState;
                KeyHandler.quizTrig = true;

                SwingUtilities.invokeLater(() -> {
                    try {
                        new Quiz(gp);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });
                gp.obj[index] = null;

            }
            //Τερματισμός παιχνιδιού σε περίπτωση νίκης
            if (Objects.equals(objectName, "Exit"))
                gp.gameState = gp.endState;

        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                image = up[spriteNum - 1];
                break;
            case "down":
                image = down[spriteNum - 1];
                break;
            case "left":
                image = left[spriteNum - 1];
                break;
            case "right":
                image = right[spriteNum - 1];
                break;
        }
        int a = screenX;
        int b = screenY;
        if (screenX > x) {
            a = x;
        }
        if (screenY > y) {
            b = y;
        }
        int rightOffsetValue = gp.screenWidth - screenX;

        if (rightOffsetValue > gp.WorldWidth - x) {
            a = gp.screenWidth - (gp.WorldWidth - x);
        }

        int bottomOffsetValue = gp.screenHeight - screenY;

        if (bottomOffsetValue > gp.WorldHeight - y) {
            b = gp.screenHeight - (gp.WorldHeight - y);
        }

        g2.drawImage(image, a, b, gp.tileSize, gp.tileSize, null);
    }
}