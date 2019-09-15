package stickman.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SID:480133780
 */

public class LevelImpl implements Level {

    private double floorHeight;
    private double levelHeight;
    private double levelWidth;
    private List<Entity> entityList;
    private Player player;

    /**
     * Constructor
     * @param floorHeight The line where the sky and ground is divided
     * @param levelHeight Height of Level
     * @param levelWidth Width of Level
     * @param player The player
     */
    public LevelImpl(double floorHeight, double levelHeight,double levelWidth, Player player){

        this.floorHeight = floorHeight;
        this.levelHeight = levelHeight;
        this.levelWidth = levelWidth;
        this.player = player;
        this.entityList = new ArrayList<>();
        this.entityList.add(player);

    }

    /**
     * Move the player forward
     * @return returns true if movement is successful
     */
    public boolean moveRight(){
        this.player.setRight(true);
        if(this.player.getXPos() + this.player.getVelocity() > this.levelWidth){
            this.player.setXPos(true, this.levelWidth - this.player.getXPos());

        } else{
            this.player.setXPos(true, this.player.getVelocity());

        }
        return true;
    }

    /**
     * Move the player backward
     * @return return true if it is successful
     */
    public boolean moveLeft(){
        this.player.setLeft(true);

        if(this.player.getXPos() - this.player.getVelocity() < 0){
            this.player.setXPos(false, this.player.getXPos());

        } else{
            this.player.setXPos(false, this.player.getVelocity());
        }
        return true;
    }

    /**
     * The player jump
     * @return if jump is successful, returns true.
     */
    public boolean jump(){

        if(!player.getJump() && !player.getFall()){
            return player.setYPos();
        }
        return false;
    }

    /**
     * Stop the movement of the character
     * @return returns true if it is successful
     */
    public boolean stopMoving(){

        if(this.player.getLeft()){
            this.player.setLeft(false);

        }

        if(this.player.getRight()){
            this.player.setRight(false);

        }
        return true;
    }

    /**
     * Update the movement of Entities.
     */
    public void tick(){
        for(int i = 0; i < entityList.size(); i ++){
            if(entityList.get(i) instanceof Cloud){
                Cloud cloud = (Cloud) entityList.get(i);
                cloud.move();
            }
        }
        if(player.getFall() || player.getJump()){
            player.setYPos();
        }

        if(this.player.getRight()){
            this.stopMoving();
            this.moveRight();
        }

        if(this.player.getLeft()){
            this.stopMoving();
            this.moveLeft();
        }
    }

    /**
     * @return returns x position of the player
     */
    public double getHeroX(){
        return this.player.getXPos();
    }

    /**
     * @return returns the height of the Level
     */
    public double getHeight(){
        return this.levelHeight;
    }

    /**
     * @return returns the width of the Level
     */
    public double getWidth(){
        return this.levelWidth;
    }

    /**
     * @return return floorHeight of the Level
     */
    public double getFloorHeight(){
        return this.floorHeight;
    }

    /**
     * @return returns all the entities within that Level
     */
    public List<Entity> getEntities(){
        return entityList;
    }
}
