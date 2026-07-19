package com.game.model;

import java.util.Random;

public class GameModel {
    private Random random = new Random();

    private int secretNumber;
    private int attemptsLeft;
    private int score = 0;
    private int highestScore = 0;
    private int totalGames = 0;

    private int minRange;
    private int maxRange;
    private String currentLevel = "Easy";

    public void setLevel(String level) {
        this.currentLevel = level;
        if (level.equals("Easy")) {
            minRange = 0; maxRange = 100; attemptsLeft = 10;
        } else if (level.equals("Medium")) {
            minRange = 0; maxRange = 300; attemptsLeft = 10;
        } else {
            minRange = 0; maxRange = 500; attemptsLeft = 15;
        }
    }

    public void generateSecretNumber() {
        secretNumber = random.nextInt(maxRange - minRange + 1) + minRange;
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
    public int getMinRange() { return minRange; }
    public int getMaxRange() { return maxRange; }
    public String getCurrentLevel() { return currentLevel; }
}