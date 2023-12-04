import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.Random;

public class GameApplication extends JFrame {
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private static final String FILE_PATH = "user_credentials.txt";
    private Map<String, User> userMap; // Store user credentials and scores

    public GameApplication() {
        setTitle("Login and Signup");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameTextField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        signupButton = new JButton("Sign Up");

        loginButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = new String(passwordField.getPassword());

                if (validateUsername(username) && validatePassword(password)) {
                    if (authenticateUser(username, password)) {
                        JOptionPane.showMessageDialog(GameApplication.this, "Login Successful");
                        showLoggedInOptions();
                    } else {
                        JOptionPane.showMessageDialog(GameApplication.this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(GameApplication.this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        signupButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = new String(passwordField.getPassword());

                if (validateUsername(username) && validatePassword(password)) {
                    if (createUser(username, password)) {
                        JOptionPane.showMessageDialog(GameApplication.this, "Signup Successful");
                        showLoggedInOptions();
                    } else {
                        JOptionPane.showMessageDialog(GameApplication.this, "Failed to create user", "Signup Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(GameApplication.this, "Invalid username or password", "Signup Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameTextField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(signupButton);

        add(panel);
    }

    private void showLoggedInOptions() {
        getContentPane().removeAll();
        setLayout(new GridLayout(3, 1));

        JButton gameMenuButton = new JButton("Game Menu");
        JButton gameInstructionButton = new JButton("Game Instruction");
        JButton scoreButton = new JButton("Score");

        gameMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showGamingApplication();
            }
        });

        gameInstructionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showGameInstructionsMenu();
            }
        });

        scoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showScore();
            }
        });

        add(gameMenuButton);
        add(gameInstructionButton);
        add(scoreButton);

        revalidate();
    }
    
    private void showGamingApplication() {
        GamingApplication gamingApplication = new GamingApplication();
        gamingApplication.setVisible(true);
        dispose();
    }

    private void showGameInstructionsMenu() {
        getContentPane().removeAll();
        setLayout(new GridLayout(4, 1));

        JButton rockPaperScissorsButton = new JButton("Rock Paper Scissors");
        JButton ticTacToeButton = new JButton("Tic Tac Toe");
        JButton guessingNumberButton = new JButton("Guessing Number");
        JButton goBackButton = new JButton("Go Back");

        rockPaperScissorsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showGameInstruction("Rock Paper Scissors", getRockPaperScissorsInstruction());
            }
        });

        ticTacToeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showGameInstruction("Tic Tac Toe", getTicTacToeInstruction());
            }
        });

        guessingNumberButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showGameInstruction("Guessing Number", getGuessingNumberInstruction());
            }
        });

        goBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLoggedInOptions();
            }
        });

        add(rockPaperScissorsButton);
        add(ticTacToeButton);
        add(guessingNumberButton);
        add(goBackButton);

        revalidate();
    }

    private void showGameInstruction(String gameName, String gameInstruction) {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JTextArea instructionArea = new JTextArea(gameInstruction);
        instructionArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(instructionArea);

        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showGameInstructionsMenu();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(goBackButton, BorderLayout.SOUTH);

        add(panel);
        setTitle(gameName + " Instructions");
        revalidate();
    }

    // Game instruction methods

    private String getRockPaperScissorsInstruction() {
        // Rock Paper Scissors instructions
    	return "Rock Paper Scissors\n\n"
        + "Instructions:\n"
        + "1. Rock Paper Scissors is a two-player game where each player selects either rock, paper, or scissors.\n"
        + "2. Rock beats scissors, scissors beats paper, and paper beats rock.\n"
        + "3. Both players make their choices simultaneously by clicking on the corresponding buttons.\n"
        + "4. The game will determine the winner based on the choices made.\n"
        + "5. The player who wins the round earns one point.\n"
        + "6. The game continues until one player reaches a score of 3.\n"
        + "7. At the end of the game, the scores are displayed, and the players can play again or choose another game.\n"
        + "Have fun playing Rock Paper Scissors!";
    }

    private String getTicTacToeInstruction() {
        // Tic Tac Toe instructions
    	return "Tic Tac Toe\n\n"
        + "Instructions:\n"
        + "1. Tic Tac Toe is a two-player game played on a 3x3 grid.\n"
        + "2. The players take turns marking a square with their respective symbols (X or O).\n"
        + "3. The goal is to get three of their symbols in a row, either horizontally, vertically, or diagonally.\n"
        + "4. The first player to achieve this wins the game.\n"
        + "5. If all the squares are filled and no player has won, the game is a draw.\n"
        + "6. Players can choose to play again or go back to the main menu.\n"
        + "Enjoy playing Tic Tac Toe!";
    }

    private String getGuessingNumberInstruction() {
        // Guessing Number instructions
    	return "Guessing Number\n\n"
        + "Instructions:\n"
        + "1. Guessing Number is a single-player game where the computer picks a random number.\n"
        + "2. The player's objective is to guess the picked number.\n"
        + "3. The player enters their guess, and the computer provides feedback.\n"
        + "4. If the guessed number is higher than the picked number, the computer says 'Lower!'\n"
        + "5. If the guessed number is lower than the picked number, the computer says 'Higher!'\n"
        + "6. The player continues guessing until they guess the correct number.\n"
        + "7. After guessing the correct number, the player can choose to play again or go back to the main menu.\n"
        + "Have fun playing Guessing Number!";
    }

    private void showScore() {
        StringBuilder scoreBuilder = new StringBuilder();
        for (User user : userMap.values()) {
            scoreBuilder.append(user.getUsername()).append(": ").append(user.getScore()).append("\n");
        }
        JOptionPane.showMessageDialog(GameApplication.this, scoreBuilder.toString(), "Score", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean authenticateUser(String username, String password) {
        User user = userMap.get(username);
        return user != null && user.getPassword().equals(password);
    }

    private boolean createUser(String username, String password) {
        if (userMap.containsKey(username)) {
            return false; // User already exists
        }

        User user = new User(username, password);
        userMap.put(username, user);
        saveUserCredentials();
        return true;
    }

    private boolean validateUsername(String username) {
        return username.length() >= 4;
    }

    private boolean validatePassword(String password) {
        return password.length() >= 4;
    }

    private void loadUserCredentials() {
        userMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    int score = Integer.parseInt(parts[2]);
                    User user = new User(username, password);
                    user.setScore(score);
                    userMap.put(username, user);
                }
            }
        } catch (IOException e) {
            // Handle exception appropriately
        }
    }

    private void saveUserCredentials() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : userMap.values()) {
                String line = user.getUsername() + ":" + user.getPassword() + ":" + user.getScore();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            // Handle exception appropriately
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GameApplication gameApplication = new GameApplication();
                gameApplication.loadUserCredentials(); // Load user credentials from file
                gameApplication.setVisible(true);
            }
        });
    }

    private static class User {
        private String username;
        private String password;
        private int score;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
            this.score = 0;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}


