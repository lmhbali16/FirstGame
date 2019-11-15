package stickman.Entity;

import javafx.scene.image.ImageView;


import java.io.File;

public class FinishLine implements Entity {

    private Layer layer;
    private double x;
    private double y;
    private double width;
    private double height;
    private String imagePath;
    private ImageView node;

    /**
     * Finish line
     * @param height height of finish line (will be x2.5 the player height always - check FactoryLevel class)
     * @param x x position
     * @param y y position
     */
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

    /**
     *
     * @return get image path;
     */
    public String getImagePath(){
        return imagePath;
    }

    /**
     *
     * @return x position
     */
    public double getXPos(){
        return x;
    }

    /**
     *
     * @return y position
     */
    public double getYPos(){
        return y;
    }

    /**
     *
     * @return height
     */
    public double getHeight(){
        return height;
    }

    /**
     *
     * @return width
     */
    public double getWidth(){
        return width;
    }

    /**
     *
     * @return layer
     */
    public Layer getLayer(){
        return layer;
    }

    /**
     *
     * @return ImageView of object
     */
    public ImageView getNode(){
        return this.node;
    }

    /**
     * Set concrete position for the object
     * @param x x position
     * @param y y position
     */
    public void setInitialPos(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Do nothing;
     */
    public void  move(){}
}
