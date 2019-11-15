package stickman.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import stickman.Entity.*;
import stickman.Strategy.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.lang.Math;


public class FactoryLevel {


    private List<Level> listLevel;

    public FactoryLevel(String s){

        this.listLevel = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        JSONObject obj;
        JSONArray obj_array = null;

        try(FileReader reader = new FileReader(s)){
            obj = (JSONObject) jsonParser.parse(reader);
            obj_array = (JSONArray) obj.get("level");


        }catch(IOException | ParseException e){
            System.out.println("Error related to the file or argument");
            e.printStackTrace();

        }

        if(obj_array != null){
            for(Object object: obj_array){
                Level temp = this.addLevel((JSONObject) object);
                listLevel.add(temp);
            }
        }
    }

    private Level addLevel(JSONObject obj){

        JSONObject player_obj = (JSONObject) obj.get("player");

        Player player = this.createPlayer(player_obj);


        return this.createLevel((JSONObject) obj.get("field"), player);
    }

    /**
     * Create level
     * @param obj read the specs from json
     * @param player update values of player
     * @return return the full level
     */
    private Level createLevel(JSONObject obj, Player player){

        int enemy = Math.abs(Integer.parseInt(obj.get("enemy").toString()));
        double levelHeight = Math.abs(Double.parseDouble(obj.get("levelHeight").toString()));
        double levelWidth = Math.abs(Double.parseDouble(obj.get("levelWidth").toString()));
        double floorHeight = levelHeight * 0.15;
        double finish = this.checkFinish(Double.parseDouble(obj.get("finish").toString()), levelWidth);
        double start = this.checkStart(Double.parseDouble(obj.get("start").toString()),levelWidth);
        int wall = Math.abs(Integer.parseInt(obj.get("wall").toString()));
        int cloudNum = Integer.parseInt(obj.get("numCloud").toString());
        double cloudVel = Double.parseDouble(obj.get("cloudVelocity").toString());



        this.setUpPlayer(player,start,levelHeight,floorHeight,levelWidth);
        Level lvl = new LevelImpl(floorHeight,levelHeight,levelWidth,start, finish, player);
        this.helpLevel(lvl, player,cloudNum,cloudVel, finish,wall, enemy);

        return lvl;
    }

    /**
     * Helper function for createLevel()
     * @param lvl lvl
     * @param player player
     * @param cloudNum number of cloud
     * @param cloudVel velocity of clouds
     * @param finish finish line
     * @param wall wall strategy
     * @param enemy number of enemy
     */
    private void helpLevel( Level lvl,Player player ,int cloudNum, double cloudVel, double finish, int wall, int enemy){
        double levelHeight = lvl.getHeight();
        double floorHeight = lvl.getFloorHeight();

        this.addWall(lvl, player, finish, wall);
        this.addCloud(lvl, cloudNum, cloudVel);
        this.setLevelLandscape(lvl);
        this.addEnemy(lvl, enemy, player, finish, player.getXPos());
        this.addFinishLine(player.getHeight() * 2.5, finish, levelHeight-floorHeight-player.getHeight() * 2.5, lvl);
    }


    /**
     * Helper function for createLevel()
     * @param player player
     * @param start start line
     * @param levelHeight levelHeight
     * @param floorHeight floorHeight;
     * @param levelWidth level width
     */
    private void setUpPlayer(Player player, double start, double levelHeight, double floorHeight, double levelWidth){
        player.setInitialPos(start, levelHeight-floorHeight-player.getHeight());
        player.setFloor(levelHeight-floorHeight);
        player.setLevelWidth(levelWidth);
    }

    /**
     * Safety check for start line
     * @param start read start line
     * @param width level width for comparison
     * @return return the right start position
     */
    private double checkStart(double start, double width){
        if(Math.abs(start) <= width * 0.5){
            return Math.abs(start);
        }
        else{
            return 50;
        }
    }

