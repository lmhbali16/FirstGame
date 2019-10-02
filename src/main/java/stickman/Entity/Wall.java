package stickman.Entity;

import stickman.Entity.Entity;

public class Wall implements Entity {

    private String path;
    private double x;
    private double y;
    private double height;
    private double width;
    private Layer layer;
    private boolean playeron;

    public Wall(double x, double y){
        this.x = x;
        this.y = y;
        this.path = "./src/main/resources/wall.png";
        this.height = 30;
        this.width = 30;
        layer = Layer.FOREGROUND;
        this.playeron = false;
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
        return layer;
    }


    public void setInitialPos(double x, double y){
        this.x = x;
        this.y = y;
    }

    public boolean getPlayerOn(){
        return this.playeron;
    }

    public void setPlayerOn(boolean playeron){
        this.playeron = playeron;
    }
}
