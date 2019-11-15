package stickman.Strategy;

import stickman.model.Level;
import stickman.Entity.Player;

public interface WallStrategy {

    void addWall(Level lvl, Player player, double finish);

}