    /**
     * Safety check for finish line
     * @param finish read finish line
     * @param width level width for comparison
     * @return correct finish line
     */
    private double checkFinish(double finish, double width){
        if(Math.abs(finish) <= width && Math.abs(finish) > width/2){
            return Math.abs(finish);
        }
        else{
            return width -100;
        }
    }

    /**
     * Add wall based on certain strategy (right now we have only one way)
     * @param lvl put them into this level obj
     * @param player get the player height and jump height for adjustment
     * @param finish get the finish line for adjustment
     * @param stategy strategy type...we have one right now
     */
    private void addWall(Level lvl, Player player, double finish, int stategy){
        if(stategy == 0){
            WallStrategy wallStrategy = new WallBasic();
            wallStrategy.addWall(lvl,player,finish);
        }

    }


    /**
     * Create clouds
     * the x position is random between levelWidth and 10
     * y position random between levelHeight *0.05 and levelHeight *0.3
     * @param lvl add the object to this level
     * @param num number of clouds created
     */
    private void addCloud(Level lvl, int num, double velocity){

        double levelHeight = lvl.getHeight();
        double levelWidth = lvl.getWidth();


        for(int i = 0; i < Math.abs(num); i++){

            double x_random = Math.random() * (levelWidth-10)+10;
            double y_random = Math.random() * (levelHeight*0.30- levelHeight * 0.05)+ levelHeight * 0.05;

            double x = Math.round(x_random * 100.00) / 100.00;
            double y = Math.round(y_random * 100.00) / 100.00;

            double height = Math.random() * (70-40) + 40;
            height = Math.round(height * 100.00) / 100.00;

            Cloud cloud = new Cloud(x,y,height,velocity,levelWidth);

            lvl.getEntities().add(cloud);
        }
    }

    /**
     * Create player based on the specs
     * Note that the base size is always tiny with size of 40
     * @param obj read specs from this json object
     * @return return the resulting player
     */
    private Player createPlayer(JSONObject obj){

        double playerHeight;

        String stickmanSize = (String) obj.get("stickmanSize");

        switch (stickmanSize.toLowerCase()){
            case "normal":
                playerHeight = 60;
                break;
            case "large":
                playerHeight = 90;
                break;
            case "giant":
                playerHeight = 120;
                break;
            default:
                playerHeight = 40;

        }


        double jumpVelocity = this.checkJumpVelocity(Double.parseDouble(obj.get("jumpVelocity").toString()));
        double velocity = this.checkVelocity((Double.parseDouble(obj.get("stickmanVelocity").toString())));
        double jump = this.checkJump(Double.parseDouble(obj.get("stickmanJump").toString()));
        int life = this.checkLife(Integer.parseInt(obj.get("life").toString()));


        Player player = new Player(playerHeight,velocity,jump, jumpVelocity);
        player.setLife(life);

        return player;
    }

    /**
     * Safety check for jump velocity
     * @param vel the read jump vel
     * @return return the correct value
     */
    private double checkJumpVelocity(double vel){
        if (vel == 0){
            return 10;
        }
        else{
            return Math.abs(vel);
        }
    }

    /**
     * Safety check for life
     * @param life read life
     * @return correct life
     */
    private int checkLife(int life){
        if(life == 0){
            return 5;
        }
        else{
            return Math.abs(life);
        }
    }

    /**
     * Safety check for jump height
     * @param jump read jump height
     * @return correct jump height
     */
    private double checkJump(double jump){
        if(jump == 0){
            return 30;
        }
        else{
            return Math.abs(jump);
        }
    }

    /**
     * safety check for velocity
     * @param velocity read velocity
     * @return correct velocity
     */
    private double checkVelocity(double velocity){
        if(velocity == 0){
            return 10;
        }
        else{
            return Math.abs(velocity);
        }
    }

    /**
     * Add a finish line to the Entity list
     * @param height height of flag
     * @param x x position
     * @param y y position
     * @param lvl put it into this level object
     */
    private void  addFinishLine(double height, double x, double y, Level lvl){
        Entity finish = new FinishLine(height,x,y);
        lvl.getEntities().add(finish);
    }

