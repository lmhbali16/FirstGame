package stickman.Strategy;

import stickman.Entity.Enemy;


public class BlueEnemy implements EnemyStrat {

    private Enemy enemy;
    private double velocity;
    private double moveLeft;
    private double moveRight;


    private double levelHeight;
    private double levelWidth;

    public BlueEnemy(double levelHeight, double levelWidth){

        velocity = 70*0.017;
        this.levelHeight = levelHeight;
        this.levelWidth = levelWidth - 150;
        this.moveRight = levelWidth *0.25;
        this.moveLeft = levelWidth*0.30;
    }

    public void addEnemy(Enemy enemy){
        this.enemy = enemy;
    }

    public void move(){
        this.setX();
        this.setY();
    }

    /**
     * moves left then right based on the constructor setup.
     * if it reaches the edges then change direction
     */
    private void setX(){
        double x = enemy.getXPos();
        if(moveLeft > 0){
            if(x-velocity <= 100){
                moveLeft = 0;
                moveRight = moveRight *1.5;
            }
            if(x-velocity > 100){
                enemy.setInitialPos(x-velocity,enemy.getYPos());
                moveLeft -= velocity;
            }
        }

        if(moveLeft <= 0 && moveRight > 0){
            if(x+velocity >= levelWidth){
                moveRight  = 0;
                moveLeft = moveLeft * 1.5;
            }
            if(x+velocity < levelWidth){
                enemy.setInitialPos(x+velocity,enemy.getYPos());
                moveRight -= velocity;
            }

            if(moveRight <= 0 && moveLeft <= 0){
                moveRight = levelWidth *0.25;
                moveLeft = levelWidth *0.30;
            }
        }

    }

    /**
     * just make sure that the character lands on the ground if it was on the air originally.
     */
    private void setY(){
        double pos = enemy.getYPos() + enemy.getHeight();

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
