package stickman.view;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import stickman.model.GameEngine;

public class BlockedBackground implements BackgroundDrawer {

    private GameEngine model;
    private Pane pane;
    private Text timeText;
    private Text lifeText;
    private double width;

    public BlockedBackground(double width){
        this.width = width;

    }

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

    /**
     * Remove the old time and life stats and update the new values
     *
     */
    @Override
    public void update() {
        // do nothing since this is a static bg
        pane.getChildren().removeAll(timeText, lifeText);

        String time =model.getMinute()+" : "+ (int) model.getSecond();

        timeText = new Text( 10, 50,time);
        timeText.setViewOrder(30);
        timeText.setFont(Font.font("family", 30));


        String life = "Life: "+ model.getLife();
        lifeText = new Text(life);
        lifeText.setViewOrder(30);
        lifeText.setFont(Font.font("family", 30));
        lifeText.setY(50);
        lifeText.setX(width - lifeText.getLayoutBounds().getWidth() - 10);

        pane.getChildren().addAll(timeText, lifeText);




    }

    /**
     * Add floor
     * @param width window width
     * @param level_height level height
     * @param floorHeight ground height
     */
    private void addFloor(double width, double level_height, double floorHeight){

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

    /**
     * Add sky background
     * @param width width of game window
     * @param level_height level height
     * @param floor_height ground height
     */
    private   void addSky(double width, double level_height, double floor_height){

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

    /**
     * Print final result
     * if game is completed print "Good job"
     * otherwise "Unlucky" if u lose
     * @param finish if game is over
     */
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
