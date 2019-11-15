package stickman.model;

import de.saxsys.javafx.test.JfxRunner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import stickman.Entity.FinishLine;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class FactoryLevelTest {

    private FactoryLevel factoryLevel;

    private int life;
    private int enemyNum;
    private double start;
    private double lvlHeight;
    private double lvlWidth;
    private double velocity;
    private JSONArray obj_array;
    private double finish;

    @Before
    public void start(){

        factoryLevel = new FactoryLevel("./src/test/resources/test.json");

        JSONParser jsonParser = new JSONParser();
        JSONObject obj;
        obj_array = null;

        try(FileReader reader = new FileReader("./src/test/resources/test.json")){
            obj = (JSONObject) jsonParser.parse(reader);
            obj_array = (JSONArray) obj.get("level");


        }catch(IOException | ParseException e){
            System.out.println("Error related to the file or argument");
            e.printStackTrace();

        }

        this.readLevel(0);
    }

    private void readLevel(int lvlInt){
        JSONObject playerObj = (JSONObject) ((JSONObject) obj_array.get(lvlInt)).get("player");
        JSONObject levelObj = (JSONObject) ((JSONObject) obj_array.get(lvlInt)).get("field");

        life = this.checkLife(Integer.parseInt(playerObj.get("life").toString()));
        enemyNum = Math.abs(Integer.parseInt(levelObj.get("enemy").toString()));

        lvlHeight = Math.abs(Double.parseDouble(levelObj.get("levelHeight").toString()));
        lvlWidth = Math.abs(Double.parseDouble(levelObj.get("levelWidth").toString()));
        velocity = this.checkVelocity(Double.parseDouble(playerObj.get("stickmanVelocity").toString()));
        start = this.checkStart(Double.parseDouble(levelObj.get("start").toString()),lvlWidth);
        finish = this.checkFinish(Double.parseDouble(levelObj.get("finish").toString()),lvlWidth);
    }


    private double checkStart(double start, double width){
        if(Math.abs(start) <= width * 0.5){
            return Math.abs(start);
        }
        else{
            return 50;
        }
    }

    private double checkFinish(double finish, double width){
        if(Math.abs(finish) <= width && Math.abs(finish) > width/2){
            return Math.abs(finish);
        }
        else{
            return width -100;
        }
    }


    private int checkLife(int life){
        if(life == 0){
            return 5;
        }
        else{
            return Math.abs(life);
        }
    }


    private double checkVelocity(double velocity){
        if(velocity == 0){
            return 10;
        }
        else{
            return Math.abs(velocity);
        }
    }

    /**
     * Check if the config is correct
     */
    @Test
    public void readBasic(){
        List<Level> levelList = factoryLevel.getLevels();

        assertEquals("Wrong number of levels", obj_array.size(), levelList.size());

        Level lvl = levelList.get(0);

        assertEquals("wrong level width", lvlWidth, lvl.getWidth(),2);
        assertEquals("Wrong level height", lvlHeight, lvl.getHeight(),2);
        assertEquals("Wrong floor height", lvlHeight*0.15, lvl.getFloorHeight(),2);
        assertEquals("Wrong number of enemies", enemyNum, lvl.getEnemyList().size());
        assertEquals("Wrong starting point", start, lvl.getHeroX(), 2);
        for(int i = 0; i < lvl.getEntities().size();i++){
            if(lvl.getEntities().get(i) instanceof FinishLine){
                FinishLine fn = (FinishLine) lvl.getEntities().get(i);
                assertEquals("Wrong finish point", finish,fn.getXPos(),2);
                break;
            }
        }

    }


    /**
     * edge cases that the FactoryLevel dealt with
     */
    @Test
    public void testLevelEdgeCase(){

        int lvlInt = 1;
        Level lvl = factoryLevel.getLevels().get(lvlInt);
        this.readLevel(lvlInt);

        assertEquals("wrong level width", lvlWidth, lvl.getWidth(),2);
        assertEquals("Wrong level height", lvlHeight, lvl.getHeight(),2);
        assertEquals("Wrong floor height", lvlHeight*0.15, lvl.getFloorHeight(),2);
        assertEquals("Wrong number of enemies", enemyNum, lvl.getEnemyList().size());
        assertEquals("Wrong starting point", start, lvl.getHeroX(), 2);
        for(int i = 0; i < lvl.getEntities().size();i++){
            if(lvl.getEntities().get(i) instanceof FinishLine){
                FinishLine fn = (FinishLine) lvl.getEntities().get(i);
                assertEquals("Wrong finish point", finish,fn.getXPos(),2);
                break;
            }
        }

    }

}