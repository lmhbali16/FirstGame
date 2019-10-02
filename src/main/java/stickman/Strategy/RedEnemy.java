package stickman.Strategy;

import stickman.Entity.Enemy;
import stickman.Strategy.EnemyStrat;

public class RedEnemy implements EnemyStrat {
    private double x;
    private double y;


    private double levelHeight;
    private double levelWidth;

    public RedEnemy(double levelHeight, double levelWidth, Enemy enemy){

        this.levelHeight = levelHeight;
        this.levelWidth = levelWidth;
    }

    public double getX(double x){
        return 0;
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
