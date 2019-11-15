package stickman.model;



import de.saxsys.javafx.test.JfxRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import stickman.Entity.Player;


import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class LevelImplTest {
    private Level lvl;
    private Player player;
    private double floorHeight;
    private double levelHeight;
    private double levelWidth;
    private double startLine;
    private double finishLine;
    private double height;
    private double velocity;
    private double jumpHeight;
    private double jumpVelocity;
    private double start;
    private double startY;


    @Before
    public void createLevel(){

        floorHeight = 30;
        levelHeight = 500;
        levelWidth = 400;
        startLine = 10;
        finishLine = 350;
        height = 50;
        velocity = 3.5;
        jumpHeight = 30;
        jumpVelocity = 4.3;
        start = 10;
        startY = levelHeight-floorHeight-height;


        player = new Player(height, velocity, jumpHeight, jumpVelocity);
        player.setInitialPos(start,startY);

        lvl = new LevelImpl(floorHeight, levelHeight, levelWidth, startLine, finishLine, player);

    }

    /**
     * Move player right and left
     */
    @Test
    public void testMovePlayer(){
        double jumpHeightCheck = startY;
        for(int i = 0; i < 5; i++){
            lvl.jump();
            if(jumpHeightCheck < 30){
                jumpHeightCheck -= 4.3 *0.017;
            }
            else{
                jumpHeightCheck += 4.3 *0.017;
            }
        }

        assertEquals("wrong y position jump!", jumpHeightCheck,player.getYPos(),2);


        player.setInitialPos(start,startY);
        lvl.moveRight();

        int idx = 10;
        for(int i = 0; i < idx;i++){
            lvl.tick();
        }

        assertEquals("Wrong x position, move right!", start+velocity*0.017*idx, lvl.getHeroX(),2);

        int x = 30;
        player.setInitialPos(x,500-30-50);
        lvl.moveLeft();

        idx = 30;
        for(int i = 0; i < idx;i++){
            lvl.tick();
        }
        assertEquals("Wrong x position, move left!", x-velocity*0.017*idx, lvl.getHeroX(),2);


    }




}
