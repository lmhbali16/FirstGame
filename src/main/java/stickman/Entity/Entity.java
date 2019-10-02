package stickman.Entity;

public interface Entity {
    String getImagePath();
    double getXPos();
    double getYPos();
    double getHeight();
    double getWidth();
    Layer getLayer();


    void setInitialPos(double x, double y);

    enum Layer{
        BACKGROUND, FOREGROUND, EFFECT
    }
}
