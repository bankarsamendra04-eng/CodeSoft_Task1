package com.game.util;

import com.game.model.Level;

public final class GameMessages {

    private GameMessages() {}

    public static String range(String prefix, Level level) {
        return prefix + " number between " + level.getMinRange() + " and " + level.getMaxRange();
    }

    public static String guessRange(Level level) {
        return range("Guess", level);
    }

    public static String invalidRange(Level level) {
        return range("Enter", level);
    }

    public static String attemptsLeft(String prefix, int attemptsLeft) {
        return prefix + " Attempts Left : " + attemptsLeft;
    }

    public static String level(Level level) {
        return "Level : " + level.getDisplayName();
    }

    public static String score(int score) {
        return "Score : " + score;
    }

    public static String highestScore(int highestScore) {
        return "Highest Score : " + highestScore;
    }

    public static String gamesPlayed(int totalGames) {
        return "Games Played : " + totalGames;
    }
}
