package stickman.Strategy;

import stickman.Entity.Enemy;
import stickman.Strategy.EnemyStrat;

public class GreenEnemy implements EnemyStrat {

    private double velocity = 90 * 0.017;
    private double jump_height = 50;
    private double counter = 0;

    private double levelHeight;
    private double levelWidth;
    private Enemy enemy;

    public GreenEnemy(double levelHeight, double levelWidth, Enemy enemy){
        this.enemy = enemy;
        this.levelHeight = levelHeight;
        this.levelWidth = levelWidth;
    }

    public void move(){
        this.setX();
        this.setY();
    }

    private void setX(){
        double x = this.enemy.getXPos();

        if(x <= 0){
            this.enemy.setRight(true);
            this.enemy.setLeft(false);
        }
        else if(x >= levelWidth){
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



    private void jump(){
       int a = (int) (Math.round(Math.random() * (10-1) + 1));

       if((a % 3 == 0) && !enemy.getJump() && !enemy.getFall()){
           enemy.setJump(true);
           enemy.setFall(false);
       }
    }
}
