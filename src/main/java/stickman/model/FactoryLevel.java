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

    private JSONObject obj;
    private List<Level> list_level;

    public FactoryLevel(String s){

        this.list_level = new ArrayList<>();
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


            for(int i = 0; i < obj_array.size();i++){
                Level temp = this.addLevel((JSONObject) obj_array.get(i));
                if(temp != null){
                    list_level.add(temp);
                }

            }

        }

    }

    public Level addLevel(JSONObject obj){


        JSONObject player_obj = (JSONObject) obj.get("player");

        Player player = this.createPlayer(player_obj);

        Level lvl = this.createLevel((JSONObject) obj.get("field"), player);

        return lvl;
    }

    public Level createLevel(JSONObject obj, Player player){

        double floorHeight = 0;
        double levelHeight = 0;
        double levelWidth = 0;
        double finish = 0;
        double start = 0;
        int enemy = 0;
        int wall = 0;


        enemy = Math.abs(Integer.parseInt(obj.get("enemy").toString()));
        levelHeight = Math.abs(Double.parseDouble(obj.get("levelHeight").toString()));
        levelWidth = Math.abs(Double.parseDouble(obj.get("levelWidth").toString()));
        floorHeight = levelHeight * 0.15;
        finish = this.checkFinish(Double.parseDouble(obj.get("finish").toString()), levelWidth);
        start = this.checkStart(Double.parseDouble(obj.get("start").toString()),levelWidth);
        wall = Math.abs(Integer.parseInt(obj.get("wall").toString()));

        player.setInitialPos(start, levelHeight-floorHeight-player.getHeight());
        player.setFloorHeight(levelHeight-floorHeight);
        player.setLevelWidth(levelWidth);

        Level lvl = new LevelImpl(floorHeight,levelHeight,levelWidth,start, finish, player);

        this.addCloud(lvl, Integer.parseInt(obj.get("numCloud").toString()), Double.parseDouble(obj.get("cloudVelocity").toString()));
        this.setLevelLandscape(lvl);
        this.addFinishLine(player.getHeight() * 2.5, finish, levelHeight-floorHeight-player.getHeight() * 2.5, lvl);
        this.addEnemy(lvl, enemy, player, finish, start);
        this.addWall(lvl, player, finish, wall);

        return lvl;
    }

    public double checkStart(double start, double width){
        if(Math.abs(start) <= width * 0.5){
            return Math.abs(start);
        }
        else{
            return 50;
        }
    }

    public double checkFinish(double finish, double width){
        if(Math.abs(finish) <= width && Math.abs(finish) > width/2){
            return Math.abs(finish);
        }
        else{
            return width -100;
        }
    }

    public void addWall(Level lvl, Player player, double finish, int stategy){
        if(stategy == 0){
            WallStrategy wallStrategy = new WallBasic();
            wallStrategy.addWall(lvl,player,finish);
        }

    }



    public void addCloud(Level lvl, int num, double v){

        double levelHeight = lvl.getHeight();
        double levelWidth = lvl.getWidth();

        Cloud cloud = null;

        double x = 0;
        double y = 0;
        double height = 0;

        double velocity = v;

        double x_random = 0;
        double y_random = 0;

        for(int i = 0; i < num; i++){

            x_random = Math.random() * (levelWidth-0.075)+0.075;
            y_random = Math.random() * (levelHeight*0.30- levelHeight * 0.05)+ levelHeight * 0.05;

            x = Math.round(x_random * 100.00) / 100.00;
            y = Math.round(y_random * 100.00) / 100.00;

            height = Math.random() * (70-40) + 40;
            height = Math.round(height * 100.00) / 100.00;





            cloud = new Cloud(x,y,height,velocity,levelWidth);

            lvl.getEntities().add(cloud);
        }
    }

    public Player createPlayer(JSONObject obj){

        String stickmanSize = "tiny";
        double playerHeight = 40;

        double velocity = 4;
        double jump = 40;

        stickmanSize = (String) obj.get("stickmanSize");

        if(stickmanSize.toLowerCase().equals("normal")){
            playerHeight = 60;

        }
        else if(stickmanSize.toLowerCase().equals("large")){
            playerHeight = 90;

        }
        else if(stickmanSize.toLowerCase().equals("giant")){
            playerHeight = 120;

        }

        double jumpVelocity = Math.abs(Double.parseDouble(obj.get("jumpVelocity").toString()));
        velocity = Math.abs((Double.parseDouble(obj.get("stickmanVelocity").toString())));

        jump = Math.abs(Double.parseDouble(obj.get("stickmanJump").toString()));

        int life = Math.abs(Integer.parseInt(obj.get("life").toString()));

        Player player = new Player(playerHeight,velocity,jump, jumpVelocity);
        player.setLife(life);

        return player;

    }

    public void  addFinishLine(double height, double x, double y, Level lvl){
        Entity finish = new FinishLine(height,x,y);
        lvl.getEntities().add(finish);
    }

    public void addEnemy(Level lvl, int enemy, Player player, double finish, double start){

        for(int i = 0; i < enemy; i++){
            int randomNumber = (int) Math.round(Math.random() * (5-1) +1);
            double x = Math.round(Math.random() * ((finish-100) - (start+ player.getWidth() +150)) * 100.00)/100.00;
            double y = Math.round(Math.random() * (lvl.getHeight() - (player.getYPos()+player.getHeight() -10)) * 100.00)/100.00;

            if(randomNumber == 1){
                double height = player.getHeight();

                int life = 1;
                String path = "./src/main/resources/slimeBa.png";

                Enemy enemyObj = new Enemy(height,life,path);
                EnemyStrat strat = new BlueEnemy(lvl.getHeight()-lvl.getFloorHeight(), lvl.getWidth() ,enemyObj);
                enemyObj.createStrategy(strat);
                enemyObj.setInitialPos(x,y);
                lvl.getEnemyList().add(enemyObj);
                lvl.getEntities().add(enemyObj);

            }
            else if(randomNumber == 2){
                double height = player.getHeight()/2;

                int life = 2;
                String path = "./src/main/resources/slimeGa.png";

                Enemy enemyObj = new Enemy(height,life,path);
                EnemyStrat strat = new GreenEnemy(lvl.getHeight()-lvl.getFloorHeight(), lvl.getWidth() ,enemyObj);
                enemyObj.createStrategy(strat);
                enemyObj.setInitialPos(x,y);
                lvl.getEnemyList().add(enemyObj);
                lvl.getEntities().add(enemyObj);

            }
            else if(randomNumber == 3){
                double height = player.getHeight()*0.75;

                int life = 2;
                String path = "./src/main/resources/slimePa.png";

                Enemy enemyObj = new Enemy(height,life,path);
                EnemyStrat strat = new PurpleEnemy(lvl.getHeight()-lvl.getFloorHeight(), lvl.getWidth() ,enemyObj);
                enemyObj.createStrategy(strat);
                enemyObj.setInitialPos(x,y);
                lvl.getEnemyList().add(enemyObj);
                lvl.getEntities().add(enemyObj);

            }
            else if(randomNumber == 4){
                double height = 30;

                int life = 2;
                String path = "./src/main/resources/slimeRa.png";

                Enemy enemyObj = new Enemy(height,life,path);
                EnemyStrat strat = new RedEnemy(lvl.getHeight()-lvl.getFloorHeight(), lvl.getWidth() ,enemyObj);
                enemyObj.createStrategy(strat);
                enemyObj.setInitialPos(x,lvl.getHeight()-lvl.getFloorHeight()-player.getJumphHeight()+ height);
                lvl.getEnemyList().add(enemyObj);
                lvl.getEntities().add(enemyObj);
            }
            else{
                double height = player.getHeight()*1.5;

                int life = 15;
                String path = "./src/main/resources/slimeYa.png";

                Enemy enemyObj = new Enemy(height,life,path);
                EnemyStrat strat = new YellowEnemy(lvl.getHeight()-lvl.getFloorHeight(), lvl.getWidth() ,enemyObj);
                enemyObj.createStrategy(strat);
                enemyObj.setInitialPos(x ,y);
                lvl.getEnemyList().add(enemyObj);
                lvl.getEntities().add(enemyObj);
            }
        }
    }

    public List<Level> getLevels(){
        return this.list_level;

    }

    public void setLevelLandscape(Level lvl){
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
