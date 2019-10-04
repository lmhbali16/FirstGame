package stickman.Entity;

import javafx.scene.image.ImageView;
import stickman.Entity.Entity;

import java.io.File;

public class Wall implements Entity {

    private String path;
    private double x;
    private double y;
    private double height;
    private double width;
    private Layer layer;
    private boolean playeron;
    private ImageView node;

    public Wall(double x, double y){
        this.x = x;
        this.y = y;
        this.path = "./src/main/resources/wall.png";
        this.height = 30;
        this.width = 30;
        layer = Layer.BACKGROUND;
        this.playeron = false;

        this.node = new ImageView(new File(path).toURI().toString());
        this.node.setFitHeight(30);
        this.node.setPreserveRatio(true);
        width = node.getBoundsInLocal().getWidth();


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

    public ImageView getNode(){
        return this.node;
    }
}
