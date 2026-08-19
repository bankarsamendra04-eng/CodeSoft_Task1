package com.game.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

class GameViewTest {

    private GameView view;

    @BeforeAll
    static void requireDisplay() {
        assumeFalse(GraphicsEnvironment.isHeadless(), "Swing components need a display");
    }

    @BeforeEach
    void setUp() {
        view = new GameView();
    }

    @AfterEach
    void tearDown() {
        view.dispose();
    }

    @Test
    void frameIsConfiguredAsAFixedSizeWindow() {
        assertEquals("Number Guessing Game", view.getTitle());
        assertEquals(700, view.getWidth());
        assertEquals(600, view.getHeight());
        assertFalse(view.isResizable());
        assertEquals(JFrame.EXIT_ON_CLOSE, view.getDefaultCloseOperation());
    }

    @Test
    void initialLabelsShowDefaultValues() {
        assertNotNull(findLabel("NUMBER GUESSING GAME"));
        assertNotNull(findLabel("Level : Easy"));
        assertNotNull(findLabel("Select level to start"));
        assertNotNull(findLabel("Score : 0"));
        assertNotNull(findLabel("Highest Score : 0"));
        assertNotNull(findLabel("Games Played : 0"));
    }

    @Test
    void feedbackMessageUpdatesTextAndColor() {
        view.setFeedbackMessage("Too Low! Attempts Left : 9", Color.YELLOW);

        JLabel message = findLabel("Too Low! Attempts Left : 9");
        assertNotNull(message);
        assertEquals(Color.YELLOW, message.getForeground());
    }

    @Test
    void statusLabelsCanBeUpdatedIndependently() {
        view.setLevelText("Level : Hard");
        view.setScoreText("Score : 10");
        view.setHighScoreText("Highest Score : 40");
        view.setGamesPlayedText("Games Played : 3");

        assertNotNull(findLabel("Level : Hard"));
        assertNotNull(findLabel("Score : 10"));
        assertNotNull(findLabel("Highest Score : 40"));
        assertNotNull(findLabel("Games Played : 3"));
    }

    @Test
    void guessInputIsReadBackAndClearedOnDemand() {
        guessField().setText("42");
        assertEquals("42", view.getGuessInput());

        view.clearGuessField();
        assertEquals("", view.getGuessInput());
    }

    @Test
    void guessButtonCanBeDisabledAndReEnabled() {
        view.setGuessButtonEnabled(false);
        assertFalse(button("Guess").isEnabled());

        view.setGuessButtonEnabled(true);
        assertTrue(button("Guess").isEnabled());
    }

    @Test
    void guessListenerFiresForBothButtonAndEnterKeyInTextField() {
        AtomicInteger fired = new AtomicInteger();
        view.addGuessListener(e -> fired.incrementAndGet());

        button("Guess").doClick();
        guessField().postActionEvent();

        assertEquals(2, fired.get());
    }

    @Test
    void newGameAndExitListenersAreWiredToTheirOwnButtons() {
        AtomicInteger newGame = new AtomicInteger();
        AtomicInteger exit = new AtomicInteger();
        view.addNewGameListener(e -> newGame.incrementAndGet());
        view.addExitListener(e -> exit.incrementAndGet());

        button("New Game").doClick();

        assertEquals(1, newGame.get());
        assertEquals(0, exit.get());

        button("Exit").doClick();

        assertEquals(1, exit.get());
    }

    private JLabel findLabel(String text) {
        for (Component component : descendants(view.getContentPane())) {
            if (component instanceof JLabel && text.equals(((JLabel) component).getText())) {
                return (JLabel) component;
            }
        }
        return null;
    }

    private JButton button(String text) {
        for (Component component : descendants(view.getContentPane())) {
            if (component instanceof JButton && text.equals(((AbstractButton) component).getText())) {
                return (JButton) component;
            }
        }
        throw new AssertionError("No button labelled " + text);
    }

    private JTextField guessField() {
        for (Component component : descendants(view.getContentPane())) {
            if (component instanceof JTextField) {
                return (JTextField) component;
            }
        }
        throw new AssertionError("No text field in view");
    }

    private List<Component> descendants(Container root) {
        List<Component> found = new ArrayList<>();
        for (Component component : root.getComponents()) {
            found.add(component);
            if (component instanceof Container) {
                found.addAll(descendants((Container) component));
            }
        }
        return found;
    }
}
