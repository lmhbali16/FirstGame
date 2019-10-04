package stickman.Entity;

import javafx.scene.image.ImageView;
import stickman.Strategy.*;

import java.io.File;

public class Enemy implements Entity {

    private double height;
    private double width;
    private int life;
    private String imagePath;
    private Layer layer;
    private double x;
    private double y;
    private EnemyStrat strat;
    private boolean left;
    private boolean right;
    private boolean jump;
    private  boolean fall;
    private ImageView node;

    public Enemy(double height, int life, String path){
        this.height = height;

        this.life = life;
        this.imagePath = path;
        this.layer = Layer.FOREGROUND;
        this.strat = null;
        left = false;
        right = false;
        jump = false;
        fall = false;
        this.node = new ImageView(new File(imagePath).toURI().toString());
        node.setFitHeight(height);
        node.setPreserveRatio(true);
        this.width = node.getBoundsInLocal().getWidth();


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
        return imagePath;
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

    public ImageView getNode(){
        return this.node;
    }


}
