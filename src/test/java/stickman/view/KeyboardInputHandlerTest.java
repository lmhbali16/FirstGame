package stickman.view;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import stickman.model.GameEngine;
import stickman.model.GameEngineImpl;
import stickman.model.Level;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class KeyboardInputHandlerTest {


    private KeyboardInputHandler k;
    private GameEngine model;
    private Scene scene;
    private Pane pane;

    @Before
    public void start(){

        model = new GameEngineImpl("./src/test/resources/test2.json");
        k = new KeyboardInputHandler(model);
        pane = new Pane();
        scene = new Scene(pane, 720,480);
        scene.setOnKeyPressed(k::handlePressed);
        scene.setOnKeyReleased(k::handleReleased);
    }

    @Test
    public void checkConfig(){
        Level lvl = model.getCurrentLevel();

        assertEquals("Wrong number of life", 5,model.getLife());
        assertEquals("Wrong number of enemies", 0, lvl.getEnemyList().size());
        assertEquals("Wrong start line", 30, lvl.getStartLine(),2);
        assertEquals("Wrong level height", 300.5, lvl.getHeight(),2);
        assertEquals("Wrong level width",300.34, lvl.getWidth(),2);

    }

    @Test
    public void testKeyBoardLR(){
        KeyEvent left = new KeyEvent(KeyEvent.KEY_PRESSED, KeyCode.LEFT.toString(),"pressed left",KeyCode.LEFT,false,false,false,false);
        KeyEvent right = new KeyEvent(KeyEvent.KEY_PRESSED, KeyCode.RIGHT.toString(),"pressed right",KeyCode.RIGHT,false,false,false,false);
        KeyEvent leftRelease = new KeyEvent(KeyEvent.KEY_RELEASED, KeyCode.LEFT.toString(),"release left",KeyCode.LEFT,false,false,false,false);
        KeyEvent rightRelease = new KeyEvent(KeyEvent.KEY_RELEASED, KeyCode.RIGHT.toString(),"release right",KeyCode.RIGHT,false,false,false,false);

        double pos = model.getCurrentLevel().getHeroX();
        k.handlePressed(right);

        for(int i = 0; i < 100; i++){
            model.tick();
        }
        k.handleReleased(rightRelease);
        k.handlePressed(left);
        for(int i = 0; i < 40; i++){
            model.tick();
        }
        k.handleReleased(leftRelease);

        assertEquals("position x is wrong", pos+60*30*0.017, model.getCurrentLevel().getHeroX(),5);

        pos = model.getCurrentLevel().getHeroX();

        k.handlePressed(left);

        for(int i =0; i < 20; i++){
            if(i == 10){
                k.handleReleased(leftRelease);
            }
            model.tick();
        }

        assertEquals("position x is wrong", pos-10*30*0.017, model.getCurrentLevel().getHeroX(),5);

    }

    @Test
    public void testKeyboardUp(){
        KeyEvent up = new KeyEvent(KeyEvent.KEY_PRESSED, KeyCode.UP.toString(),"press up", KeyCode.UP,false, false, false,false);
        KeyEvent upRelease = new KeyEvent(KeyEvent.KEY_PRESSED, KeyCode.UP.toString(),"release up", KeyCode.UP,false, false, false,false);

        double pos = model.getCurrentLevel().getHeroX();
        k.handlePressed(up);

        for(int i = 0; i < 12; i++){
            k.handlePressed(up);
            model.tick();
        }
        k.handleReleased(upRelease);

        assertEquals("Wrong x position", pos, model.getCurrentLevel().getHeroX(),5);

    }



}