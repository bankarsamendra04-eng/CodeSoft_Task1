package com.game.controller;

import com.game.view.GameView;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Test double that keeps the real view's widgets but replaces every blocking
 * dialog and window operation with scripted answers and recorded calls.
 */
class RecordingGameView extends GameView {

    static final String EASY = "Easy (0-100)";
    static final String MEDIUM = "Medium (0-300)";
    static final String HARD = "Hard (0-500)";

    private final Deque<String> levelChoices = new ArrayDeque<>();
    private String defaultLevelChoice = EASY;
    private String guessInput = "";

    final List<String> messages = new ArrayList<>();
    final List<Color> messageColors = new ArrayList<>();
    final List<String> dialogs = new ArrayList<>();
    final List<String> levelTexts = new ArrayList<>();
    final List<String> scoreTexts = new ArrayList<>();
    final List<String> highScoreTexts = new ArrayList<>();
    final List<String> gamesPlayedTexts = new ArrayList<>();
    final List<Boolean> guessButtonStates = new ArrayList<>();
    int clearGuessFieldCalls;
    int levelSelectionCalls;

    private ActionListener guessListener;
    private ActionListener newGameListener;

    void scriptLevelChoices(String... choices) {
        for (String choice : choices) {
            levelChoices.add(choice);
        }
        if (choices.length > 0) {
            defaultLevelChoice = choices[choices.length - 1];
        }
    }

    void typeGuess(String input) {
        this.guessInput = input;
    }

    String lastMessage() {
        return messages.get(messages.size() - 1);
    }

    Color lastMessageColor() {
        return messageColors.get(messageColors.size() - 1);
    }

    @Override
    public void addGuessListener(ActionListener listener) {
        super.addGuessListener(listener);
        guessListener = listener;
    }

    @Override
    public void addNewGameListener(ActionListener listener) {
        super.addNewGameListener(listener);
        newGameListener = listener;
    }

    void fireGuess() {
        guessListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "guess"));
    }

    void fireNewGame() {
        newGameListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "newGame"));
    }

    @Override
    public String showLevelSelectionDialog() {
        levelSelectionCalls++;
        return levelChoices.isEmpty() ? defaultLevelChoice : levelChoices.poll();
    }

    @Override
    public void showMessageDialog(String msg, String title, int type) {
        dialogs.add(title + "|" + msg);
    }

    @Override
    public void setVisible(boolean visible) {
        // Keep the window off screen during tests.
    }

    @Override
    public void setFeedbackMessage(String msg, Color color) {
        messages.add(msg);
        messageColors.add(color);
    }

    @Override
    public void setLevelText(String text) {
        levelTexts.add(text);
    }

    @Override
    public void setScoreText(String text) {
        scoreTexts.add(text);
    }

    @Override
    public void setHighScoreText(String text) {
        highScoreTexts.add(text);
    }

    @Override
    public void setGamesPlayedText(String text) {
        gamesPlayedTexts.add(text);
    }

    @Override
    public void clearGuessField() {
        clearGuessFieldCalls++;
    }

    @Override
    public String getGuessInput() {
        return guessInput;
    }

    @Override
    public void setGuessButtonEnabled(boolean state) {
        guessButtonStates.add(state);
    }
}
