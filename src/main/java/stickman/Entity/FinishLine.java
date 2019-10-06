package stickman.Entity;

import javafx.scene.image.ImageView;
import stickman.Entity.Entity;

import java.io.File;

public class FinishLine implements Entity {

    private Layer layer;
    private double x;
    private double y;
    private double width;
    private double height;
    private String imagePath;
    private ImageView node;

    public FinishLine(double height,double x, double y){
        layer = Layer.BACKGROUND;
        this.height = height;
        this.x = x;
        this.y = y;
        width = height * 1.5;
        imagePath = "./src/main/resources/finishline.png";
        this.node = new ImageView(new File(imagePath).toURI().toString());
        this.node.setFitHeight(height);
        this.node.setPreserveRatio(true);
        width = node.getBoundsInLocal().getWidth();

    }

    public String getImagePath(){
        return imagePath;
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

    public ImageView getNode(){
        return this.node;
    }

    public void collision(Entity A){

    }

    public void setInitialPos(double x, double y){
        this.x = x;
        this.y = y;
    }
    public void  move(){}
}
