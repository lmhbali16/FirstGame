package stickman.model;

import stickman.Entity.*;

import java.util.List;

public class CollisionHandler {

    private double floorHeight;

    public CollisionHandler(double floorHeight){
        this.floorHeight = floorHeight;
    }

    public void handleObjectCollision(List<Entity> entities){

    }

    public void handlePlayerCollision(Player player, List<Entity> entities){

        Wall wall = null;
        boolean flag = false;

        for(int i = 0; i < entities.size(); i++){
            if(entities.get(i) instanceof Wall){
                if(this.checkWall(player,(Wall) entities.get(i))){
                    wall = (Wall) entities.get(i);
                    break;
                }
            }
        }

        if(wall != null){
            flag = this.collisionWall(player,wall);
        }

        if(!flag){
            if(player.getYPos()+player.getHeight() < floorHeight && !player.getJump()){
                player.setFall(true);
                player.setJump(false);
            }
        }

    }

    public boolean checkWall(Player player,Wall wall){
        if(player.getXPos()+player.getWidth() >= wall.getXPos() && player.getXPos() <= wall.getXPos()+wall.getWidth()){
            return true;
        }

        return false;
    }

    public boolean collisionWall(Player player, Wall wall){
        if(player.getHeight()+player.getYPos() == wall.getYPos()){
            player.setJump(false);
            player.setFall(false);
            player.setCounter(0);
            return true;
        }
        else if(player.getYPos() == wall.getYPos() + wall.getHeight()){
            player.setJump(false);
            player.setFall(true);
            return true;
        }

        /*if(player.getXPos() <= wall.getXPos()+wall.getWidth() && player.getXPos()+player.getWidth() >= wall.getXPos()+wall.getWidth()){
            double playerPos = player.getYPos()+player.getHeight();
            double wallPos = wall.getYPos()+wall.getHeight();

            if(player.getYPos() <= wall.getYPos() && playerPos>= wallPos){
                player.setInitialPos(wallPos,player.getYPos());
                return true;
            }
            else if(playerPos <= wallPos && playerPos<= wall.getYPos()){
                player.setInitialPos(wall.getXPos()+wall.getWidth(),player.getYPos());
                return true;
            }
            else if(player.getYPos() <= wallPos && player.getYPos()>=wall.getYPos()){
                player.setInitialPos(wall.getXPos()+wall.getWidth(),player.getYPos());
                return true;
            }
            else if(player.getYPos()>= wall.getYPos() && playerPos <= wallPos){
                player.setInitialPos(wall.getXPos()+wall.getWidth(),player.getYPos());
                return true;
            }

        }

        else if(player.getXPos()+player.getWidth() >= wall.getXPos() && player.getXPos() < wall.getXPos()){
            double playerPos = player.getYPos()+player.getHeight();
            double wallPos = wall.getYPos()+wall.getHeight();

            if(player.getYPos() <= wall.getYPos() && playerPos>= wallPos){
                player.setInitialPos(wall.getXPos()-player.getWidth(),player.getYPos());
                return true;
            }
            else if(player.getHeight()+player.getYPos() <= wallPos && playerPos<= wall.getYPos()){
                player.setInitialPos(wall.getXPos()-player.getWidth(),player.getYPos());
                return true;
            }
            else if(player.getYPos() <= wallPos && player.getYPos()>=wall.getYPos()){
                player.setInitialPos(wall.getXPos()-player.getWidth(),player.getYPos());
                return true;
            }
            else if(player.getYPos()>= wall.getYPos() && playerPos <= wallPos){
                player.setInitialPos(wall.getXPos()-player.getWidth(),player.getYPos());
                return true;
            }
        }*/

        return false;
    }


    public void handlePlayerEnemy(Player player, Enemy enemy){

    }
}
