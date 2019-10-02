package stickman.Strategy;

import stickman.model.Level;
import stickman.model.Player;
import stickman.Entity.Wall;

public class WallBasic implements WallStrategy {

    public void addWall(Level lvl, Player player, double finish){
        double y = lvl.getHeight()-lvl.getFloorHeight() - player.getHeight()-30 - 10;
        double x = 0;
        int random = 0;
        int random2 = 0;


        for(int j = (30*3+50+30*3); j < lvl.getWidth(); j+=(30*3+50+30*3)){
            random = (int) Math.round(Math.random() * (4-1) +1);
            random2 = (int) Math.round(Math.random() * (4-1) +1);

            if(j < finish-(30*3+50+30*3)){
                if(random % 2 == 0 && random2 % 2 == 0){
                    this.createWall(lvl, j, y);
                    this.createWall(lvl, j+3*30+40, y - player.getHeight()-30-10);

                }
                else if(random % 2 == 0 && random2 % 2 != 0){
                    this.createWall(lvl, j, y);
                }
            }
        }

    }

    public void createWall(Level lvl, double x, double y){

        for(int i = 0; i < 3; i++){
            Wall wall = new Wall(x+i*30, y);
            lvl.getEntities().add(wall);
        }
    }
}
