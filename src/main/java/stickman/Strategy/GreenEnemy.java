package stickman.Strategy;

import stickman.Entity.Enemy;


public class GreenEnemy implements EnemyStrat {

    private double velocity;
    private double jump_height;
    private double counter;

    private double levelHeight;
    private double levelWidth;
    private Enemy enemy;

    public GreenEnemy(double levelHeight, double levelWidth){

        velocity = 60 * 0.017;
        jump_height = 50;
        counter = 0;
        this.levelHeight = levelHeight;
        this.levelWidth = levelWidth;
    }

    public void move(){
        this.setX();
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
     * Move enemy from 100 to (levelWidth-100)
     * once it arrives to an edge change direction
     */
    private void setX(){
        double x = this.enemy.getXPos();

        if(x <= 100){
            this.enemy.setRight(true);
            this.enemy.setLeft(false);
        }
        else if(x >= levelWidth-100){
            this.enemy.setLeft(true);
            this.enemy.setRight(false);
        }


        if(!this.enemy.getRight()){
            this.enemy.setInitialPos(x-velocity,enemy.getYPos());
        }
        else{
            this.enemy.setInitialPos(x+velocity,enemy.getYPos());
        }
    }

    /**
     * Since we set the initial y position to random, we may have to make the enemy fall down the ground first
     *
     * otherwise just go up and down 50 pixels in a certain velocity while moving left and right if jump ==true
     */
    private void setY(){
        this.jump();
        double pos = enemy.getYPos()+enemy.getHeight();

        if(pos >= levelHeight){
            if(!enemy.getFall() && enemy.getJump()){
                enemy.setInitialPos(enemy.getXPos(),enemy.getYPos()-1);
                counter +=1;
            }
            else if(!enemy.getJump() && enemy.getFall()){
                enemy.setInitialPos(enemy.getXPos(), levelHeight - enemy.getHeight());
                counter = 0;
                enemy.setFall(false);
                enemy.setJump(false);
            }
        }
        else if(pos < levelHeight){
            if(!enemy.getFall() && enemy.getJump()){
                if(counter < jump_height){
                    counter += 1;
                    enemy.setInitialPos(enemy.getXPos(), enemy.getYPos() -1);
                }
                else if( counter >= jump_height){
                    enemy.setInitialPos(enemy.getXPos(), enemy.getYPos()+ 1);
                    enemy.setJump(false);
                    enemy.setFall(true);
                }
            }
            else if(enemy.getFall() && !enemy.getJump()){
                if(pos < levelHeight){
                    enemy.setInitialPos(enemy.getXPos(),enemy.getYPos() +1);
                    counter -= 1;
                }
            }
        }
    }


    /**
     * Jump if the random number is divisible by 7 and the character is on the ground
     */
    private void jump(){
       int a = (int) (Math.round(Math.random() * (10-1) + 1));

       if((a % 7 == 0) && !enemy.getJump() && !enemy.getFall()){
           enemy.setJump(true);
           enemy.setFall(false);
       }
    }
}
