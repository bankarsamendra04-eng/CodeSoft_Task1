package com.game.main;

import com.game.model.GameModel;
import com.game.view.GameView;
import com.game.controller.GameController;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) ->
                reportFailure("Unexpected error on " + thread.getName(), throwable));

        SwingUtilities.invokeLater(() -> {
            try {
                GameModel model = new GameModel();
                GameView view = new GameView();
                new GameController(model, view);
            } catch (Throwable ex) {
                reportFailure("Unable to start the game", ex);
                System.exit(1);
            }
        });
    }

    private static void reportFailure(String context, Throwable throwable) {
        System.err.println(context);
        throwable.printStackTrace();

        String message = throwable.getMessage();
        if (message == null || message.trim().isEmpty()) {
            message = throwable.getClass().getName();
        }
        JOptionPane.showMessageDialog(null, context + ":\n" + message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}