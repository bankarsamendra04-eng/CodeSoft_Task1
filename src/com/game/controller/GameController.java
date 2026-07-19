package com.game.controller;

import com.game.model.GameModel;
import com.game.view.GameView;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import java.awt.Color;

public class GameController {
    private GameModel model;
    private GameView view;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;

        // Attach event listeners to the view
        this.view.addGuessListener(e -> checkGuess());
        this.view.addNewGameListener(e -> startNewGameFlow(true));
        this.view.addExitListener(e -> System.exit(0));

        // Start initial game
        startNewGameFlow(false);
        this.view.setVisible(true);
    }

    private void startNewGameFlow(boolean countGame) {
        String choice = view.showLevelSelectionDialog();

        if (choice == null) {
            if (!countGame) System.exit(0); // Exit if canceled on launch
            return;
        }

        if (choice.contains("Easy")) model.setLevel("Easy");
        else if (choice.contains("Medium")) model.setLevel("Medium");
        else model.setLevel("Hard");

        if (countGame) {
            model.incrementGamesPlayed();
            view.setGamesPlayedText("Games Played : " + model.getTotalGames());
        }

        model.generateSecretNumber();
        view.setLevelText("Level : " + model.getCurrentLevel());
        view.setFeedbackMessage("Guess number between " + model.getMinRange() + " and " + model.getMaxRange(), Color.ORANGE);
        view.clearGuessField();
        view.setGuessButtonEnabled(true);
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(view.getGuessInput());

            if (guess < model.getMinRange() || guess > model.getMaxRange()) {
                view.setFeedbackMessage("Enter number between " + model.getMinRange() + " and " + model.getMaxRange(), Color.RED);
                return;
            }

            model.decreaseAttempt();

            if (guess == model.getSecretNumber()) {
                handleWin();
            } else if (model.getAttemptsLeft() <= 0) {
                handleLoss();
            } else if (guess < model.getSecretNumber()) {
                view.setFeedbackMessage("Too Low! Attempts Left : " + model.getAttemptsLeft(), Color.YELLOW);
            } else {
                view.setFeedbackMessage("Too High! Attempts Left : " + model.getAttemptsLeft(), Color.RED);
            }

            view.clearGuessField();

        } catch (NumberFormatException ex) {
            view.setFeedbackMessage("Please enter a valid number!", Color.RED);
            view.clearGuessField();
        }
    }

    private void handleWin() {
        model.addScore(10);
        view.setScoreText("Score : " + model.getScore());
        view.setHighScoreText("Highest Score : " + model.getHighestScore());
        view.setFeedbackMessage("Correct! You Win!", Color.GREEN);
        view.setGuessButtonEnabled(false);

        view.showMessageDialog("Congratulations!\nLevel : " + model.getCurrentLevel() + "\nCorrect Number : " + model.getSecretNumber(), "Winner", JOptionPane.INFORMATION_MESSAGE);

        Timer timer = new Timer(2000, e -> startNewGameFlow(true));
        timer.setRepeats(false);
        timer.start();
    }

    private void handleLoss() {
        view.setFeedbackMessage("Game Over! Number was " + model.getSecretNumber(), Color.RED);
        view.showMessageDialog("Game Over!\nCorrect Number : " + model.getSecretNumber(), "Game Over", JOptionPane.ERROR_MESSAGE);
        startNewGameFlow(true);
    }
}