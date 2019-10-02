package stickman.Entity;

/**
 * @author SID:480133780
 */

public class Cloud implements Entity {

    private double x;
    private double y;
    private double height;
    private double width;
    private double velocity;
    private String imagePath;
    private Layer layer;
    private double levelWidth;

    /**
     * Constructor
     * @param x X coordinate of the Cloud Object
     * @param y Y coordinate of the CLoud Object
     * @param height Height of the CLoud
     * @param width Width of the Cloud
     * @param velocity Cloud velocity
     * @param levelWidth Width of the Level the Cloud is in
     */
    public Cloud(double x, double y, double height, double width, double velocity, double levelWidth){
        this.x = x;
        this.y = y;

        this.height = height;
        this.width = width;
        this.velocity = velocity;

        this.imagePath = "./src/main/resources/cloud_2.png";
        this.layer = Layer.BACKGROUND;
        this.levelWidth = levelWidth;
    }

    /**
     *
     * @return return x position
     */
    public double getXPos(){
        return this.x;
    }

    /**@return return y position*/
    public double getYPos(){
        return this.y;
    }

    /**@return return height of Cloud*/
    public double getHeight(){
        return this.height;
    }

    public void setInitialPos(double x, double y){
        this.x = x;
        this.y = y;
    }


    /**
     * Move the cloud object
     */
    public void move(){
        if( this.velocity * 0.017 >= 0 ){
            if(this.x <= 0){
                this.x = this.levelWidth;
            }

            if(this.x - this.velocity * 0.017 > 0){
                this.x -= this.velocity * 0.017;
            }
            else if(this.x > 0 && this.x - this.velocity * 0.017 < 0){
                this.x = 0;
            }
        }
        else{
            if(this.x  >= this.levelWidth){
                this.x = 0;
            }

            if(this.x - this.velocity * 0.017 < this.levelWidth){
                this.x -= this.velocity * 0.017;
            } else if(this.x < this.levelWidth && this.x - this.velocity * 0.017 >= this.levelWidth){
                this.x = this.levelWidth;
            }
        }
    }

    public void setLife(int life){}
    public int getLife(){ return 0;}
    public void collision(Entity A){}



    /**@return return the width of the Cloud*/
    public double getWidth(){
        return this.width;
    }

    /**@return return the Layer type of the Cloud (Check out Entity class)*/
    public Layer getLayer(){
        return this.layer;
    }

    /**@return return path of Cloud image */
    public String getImagePath(){
        return this.imagePath;
    }



}
