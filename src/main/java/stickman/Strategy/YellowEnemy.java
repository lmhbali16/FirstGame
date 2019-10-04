package stickman.Strategy;

import stickman.Entity.Enemy;
import stickman.Strategy.EnemyStrat;

public class YellowEnemy implements EnemyStrat {

    private Enemy enemy;
    private double height;
    private double width;
    private double levelHeight;
    private double levelWidth;

    public YellowEnemy(double levelHeight, double levelWidth, Enemy enemy){
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
        double pos = enemy.getYPos() +enemy.getHeight();

        if(pos >= levelHeight){
            if(!enemy.getJump() && enemy.getFall()){
                enemy.setInitialPos(enemy.getXPos(), levelHeight - enemy.getHeight());
                enemy.setFall(false);
                enemy.setJump(false);
            }
        }
        else if(pos < levelHeight){
            if(!enemy.getFall() && !enemy.getJump()){
                enemy.setFall(true);
                enemy.setJump(false);
                enemy.setInitialPos(enemy.getXPos(),enemy.getYPos() +1);
            }
            else if(enemy.getFall() && !enemy.getJump()){
                if(pos < levelHeight){
                    enemy.setInitialPos(enemy.getXPos(),enemy.getYPos() +1);

                }
            }
        }
    }
}
