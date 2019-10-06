package stickman.model;

import java.util.Timer;

public interface GameEngine {
    Level getCurrentLevel();

    void startLevel();

    // Hero inputs - boolean for success (possibly for sound feedback)
    boolean jump();
    boolean moveLeft();
    boolean moveRight();
    boolean stopMoving();
    boolean getStart();
    void setSecond(double second);
    double getSecond();
    int getMinute();
    void setMinute(int minute);
    boolean getFinish();
    int getLife();
    void tick();
}
