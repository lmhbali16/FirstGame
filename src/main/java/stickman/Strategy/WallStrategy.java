package stickman.Strategy;

import stickman.model.Level;
import stickman.model.Player;

public interface WallStrategy {

    void addWall(Level lvl, Player player, double finish);
    void createWall(Level lvl, double x, double y);
}
