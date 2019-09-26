package stickman.model;

public interface Object extends Entity {

    void move();

    void setLife(int life);
    int getLife();

    void collision(Entity A);
}
