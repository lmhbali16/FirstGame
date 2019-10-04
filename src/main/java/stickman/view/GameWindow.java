package stickman.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import stickman.Entity.Entity;
import stickman.model.GameEngine;
import java.util.ArrayList;
import java.util.List;

public class GameWindow {

    private final int width;
    private Scene scene;
    private Pane pane;
    private GameEngine model;
    private List<EntityView> entityViews;
    private BackgroundDrawer backgroundDrawer;
    private Timeline timeline;
    private double finish;


    private double xViewportOffset = 0.0;
    private static final double VIEWPORT_MARGIN = 280.0;

    /**
     * Constructor for creating the game window
     * @param model GameEngine
     * @param width window width
     * @param height window height
     */
    public GameWindow(GameEngine model, int width, int height) {
        this.model = model;
        this.pane = new Pane();

        this.width = width;
        this.scene = new Scene(pane, width, height);
        this.entityViews = new ArrayList<>();

        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(model);
        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

        this.backgroundDrawer = new BlockedBackground();
        backgroundDrawer.draw(model, pane);

        finish = 0;
    }

    /**
     * @return returns the Scene object
     */
    public Scene getScene() {
        return this.scene;
    }

    /**
     * Draw the game every 17 millisecond
     */
    public void run() {
        timeline = new Timeline(new KeyFrame(Duration.millis(17),
                t -> this.draw()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


    }

    /**
     * Draw the game with entities
     */
    private void draw() {
        this.model.tick();


        List<Entity> entities = this.model.getCurrentLevel().getEntities();

        for (EntityView entityView: this.entityViews) {
            entityView.markForDelete();
        }

        double heroXPos = this.model.getCurrentLevel().getHeroX();

        heroXPos -= this.xViewportOffset;

        if (heroXPos < this.VIEWPORT_MARGIN) {

            if (this.xViewportOffset >= 0) { // Don't go further left than the start of the level

                this.xViewportOffset -= this.VIEWPORT_MARGIN - heroXPos;
                if (this.xViewportOffset < 0) {
                    this.xViewportOffset = 0;
                }
            }
        } else if (heroXPos > this.width - this.VIEWPORT_MARGIN) {
            this.xViewportOffset += heroXPos - (this.width - this.VIEWPORT_MARGIN);
        }

        this.backgroundDrawer.update(model.getMinute(), model.getSecond());
        this.updateTime();

        for (Entity entity : entities) {
            boolean notFound = true;
            for (EntityView view: this.entityViews) {

                if(view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(this.xViewportOffset);
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                this.entityViews.add(entityView);
                this.pane.getChildren().add(entityView.getNode());
            }
        }

        for (EntityView entityView : entityViews) {
            if (entityView.isMarkedForDelete()) {
                this.pane.getChildren().remove(entityView.getNode());
            }
        }
        this.entityViews.removeIf(EntityView::isMarkedForDelete);

        if(model.getCurrentLevel().getFinish()){
            backgroundDrawer.gameOver(true);
            if(finish > 1.5){
                Platform.exit();
            }
            else{
                finish+= timeline.getCurrentTime().toSeconds();
            }
        }

    }

    public void updateTime(){
        if(model.getCurrentLevel().getStart()){
            model.setSecond(model.getSecond() + timeline.getCurrentTime().toSeconds());
            if(model.getSecond() >= 60){
                model.setMinute(model.getMinute()+1);
                model.setSecond(0);
            }
        }
    }





}
