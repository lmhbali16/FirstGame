package stickman.model;


import java.util.List;

/**
 * @author SID:480133780
 */

public class GameEngineImpl implements GameEngine{

    private List<Level> listLevel;
    private Level lvl;


    private int minute; //record minutes in the game
    private double second; // record seconds int the game


    /**
     * Constructor that check the validity of the argument
     * @param s JSON file name. It is assumed that the file is in the resources folder
     */
    public GameEngineImpl(String s){


        FactoryLevel read_json = new FactoryLevel(s);

        listLevel = read_json.getLevels();


        if(listLevel.size() > 0){

            this.startLevel();
        }
    }



    /**Start the game/Level*/
    public void startLevel(){
        lvl = listLevel.get(0);




    }

    public boolean getFinish(){
        return lvl.getFinish();
    }

    /**
     *
     * @return current game time minute
     */
    public int getMinute(){
        return minute;
    }

    /**
     *
     * @return current game time second
     */
    public double getSecond(){
        return second;
    }

    /**
     * set second
     * @param second new second
     */
    public void setSecond(double second){
        if(lvl.getStart()){
            this.second = second;
            if(this.second >= 60){
                minute += 1;
                this.second = 0;
            }
        }
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

    /**
     * Check if the game is finished
     * @return return 1 if successful, -1 if player dies and 0 otherwise
     */
    public int isFinished(){
        if(lvl.getFinish() && lvl.getHeroLife() > 0){
           return 1;
        }
        else if(lvl.getFinish() && lvl.getHeroLife() <= 0){
            return -1;
        }
        return 0;
    }


}
