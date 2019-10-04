package stickman.Strategy;

import stickman.Entity.Enemy;
import stickman.Strategy.EnemyStrat;

public class RedEnemy implements EnemyStrat {

    private Enemy enemy;
    private double levelHeight;
    private double levelWidth;
    private double velocity = 30 * 0.017;

    private double moveRight = 70;
    private double moveLeft = 70;

    public RedEnemy(double levelHeight, double levelWidth, Enemy enemy){
        this.enemy = enemy;
        this.levelHeight = levelHeight;
        this.levelWidth = levelWidth;
    }


    public void move(){
        this.setX();
        this.setY();
    }

    private void setX(){


        if(moveLeft > 0){
            if(enemy.getXPos() - velocity <= 0){
                enemy.setInitialPos(0, enemy.getYPos());
                moveLeft -= velocity;
                moveRight += velocity;
            }
            else{
                enemy.setInitialPos(enemy.getXPos()-velocity, enemy.getYPos());
                moveLeft -= velocity;
            }
        }

        else{
            if(moveRight > 0){
                if(enemy.getXPos() + velocity <= levelWidth){
                    enemy.setInitialPos(enemy.getXPos()+velocity, enemy.getYPos());
                    moveRight -= velocity;
                }
                else{
                    enemy.setInitialPos(enemy.getXPos() -velocity, enemy.getYPos());
                    moveRight -= velocity;
                }
            }
            else{
                moveLeft = 70;
                moveRight = 70;
            }
        }
    }

    private void setY(){


    }
}
