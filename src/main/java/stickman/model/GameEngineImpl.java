package stickman.model;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author SID:480133780
 */

public class GameEngineImpl implements GameEngine{

    private List<Level> list_lvl;
    private Level lvl;


    private int minute;
    private double second;


    /**
     * Constructor that check the validity of the argument
     * @param s JSON file name. It is assumed that the file is in the resources folder
     */
    public GameEngineImpl(String s){


        FactoryLevel read_json = new FactoryLevel(s);

        list_lvl = read_json.getLevels();


        if(list_lvl.size() > 0){

            this.startLevel();
        }
    }



    /**Start the game/Level*/
    public void startLevel(){
        lvl = list_lvl.get(0);




    }

    public boolean getFinish(){
        return lvl.getFinish();
    }

    public int getMinute(){
        return minute;
    }
    public double getSecond(){
        return second;
    }

    public void setSecond(double second){
        this.second = second;
    }

    public void setMinute(int minute){
        this.minute = minute;
    }


    public int getLife() {
        return lvl.getHeroLife();
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

    public boolean getStart(){
        return lvl.getStart();
    }
}
