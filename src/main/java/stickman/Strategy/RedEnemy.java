package stickman.Strategy;

import stickman.Entity.Enemy;

public class RedEnemy implements EnemyStrat {

    private Enemy enemy;
    private double levelWidth;
    private double velocity;

    private double moveRight;
    private double moveLeft;

    public RedEnemy( double levelWidth){

        velocity = 30 * 0.017;
        moveRight = 70;
        moveLeft = 70;
        this.levelWidth = levelWidth;
    }

    /**
     * add our enemy that we have control on
     * @param enemy enemy object
     */
    public void addEnemy(Enemy enemy){
        this.enemy = enemy;
    }

    public void move(){
        this.setX();
    }

    /**
     * move left 70 pixels then move right 70 pixels with velocity of 30/s
     */
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


}
