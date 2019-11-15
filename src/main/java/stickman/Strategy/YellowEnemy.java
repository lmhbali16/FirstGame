package stickman.Strategy;

import stickman.Entity.Enemy;


public class YellowEnemy implements EnemyStrat {

    private Enemy enemy;
    private double levelHeight;


    public YellowEnemy(double levelHeight){

        this.levelHeight = levelHeight;
    }

    public void move(){
        this.setY();
    }



    /**
     * add our enemy that we have control on
     * @param enemy enemy object
     */
    public void addEnemy(Enemy enemy){
        this.enemy = enemy;
    }

    /**
     * Just make sure the enemy falls down to the ground
     */
    private void setY(){
        double pos = enemy.getYPos() +enemy.getHeight();

       if(pos < levelHeight){
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
