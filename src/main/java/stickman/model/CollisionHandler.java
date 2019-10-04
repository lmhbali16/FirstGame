package stickman.model;

import stickman.Entity.*;

import java.util.List;

public class CollisionHandler {

    private double floorHeight;
    private List<Entity> entityList;
    private List<Enemy> enemyList;

    public CollisionHandler(double floorHeight, List<Entity> entityList, List<Enemy> enemyList){
        this.floorHeight = floorHeight;
        this.enemyList = enemyList;
        this.entityList = entityList;

    }


    public void handleEnemyCollision(Player player){

    }

    public void handleWallCollision(Player player){

        for(int i = 0; i < entityList.size(); i++){
            if(this.checkWall(player,entityList.get(i))){
                this.actionWall(player,(Wall) entityList.get(i));
                break;
            }
        }

        if(player.getYPos()+player.getHeight() < floorHeight && !player.getFall() && !player.getJump()){
            player.setFall(true);
            player.setJump(false);
        }
    }

    public void actionWall(Player player, Wall wall){
        double playerPos = player.getXPos()+player.getWidth();
        double wallPos = wall.getXPos() + wall.getWidth();

        double playerH = player.getYPos()+player.getHeight();
        double wallH = wall.getYPos()+wall.getHeight();
        int state = this.checkWallState(player, wall);
        
        if(state == 1){
            if(player.getFall()){

                player.setInitialPos(player.getXPos(), wall.getYPos()- player.getHeight());
                player.setFall(false);
                player.setJump(false);
            }
        }
        else if(state == 2){
            player.setInitialPos(player.getXPos(), wallH);
            player.setFall(true);
            player.setJump(false);
        }
        else if(state == -1){
            player.setInitialPos(wall.getXPos()-player.getWidth(), player.getYPos());
        }
        else if(state == -2){
            player.setInitialPos(wallPos,player.getYPos());
        }


    }

    //return 1 if he is on the wall
    // 2 if he is right under the wall
    //0 if no contact
    //-1 if he is right next to the wall from the left
    //-2 if he is next to the wall from the right
    public int checkWallState(Player player, Wall wall){
        double playerPos = player.getXPos()+player.getWidth();
        double wallPos = wall.getXPos() + wall.getWidth();

        double playerH = player.getYPos()+player.getHeight();
        double wallH = wall.getYPos()+wall.getHeight();

        if(playerPos > wall.getXPos() && player.getXPos() < wallPos){
            if(playerH >= wall.getYPos() && player.getYPos() < wall.getYPos()){
                return 1;
            }
            else if(player.getYPos()<= wallH && playerH > wallH){
                return 2;
            }
        }

        /*if(playerPos >= wall.getXPos() && player.getXPos() < wall.getXPos()){
            if(player.getYPos() >= wall.getYPos() && player.getYPos() <= wallH){
                return -1;
            }
            else if(player.getYPos() <= wall.getYPos() && playerH >= wallH){
                return -1;
            }
            else if(playerH >= wall.getYPos()+wall.getHeight()/2 && player.getYPos() < wall.getYPos()){
                return -1;
            }
        }
        else if(player.getXPos() <= wallPos && playerPos > wallPos){
            if(player.getYPos() >= wall.getYPos() && player.getYPos() <= wallH){
                return -2;
            }
            else if(player.getYPos() <= wall.getYPos() && playerH >= wallH){
                return -2;
            }
            else if(playerH >= wall.getYPos()+wall.getHeight()/2 && player.getYPos() <= wall.getYPos()){
                return -2;
            }
        }*/

        return 0;

    }

    public boolean checkWall(Player player, Entity entity){
        double playerPos = player.getXPos() + player.getWidth();

        if(entity instanceof Wall){
            Wall wall = (Wall) entity;
            double wallPos = wall.getXPos()+wall.getWidth();
            if(playerPos >= wall.getXPos() && player.getXPos() <= wallPos){
                return true;
            }
        }

        return false;
    }

}
