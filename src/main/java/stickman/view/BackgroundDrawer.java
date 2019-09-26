package stickman.view;

import javafx.scene.layout.Pane;
import stickman.model.GameEngine;

public interface BackgroundDrawer {
    void draw(GameEngine model, Pane pane);
    void update(int minute, double second);
    void gameOver(boolean finish);
}