class GamingApplication extends JFrame {

    private JButton rockPaperScissorsButton;
    private JButton ticTacToeButton;
    private JButton guessingNumberButton;
    

    private int playerScore = 0;
    private int botScore = 0;

    public GamingApplication() {
        setTitle("Gaming Application");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        
        rockPaperScissorsButton = new JButton("Rock Paper Scissors");
        ticTacToeButton = new JButton("Tic Tac Toe");
        guessingNumberButton = new JButton("Guessing Number");

        rockPaperScissorsButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                showRockPaperScissorsOptions();
            }
        });

        ticTacToeButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                showTicTacToeOptions();
            }
        });

        guessingNumberButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                showGuessingNumberOptions();
            }
        });

        panel.add(rockPaperScissorsButton);
        panel.add(ticTacToeButton);
        panel.add(guessingNumberButton);

        add(panel);
    }
    private enum Choice {
        ROCK,
        PAPER,
        SCISSORS
    }
    private void showRockPaperScissorsOptions() {
        // Player vs. Bot mode
        showRockPaperScissorsPanel();
    }

    private void showRockPaperScissorsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel scoreLabel = new JLabel("Player: " + playerScore + " Bot: " + botScore);
        JButton rockButton = new JButton();
        JButton paperButton = new JButton();
        JButton scissorsButton = new JButton();

        // Set images for the buttons
        ImageIcon rockIcon = new ImageIcon("rock.png");
        ImageIcon paperIcon = new ImageIcon("paper.png");
        ImageIcon scissorsIcon = new ImageIcon("scissors.png");
        rockButton.setIcon(rockIcon);
        paperButton.setIcon(paperIcon);
        scissorsButton.setIcon(scissorsIcon);

        rockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playRockPaperScissors(Choice.ROCK);
            }
        });

        paperButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playRockPaperScissors(Choice.PAPER);
            }
        });

        scissorsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playRockPaperScissors(Choice.SCISSORS);
            }
        });

        panel.add(scoreLabel, BorderLayout.NORTH);
        panel.add(rockButton, BorderLayout.WEST);
        panel.add(paperButton, BorderLayout.CENTER);
        panel.add(scissorsButton, BorderLayout.EAST);

        setContentPane(panel);
        revalidate();
    }

    private void playRockPaperScissors(Choice playerChoice) {
        Random random = new Random();
        Choice botChoice;
        int botRandom = random.nextInt(3);

        if (botRandom == 0) {
            botChoice = Choice.ROCK;
        } else if (botRandom == 1) {
            botChoice = Choice.PAPER;
        } else {
            botChoice = Choice.SCISSORS;
        }

        String result = determineRockPaperScissorsResult(playerChoice, botChoice);
        JOptionPane.showMessageDialog(GamingApplication.this, result, "Result", JOptionPane.INFORMATION_MESSAGE);

        if (result.equals("Player wins")) {
            playerScore++;
        } else if (result.equals("Bot wins")) {
            botScore++;
        }

        if (playerScore == 3 || botScore == 3) {
            showGamingApplication();
        } else {
            showRockPaperScissorsPanel();
        }
    }


    private String determineRockPaperScissorsResult(Choice playerChoice, Choice botChoice) {
        if (playerChoice == botChoice) {
            return "It's a tie!";
        } else if (playerChoice == Choice.ROCK) {
            if (botChoice == Choice.SCISSORS) {
                return "Player wins";
            } else {
                return "Bot wins";
            }
        } else if (playerChoice == Choice.PAPER) {
            if (botChoice == Choice.ROCK) {
                return "Player wins";
            } else {
                return "Bot wins";
            }
        } else if (playerChoice == Choice.SCISSORS) {
            if (botChoice == Choice.PAPER) {
                return "Player wins";
            } else {
                return "Bot wins";
            }
        } else {
            return "Invalid choice";
        }
    }


    private void showTicTacToeOptions() {
        // Directly show the Tic Tac Toe panel
        showTicTacToePanel();
    }

    private void showTicTacToePanel() {
        TicTacToeGUI ticTacToeGUI = new TicTacToeGUI();
        ticTacToeGUI.createGUI();
    }

    private void showGuessingNumberOptions() {
        // Directly show the Guessing number panel
        showGuessingNumberPanel();
    }

    private void showGuessingNumberPanel() {
    	GuessingGameGUI guessingGame = new GuessingGameGUI();
    	guessingGame.setVisible(true);

    }

    private void showGamingApplication() {
        JOptionPane.showMessageDialog(GamingApplication.this, "Game Over! Player: " + playerScore + " Bot: " + botScore);
        playerScore = 0;
        botScore = 0;
        getContentPane().removeAll();
        add(rockPaperScissorsButton);
        add(ticTacToeButton);
        add(guessingNumberButton);
        revalidate();
    }
}
