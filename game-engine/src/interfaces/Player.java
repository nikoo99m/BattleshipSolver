package interfaces;

import core.Board;
import models.Location;

public interface Player {
    void placeAllShips();

    void shoot(Board enemyBoard);

    Board getBoard();

    Location getLastShot();

    void setLastShot(Location lastShot);
}
