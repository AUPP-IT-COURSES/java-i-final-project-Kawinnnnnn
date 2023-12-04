import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI {
    private JFrame frame;
    private JButton[][] buttons;
    private JLabel scoreLabel;
    private int scorePlayer1;
    private int scorePlayer2;
    private boolean player1Turn;

    public void createGUI() {
        frame = new JFrame("Tic-Tac-Toe");
        frame.setSize(300, 380);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        	
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        frame.add(boardPanel, BorderLayout.CENTER);

        buttons = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                boardPanel.add(buttons[i][j]);
            }
        }

        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new FlowLayout());
        frame.add(scorePanel, BorderLayout.SOUTH);

        scorePlayer1 = 0;
        scorePlayer2 = 0;
        player1Turn = true;

        scoreLabel = new JLabel("Player 1: " + scorePlayer1 + "   Player 2: " + scorePlayer2);
        scorePanel.add(scoreLabel);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        scorePanel.add(resetButton);

        frame.setVisible(true);
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        player1Turn = true;
    }

    private void updateScore() {
        scoreLabel.setText("Player 1: " + scorePlayer1 + "   Player 2: " + scorePlayer2);
    }

    private boolean checkWin(String symbol) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(symbol) && buttons[i][1].getText().equals(symbol) && buttons[i][2].getText().equals(symbol)) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (buttons[0][j].getText().equals(symbol) && buttons[1][j].getText().equals(symbol) && buttons[2][j].getText().equals(symbol)) {
                return true;
            }
        }

        // Check diagonals
        if (buttons[0][0].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[2][2].getText().equals(symbol)) {
            return true;
        }

        if (buttons[2][0].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[0][2].getText().equals(symbol)) {
            return true;
        }

        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            JButton buttonClicked = (JButton) e.getSource();

            if (buttonClicked.getText().isEmpty()) {
                if (player1Turn) {
                    buttonClicked.setText("X");
                    player1Turn = false;
                } else {
                    buttonClicked.setText("O");
                    player1Turn = true;
                }

                buttonClicked.setEnabled(false);

                if (checkWin("X")) {
                    scorePlayer1++;
                    updateScore();
                    JOptionPane.showMessageDialog(frame, "Player 1 wins!");
                    resetGame();
                } else if (checkWin("O")) {
                    scorePlayer2++;
                    updateScore();
                    JOptionPane.showMessageDialog(frame, "Player 2 wins!");
                    resetGame();
                } else if (checkDraw()) {
                    JOptionPane.showMessageDialog(frame, "It's a draw!");
                    resetGame();
                }
            }
        }
    }

    public static void main(String[] args) {
        TicTacToeGUI gui = new TicTacToeGUI();
        gui.createGUI();
    }
}
