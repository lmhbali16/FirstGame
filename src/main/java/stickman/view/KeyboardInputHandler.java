package stickman.view;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import stickman.model.GameEngine;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class KeyboardInputHandler{
    private final GameEngine model;
    private boolean left = false;
    private boolean right = false;
    private Set<KeyCode> pressedKeys = new HashSet<>();

    private Map<String, MediaPlayer> sounds = new HashMap<>();

    /**
     * Constructor
     * @param model GameEngine
     */
    KeyboardInputHandler(GameEngine model) {
        this.model = model;

        URL mediaUrl = getClass().getResource("/jump.wav");
        String jumpURL = mediaUrl.toExternalForm();

        Media sound = new Media(jumpURL);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        this.sounds.put("jump", mediaPlayer);
    }

    /**
     * Handle the event of pressing a key
     * @param keyEvent The pressed key
     */
    void handlePressed(KeyEvent keyEvent) {
        if (this.pressedKeys.contains(keyEvent.getCode())) {
            return;
        }

        this.pressedKeys.add(keyEvent.getCode());

        if (keyEvent.getCode().equals(KeyCode.UP)) {
            if (this.model.jump()) {
                MediaPlayer jumpPlayer = this.sounds.get("jump");
                jumpPlayer.stop();
                jumpPlayer.play();

            }
        }

        if (keyEvent.getCode() == KeyCode.LEFT) {
            this.left = true;
        }
        else if (keyEvent.getCode() == KeyCode.RIGHT) {
            this.right = true;
        }

        if(this.left) {
            if (this.right) {
                this.model.stopMoving();
            } else {
                this.model.moveLeft();
            }
            this.model.moveLeft();
        }
        else if(right) {

            this.model.moveRight();
        }
    }

    /**
     * Handling the event of releasing a key
     * @param keyEvent the key that has been released
     */
    void handleReleased(KeyEvent keyEvent) {
        this.pressedKeys.remove(keyEvent.getCode());

        if (keyEvent.getCode() == KeyCode.LEFT) {
            this.left = false;
        }
        else if (keyEvent.getCode()== KeyCode.RIGHT) {
            this.right = false;
        }


        if (!(this.right || this.left)) {
            this.model.stopMoving();
        } else if (this.right) {
            this.model.moveRight();
        } else {
            this.model.moveLeft();
        }
    }
}
