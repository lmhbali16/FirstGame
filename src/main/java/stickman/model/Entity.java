package stickman.model;

public interface Entity {
    String getImagePath();
    double getXPos();
    double getYPos();
    double getHeight();
    double getWidth();
    Layer getLayer();
    void collision(Entity A);

    void setInitialPos(double x, double y);

    enum Layer{
        BACKGROUND, FOREGROUND, EFFECT
    }
}
