package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//Waiting for explanation by mallikok
public class Quiz implements ActionListener {
    ArrayList<String> questions = new ArrayList<String>();
    ArrayList<String> options = new ArrayList<String>();
    ArrayList<Character> answers = new ArrayList<Character>();
    char answer;
    private final Random random = new Random();
    int index;

    //------------------------------//----------------------------------------//
    /*protected static void setQuestionsDifficulty(){
        switch (Levels.difficulty) {
            case "easy":
                // JOptionPane.showMessageDialog(null,"1","ferwu",JOptionPane.INFORMATION_MESSAGE);
                break;
            case "medium":
                //file=MediumQuestions.txt
                break;
            default:
                //file=HardQuestions.txt
                break;
        }
    }*/
    //------------------------------//----------------------------------------//

    JFrame frame = new JFrame();
    //JTextField textField = new JTextField();
    JTextArea textArea = new JTextArea();

    JButton[] buttons = new JButton[4];
    char[] symbols = {'A', 'B', 'C', 'D'};

    JLabel label = new JLabel();
    JLabel[] labels = new JLabel[4];

    public Quiz() throws FileNotFoundException {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(700, 550);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setTitle("Question"); //setTitle of frame
        frame.setIconImage(Main.icon.getImage());
        //Για να εμφανίζεται στο κέντρο της οθόνης του χρήστη
        frame.setLocationRelativeTo(null);

        /*textField.setBounds(0, 0, 700, 50);
        textField.setFont(new Font("Calibri", Font.BOLD, 25));
        textField.setBorder(BorderFactory.createBevelBorder(1));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setEditable(false);*/


        textArea.setBounds(0, 0, 700, 100);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(25, 25, 25));
        textArea.setForeground(new Color(25, 255, 0));
        textArea.setFont(new Font("Calibri", Font.BOLD, 25));
        textArea.setBorder(BorderFactory.createBevelBorder(1));
        textArea.setEditable(false);


        setLabels();
        setButtons();
        for (JLabel label : labels)
            frame.add(label);
        for (JButton button : buttons)
            frame.add(button);
        frame.add(textArea);
        //frame.add(textField);
        frame.setVisible(true);
        readQuestions();
        index = random.nextInt(questions.size());
        displayQuestion();

        Image img = Main.background.getImage();
        Image temp = img.getScaledInstance(685, 550, Image.SCALE_SMOOTH);
        ImageIcon back = new ImageIcon(temp);
        label.setIcon(back);
        label.setBounds(0, 0, 700, 550);
        //Για να εμφανίζεται στο κέντρο της οθόνης του χρήστη
        frame.add(label);
    }

    public void displayQuestion() {
        //textField.setText("Game");
        textArea.setText(questions.get(index));
        for (int i = 0; i < labels.length; i++)
            labels[i].setText(options.get(4 * index + i));

    }

    public void setButtons() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setBounds(0, (i + 1) * 100, 100, 100);
            buttons[i].setFont(new Font("Calibri", Font.BOLD, 35));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
            buttons[i].setText(String.valueOf(symbols[i]));
        }
    }

    public void setLabels() {
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel();
            labels[i].setBounds(125, (i + 1) * 100, 500, 100);
            labels[i].setBackground(new Color(50, 50, 50));
            labels[i].setForeground(new Color(0, 72, 255));
            labels[i].setFont(new Font("Calibri", Font.PLAIN, 20));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < buttons.length; i++) {
            if (e.getSource() == buttons[i]) {
                answer = symbols[i];
                break;
            }
        }
        checkAnswer();
        frame.dispose();
    }

    public void correctAnswer() {
        JOptionPane.showMessageDialog(null, "Correct answer!", "Review", JOptionPane.INFORMATION_MESSAGE);

    }

    public void wrongAnswer() {
        JOptionPane.showMessageDialog(null, "Wrong answer!", "Review", JOptionPane.ERROR_MESSAGE);

    }

    public void checkAnswer() {
        if (answer == answers.get(index)) {
            correctAnswer();
        } else wrongAnswer();

    }

    public void readQuestions() throws FileNotFoundException {
        Scanner q = new Scanner(new File(String.format("src/main/resources/Easy Questions.txt")));
        while (q.hasNextLine()) {
            questions.add(q.nextLine());
        }
        Scanner o = new Scanner(new File(String.format("src/main/resources/Easy Options.txt")));
        while (o.hasNextLine()) {
            options.add(o.nextLine());
        }
        Scanner a = new Scanner(new File(String.format("src/main/resources/Easy Answers.txt")));
        while (a.hasNext()) {
            answers.add(a.next().charAt(0));
        }
    }
}



