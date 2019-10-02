package stickman.Strategy;

import stickman.Entity.Enemy;
import stickman.Strategy.EnemyStrat;

public class YellowEnemy implements EnemyStrat {
    private double x;
    private double y;

    private double height;
    private double width;
    private double levelHeight;
    private double levelWidth;

    public YellowEnemy(double levelHeight, double levelWidth, Enemy enemy){
        this.height = height;
        this.width = width;
        this.levelHeight = levelHeight;
        this.levelWidth = levelWidth;
    }

    public void move(){
        this.setX();
        this.setY();
    }

    private void setX(){

    }

    private void setY(){

    }
}
