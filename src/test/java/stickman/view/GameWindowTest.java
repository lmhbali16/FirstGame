package stickman.view;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import stickman.model.GameEngine;
import stickman.model.GameEngineImpl;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class GameWindowTest {

    private WindowClass window;
    private GameEngine model;

    @Before
    public void start(){
        model = new GameEngineImpl("./src/test/resources/test2.json");
        window = new WindowClass(model,720,480);

    }

    @Test
    public void testBasic(){
        double hero = model.getCurrentLevel().getHeroX();
        window.run();
        for(int i = 0; i < 500; i++){
            window.draw();
        }

        assertEquals("wrong x position", hero, model.getCurrentLevel().getHeroX(),2);
        assertEquals("Wrong timer", 0, model.getSecond(),5);
        model.moveRight();

        for(int i = 0; i < 200; i++){
            window.draw();
        }


        assertEquals("wrong x position", hero+200*30*0.017, model.getCurrentLevel().getHeroX(),2);
        window.stopTimer();

    }


    @Test
    public void testImageView(){
        Pane pane  = (Pane) window.getScene().getRoot();

        double skyWidth = 720;
        double skyHeight = model.getCurrentLevel().getHeight()-model.getCurrentLevel().getFloorHeight();
        boolean isSky = false;
        int groundNum = 15;
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