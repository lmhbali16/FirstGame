package stickman.model;

public class FinishLine implements Entity {

    private Layer layer;
    private double x;
    private double y;
    private double width;
    private double height;
    private String imagePath;

    public FinishLine(double height,double x, double y){
        layer = Layer.BACKGROUND;
        this.height = height;
        this.x = x;
        this.y = y;
        width = height * 1.5;
        imagePath = "./src/main/resources/finishline.png";

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

    public void collision(Entity A){

    }

    public void setInitialPos(double x, double y){
        this.x = x;
        this.y = y;
    }

}
