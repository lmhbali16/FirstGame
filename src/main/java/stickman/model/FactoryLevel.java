package stickman.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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

            Iterator<JSONObject> iter = obj_array.iterator();
            while(iter.hasNext()){
                Level temp = this.addLevel(iter.next());
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

        double x_player = 0;
        double y_player = 0;

        levelHeight =Math.abs(((Long) obj.get("levelHeight")).doubleValue());

        levelWidth = Math.abs(((Long) obj.get("levelWidth")).doubleValue());

        floorHeight = levelHeight * 0.25;

        x_player = levelWidth * 0.01;
        y_player = levelHeight - floorHeight * 0.75;

        player.setInitialPos(x_player,y_player);

        Level lvl = new LevelImpl(floorHeight,levelHeight,levelWidth,player);

        this.addCloud(lvl, ((Long) obj.get("numCloud")).intValue(), (double) obj.get("cloudVelocity"));

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

            x_random = Math.random() * (1-0.075)+0.075;
            y_random = Math.random() * (0.5-0.05)+0.05;

            height = Math.random() * (levelHeight * 0.33 - levelHeight * 0.05) + levelHeight * 0.05;
            height = Math.round(height * 100.00) / 100.00;

            width = Math.random() * (levelWidth * 0.4 - levelWidth * 0.2) + levelWidth * 0.2;

            x = levelWidth * Math.round(x_random * 100.00) / 100.00;

            y = levelHeight * Math.round(y_random * 100.00) / 100.00;

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

        return new Player(playerHeight,playerWidth,velocity,jump);

    }

    public List<Level> getLevels(){
        return this.list_level;

    }
}
