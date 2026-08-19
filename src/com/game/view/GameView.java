package com.game.view;

import com.game.model.Level;
import com.game.util.GameMessages;

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
        getContentPane().setBackground(UiTheme.BACKGROUND);
        setLayout(new GridLayout(10, 1, 10, 15));

        titleLabel = createLabel("NUMBER GUESSING GAME", UiTheme.TITLE_FONT, Color.WHITE);
        levelLabel = createLabel(GameMessages.level(Level.EASY), UiTheme.LABEL_FONT, Color.CYAN);
        messageLabel = createLabel("Select level to start", UiTheme.MESSAGE_FONT, Color.ORANGE);
        scoreLabel = createLabel(GameMessages.score(0), UiTheme.LABEL_FONT, Color.WHITE);
        highestScoreLabel = createLabel(GameMessages.highestScore(0), UiTheme.LABEL_FONT, Color.WHITE);
        gamesPlayedLabel = createLabel(GameMessages.gamesPlayed(0), UiTheme.LABEL_FONT, Color.WHITE);

        guessField = new JTextField();
        guessField.setFont(UiTheme.INPUT_FONT);
        guessField.setHorizontalAlignment(JTextField.CENTER);

        guessButton = createButton("Guess", UiTheme.GUESS_BUTTON);
        newGameButton = createButton("New Game", UiTheme.NEW_GAME_BUTTON);
        exitButton = createButton("Exit", UiTheme.EXIT_BUTTON);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBackground(UiTheme.BACKGROUND);
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

    public Level showLevelSelectionDialog() {
        Level[] levels = Level.values();
        String[] labels = new String[levels.length];
        for (int i = 0; i < levels.length; i++) labels[i] = levels[i].getLabel();

        String choice = (String) JOptionPane.showInputDialog(this, "Select Game Level", "Game Level",
                JOptionPane.QUESTION_MESSAGE, null, labels, labels[0]);

        return choice == null ? null : Level.fromLabel(choice);
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
