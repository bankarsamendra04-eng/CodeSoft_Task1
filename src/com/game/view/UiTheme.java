package com.game.view;

import java.awt.Color;
import java.awt.Font;

public final class UiTheme {

    public static final String FONT_FAMILY = "Segoe UI";

    public static final Font TITLE_FONT = new Font(FONT_FAMILY, Font.BOLD, 24);
    public static final Font LABEL_FONT = new Font(FONT_FAMILY, Font.BOLD, 16);
    public static final Font MESSAGE_FONT = new Font(FONT_FAMILY, Font.PLAIN, 16);
    public static final Font INPUT_FONT = new Font(FONT_FAMILY, Font.PLAIN, 18);

    public static final Color BACKGROUND = new Color(30, 30, 46);
    public static final Color GUESS_BUTTON = new Color(0, 200, 83);
    public static final Color NEW_GAME_BUTTON = new Color(41, 98, 255);
    public static final Color EXIT_BUTTON = new Color(213, 0, 0);

    private UiTheme() {}
}
