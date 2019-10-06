package stickman.model;



import de.saxsys.javafx.test.JfxRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class LevelImplTest {
    public Level lvl;
    public Player player;


    @Before
    public void createLevel(){

        double floorHeight = 30;
        double levelHeight = 500;
        double levelWidth = 400;
        double startLine = 10;
        double finishLine = 350;
        player = new Player(50, 3.5, 30, 4.3);
        player.setInitialPos(10,500-30-50);

        lvl = new LevelImpl(floorHeight, levelHeight, levelWidth, startLine, finishLine, player);

    }

    @Test
    public void testMovePlayer(){
        double jumpHeight = 500-30-50;
        for(int i = 0; i < 5; i++){
            lvl.jump();
            if(jumpHeight < 30){
                jumpHeight -= 4.3 *0.017;
            }
            else{
                jumpHeight += 4.3 *0.017;
            }
        }

        assertEquals("wrong y position jump!", jumpHeight,player.getYPos(),2);
        player.setInitialPos(10,500-30-50);
        lvl.moveRight();
        lvl.tick();

        assertEquals("Wrong x position, move right!", 10+3.5*0.017, lvl.getHeroX(),2);

        player.setInitialPos(30,500-30-50);
        lvl.moveLeft();
        lvl.tick();
        assertEquals("Wrong x position, move left!", 30-3.5*0.017, lvl.getHeroX(),2);


    }




}
