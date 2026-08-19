package com.game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameModelTest {

    private GameModel model;

    @BeforeEach
    void setUp() {
        model = new GameModel();
    }

    @Test
    void newModelStartsWithEasyLevelAndZeroedCounters() {
        assertEquals("Easy", model.getCurrentLevel());
        assertEquals(0, model.getScore());
        assertEquals(0, model.getHighestScore());
        assertEquals(0, model.getTotalGames());
    }

    @Test
    void easyLevelUsesZeroToHundredRangeAndTenAttempts() {
        model.setLevel("Easy");

        assertEquals("Easy", model.getCurrentLevel());
        assertEquals(0, model.getMinRange());
        assertEquals(100, model.getMaxRange());
        assertEquals(10, model.getAttemptsLeft());
    }

    @Test
    void mediumLevelUsesZeroToThreeHundredRangeAndTenAttempts() {
        model.setLevel("Medium");

        assertEquals("Medium", model.getCurrentLevel());
        assertEquals(0, model.getMinRange());
        assertEquals(300, model.getMaxRange());
        assertEquals(10, model.getAttemptsLeft());
    }

    @Test
    void hardLevelUsesZeroToFiveHundredRangeAndFifteenAttempts() {
        model.setLevel("Hard");

        assertEquals("Hard", model.getCurrentLevel());
        assertEquals(0, model.getMinRange());
        assertEquals(500, model.getMaxRange());
        assertEquals(15, model.getAttemptsLeft());
    }

    @Test
    void unknownLevelFallsBackToHardSettings() {
        model.setLevel("Impossible");

        assertEquals("Impossible", model.getCurrentLevel());
        assertEquals(0, model.getMinRange());
        assertEquals(500, model.getMaxRange());
        assertEquals(15, model.getAttemptsLeft());
    }

    @Test
    void switchingLevelReplacesRangeAndRestoresAttempts() {
        model.setLevel("Hard");
        model.decreaseAttempt();
        model.setLevel("Easy");

        assertEquals(100, model.getMaxRange());
        assertEquals(10, model.getAttemptsLeft());
    }

    @Test
    void secretNumberStaysWithinLevelRange() {
        model.setLevel("Medium");

        for (int i = 0; i < 1000; i++) {
            model.generateSecretNumber();
            int secret = model.getSecretNumber();
            assertTrue(secret >= model.getMinRange() && secret <= model.getMaxRange(),
                    "secret number " + secret + " outside range");
        }
    }

    @Test
    void secretNumberCanReachBothRangeBounds() {
        model.setLevel("Easy");
        model.setLevel("Easy");

        boolean sawSeveralDistinctValues = false;
        int first = -1;
        for (int i = 0; i < 200 && !sawSeveralDistinctValues; i++) {
            model.generateSecretNumber();
            if (first == -1) {
                first = model.getSecretNumber();
            } else if (model.getSecretNumber() != first) {
                sawSeveralDistinctValues = true;
            }
        }

        assertTrue(sawSeveralDistinctValues, "secret number never changed across draws");
    }

    @Test
    void generateSecretNumberOnSingleValueRangeIsDeterministic() {
        model.generateSecretNumber();

        assertEquals(0, model.getSecretNumber());
    }

    @Test
    void decreaseAttemptRemovesOneAttemptAtATime() {
        model.setLevel("Easy");

        model.decreaseAttempt();
        model.decreaseAttempt();

        assertEquals(8, model.getAttemptsLeft());
    }

    @Test
    void decreaseAttemptCanGoBelowZero() {
        model.setLevel("Easy");

        for (int i = 0; i < 11; i++) {
            model.decreaseAttempt();
        }

        assertEquals(-1, model.getAttemptsLeft());
    }

    @Test
    void incrementGamesPlayedCountsEveryGame() {
        model.incrementGamesPlayed();
        model.incrementGamesPlayed();
        model.incrementGamesPlayed();

        assertEquals(3, model.getTotalGames());
    }

    @Test
    void addScoreAccumulatesPointsAndTracksHighestScore() {
        model.addScore(10);
        model.addScore(15);

        assertEquals(25, model.getScore());
        assertEquals(25, model.getHighestScore());
    }

    @Test
    void highestScoreIsNotLoweredByNegativeScoring() {
        model.addScore(30);
        model.addScore(-10);

        assertEquals(20, model.getScore());
        assertEquals(30, model.getHighestScore());
    }

    @Test
    void addScoreWithZeroPointsLeavesTotalsUnchanged() {
        model.addScore(10);
        model.addScore(0);

        assertEquals(10, model.getScore());
        assertEquals(10, model.getHighestScore());
    }
}
