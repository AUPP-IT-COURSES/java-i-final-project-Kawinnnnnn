import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class GuessingGameGUI extends JFrame {
    private JTextField guess, bestScoreField, totalGuess;
    private JLabel inputLabel, guessLabel, tryLabel, bestScoreLabel, totalGuessLabel;
    private int rand = (int) (Math.random() * 50);
    private int count = 0;
    private int maxGuesses = 5;

    public GuessingGameGUI() {
        Container c = getContentPane();
        c.setLayout(null);
        ((JPanel) c).setBackground(null);

        guessLabel = new JLabel("Guess the Number ?");
        guessLabel.setForeground(Color.RED);
        guessLabel.setFont(new Font("times new roman", Font.BOLD, 24));
        guessLabel.setSize(270, 20);
        guessLabel.setLocation(70, 70);

        inputLabel = new JLabel("Enter a number between 1-50");
        inputLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        inputLabel.setSize(270, 20);
        inputLabel.setLocation(70, 90);

        tryLabel = new JLabel("Try and guess it !");
        tryLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        tryLabel.setSize(270, 20);
        tryLabel.setLocation(110, 160);

        guess = new JTextField(10);
        guess.setSize(50, 30);
        guess.setLocation(140, 120);

        bestScoreField = new JTextField(10);
        bestScoreField.setSize(30, 20);
        bestScoreField.setLocation(10, 10);

        bestScoreLabel = new JLabel("Best Score");
        bestScoreLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        bestScoreLabel.setSize(270, 20);
        bestScoreLabel.setLocation(50, 10);

        totalGuess = new JTextField(10);
        totalGuess.setSize(30, 20);
        totalGuess.setLocation(10, 40);

        totalGuessLabel = new JLabel("Number of Guesses");
        totalGuessLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        totalGuessLabel.setSize(270, 20);
        totalGuessLabel.setLocation(50, 40);

        JButton guessButton = new JButton("Guess");
        guessButton.setSize(100, 30);
        guessButton.setLocation(110, 190);
        guessButton.setBackground(Color.LIGHT_GRAY);
        guessButton.addActionListener(new ButtonListener());

        JButton giveUpButton = new JButton("Give up!");
        giveUpButton.setSize(100, 30);
        giveUpButton.setLocation(50, 240);
        giveUpButton.setBackground(Color.LIGHT_GRAY);
        giveUpButton.addActionListener(new ButtonListener2());

        JButton playAgainButton = new JButton("Play Again!");
        playAgainButton.setSize(100, 30);
        playAgainButton.setLocation(170, 240);
        playAgainButton.setBackground(Color.LIGHT_GRAY);
        playAgainButton.addActionListener(new ButtonListener3());

        c.add(bestScoreLabel);
        c.add(totalGuessLabel);
        c.add(tryLabel);
        c.add(guessLabel);
        c.add(inputLabel);
        c.add(guess);
        c.add(bestScoreField);
        c.add(totalGuess);
        c.add(guessButton);
        c.add(giveUpButton);
        c.add(playAgainButton);

        bestScoreField.setEditable(false);
        totalGuess.setEditable(false);
        setTitle("GUESS THE NUMBER");
        setSize(350, 350);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private class ButtonListener implements ActionListener {
        int bestScore = 100;

        public void actionPerformed(ActionEvent e) {
            String input = guess.getText();
            try {
                int guessValue = Integer.parseInt(input);
                count++;

                if (guessValue < 1 || guessValue > 50) {
                    showErrorDialog("Please enter a number between 1 and 50.");
                    guess.requestFocus();
                    guess.selectAll();
                    return;
                }

                if (guessValue < rand) {
                    tryLabel.setText(guessValue + " is low, try again!!");
                } else if (guessValue > rand) {
                    tryLabel.setText(guessValue + " is high, try again!!");
                } else {
                    tryLabel.setText("Your guess is correct, You win!!");
                    if (count < bestScore) {
                        bestScore = count;
                        bestScoreField.setText(String.valueOf(bestScore));
                    }
                    guess.setEditable(false);
                    saveScore();
                }

                guess.requestFocus();
                guess.selectAll();
                totalGuess.setText(String.valueOf(count));

                if (count == maxGuesses) {
                    guess.setEditable(false);
                    JOptionPane.showMessageDialog(null, "Game Over! You have reached the maximum number of guesses.");

                    // Display the answer after 5 attempts
                    JOptionPane.showMessageDialog(null, "The correct answer is " + rand + "!");
                }
            } catch (NumberFormatException ex) {
                showErrorDialog("Invalid input. Please enter a valid number.");
                guess.requestFocus();
                guess.selectAll();
            }
        }
    }


    private class ButtonListener2 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            totalGuess.setText("");
            tryLabel.setText("The correct answer is " + rand + "!!");
            guess.setText("");
            guess.setEditable(false);
        }
    }

    private class ButtonListener3 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            rand = (int) (Math.random() * 50);
            guess.setText("");
            totalGuess.setText("");
            tryLabel.setText("Try and guess it !");
            totalGuess.setText("");
            count = 0;
            guess.setEditable(true);
            guess.requestFocus();
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void saveScore() {
        String username = JOptionPane.showInputDialog(this, "Enter your username:");
        if (username != null && !username.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_credentials.txt", true))) {
                writer.write(username + ":" + count);
                writer.newLine();
            } catch (IOException e) {
                showErrorDialog("Failed to save the score.");
            }
        }
    }

    public static void main(String[] args) {
        new GuessingGameGUI();
    }
}
	