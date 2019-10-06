package stickman.model;

import de.saxsys.javafx.test.JfxRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import stickman.Entity.Cloud;
import stickman.Entity.Entity;
import stickman.Entity.FinishLine;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(JfxRunner.class)
public class FactoryLevelTest {

    private FactoryLevel factoryLevel;

    @Before
    public void start(){

        factoryLevel = new FactoryLevel("./src/test/resources/test.json");
    }


    @Test
    public void readBasic(){
        List<Level> levelList = factoryLevel.getLevels();

        assertEquals("Wrong number of levels", 2, levelList.size());

        Level lvl = levelList.get(0);

        assertEquals("wrong level width", 123.34, lvl.getWidth(),2);
        assertEquals("Wrong level height", 40.5, lvl.getHeight(),2);
        assertEquals("Wrong floor height", 40.5*0.15, lvl.getFloorHeight(),2);
        assertEquals("Wrong number of enemies", 20, lvl.getEnemyList().size());
        assertEquals("Wrong starting point", 30, lvl.getStartLine(), 2);
        for(int i = 0; i < lvl.getEntities().size();i++){
            if(lvl.getEntities().get(i) instanceof FinishLine){
                FinishLine fn = (FinishLine) lvl.getEntities().get(i);
                assertEquals("Wrong finish point", 100,fn.getXPos(),2);
                break;
            }
        }

    }

    @Test
    public void testLevelEdgeCase(){

        Level lvl = factoryLevel.getLevels().get(1);

        assertEquals("wrong level width", 380, lvl.getWidth(),2);
        assertEquals("Wrong level height", 500, lvl.getHeight(),2);
        assertEquals("Wrong floor height", 500*0.15, lvl.getFloorHeight(),2);
        assertEquals("Wrong number of enemies", 1, lvl.getEnemyList().size());
        assertEquals("Wrong starting point", 50, lvl.getStartLine(), 2);
        for(int i = 0; i < lvl.getEntities().size();i++){
            if(lvl.getEntities().get(i) instanceof FinishLine){
                FinishLine fn = (FinishLine) lvl.getEntities().get(i);
                assertEquals("Wrong finish point", lvl.getWidth()-100,fn.getXPos(),2);
                break;
            }
        }

    }

}