package com.game.controller;

import com.game.model.GameModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.GraphicsEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

class GameControllerTest {

    private GameModel model;
    private RecordingGameView view;

    @BeforeAll
    static void requireDisplay() {
        assumeFalse(GraphicsEnvironment.isHeadless(), "Swing components need a display");
    }

    @BeforeEach
    void setUp() {
        model = new GameModel();
        view = new RecordingGameView();
    }

    @AfterEach
    void tearDown() {
        view.dispose();
    }

    private void startGame(String levelChoice) {
        view.scriptLevelChoices(levelChoice);
        new GameController(model, view);
    }

    private int aWrongGuess() {
        return model.getSecretNumber() == model.getMinRange() ? model.getMinRange() + 1 : model.getMinRange();
    }

    private void guess(int value) {
        view.typeGuess(String.valueOf(value));
        view.fireGuess();
    }

    @Test
    void startupSelectsLevelAndPrimesTheViewForGuessing() {
        startGame(RecordingGameView.EASY);

        assertEquals(1, view.levelSelectionCalls);
        assertEquals("Easy", model.getCurrentLevel());
        assertEquals("Level : Easy", view.levelTexts.get(0));
        assertEquals("Guess number between 0 and 100", view.lastMessage());
        assertEquals(Color.ORANGE, view.lastMessageColor());
        assertEquals(true, view.guessButtonStates.get(0));
        assertEquals(1, view.clearGuessFieldCalls);
    }

    @Test
    void startupDoesNotCountTheFirstGameAsPlayed() {
        startGame(RecordingGameView.EASY);

        assertEquals(0, model.getTotalGames());
        assertTrue(view.gamesPlayedTexts.isEmpty());
    }

    @Test
    void mediumChoiceConfiguresTheMediumRange() {
        startGame(RecordingGameView.MEDIUM);

        assertEquals("Medium", model.getCurrentLevel());
        assertEquals("Guess number between 0 and 300", view.lastMessage());
    }

    @Test
    void anyOtherChoiceConfiguresTheHardRange() {
        startGame(RecordingGameView.HARD);

        assertEquals("Hard", model.getCurrentLevel());
        assertEquals("Guess number between 0 and 500", view.lastMessage());
        assertEquals(15, model.getAttemptsLeft());
    }

    @Test
    void newGameRequestCountsAGameAndResetsTheRound() {
        startGame(RecordingGameView.EASY);
        view.scriptLevelChoices(RecordingGameView.HARD);

        view.fireNewGame();

        assertEquals(1, model.getTotalGames());
        assertEquals("Games Played : 1", view.gamesPlayedTexts.get(0));
        assertEquals("Hard", model.getCurrentLevel());
        assertEquals(15, model.getAttemptsLeft());
    }

    @Test
    void guessBelowSecretNumberReportsTooLowAndConsumesAnAttempt() {
        startGame(RecordingGameView.EASY);
        view.scriptLevelChoices(RecordingGameView.EASY);
        while (model.getSecretNumber() == 0) {
            view.fireNewGame();
        }

        guess(model.getSecretNumber() - 1);

        assertEquals(9, model.getAttemptsLeft());
        assertEquals("Too Low! Attempts Left : 9", view.lastMessage());
        assertEquals(Color.YELLOW, view.lastMessageColor());
    }

    @Test
    void guessAboveSecretNumberReportsTooHighAndConsumesAnAttempt() {
        startGame(RecordingGameView.EASY);
        view.scriptLevelChoices(RecordingGameView.EASY);
        while (model.getSecretNumber() == 100) {
            view.fireNewGame();
        }

        guess(model.getSecretNumber() + 1);

        assertEquals(9, model.getAttemptsLeft());
        assertEquals("Too High! Attempts Left : 9", view.lastMessage());
        assertEquals(Color.RED, view.lastMessageColor());
    }

    @Test
    void guessOutsideTheRangeIsRejectedWithoutCostingAnAttempt() {
        startGame(RecordingGameView.EASY);

        guess(101);

        assertEquals(10, model.getAttemptsLeft());
        assertEquals("Enter number between 0 and 100", view.lastMessage());
        assertEquals(Color.RED, view.lastMessageColor());
    }

    @Test
    void negativeGuessBelowTheRangeIsAlsoRejected() {
        startGame(RecordingGameView.EASY);

        guess(-5);

        assertEquals(10, model.getAttemptsLeft());
        assertEquals("Enter number between 0 and 100", view.lastMessage());
    }

    @Test
    void nonNumericGuessShowsValidationMessageAndClearsTheField() {
        startGame(RecordingGameView.EASY);
        int clearsBefore = view.clearGuessFieldCalls;

        view.typeGuess("abc");
        view.fireGuess();

        assertEquals(10, model.getAttemptsLeft());
        assertEquals("Please enter a valid number!", view.lastMessage());
        assertEquals(Color.RED, view.lastMessageColor());
        assertEquals(clearsBefore + 1, view.clearGuessFieldCalls);
    }

    @Test
    void emptyGuessIsTreatedAsInvalidInput() {
        startGame(RecordingGameView.EASY);

        view.typeGuess("");
        view.fireGuess();

        assertEquals("Please enter a valid number!", view.lastMessage());
    }

    @Test
    void correctGuessAwardsPointsDisablesGuessingAndAnnouncesTheWin() {
        startGame(RecordingGameView.EASY);
        int secret = model.getSecretNumber();

        guess(secret);

        assertEquals(10, model.getScore());
        assertEquals(10, model.getHighestScore());
        assertEquals("Score : 10", view.scoreTexts.get(0));
        assertEquals("Highest Score : 10", view.highScoreTexts.get(0));
        assertEquals("Correct! You Win!", view.lastMessage());
        assertEquals(Color.GREEN, view.lastMessageColor());
        assertEquals(false, view.guessButtonStates.get(view.guessButtonStates.size() - 1));
        assertEquals("Winner|Congratulations!\nLevel : Easy\nCorrect Number : " + secret,
                view.dialogs.get(0));
    }

    @Test
    void runningOutOfAttemptsEndsTheGameAndStartsANewRound() {
        startGame(RecordingGameView.EASY);
        view.scriptLevelChoices(RecordingGameView.EASY);
        int secret = model.getSecretNumber();

        for (int i = 0; i < 10; i++) {
            guess(aWrongGuess());
        }

        assertEquals("Game Over|Game Over!\nCorrect Number : " + secret, view.dialogs.get(0));
        assertTrue(view.messages.contains("Game Over! Number was " + secret));
        assertEquals(1, model.getTotalGames());
        assertEquals(0, model.getScore());
        assertEquals(10, model.getAttemptsLeft());
        assertEquals("Guess number between 0 and 100", view.lastMessage());
    }
}
