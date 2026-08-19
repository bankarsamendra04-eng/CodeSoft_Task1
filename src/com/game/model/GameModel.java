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
    private String currentLevel;
    private boolean levelSet;

    public void setLevel(String level) {
        if (level == null) {
            throw new IllegalArgumentException("Unknown level: null");
        }

        if (level.equals("Easy")) {
            currentLevel = level;
            minRange = 0; maxRange = 100; attemptsLeft = 10;
        } else if (level.equals("Medium")) {
            currentLevel = level;
            minRange = 0; maxRange = 300; attemptsLeft = 10;
        } else if (level.equals("Hard")) {
            currentLevel = level;
            minRange = 0; maxRange = 500; attemptsLeft = 15;
        } else {
            throw new IllegalArgumentException("Unknown level: " + level);
        }
        levelSet = true;
    }

    public void generateSecretNumber() {
        ensureLevelSet();
        secretNumber = random.nextInt(maxRange - minRange + 1) + minRange;
    }

    public void incrementGamesPlayed() { totalGames++; }
    public void decreaseAttempt() {
        ensureLevelSet();
        if (attemptsLeft <= 0) {
            throw new IllegalStateException("No attempts left");
        }
        attemptsLeft--;
    }

    public void addScore(int points) {
        if (points <= 0) {
            throw new IllegalArgumentException("Score points must be positive: " + points);
        }
        score += points;
        if (score > highestScore) highestScore = score;
    }

    private void ensureLevelSet() {
        if (!levelSet) {
            throw new IllegalStateException("Level must be set before starting a game");
        }
    }

    // Getters
    public int getSecretNumber() { ensureLevelSet(); return secretNumber; }
    public int getAttemptsLeft() { ensureLevelSet(); return attemptsLeft; }
    public int getScore() { return score; }
    public int getHighestScore() { return highestScore; }
    public int getTotalGames() { return totalGames; }
    public int getMinRange() { ensureLevelSet(); return minRange; }
    public int getMaxRange() { ensureLevelSet(); return maxRange; }
    public String getCurrentLevel() { ensureLevelSet(); return currentLevel; }
}