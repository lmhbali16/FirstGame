package stickman.model;

/**
 * @author SID:480133780
 */

public class Player implements Entity {

    private double x;
    private double y;
    private double height;
    private double width;
    private double velocity;
    private String imagePath;
    private Layer layer;
    private boolean jump;
    private boolean fall;
    private boolean right;
    private boolean left;
    private double jump_height;
    private double counter = 0;
    private int life;

    /**
     * Constructor
     * @param height height of player
     * @param width width of player
     * @param velocity velocity of player
     * @param jump_height height of jump
     */
    public Player(double height, double width, double velocity, double jump_height){


        this.height = height;
        this.width = width;
        this.velocity = velocity;

        this.imagePath = "./src/main/resources/ch_stand1.png";
        this.layer = Layer.FOREGROUND;

        this.jump = false;
        this.fall = false;
        this.right = false;
        this.left = false;
        this.jump_height = jump_height;
    }

    /**
     * @return get x position of player
     */
    public double getXPos(){
        return this.x;
    }

    /**
     * @return get y position of player
     */
    public double getYPos(){
        return this.y;
    }

    /**
     * @return get velocity of player
     */
    public double getVelocity(){
        return this.velocity;
    }

    /**
     * @return return the jumping state of player
     */
    public boolean getJump(){
        return this.jump;
    }

    /**
     * @return returns falling state of player
     */
    public boolean getFall(){
        return this.fall;
    }

    /**
     * @return returns true if the player is currently moving backward, otherwise false.
     */
    public boolean getLeft(){
        return this.left;
    }

    /**
     * @return returns true if the player is currently moving forward, otherwise false.
     */
    public boolean getRight(){
        return this.right;
    }

    /**
     * @return returns height of player
     */
    public double getHeight(){
        return this.height;
    }

    /**
     * @return returns width of player
     */
    public double getWidth(){
        return this.width;
    }

    /**
     * @return returns the path of player image
     */
    public String getImagePath(){
        return this.imagePath;
    }

    /**
     * @return returns the Layer type of player (Check out Entity interface)
     */
    public Layer getLayer(){
        return this.layer;
    }


    public void setInitialPos(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Set the x position of player
     * @param right boolean of going backwards or forward (true if forward).
     * @param velocity the speed which the player moves
     */

    public void setXPos(boolean right, double velocity){
        if(right && this.right){
            this.x += velocity * 0.017;
            if(this.jump || this.fall){
                return;
            }
            this.updateImageRight();

        } else if(!right && this.left){
            this.x -= velocity * 0.017;
            if(this.jump || this.fall){
                return;
            }
            this.updateImageLeft();
        }
    }

    /**
     * If the player moves forward, update the image accordingly
     */
    public void updateImageRight(){

        int step = (int) this.x;
        if(step % 4 == 0){
            this.imagePath = "./src/main/resources/ch_walk1.png";

        } else if(step % 4 == 1){
            this.imagePath = "./src/main/resources/ch_walk2.png";

        } else if(step % 4 == 2){
            this.imagePath = "./src/main/resources/ch_walk3.png";

        } else{
            this.imagePath = "./src/main/resources/ch_walk4.png";

        }
    }

    /**
     * If the player moves backward, update the image accordingly
     */
    public void updateImageLeft(){
        int step = (int) this.x;
        if(step % 4 == 0){
            this.imagePath = "./src/main/resources/ch_walk5.png";

        } else if(step % 4 == 1){
            this.imagePath = "./src/main/resources/ch_walk6.png";

        } else if(step % 4 == 2){
            this.imagePath = "./src/main/resources/ch_walk7.png";

        } else{
            this.imagePath = "./src/main/resources/ch_walk8.png";

        }
    }

    /**
     * Set the y position of player (when it jumps basically)
     * @return returns true when it first jumps and false until it lands to the ground
     */
    public boolean setYPos(){

        if(!this.jump && !this.fall){
            if(this.y != 0){
                this.jump = true;
                this.y -= 1;
                this.counter += 1;
                return true;
            }
        } else if(this.jump && !this.fall){
            if(this.jump_height == this.counter || this.y == 0){
                this.jump = false;
                this.fall = true;
                this.y += 1;
                this.counter -= 1;

            } else if(this.counter < this.jump_height && 0 < y){
                this.y -= 1;
                this.counter += 1;
            }
        } else if(!jump && fall){
            this.y += 1;
            this.counter -= 1;
            if(this.counter == 0){
                this.fall = false;
                if(right){
                    this.setRight(right);
                }
                else if(left){
                    this.setLeft(left);
                }
            }
        }
        return false;
    }

    public int getLife(){
        return life;
    }
    public void setLife(int life){
        this.life = life;
    }

    /**
     * Set whether the player currently moves back or not
     * @param left boolean for moving back or not
     */
    public void setLeft(boolean left){

        this.left = left;

        if(!this.left && (!jump && !fall)){
            this.setStandImage("./src/main/resources/ch_stand5.png");
        }
    }

    public void setStandImage(String s){
        this.imagePath = s;
    }

    /**
     * Set whether the player currently moves forward or not
     * @param right boolean for moving forward or not
     */
    public void setRight(boolean right){
        this.right = right;
        if(!right && (!jump && !fall)){
            this.setStandImage("./src/main/resources/ch_stand2.png");
        }
    }

   public void collision(Entity A){

   }

}
