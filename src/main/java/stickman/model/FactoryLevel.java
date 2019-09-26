package stickman.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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



        levelHeight =Math.abs(((Long) obj.get("levelHeight")).doubleValue());

        levelWidth = Math.abs(((Long) obj.get("levelWidth")).doubleValue());

        floorHeight = levelHeight * 0.15;

        finish = Math.abs(((Long) obj.get("finish")).doubleValue());
        start = Math.abs(((Long) obj.get("start")).doubleValue());

        player.setInitialPos(start, levelHeight-floorHeight-player.getHeight());


        Level lvl = new LevelImpl(floorHeight,levelHeight,levelWidth,start, finish, player);

        this.addCloud(lvl, ((Long) obj.get("numCloud")).intValue(), (double) obj.get("cloudVelocity"));

        this.setLevelLandscape(lvl);



        this.addFinishLine(player.getHeight() * 2.5, finish, levelHeight-floorHeight-player.getHeight() * 2.5, lvl);

        return lvl;
    }

    public void addCloud(Level lvl, int num, double v){

        double levelHeight = lvl.getHeight();
        double floorHeight = lvl.getFloorHeight();
        double levelWidth = lvl.getWidth();

        Cloud cloud = null;

        double x = 0;
        double y = 0;
        double height = 0;
        double width = 0;
        double velocity = v;

        double x_random = 0;
        double y_random = 0;

        for(int i = 0; i < num; i++){

            x_random = Math.random() * (levelWidth-0.075)+0.075;
            y_random = Math.random() * (levelHeight*0.30- levelHeight * 0.05)+ levelHeight * 0.05;

            x = Math.round(x_random * 100.00) / 100.00;
            y = Math.round(y_random * 100.00) / 100.00;

            height = Math.random() * (150-50) + 50;
            height = Math.round(height * 100.00) / 100.00;

            width = Math.random() * (200 - 100) + 100;
            width = Math.round(width* 100.00) / 100.00;



            cloud = new Cloud(x,y,height,width,velocity,levelWidth);

            lvl.getEntities().add(cloud);
        }
    }

    public Player createPlayer(JSONObject obj){

        String stickmanSize = "tiny";
        double playerHeight = 40;
        double playerWidth = 30;
        double velocity = 4;
        double jump = 40;

        stickmanSize = (String) obj.get("stickmanSize");

        if(stickmanSize.toLowerCase().equals("normal")){
            playerHeight = 60;
            playerWidth = 35;
        }
        else if(stickmanSize.toLowerCase().equals("large")){
            playerHeight = 90;
            playerWidth = 65;
        }
        else if(stickmanSize.toLowerCase().equals("giant")){
            playerHeight = 120;
            playerWidth = 75;
        }


        velocity = Math.abs(((Long) obj.get("stickmanVelocity")).doubleValue());

        jump = Math.abs(((Long) obj.get("stickmanJump")).doubleValue());

        int life = Math.abs(((Long) obj.get("life")).intValue());

        Player player = new Player(playerHeight,playerWidth,velocity,jump);
        player.setLife(life);

        return player;

    }

    public void  addFinishLine(double height, double x, double y, Level lvl){
        Entity finish = new FinishLine(height,x,y);
        lvl.getEntities().add(finish);
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
