package com.game.main;

import com.game.model.GameModel;
import com.game.view.GameView;
import com.game.controller.GameController;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameModel model = new GameModel();
            GameView view = new GameView();
            new GameController(model, view);
        });
    }
}