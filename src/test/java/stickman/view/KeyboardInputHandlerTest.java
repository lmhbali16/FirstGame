package stickman.view;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import stickman.model.GameEngine;
import stickman.model.GameEngineImpl;
import stickman.model.Level;

import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class KeyboardInputHandlerTest {


    private KeyboardInputHandler k;
    private GameEngine model;
    private Scene scene;
    private Pane pane;

    private int life;
    private int enemyNum;
    private double start;
    private double lvlHeight;
    private double lvlWidth;
    private double velocity;

    /**
     * Read the JSON file and see if the program read them correctly
     */

    @Before
    public void start(){

        model = new GameEngineImpl("./src/test/resources/test2.json");
        k = new KeyboardInputHandler(model);
        pane = new Pane();
        scene = new Scene(pane, 720,480);
        scene.setOnKeyPressed(k::handlePressed);
        scene.setOnKeyReleased(k::handleReleased);

        JSONParser jsonParser = new JSONParser();
        JSONObject obj;
        JSONArray obj_array = null;

        try(FileReader reader = new FileReader("./src/test/resources/test2.json")){
            obj = (JSONObject) jsonParser.parse(reader);
            obj_array = (JSONArray) obj.get("level");


        }catch(IOException | ParseException e){
            System.out.println("Error related to the file or argument");
            e.printStackTrace();

        }

        JSONObject playerObj = (JSONObject) ((JSONObject) obj_array.get(0)).get("player");
        JSONObject levelObj = (JSONObject) ((JSONObject) obj_array.get(0)).get("field");

        life = this.checkLife(Integer.parseInt(playerObj.get("life").toString()));
        enemyNum = Math.abs(Integer.parseInt(levelObj.get("enemy").toString()));

        lvlHeight = Math.abs(Double.parseDouble(levelObj.get("levelHeight").toString()));
        lvlWidth = Math.abs(Double.parseDouble(levelObj.get("levelWidth").toString()));
        velocity = this.checkVelocity(Double.parseDouble(playerObj.get("stickmanVelocity").toString()));
        start = this.checkStart(Double.parseDouble(levelObj.get("start").toString()),lvlWidth);

    }


    private double checkStart(double start, double width){
        if(Math.abs(start) <= width * 0.5){
            return Math.abs(start);
        }
        else{
            return 50;
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
     * Check if the reading of JSON file was correct
     */
    @Test
    public void checkConfig(){
        Level lvl = model.getCurrentLevel();

        assertEquals("Wrong number of life", life,model.getLife());
        assertEquals("Wrong number of enemies", enemyNum, lvl.getEnemyList().size());
        assertEquals("Wrong start line", start, lvl.getHeroX(),2);
        assertEquals("Wrong level height", lvlHeight, lvl.getHeight(),2);
        assertEquals("Wrong level width",lvlWidth, lvl.getWidth(),2);

    }

    /**
     * Test keyboard left and right
     */
    @Test
    public void testKeyBoardLR(){
        KeyEvent left = new KeyEvent(KeyEvent.KEY_PRESSED, KeyCode.LEFT.toString(),"pressed left",KeyCode.LEFT,false,false,false,false);
        KeyEvent right = new KeyEvent(KeyEvent.KEY_PRESSED, KeyCode.RIGHT.toString(),"pressed right",KeyCode.RIGHT,false,false,false,false);
        KeyEvent leftRelease = new KeyEvent(KeyEvent.KEY_RELEASED, KeyCode.LEFT.toString(),"release left",KeyCode.LEFT,false,false,false,false);
        KeyEvent rightRelease = new KeyEvent(KeyEvent.KEY_RELEASED, KeyCode.RIGHT.toString(),"release right",KeyCode.RIGHT,false,false,false,false);

        double pos = model.getCurrentLevel().getHeroX();
        k.handlePressed(right);

        int idx = 100;
        for(int i = 0; i < idx; i++){
            model.tick();
        }
        k.handleReleased(rightRelease);
        k.handlePressed(left);
        int idx2 = 40;
        for(int i = 0; i < idx2; i++){
            model.tick();
        }
        k.handleReleased(leftRelease);

        idx -= idx2;

        assertEquals("position x is wrong", pos+idx*velocity*0.017, model.getCurrentLevel().getHeroX(),5);

        pos = model.getCurrentLevel().getHeroX();

        k.handlePressed(left);

        idx = 20;
        int stop = 10;
        for(int i =0; i < idx; i++){
            if(i == stop){
                k.handleReleased(leftRelease);
            }
            model.tick();
        }
        idx = idx -stop;

        assertEquals("position x is wrong", pos-idx*velocity*0.017, model.getCurrentLevel().getHeroX(),5);

    }

    /**
     * test keyboard up. Should not be able to jump while in the air so the Level.jump() should be false
     */
    @Test
    public void testKeyboardUp(){
        KeyEvent up = new KeyEvent(KeyEvent.KEY_PRESSED, KeyCode.UP.toString(),"press up", KeyCode.UP,false, false, false,false);
        KeyEvent upRelease = new KeyEvent(KeyEvent.KEY_PRESSED, KeyCode.UP.toString(),"release up", KeyCode.UP,false, false, false,false);

        double pos = model.getCurrentLevel().getHeroX();
        k.handlePressed(up);

        int idx = 12;
        for(int i = 0; i < idx; i++){
            k.handlePressed(up);
            model.tick();
        }
        k.handleReleased(upRelease);

        assertEquals("Wrong x position", pos, model.getCurrentLevel().getHeroX(),5);
        assertFalse(model.getCurrentLevel().jump());

    }



}