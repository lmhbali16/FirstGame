package stickman.view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import stickman.model.GameEngine;

public class BlockedBackground implements BackgroundDrawer {

    private GameEngine model;
    private Pane pane;
    private Rectangle sky;
    private Rectangle floor;

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
        double floorHeight = model.getCurrentLevel().getFloorHeight();

        this.sky = new Rectangle(0, 0, width, model.getCurrentLevel().getHeight()-floorHeight);
        sky.setFill(Paint.valueOf("LIGHTBLUE"));
        sky.setViewOrder(1000.0);

        this.floor = new Rectangle(0,model.getCurrentLevel().getHeight()-floorHeight,width,floorHeight);
        floor.setFill(Paint.valueOf("GREEN"));
        floor.setViewOrder(1000.0);

        pane.getChildren().addAll(sky, floor);
    }

    @Override
    public void update(double xViewportOffset) {
        // do nothing since this is a static bg
    }
}
