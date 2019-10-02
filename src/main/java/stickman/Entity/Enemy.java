package stickman.Entity;

import stickman.Strategy.*;

public class Enemy implements Entity {

    private double height;
    private double width;
    private int life;
    private String path;
    private Layer layer;
    private double x;
    private double y;
    private EnemyStrat strat;
    private boolean left;
    private boolean right;
    private boolean jump;
    private  boolean fall;
    private double jump_height;
    private double counter = 0;

    public Enemy(double height, double width, int life, String path){
        this.height = height;
        this.width = width;
        this.life = life;
        this.path = path;
        this.layer = Layer.FOREGROUND;
        this.strat = null;
        left = false;
        right = false;
        jump = false;
        fall = false;
        jump_height = 0;
    }

    public void move(){
        this.strat.move();
    }



    public void setLife(int life){
        this.life = life;
    }

    public int getLife(){
        return life;
    }

    public String getImagePath(){
        return path;
    }

    public double getXPos(){
        return x;
    }

    public double getYPos(){
        return y;
    }

    public double getHeight(){
        return height;
    }

    public double getWidth(){
        return width;
    }

    public Layer getLayer(){
        return Layer.FOREGROUND;
    }

    public void setInitialPos(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void createStrategy(EnemyStrat strat){

        this.strat = strat;
    }

    public boolean getLeft(){
        return left;
    }
    public void setLeft(boolean left){
        this.left =left;
    }
    public boolean getRight(){
        return right;
    }
    public void setRight(boolean right){
        this.right = right;
    }
    public boolean getJump(){
        return jump;
    }
    public void setJump(boolean jump){
        this.jump = jump;
    }
    public boolean getFall(){
        return fall;
    }
    public void setFall(boolean fall){
        this.fall = fall;
    }


}
