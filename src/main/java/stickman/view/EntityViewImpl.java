package stickman.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import stickman.Entity.Entity;
import java.io.File;

public class EntityViewImpl implements EntityView {

    private Entity entity;
    private String imagePath;
    private ImageView node;
    private boolean delete = false;

    /**
     * Constructor
     * Assign image to entities
     * @param entity the Entity object we want to assign image to
     */
    EntityViewImpl(Entity entity) {
        this.entity = entity;
        this.imagePath = entity.getImagePath();

        this.node = entity.getNode();
        node.setFitHeight(entity.getHeight());

        node.setPreserveRatio(true);
        node.setX(entity.getXPos());
        node.setY(entity.getYPos());

        this.node.setViewOrder(getViewOrder(entity.getLayer()));
        update(0);
    }

    /**
     *
     * @param layer Layer type (Check Entity object)
     * @return returns a parameter
     */
    private double getViewOrder(Entity.Layer layer) {
        switch (layer) {
            case BACKGROUND: return 100.0;
            case FOREGROUND: return 50.0;
            case EFFECT: return 25.0;
            default: throw new IllegalStateException("Javac doesn't understand how enums work so now I have to exist");
        }
    }

    /**
     * @return returns Node object
     */
    @Override
    public Node getNode() {
        return this.node;
    }

    /**
     * @return returns whether the EntityView is marked for delete
     */
    @Override
    public boolean isMarkedForDelete() {
        return this.delete;
    }

    /**
     * Compare Entities
     * @param entity The comparable Entity
     * @return returns whether they are the same or not
     */
    @Override
    public boolean matchesEntity(Entity entity) {
        return this.entity.equals(entity);
    }

    /**
     * Update the Image if the Entity has moved
     * @param xViewportOffset The distance the Entity has moved
     */
    @Override
    public void update(double xViewportOffset) {
        String newPath = entity.getImagePath();
        if (!imagePath.equals(newPath)) {
            imagePath = newPath;
            node.setImage(new Image(new File(imagePath).toURI().toString()));
        }
        node.setX(entity.getXPos() - xViewportOffset);
        node.setY(entity.getYPos());
        node.setFitHeight(entity.getHeight());

        node.setPreserveRatio(true);
        delete = false;
    }

    /**
     * Mark the EntityView for delete
     */
    @Override
    public void markForDelete() {
        this.delete = true;
    }

}
