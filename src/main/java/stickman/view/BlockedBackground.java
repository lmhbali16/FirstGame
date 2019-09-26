package stickman.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import stickman.model.GameEngine;

public class BlockedBackground implements BackgroundDrawer {

    private GameEngine model;
    private Pane pane;
    private Rectangle floor;
    private Text time_text;


    /**
     * Draw the background
     * @param model GameEngine for getting the Level
     * @param pane Set up the background
     */
    @Override
    public void draw(GameEngine model, Pane pane) {
        this.model = model;
        this.pane = pane;

        double width = pane.getWidth();
        double lvl_height = model.getCurrentLevel().getHeight();
        double floorHeight = model.getCurrentLevel().getFloorHeight();

        this.addFloor(width, lvl_height,floorHeight);
        this.addSky(width, lvl_height,floorHeight);

    }

    @Override
    public void update(int minute, double second) {
        // do nothing since this is a static bg
        pane.getChildren().removeAll(time_text);

        String time =minute+" : "+ (int) second;

        time_text = new Text( 10, 50,time);
        time_text.setViewOrder(30);
        time_text.setFont(Font.font("family", 50));


        pane.getChildren().add(time_text);

    }

    public void addFloor(double width, double level_height, double floorHeight){

        for(int i = 0; i < width; i+= width/15){

            ImageView ground = new ImageView(new Image("foot_tile.png"));
            ground.setFitHeight(floorHeight);
            ground.setPreserveRatio(true);

            ground.setX(i);
            ground.setY(level_height-floorHeight);


            ground.setViewOrder(1000.0);
            pane.getChildren().add(ground);
        }

    }

    public  void addSky(double width, double level_height, double floor_height){

        Rectangle sky = new Rectangle(0,0, width, level_height-floor_height);
        sky.setFill(Paint.valueOf("#8bb6e0"));
        sky.setViewOrder(1000.0);

        ImageView sky_view = new ImageView(new Image(model.getCurrentLevel().getLandscapeImage()));

        sky_view.setFitWidth(width);
        sky_view.setFitHeight(level_height-floor_height);
        sky_view.setX(0);
        sky_view.setY(0);
        sky_view.setViewOrder(950.0);
        pane.getChildren().addAll(sky_view, sky);

    }

    public void gameOver(boolean finish){
        if(finish){
            String gameover = "Good Job! You succeeded!";
            Text go_text = new Text( 30, model.getCurrentLevel().getHeight()*0.25,gameover);
            go_text.setViewOrder(20);
            go_text.setFont(Font.font("family", 30));
            pane.getChildren().add(go_text);
        }
        else{
            String gameover = "Unlucky! Better luck next time!";
            Text go_text = new Text( 30, model.getCurrentLevel().getHeight()*0.25,gameover);
            go_text.setViewOrder(20);
            go_text.setFont(Font.font("family", 30));
            pane.getChildren().add(go_text);
        }
    }
}
