package stickman.Strategy;

import stickman.model.Level;
import stickman.Entity.Player;
import stickman.Entity.Wall;

public class WallBasic implements WallStrategy {


    /**
     * We are creating walls and position the in the following way
     *
     * a wall have 3 blocks and will be drawn based on the first random number
     * it is placed at a certain x position based on the iterator j and height based on lvl height and player height
     * as well as wall height and floorHeight.
     * if the second random number is modulo 2 == 0 then we will put a new will a little bit higher next to the previous wall
     * with a gap
     *Note that the wall height is always 30
     * @param lvl put into this level object
     * @param player get the player jump height
     * @param finish get the finish line to stop the wall building
     */
    public void addWall(Level lvl, Player player, double finish){
        Wall wall = new Wall(0,0);
        double y = lvl.getHeight()-lvl.getFloorHeight() - player.getHeight()- wall.getHeight()-20;
        double wallWidth = wall.getWidth();
        int wallRow = 3;
        double gap = 50;
        double section = wallRow*wallWidth*2+gap;


        for(double j = section; j < lvl.getWidth(); j+=section){
            int random = (int) Math.round(Math.random() * (4-1) +1);
            int random2 = (int) Math.round(Math.random() * (4-1) +1);

            if(j < finish-section){
                if(random % 2 == 0 && random2 % 2 == 0){
                    this.createWall(lvl, j, y);
                    this.createWall(lvl, j+wallRow*wallWidth+gap, y - player.getHeight()-wall.getHeight()-20);

                }
                else if(random % 2 == 0){
                    this.createWall(lvl, j, y);
                }
            }
        }

    }

    /**
     * create a 3 bocks of wall next to each other
     * @param lvl add walls to this level
     * @param x x position
     * @param y y position
     */
    private void createWall(Level lvl, double x, double y){

        for(int i = 0; i < 3; i++){
            Wall wall = new Wall(x+i*30, y);
            lvl.getEntities().add(wall);
        }
    }
}
