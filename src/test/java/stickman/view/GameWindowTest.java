package stickman.view;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
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

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class GameWindowTest {

    private WindowClass window;
    private GameEngine model;

    private double velocity;
    private double start;
    private int windowWidth;
    private int windowHeight;

    /**
     * Set up the values that we want to compare with from json file
     */
    @Before
    public void start(){
        windowHeight = 480;
        windowWidth = 720;

        model = new GameEngineImpl("./src/test/resources/test2.json");
        window = new WindowClass(model,windowWidth,windowHeight);

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

        velocity = Math.abs(Double.parseDouble(playerObj.get("stickmanVelocity").toString()));
        start = Math.abs(Double.parseDouble(levelObj.get("start").toString()));



    }

    /**
     * Check basic functionality. If player did not move at the start, the timer should be 0
     * as soon as we move and call the window draw, the timer should not be 0 and the character position
     */
    @Test
    public void testBasic(){

        window.run();
        for(int i = 0; i < 500; i++){
            window.draw();
        }

        assertEquals("wrong x position", start, model.getCurrentLevel().getHeroX(),2);
        assertEquals("Wrong timer", 0, model.getSecond(),5);
        assertEquals("Wrong timer (minute)", 0, model.getMinute(),5);
        model.moveRight();

        int idx = 200;
        for(int i = 0; i < idx; i++){
            window.draw();
        }
        double result = idx*velocity*0.017;

        assertEquals("wrong x position", start+result, model.getCurrentLevel().getHeroX(),2);
        assertTrue("Wrong timer",0!= model.getSecond() || 0!= model.getMinute());
        window.stopTimer();

    }

    /**
     * Check if our background draw the sky and ground accordingly
     * We should have the sky width the same as the window width and the sky height should be levelHeight-floorHeight
     * The ground is made up of 15 blocks (Check FactoryLevel) and their heights are == floorHeight
     *
     */
    @Test
    public void testImageView(){
        Pane pane  = (Pane) window.getScene().getRoot();

        double skyWidth = windowWidth;
        double skyHeight = model.getCurrentLevel().getHeight()-model.getCurrentLevel().getFloorHeight();
        boolean isSky = false;
        int groundNum = 15;  //Check FactoryLevel. We divided the full width length by 15 and created ground accordingly
        double groundHeight = model.getCurrentLevel().getFloorHeight();

        List children = pane.getChildren();

        for(int i = 0; i < children.size(); i++){
            if(!(children.get(i) instanceof ImageView)){
                continue;
            }
            ImageView image = (ImageView) children.get(i);

            if(skyWidth-image.getFitWidth() < 0.001 && image.getX() == 0 && image.getY() == 0){
                if(skyHeight - image.getFitHeight() < 0.001){
                    isSky = true;
                }
            }
            if(image.getY() == skyHeight && image.getFitHeight() == groundHeight){
                groundNum -= 1;
            }

        }

        assertTrue("No Sky background",isSky);
        assertEquals("Not enough or no ground background", 0,groundNum);


    }

}