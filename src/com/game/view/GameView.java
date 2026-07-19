package com.game.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameView extends JFrame {

    private JLabel titleLabel, messageLabel, scoreLabel, highestScoreLabel, gamesPlayedLabel, levelLabel;
    private JTextField guessField;
    private JButton guessButton, newGameButton, exitButton;

    public GameView() {
        setTitle("Number Guessing Game");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(30, 30, 46));
        setLayout(new GridLayout(10, 1, 10, 15));

        titleLabel = createLabel("NUMBER GUESSING GAME", new Font("Segoe UI", Font.BOLD, 24), Color.WHITE);
        levelLabel = createLabel("Level : Easy", new Font("Segoe UI", Font.BOLD, 16), Color.CYAN);
        messageLabel = createLabel("Select level to start", new Font("Segoe UI", Font.PLAIN, 16), Color.ORANGE);
        scoreLabel = createLabel("Score : 0", new Font("Segoe UI", Font.BOLD, 16), Color.WHITE);
        highestScoreLabel = createLabel("Highest Score : 0", new Font("Segoe UI", Font.BOLD, 16), Color.WHITE);
        gamesPlayedLabel = createLabel("Games Played : 0", new Font("Segoe UI", Font.BOLD, 16), Color.WHITE);

        guessField = new JTextField();
        guessField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        guessField.setHorizontalAlignment(JTextField.CENTER);

        guessButton = createButton("Guess", new Color(0, 200, 83));
        newGameButton = createButton("New Game", new Color(41, 98, 255));
        exitButton = createButton("Exit", new Color(213, 0, 0));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBackground(new Color(30, 30, 46));
        buttonPanel.add(guessButton);
        buttonPanel.add(newGameButton);
        buttonPanel.add(exitButton);

        add(titleLabel); add(levelLabel); add(messageLabel); add(guessField);
        add(buttonPanel); add(scoreLabel); add(highestScoreLabel); add(gamesPlayedLabel);
    }

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    private JButton createButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    public String showLevelSelectionDialog() {
        String[] levels = {"Easy (0-100)", "Medium (0-300)", "Hard (0-500)"};
        return (String) JOptionPane.showInputDialog(this, "Select Game Level", "Game Level",
                JOptionPane.QUESTION_MESSAGE, null, levels, levels[0]);
    }

    public void showMessageDialog(String msg, String title, int type) {
        JOptionPane.showMessageDialog(this, msg, title, type);
    }

    // UI Updaters
    public void setFeedbackMessage(String msg, Color color) { messageLabel.setText(msg); messageLabel.setForeground(color); }
    public void setLevelText(String text) { levelLabel.setText(text); }
    public void setScoreText(String text) { scoreLabel.setText(text); }
    public void setHighScoreText(String text) { highestScoreLabel.setText(text); }
    public void setGamesPlayedText(String text) { gamesPlayedLabel.setText(text); }

    public void clearGuessField() { guessField.setText(""); guessField.requestFocus(); }
    public String getGuessInput() { return guessField.getText(); }
    public void setGuessButtonEnabled(boolean state) { guessButton.setEnabled(state); }

    // Listeners
    public void addGuessListener(ActionListener listener) {
        guessButton.addActionListener(listener);
        guessField.addActionListener(listener);
    }
    public void addNewGameListener(ActionListener listener) { newGameButton.addActionListener(listener); }
    public void addExitListener(ActionListener listener) { exitButton.addActionListener(listener); }
}