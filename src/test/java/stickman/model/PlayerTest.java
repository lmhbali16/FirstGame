package stickman.model;

import de.saxsys.javafx.test.JfxRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class PlayerTest {


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
        player.setFloorHeight(floorHeight);
        player.setInitialPos(x,y);
        player.setLevelWidth(lvlWidth);

        player.moveLeft();
        for(int i = 0; i < 60; i ++){
            player.move();
        }

        assertEquals("Wrong player x position", 0, player.getXPos(),2);

        player.moveRight();

        for(int i = 0; i < 100; i++){
            player.move();
        }

        assertEquals("Wrong x position", velocity*0.017*100,player.getXPos(),4);
        assertEquals("Wrong y position", floorHeight-height, player.getYPos(),4);

    }

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
        player.setFloorHeight(floorHeight);

        player.moveRight();
        player.setJump(true);
        player.setFall(false);

        for(int i = 0; i < 10;i++){
            player.move();
        }
        //Remember, we set to velocity*0.01 instead of 0.017 when the player is in the air
        assertEquals("Wrong x position",x+velocity*0.01*10,player.getXPos(),2);
        assertEquals("Wrong y position", y-jumpVelocity*10*0.017,player.getYPos(),2);


    }

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
        player.setFloorHeight(floorHeight);

        player.setFall(true);

        for(int i = 0; i < 120; i++){
            player.move();
        }

        assertEquals("Wrong y position", floorHeight-height, player.getYPos(),4);

    }

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
        player.setFloorHeight(floorHeight);

        player.moveRight();

        for(int i = 0; i < 150; i++){
            player.move();
        }

        assertEquals("Wrong x position", lvlWidth-player.getWidth(), player.getXPos(),2);

    }


}