    /**
     * Add new enemies: the type is random as well as the x and y position.
     * @param lvl put them into this level object
     * @param enemyNum number of enemies
     * @param player get the player height
     * @param finish finish line to adjust x position
     * @param start start line to adjust x position
     */
    private void addEnemy(Level lvl, int enemyNum, Player player, double finish, double start){

        for(int i = 0; i < enemyNum; i++){
            int randomNumber = (int) Math.round(Math.random() * (5-1) +1);
            double x = Math.random() * ((finish-100) - (start+ player.getWidth() +150))+ (start+ player.getWidth() +150);
            double y = Math.random() * ((lvl.getHeight()-lvl.getFloorHeight() -100)- 10)+10;

            if(randomNumber == 1){
                EnemyStrat strat = new BlueEnemy(lvl.getHeight()-lvl.getFloorHeight(), lvl.getWidth());
                Enemy enemy = this.createEnemy(player.getHeight(), 1,"./src/main/resources/slimeBa.png", strat,x,y);
                lvl.getEnemyList().add(enemy);
                lvl.getEntities().add(enemy);

            }
            else if(randomNumber == 2){

                EnemyStrat strat = new GreenEnemy(lvl.getHeight()-lvl.getFloorHeight(), lvl.getWidth());
                Enemy enemy = this.createEnemy(player.getHeight()/2, 1,"./src/main/resources/slimeGa.png", strat,x,y);
                lvl.getEnemyList().add(enemy);
                lvl.getEntities().add(enemy);


            }
            else if(randomNumber == 3){

                EnemyStrat strat = new PurpleEnemy(lvl.getHeight()-lvl.getFloorHeight());
                Enemy enemy = this.createEnemy(player.getHeight()*0.75, 1,"./src/main/resources/slimePa.png", strat,x,y);
                lvl.getEnemyList().add(enemy);
                lvl.getEntities().add(enemy);

            }
            else if(randomNumber == 4){
                y = lvl.getHeight()-lvl.getFloorHeight()-player.getJumphHeight()+ 30;
                EnemyStrat strat = new RedEnemy(lvl.getWidth());
                Enemy enemy = this.createEnemy(30, 2,"./src/main/resources/slimeRa.png", strat,x,y);
                lvl.getEnemyList().add(enemy);
                lvl.getEntities().add(enemy);
            }
            else{

                EnemyStrat strat = new YellowEnemy(lvl.getHeight()-lvl.getFloorHeight());
                Enemy enemy = this.createEnemy(player.getHeight()*1.5, 3,"./src/main/resources/slimeYa.png", strat,x,y);
                lvl.getEnemyList().add(enemy);
                lvl.getEntities().add(enemy);
            }
        }
    }

    private Enemy createEnemy(double height, int life, String path, EnemyStrat strat, double x, double y){
        Enemy enemy = new Enemy(height, life, path);
        enemy.createStrategy(strat);
        enemy.setInitialPos(x,y);
        strat.addEnemy(enemy);

        return enemy;
    }

    /**
     *
     * @return returns level list
     */
    public List<Level> getLevels(){
        return this.listLevel;

    }

    /**
     * Set up background landscape based on a random number
     * @param lvl add the new background to this lvl
     */
    private void setLevelLandscape(Level lvl){
        int random_number = (int) Math.round(Math.random() * (6-1) +1);

        if(random_number == 1){
            lvl.setLandscapeImage("landscape_0000_1_trees.png");
        }
        else if(random_number == 2){
            lvl.setLandscapeImage("landscape_0001_2_trees.png");
        }
        else if(random_number == 3){
            lvl.setLandscapeImage("landscape_0002_3_trees.png");
        }
        else if(random_number == 4){
            lvl.setLandscapeImage("landscape_0003_4_mountain.png");
        }
        else if(random_number == 5){
            lvl.setLandscapeImage("landscape_0004_5_clouds.png");
        }
        else{
            lvl.setLandscapeImage("landscape_0005_6_background.png");
        }
    }
}
