package stickman.model;

import de.saxsys.javafx.test.JfxRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import stickman.Entity.Player;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class PlayerTest {


    /**
     * Move player right and left
     */
    @Test
    public void testBasic(){
        double height = 40;
        double velocity = 30;
        double jump_height = 30;
        double jumpVelocity = 4;
        double floorHeight = 370;
        double lvlWidth = 600;
        double x = 30;

        Player player = new Player(height,velocity,jump_height,jumpVelocity);
        double y = floorHeight-player.getHeight();
        player.setFloor(floorHeight);
        player.setInitialPos(x,y);
        player.setLevelWidth(lvlWidth);
        player.moveLeft();

        int idx = 60;

        for(int i = 0; i < idx; i ++){
            player.move();
        }

        assertEquals("Wrong player x position", 0, player.getXPos(),2);

        player.moveRight();

        idx = 100;
        for(int i = 0; i < idx; i++){
            player.move();
        }

        assertEquals("Wrong x position", velocity*0.017*idx,player.getXPos(),4);
        assertEquals("Wrong y position", floorHeight-height, player.getYPos(),4);

    }

    /**
     *
     * Check jump
     */
    @Test
    public void testJumpMove(){
        double height = 40;
        double velocity = 30;
        double jump_height = 30;
        double jumpVelocity = 4;
        double floorHeight = 370;
        double lvlWidth = 600;
        double x = 30;
        double y = floorHeight-height;
        Player player = new Player(height,velocity,jump_height,jumpVelocity);
        player.setInitialPos(x,y);
        player.setLevelWidth(lvlWidth);
        player.setFloor(floorHeight);

        player.moveRight();
        player.setJump(true);
        player.setFall(false);

        int idx = 10;
        for(int i = 0; i < idx;i++){
            player.move();
        }
        //Remember, we set to velocity*0.01 instead of 0.017 when the player is in the air
        assertEquals("Wrong x position",x+velocity*0.01*idx,player.getXPos(),2);
        assertEquals("Wrong y position", y-jumpVelocity*idx*0.017,player.getYPos(),2);


    }

    /**
     * Check fall
     */
    @Test
    public void playerFall(){
        double height = 40;
        double velocity = 30;
        double jump_height = 30;
        double jumpVelocity = 4;
        double floorHeight = 370;
        double lvlWidth = 600;
        double x = 30;
        double y = floorHeight-50;
        Player player = new Player(height,velocity,jump_height,jumpVelocity);
        player.setInitialPos(x,y);
        player.setLevelWidth(lvlWidth);
        player.setFloor(floorHeight);

        player.setFall(true);

        int idx = 120;
        for(int i = 0; i < idx; i++){
            player.move();
        }

        assertEquals("Wrong y position", floorHeight-height, player.getYPos(),4);

    }

    /**
     * move to the edge of level
     */
    @Test
    public void testPlayerMoveEdge(){
        double height = 40;
        double velocity = 30;
        double jump_height = 30;
        double jumpVelocity = 4;
        double floorHeight = 370;
        double lvlWidth = 600;
        double x = lvlWidth-60;
        double y = floorHeight-height;
        Player player = new Player(height,velocity,jump_height,jumpVelocity);
        player.setInitialPos(x,y);
        player.setLevelWidth(lvlWidth);
        player.setFloor(floorHeight);

        player.moveRight();

        int idx = 150;
        for(int i = 0; i < 150; i++){
            player.move();
        }

        assertEquals("Wrong x position", lvlWidth-player.getWidth(), player.getXPos(),2);

    }


}