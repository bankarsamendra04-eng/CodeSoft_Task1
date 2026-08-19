package com.game.controller;

import com.game.model.GameModel;
import com.game.model.Level;
import com.game.util.GameMessages;
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
        Level choice = view.showLevelSelectionDialog();

        if (choice == null) {
            if (!countGame) System.exit(0); // Exit if canceled on launch
            return;
        }

        model.setLevel(choice);

        if (countGame) {
            model.incrementGamesPlayed();
            view.setGamesPlayedText(GameMessages.gamesPlayed(model.getTotalGames()));
        }

        model.generateSecretNumber();
        view.setLevelText(GameMessages.level(model.getCurrentLevel()));
        view.setFeedbackMessage(GameMessages.guessRange(model.getCurrentLevel()), Color.ORANGE);
        view.clearGuessField();
        view.setGuessButtonEnabled(true);
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(view.getGuessInput());

            if (guess < model.getMinRange() || guess > model.getMaxRange()) {
                view.setFeedbackMessage(GameMessages.invalidRange(model.getCurrentLevel()), Color.RED);
                return;
            }

            model.decreaseAttempt();

            if (guess == model.getSecretNumber()) {
                handleWin();
            } else if (model.getAttemptsLeft() <= 0) {
                handleLoss();
            } else if (guess < model.getSecretNumber()) {
                view.setFeedbackMessage(GameMessages.attemptsLeft("Too Low!", model.getAttemptsLeft()), Color.YELLOW);
            } else {
                view.setFeedbackMessage(GameMessages.attemptsLeft("Too High!", model.getAttemptsLeft()), Color.RED);
            }

            view.clearGuessField();

        } catch (NumberFormatException ex) {
            view.setFeedbackMessage("Please enter a valid number!", Color.RED);
            view.clearGuessField();
        }
    }

    private void handleWin() {
        model.addScore(10);
        view.setScoreText(GameMessages.score(model.getScore()));
        view.setHighScoreText(GameMessages.highestScore(model.getHighestScore()));
        view.setFeedbackMessage("Correct! You Win!", Color.GREEN);
        view.setGuessButtonEnabled(false);

        view.showMessageDialog("Congratulations!\n" + GameMessages.level(model.getCurrentLevel())
                + "\nCorrect Number : " + model.getSecretNumber(), "Winner", JOptionPane.INFORMATION_MESSAGE);

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
