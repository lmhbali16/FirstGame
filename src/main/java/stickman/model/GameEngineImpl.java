package stickman.model;

import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author SID:480133780
 */

public class GameEngineImpl implements GameEngine{

    private Level lvl;
    private double cloudVelocity;
    private double playerHeight = 40;
    private double playerWidth = 30;
    private double playerX;

    /**
     * Constructor that check the validity of the argument
     * @param s JSON file name. It is assumed that the file is in the resources folder
     */
    public GameEngineImpl(String s){
        s = "./src/main/resources/" + s;

        JSONParser jsonParser = new JSONParser();

        JSONObject obj = null;
        try(FileReader reader = new FileReader(s)){
            obj = (JSONObject) jsonParser.parse(reader);

        }catch(IOException | ParseException e){
            System.out.println("Error related to the file or argument");
            e.printStackTrace();

        }

        //loop through the JSON file
       for(Object name : obj.keySet()){

           String key = (String) name;
            //If it start with '_', we ignore it
           if(key.charAt(0) == '_'){
               continue;
           }
           //Check Stickman size
           if(key.toLowerCase().equals("stickmansize")){

               String size = (String) obj.get(key);

               if(size.toLowerCase().equals("tiny")){
                   this.playerHeight = 40;
                   this.playerWidth = 30;

                } else if(key.toLowerCase().equals("normal")){
                   this.playerHeight = 60;
                   this.playerWidth = 35;

                } else if(key.toLowerCase().equals("large")){
                   this.playerHeight = 90;
                   this.playerWidth = 65;

                } else if(key.toLowerCase().equals("giant")){
                   this.playerHeight = 120;
                   this.playerWidth = 75;

                } else{
                   //we use the base case above
               }
           }
           //stickman size in absolute value so we don't need to deal with negative
           else if(key.toLowerCase().equals("stickmanpos")){
               JSONObject position = (JSONObject) obj.get(key);
               this.playerX = Math.abs((double) position.get("x"));

           } else if(key.toLowerCase().equals("cloudvelocity")){
               if(!(obj.get(key) instanceof Double)){
                   this.cloudVelocity = 0;

               } else{
                   this.cloudVelocity = (double) obj.get(key);

               }
           }
       }

        if(obj != null){
            this.startLevel();
        }
    }

    /**Start the game/Level*/
    public void startLevel(){
        Player player = new Player(this.playerX,400 - 50 * 1.5, this.playerHeight, this.playerWidth, 8);
        Cloud cloud = new Cloud(600, 200, 150, 100, this.cloudVelocity, 640);
        this.lvl = new LevelImpl(50, 400, 440, player);
        this.lvl.getEntities().add(cloud);
    }

    /**
     * @return return the current Level the player is in
     */
    public Level getCurrentLevel(){
        return this.lvl;
    }

    /**Move the player forward (Check Level.moveRight())*/
    public boolean moveRight(){
        return lvl.moveRight();
    }

    /**Move the player backward (Check Level.moveLeft())*/
    public boolean moveLeft(){
        return lvl.moveLeft();
    }

    /**Player jump (Check Level.jump())*/
    public boolean jump() {
        return lvl.jump();
    }

    /**player stop moving (Check Level.stopMoving())*/
    public boolean stopMoving(){
        return lvl.stopMoving();
    }

    /**update the movements (Check Level.tick())*/
    public void tick(){
        lvl.tick();
    }
}
