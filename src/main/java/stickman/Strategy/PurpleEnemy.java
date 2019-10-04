package stickman.Strategy;

import stickman.Entity.Enemy;
import stickman.Strategy.EnemyStrat;

public class PurpleEnemy implements EnemyStrat {

    private Enemy enemy;
    private double velocity = 30 *0.017;
    private double levelHeight;
    private double levelWidth;

    public PurpleEnemy(double levelHeight, double levelWidth, Enemy enemy){
        this.enemy = enemy;
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
        double pos = enemy.getYPos()+ enemy.getHeight();


        if(!enemy.getFall() && !enemy.getJump() && pos < levelHeight){
            enemy.setInitialPos(enemy.getXPos(), enemy.getYPos() + velocity);
            enemy.setFall(true);
            enemy.setJump(false);
        }
        else if(!enemy.getJump() && enemy.getFall()){
            if(pos + velocity < levelHeight){
                enemy.setInitialPos(enemy.getXPos(), enemy.getYPos() + velocity);
            }
            else if(pos + velocity >= levelHeight){
                enemy.setInitialPos(enemy.getXPos(), levelHeight-enemy.getHeight());
                enemy.setJump(true);
                enemy.setFall(false);
            }
        }
        else if(!enemy.getFall() && enemy.getJump()){
            if(pos -velocity > levelHeight/2){
                enemy.setInitialPos(enemy.getXPos(), enemy.getYPos() -velocity);
            }
            else{
                enemy.setInitialPos(enemy.getXPos(), enemy.getYPos() -velocity);
                enemy.setFall(true);
                enemy.setJump(false);
            }
        }
    }

}
