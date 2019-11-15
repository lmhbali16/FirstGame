package stickman.model;


import de.saxsys.javafx.test.JfxRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import stickman.Entity.Enemy;
import stickman.Entity.Player;
import stickman.Entity.Wall;
import stickman.Strategy.EnemyStrat;
import stickman.Strategy.YellowEnemy;


import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class CollisionHandlerTest {



    private Player player;
    private Level lvl;

    private double playerHeight;
    private double playerVelocity;
    private double playerJumpHeight;
    private double jumpVelocity;
    private double levelHeight;
    private double floorHeight;
    private double levelWidth;
    private double start;
    private double finish;

    /**
     * set up
     */
    @Before
    public void start(){
        start = 10;
        playerHeight = 50;
        playerVelocity = 3;
        playerJumpHeight = 50;
        jumpVelocity = 10;
        floorHeight = 30;
        levelHeight = 400;
        levelWidth = 600;
        finish = 550;
        player = new Player(playerHeight,playerVelocity,playerJumpHeight,jumpVelocity);
        player.setFloor(levelHeight-floorHeight);
        lvl = new LevelImpl(floorHeight,levelHeight,levelWidth,start,finish,player);
        player.setLevelWidth(levelWidth);



    }


    /**
     * land on wall
     */
    @Test
    public void collisionWall(){

        double x = 40;
        double yPlayer = 230;
        double yWall = 300;
        player.setInitialPos(x,yPlayer);

        Wall wall = new Wall(x, yWall);
        lvl.getEntities().add(wall);

        double idx = 20 / (jumpVelocity*0.017);
        for(double i = 0; i < idx+1;i++){
            lvl.tick();
        }

        assertEquals("land on wall is wrong (x)!", wall.getXPos(), player.getXPos(),2);
        assertEquals("land on wall is wrong (y)!", wall.getYPos() -playerHeight,player.getYPos(), 2);
        assertFalse("Wrong falling state!",player.getFall());
        assertFalse("Wrong jumping state!",player.getJump());

        lvl.getEntities().remove(wall);

    }

    /**
     * Pump into wall from bottom
     */
    @Test
    public void collisionWall2(){
        int gap = 10;
        double x = 60;

        double yWall = levelHeight-floorHeight-playerHeight-30-gap;//- 30 is the wall height and 10 is a gap
        double yPlayer = levelHeight-floorHeight-playerHeight;
        Wall wall = new Wall(x, yWall);
        double floor = levelHeight-floorHeight;

        lvl.getEntities().add(wall);

        player.setInitialPos(x, yPlayer);
        player.setJump(true);

        double idx = gap / (jumpVelocity*0.017);
        for(int i = 0; i < idx; i++){
            lvl.tick();
        }


        double playerH = player.getHeight()+player.getYPos();
        assertTrue("Wrong falling state! ",player.getFall());
        assertTrue("Wrong y position for player!" ,yWall-wall.getHeight() < player.getYPos() && playerH < floor);
        lvl.getEntities().remove(wall);
    }


    /**
     * Check if enemy dies
     */
    @Test
    public void enemyDies(){

        double enemyHeight = 40;
        int enemyLife = 1;
        double enemyX = 50;
        double enemyY = levelHeight-floorHeight-enemyHeight;
        double playerX = 60;
        double playerY = enemyY-playerHeight-5;

        Enemy enemy = new Enemy(enemyHeight,enemyLife, "./src/main/resources/slimeBa.png");
        EnemyStrat strat = new YellowEnemy(levelHeight-floorHeight);
        strat.addEnemy(enemy);
        enemy.createStrategy(strat);
        enemy.setInitialPos(enemyX, enemyY);
        lvl.getEntities().add(enemy);
        lvl.getEnemyList().add(enemy);


        player.setInitialPos(playerX, playerY);
        player.stopMoving();
        int idx = 11;
        for(double i = 0; i < idx; i+=4*0.017){
            lvl.tick();
        }

        assertEquals("not empty list!", 0, lvl.getEnemyList().size(),2);
        assertEquals("no the same size of entity list", 1, lvl.getEntities().size());
        assertTrue("wrong jumping state or falling state!", player.getJump() || player.getFall());


    }

    /**
     * Check if player dies
     */
    @Test
    public void playerLoseLife(){
        int gap = 10;
        double enemyHeight = 49;
        int enemyLife = 2;
        double enemyX = 100;
        double enemyY = levelHeight-floorHeight-enemyHeight;
        double playerX = enemyX-player.getWidth()-gap;
        double playerY = lvl.getHeight()-lvl.getFloorHeight()-playerHeight;
        int playerLife = 5;

        Enemy enemy = new Enemy(enemyHeight,enemyLife,"./src/main/resources/slimeYa.png");


        enemy.setInitialPos(enemyX,enemyY);
        lvl.getEntities().add(enemy);
        lvl.getEnemyList().add(enemy);


        player.setInitialPos(playerX,playerY);
        player.setLife(playerLife);
        lvl.moveRight();

        double idx = gap / (playerVelocity*0.017);
        for(int i = 0; i < idx+3; i++){
            lvl.tick();
        }

        assertEquals("Should have lost life!", playerLife-1, player.getLife());
        assertEquals("wrong x coordinate!"+playerX,start,lvl.getHeroX(),2);

        playerLife--;

        playerX = enemyX+enemy.getWidth()+10;

        player.setInitialPos(playerX, player.getYPos());
        lvl.moveLeft();

        idx = 10;
        for(double i = 0; i < idx; i+=3*0.017){
            lvl.tick();
        }

        assertEquals("wrong x coordinate!",start,lvl.getHeroX(),2);
        assertEquals("Should have lost life!", playerLife-1, player.getLife());

    }




}