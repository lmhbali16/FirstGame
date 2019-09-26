package stickman.model;

import java.util.List;

public interface Level {
    List<Entity> getEntities();
    double getHeight();
    double getWidth();

    void tick();

    double getFloorHeight();
    double getHeroX();

    void setLandscapeImage(String s);
    String getLandscapeImage();
    boolean getStart();
    void setStart(boolean start);
    boolean getFinish();

    int getHeroLife();

    boolean jump();
    boolean moveLeft();
    boolean moveRight();
    boolean stopMoving();
}
