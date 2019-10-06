package stickman.Entity;

import javafx.scene.image.ImageView;

public interface Entity {
    String getImagePath();
    double getXPos();
    double getYPos();
    double getHeight();
    double getWidth();
    Layer getLayer();
    void move();

    void setInitialPos(double x, double y);

    ImageView getNode();

    enum Layer{
        BACKGROUND, FOREGROUND, EFFECT
    }
}
