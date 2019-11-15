package stickman.Strategy;

import stickman.Entity.Enemy;


public class PurpleEnemy implements EnemyStrat {

    private Enemy enemy;
    private double velocity;
    private double levelHeight;


    public PurpleEnemy(double levelHeight){

        velocity = 30 *0.017;
        this.levelHeight = levelHeight;

    }


    public void move(){
        this.setY();
    }



    /**
     * Moves up and down: if it touches the ground moves up, if it reaches the half of the levelHeight moves down
     */
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

    public void addEnemy(Enemy enemy){
        this.enemy = enemy;
    }

}
