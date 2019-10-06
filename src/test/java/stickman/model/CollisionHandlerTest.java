package stickman.model;


import de.saxsys.javafx.test.JfxRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import stickman.Entity.Enemy;
import stickman.Entity.Entity;
import stickman.Entity.Wall;
import stickman.Strategy.YellowEnemy;
import stickman.model.Player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class CollisionHandlerTest {


    private double startLine;
    private Player player;
    private Level lvl;

    @Before
    public void start(){
        startLine = 10;
        player = new Player(50,3,50,4);
        player.setFloorHeight(400-30);
        lvl = new LevelImpl(30,400,600,13,550,player);
        player.setLevelWidth(600);



    }



    @Test
    public void collisionWall(){
        player.setInitialPos(40,230);

        Wall wall = new Wall(40, 300);
        lvl.getEntities().add(wall);

        for(double i = 0; i < 21;i+=4*0.017){
            lvl.tick();
        }

        assertEquals("land on wall is wrong (x)!", wall.getXPos(), player.getXPos(),2);
        assertEquals("land on wall is wrong (y)!", wall.getYPos() -player.getHeight(),player.getYPos(), 2);
        assertFalse("Wrong falling state!",player.getFall());
        assertFalse("Wrong jumping state!",player.getJump());

        lvl.getEntities().remove(wall);

    }

    @Test
    public void collisionWall2(){
        player.setInitialPos(62,320);
        player.setJump(false);
        player.setFall(false);
        player.setCounter(0);

        Wall wall = new Wall(60,280);
        lvl.getEntities().add(wall);

        double pos = 320;
        boolean flag = false;
        for(double i = 0; i < 14; i+=4*0.017){
            lvl.jump();
            lvl.tick();

        }
        double floor = lvl.getHeight()-lvl.getFloorHeight();
        double playerH = player.getYPos()+player.getHeight();
        assertTrue("Wrong falling state!",player.getFall());
        assertTrue("Wrong y position for player!" ,310 < player.getYPos() && playerH < floor);
        lvl.getEntities().remove(wall);
    }

    @Test
    public void collisionWall3(){
        Wall wall = new Wall(50, 280);
        player.setInitialPos(50-player.getWidth(), 305);
        player.setCounter(1);
        player.setRight(true);
        player.setJump(true);
        player.setFall(false);

        lvl.getEntities().add(wall);

        for(int i= 0; i < 20;i++){
            lvl.tick();
        }

        assertEquals("Wrong x position!", 50-player.getWidth(), player.getXPos(),2);
        assertTrue("Wrong jumping state!",player.getJump());
        assertTrue("Wrong right moving state",player.getRight());
        assertEquals("Wrong y position!", 305 -20*4*0.017,player.getYPos(),2);

        player.setInitialPos(50+30, 305);
        player.setCounter(1);
        player.setRight(false);
        player.setLeft(true);
        player.setJump(true);
        player.setFall(false);

        for(int i= 0; i < 20;i++){
            lvl.tick();
        }

        assertEquals("Wrong x position!", 50+30, player.getXPos(),2);
        assertTrue("Wrong jumping state!",player.getJump());
        assertTrue("Wrong right moving state",player.getLeft());
        assertEquals("Wrong y position!", 305 -20*4*0.017,player.getYPos(),2);

        lvl.getEntities().remove(wall);
    }

    @Test
    public void enemyDies(){
        Enemy enemy = new Enemy(40,1, "./src/main/resources/slimeBa.png");
        enemy.createStrategy(new YellowEnemy(400-30,500,enemy));
        enemy.setInitialPos(50, 370-40);
        lvl.getEntities().add(enemy);
        lvl.getEnemyList().add(enemy);


        player.setInitialPos(60, 270);
        player.stopMoving();
        for(double i = 0; i < 11; i+=4*0.017){
            lvl.tick();
        }

        assertEquals("not empty list!", 0, lvl.getEnemyList().size(),2);
        assertEquals("no the same size of entity list", 1, lvl.getEntities().size());
        assertTrue("wrong jumping state!", player.getJump());


    }


    @Test
    public void playerLoseLife(){

        Enemy enemy = new Enemy(40,2,"./src/main/resources/slimeYa.png");
        enemy.createStrategy(new YellowEnemy(370,lvl.getWidth(),enemy));
        enemy.setInitialPos(100,lvl.getHeight()-lvl.getFloorHeight()-40);
        lvl.getEntities().add(enemy);
        lvl.getEnemyList().add(enemy);


        player.setInitialPos(100-player.getWidth()-10,lvl.getHeight()-lvl.getFloorHeight()-50);

        player.setLife(5);
        lvl.moveRight();
        for(double i = 0; i < 10; i+=3*0.017){
            lvl.tick();
        }


        assertEquals("wrong x coordinate!",13,lvl.getHeroX(),2);
        assertEquals("Should have lost life!", 4, player.getLife());

        player.setInitialPos(enemy.getWidth()+100+10, player.getYPos());
        lvl.moveLeft();
        for(double i = 0; i < 10; i+=3*0.017){
            lvl.tick();
        }

        assertEquals("wrong x coordinate!",13,lvl.getHeroX(),2);
        assertEquals("Should have lost life!", 3, player.getLife());

    }




}