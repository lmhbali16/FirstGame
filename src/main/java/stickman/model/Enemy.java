package stickman.model;

public class Enemy implements Object {

    private double height;
    private double width;
    private int life;
    private String path;
    private Layer layer;
    private double x;
    private double y;

    public Enemy(double height, double width, int life, String path){
        this.height = height;
        this.width = width;
        this.life = life;
        this.path = path;
        this.layer = Layer.FOREGROUND;
    }

    public void move(){

    }

    public void collision(Entity A){

    }

    public void setLife(int life){
        this.life = life;
    }

    public int getLife(){
        return life;
    }

    public String getImagePath(){
        return path;
    }

    public double getXPos(){
        return x;
    }

    public double getYPos(){
        return y;
    }

    public double getHeight(){
        return height;
    }

    public double getWidth(){
        return width;
    }

    public Layer getLayer(){
        return Layer.FOREGROUND;
    }

    public void setInitialPos(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void createStrategy(double lvlHeight, double lvlWidth){
        
    }


}
