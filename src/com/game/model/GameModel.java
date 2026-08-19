package com.game.model;

import java.util.Random;

public class GameModel {
    private Random random = new Random();

    private int secretNumber;
    private int attemptsLeft;
    private int score = 0;
    private int highestScore = 0;
    private int totalGames = 0;

    private Level currentLevel = Level.EASY;

    public void setLevel(Level level) {
        this.currentLevel = level;
        this.attemptsLeft = level.getAttempts();
    }

    public void generateSecretNumber() {
        secretNumber = random.nextInt(getMaxRange() - getMinRange() + 1) + getMinRange();
    }

    public void incrementGamesPlayed() { totalGames++; }
    public void decreaseAttempt() { attemptsLeft--; }
    public void addScore(int points) {
        score += points;
        if (score > highestScore) highestScore = score;
    }

    // Getters
    public int getSecretNumber() { return secretNumber; }
    public int getAttemptsLeft() { return attemptsLeft; }
    public int getScore() { return score; }
    public int getHighestScore() { return highestScore; }
    public int getTotalGames() { return totalGames; }
    public int getMinRange() { return currentLevel.getMinRange(); }
    public int getMaxRange() { return currentLevel.getMaxRange(); }
    public Level getCurrentLevel() { return currentLevel; }
}
