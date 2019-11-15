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

    void setSecond(double second);
    double getSecond();
    int getMinute();
    int isFinished();
    int getLife();
    void tick();
}
