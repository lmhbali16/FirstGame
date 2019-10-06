package stickman.model;

import stickman.Entity.Cloud;
import stickman.Entity.Enemy;
import stickman.Entity.Entity;

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
    private List<Enemy> enemyList;
    private Player player;
    private String landscapeImage;
    private boolean start;
    private boolean finish;
    private double finishLine;
    private double startLine;
    private CollisionHandler collisionHandler;



    /**
     * Constructor
     * @param floorHeight The line where the sky and ground is divided
     * @param levelHeight Height of Level
     * @param levelWidth Width of Level
     * @param player The player
     */
    public LevelImpl(double floorHeight, double levelHeight,double levelWidth,double startLine, double finishLine,Player player){

        this.floorHeight = floorHeight;
        this.levelHeight = levelHeight;
        this.levelWidth = levelWidth;
        this.landscapeImage = "";
        this.player = player;
        this.start = false;
        this.finish = false;
        this.entityList = new ArrayList<>();
        this.enemyList = new ArrayList<>();
        this.entityList.add(player);
        this.collisionHandler = new CollisionHandler(levelHeight-floorHeight, entityList, enemyList, startLine);
        this.startLine = startLine;
        this.finishLine = finishLine;



    }

    /**
     * Move the player forward
     * @return returns true if movement is successful
     */
    public boolean moveRight(){
        this.player.setRight(true);
        this.player.setLeft(false);

        return true;
    }

    /**
     * Move the player backward
     * @return return true if it is successful
     */
    public boolean moveLeft(){
        this.player.setLeft(true);
        this.player.setRight(false);


        return true;
    }

    /**
     * The player jump
     * @return if jump is successful, returns true.
     */
    public boolean jump(){


        return this.player.setYPos();
    }

    /**
     * Stop the movement of the character
     * @return returns true if it is successful
     */
    public boolean stopMoving(){

        player.stopMoving();

        return true;
    }

    /**
     * Update the movement of Entities.
     */
    public void tick(){
        if(player.getXPos() != startLine && !finish){
            this.setStart(true);
        }
        if(player.getLife() <= 0 || finish){
            this.setStart(false);
            finish = true;
        }

        this.move();
        this.movePlayer();

        this.handleCollision();

    }

    public void move(){
        for(int i = 0; i < entityList.size();i++){
            if(!(entityList.get(i) instanceof Player)){
                entityList.get(i).move();
            }
        }
    }



    public void movePlayer(){
        player.move();
        if(this.getHeroX() >= finishLine){
            this.setStart(false);
            finish = true;
        }
    }

    public void setStart(boolean start) {

        this.start = start;

    }

    public void handleCollision(){
        this.collisionHandler.handleWallCollision(player);
        this.collisionHandler.handleEnemyCollision(player);
    }



    public void setLandscapeImage(String s){
        this.landscapeImage = s;
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

    public String getLandscapeImage(){return this.landscapeImage; }

    public boolean getStart(){ return this.start; }

    public int getHeroLife(){
        return player.getLife();
    }

    public boolean getFinish(){
        return finish;
    }

    public List<Enemy> getEnemyList(){
        return enemyList;
    }

    public double getStartLine(){ return this.startLine;}
}
