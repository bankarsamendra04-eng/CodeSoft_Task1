package com.game.model;

public enum Level {
    EASY("Easy", 0, 100, 10),
    MEDIUM("Medium", 0, 300, 10),
    HARD("Hard", 0, 500, 15);

    private final String displayName;
    private final int minRange;
    private final int maxRange;
    private final int attempts;

    Level(String displayName, int minRange, int maxRange, int attempts) {
        this.displayName = displayName;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.attempts = attempts;
    }

    public String getDisplayName() { return displayName; }
    public int getMinRange() { return minRange; }
    public int getMaxRange() { return maxRange; }
    public int getAttempts() { return attempts; }

    public String getLabel() {
        return displayName + " (" + minRange + "-" + maxRange + ")";
    }

    public static Level fromLabel(String label) {
        for (Level level : values()) {
            if (label != null && label.contains(level.displayName)) return level;
        }
        throw new IllegalArgumentException("Unknown level label: " + label);
    }
}
