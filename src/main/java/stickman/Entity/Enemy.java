package stickman.Entity;

import javafx.scene.image.ImageView;
import stickman.Strategy.*;

import java.io.File;

public class Enemy implements Entity {

    private double height;
    private double width;
    private int life;
    private String imagePath;

    private double x;
    private double y;
    private EnemyStrat strat;
    private boolean left;
    private boolean right;
    private boolean jump;
    private  boolean fall;
    private ImageView node;

    /**
     *
     * @param height enemy height
     * @param life enemy life
     * @param path path to the image of enemy
     */
    public Enemy(double height, int life, String path){
        this.height = height;

        this.life = life;
        this.imagePath = path;
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

    /**
     * Move the enemy
     */
    public void move(){
        if(strat == null){
            return;
        }
        this.strat.move();
    }


    /**
     * set Life of the enemy
     * @param life life of enemy
     */
    public void setLife(int life){
        this.life = life;
    }

    /**
     *
     * @return return current life of enemy
     */
    public int getLife(){
        return life;
    }

    /**
     * @return return image path
     * */
    public String getImagePath(){
        return imagePath;
    }

    /**
     *
     * @return get x position
     */
    public double getXPos(){
        return x;
    }

    /**
     *
     * @return get y position
     */
    public double getYPos(){
        return y;
    }

    /**
     *
     * @return get height
     */
    public double getHeight(){
        return height;
    }

    /**
     *
     * @return get width
     */
    public double getWidth(){
        return width;
    }

    public Layer getLayer(){
        return Layer.FOREGROUND;
    }

    /**
     * Set a concrete position for the enemy
     * @param x x position
     * @param y y position
     */
    public void setInitialPos(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param strat add a movement strategy to the enemy
     */
    public void createStrategy(EnemyStrat strat){

        this.strat = strat;
    }

    /**
     *
     * @return return boolean of enemy moving left or not
     */
    public boolean getLeft(){
        return left;
    }

    /**
     * Set the left moving state
     * @param left left movement
     */
    public void setLeft(boolean left){
        this.left =left;
    }

    /**
     * get the right moving state
     * @return right movement
     */
    public boolean getRight(){
        return right;
    }

    /**
     * Set the right moving state
     * @param right right movement
     */
    public void setRight(boolean right){
        this.right = right;
    }

    /**
     * get the jump state
     * @return jump state
     */
    public boolean getJump(){
        return jump;
    }

    /**
     * Set the jump state
     * @param jump jump state
     */
    public void setJump(boolean jump){
        this.jump = jump;
    }

    /**
     * get the falling state
     * @return falling state
     */
    public boolean getFall(){
        return fall;
    }


    /**
     * set the falling state;
     * @param fall falling state
     */
    public void setFall(boolean fall){
        this.fall = fall;
    }

    /**
     * get the image of enemy
     * @return return ImageView object
     */
    public ImageView getNode(){
        return this.node;
    }


}
