package stickman.Strategy;

import stickman.Entity.Enemy;
import stickman.Strategy.EnemyStrat;

public class PurpleEnemy implements EnemyStrat {
    private double x;
    private double y;


    private double levelHeight;
    private double levelWidth;

    public PurpleEnemy(double levelHeight, double levelWidth, Enemy enemy){

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
