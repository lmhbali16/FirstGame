package stickman.model;

import stickman.Entity.*;

import java.util.List;

public class CollisionHandler {

    private double floorHeight;
    private List<Entity> entityList;
    private List<Enemy> enemyList;
    private double startLine;

    /**
     * Constructor
     * @param floorHeight floorHeight
     * @param entityList entityList of Level object
     * @param enemyList enemyList of Level object
     * @param startLine start line of Level object
     */
    public CollisionHandler(double floorHeight, List<Entity> entityList, List<Enemy> enemyList, double startLine){
        this.floorHeight = floorHeight;
        this.enemyList = enemyList;
        this.entityList = entityList;
        this.startLine = startLine;
    }

    /**
     * Iterate through enemies and check if they are killed or they kill
     * @param player player
     */
    public void handleEnemyCollision(Player player){

        for(int i = 0; i < enemyList.size();i++){
            int state = this.checkEnemy(player,enemyList.get(i));
            if(state == 1){
                this.killEnemy(player,enemyList.get(i));
            }
            else if(state == -1){
                this.killPlayer(player);
            }
        }

    }

    //1 if jump on
    //0 if nothing happens
    //-1 if lose life
    private int checkEnemy(Player player, Enemy enemy){

        double enemyPos = enemy.getXPos()+enemy.getWidth();
        double playerPos = player.getXPos()+player.getWidth();
        double playerH = player.getYPos()+player.getHeight();
        double enemyH = enemy.getYPos()+enemy.getHeight();

        //If player on top of enemy.
        if(player.getYPos() < enemy.getYPos() && playerH >= enemy.getYPos() && playerH < enemy.getYPos()+enemy.getHeight()/2){
            if(player.getXPos() >= enemy.getXPos() && player.getXPos() <= enemyPos){
                return 1;
            }
            else if(playerPos >= enemy.getXPos() && playerPos <= enemyPos){
                if(player.getJump() || player.getFall()){
                    return 1;
                }
            }

        }   //if enemy is right above the player
        else if(player.getYPos() <= enemyH && playerH > enemyH){
            if(playerPos >= enemy.getXPos() && player.getXPos() <= enemyPos){
                return -1;
            }
        }   //if enemy is in between player top and bottom --> approaching from the side
        else if(player.getYPos() <= enemy.getYPos() && playerH >= enemyH){
            if(playerPos >= enemy.getXPos() && player.getXPos() < enemy.getXPos()){
                return -1;
            }
            else if(player.getXPos() <= enemyPos && playerPos > enemyPos){
                return -1;
            }
        }
            //if player is in between enemy top and bottom --> approaching from the side
        else if(player.getYPos() >= enemy.getYPos() && playerH <= enemyH){
            if(playerPos >= enemy.getXPos() && playerPos < enemyPos){
                return -1;
            }
            else if(player.getXPos() <= enemyPos && player.getXPos() > enemy.getXPos()){
                return -1;
            }
        }




        return 0;
    }

    /**
     * Kill enemy
     * @param player player will jump up a little bit
     * @param enemy enemy dies
     */
    private void killEnemy(Player player, Enemy enemy){
        enemy.setLife(enemy.getLife()-1);
        if(enemy.getLife() <= 0){
            entityList.remove(enemy);
            enemyList.remove(enemy);
        }

        player.setJump(true);
        player.setFall(false);
        player.setCounter(player.getJumphHeight() * 0.60);
    }

    /**
     * deduct a life from player
     * @param player Player object
     */
    private void killPlayer(Player player){
        player.setLife(player.getLife()-1);
        player.setInitialPos(startLine, floorHeight-player.getHeight());
    }

    /**
     * Handle collision with wall
     * @param player Player object
     */
    public void handleWallCollision(Player player){
        boolean action = false;

        for(Entity entity : entityList){
            if(this.checkWall(player,entity)){
                action = this.actionWall(player,(Wall) entity);
                break;
            }
        }

        if(!action && player.getYPos()+player.getHeight() < floorHeight && !player.getJump()){
            player.setFall(true);
            player.setJump(false);
        }
    }

    /**
     * Based on the resulting state do the action
     * @param player PLayer object
     * @param wall the wall that the player collided with
     * @return return if there was a successful collision or not
     */
    private boolean actionWall(Player player, Wall wall){
        double wallPos = wall.getXPos() + wall.getWidth();
        int state = this.checkWallState(player, wall);

        if(state == 1){
            if(player.getFall()){
                player.setInitialPos(player.getXPos(),wall.getYPos()-player.getHeight());
                player.setFall(false);
                player.setJump(false);
                player.setCounter(0);
            }
        }
        else if(state == 2){
            player.setInitialPos(player.getXPos(), wall.getHeight()+wall.getYPos());
            player.setFall(true);
            player.setJump(false);

        }
        else if(state == -1){
            player.setInitialPos(wall.getXPos()-player.getWidth(), player.getYPos());
        }
        else if(state == -2){
            player.setInitialPos(wallPos,player.getYPos());
        }
        else{
            return false;
        }


        return true;

    }

    //return 1 if he is on the wall
    // 2 if he is right under the wall
    //0 if no contact
    //-1 if he is right next to the wall from the left
    //-2 if he is next to the wall from the right
    private int checkWallState(Player player, Wall wall){
        double playerPos = player.getXPos()+player.getWidth();
        double wallPos = wall.getXPos() + wall.getWidth();
        double playerH = player.getYPos()+player.getHeight();
        double wallH = wall.getYPos()+wall.getHeight();

        double wallLR =wall.getYPos()+wall.getHeight()/2;

        if(playerPos >= wall.getXPos() && player.getXPos() <= wallPos){
            if(playerH >= wall.getYPos() && player.getYPos() < wall.getYPos()){
                return 1;
            }
            else if(player.getYPos()<= wallH && playerH > wallH){
                return 2;
            }
            else if(player.getYPos() >= wall.getYPos() && player.getYPos() <= wallH){
                return -1;
            }
            else if(player.getYPos() <= wall.getYPos() && playerH >= wallH){
                return -1;
            }
            else if(playerH >= wallLR && player.getYPos() < wall.getYPos()){
                return -1;
            }
        }

        if(player.getXPos() <= wallPos && playerPos > wallPos){
            if(player.getYPos() >= wall.getYPos() && player.getYPos() <= wallH){
                return -2;
            }
            else if(player.getYPos() <= wall.getYPos() && playerH >= wallH){
                return -2;
            }
            else if(playerH >= wallLR && player.getYPos() <= wall.getYPos()){
                return -2;
            }
        }

        return 0;

    }

    /**
     * Check if entity is a wall and if its x position matches player's x position
     * @param player Player object for position
     * @param entity Entity to check
     * @return if entity is wall and its x position is close / equal to player x position return true
     */
    private boolean checkWall(Player player, Entity entity){
        double playerPos = player.getXPos() + player.getWidth();

        if(entity instanceof Wall){
            Wall wall = (Wall) entity;
            double wallPos = wall.getXPos()+wall.getWidth();
            return playerPos >= wall.getXPos() && player.getXPos() <= wallPos;

        }
        return false;
    }

}
