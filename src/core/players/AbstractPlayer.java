package core.players;

import core.Board;
import models.Location;

public abstract class AbstractPlayer {
    protected Location lastShot;
    
    protected Board board;

    public AbstractPlayer(Board board) {
        this.board = board;
    }

    public abstract void placeAllShips();

    public abstract void shoot(Board enemyBoard);

    public Board getBoard() {
        return board;
    }
    public Location getLastShot() {
        return lastShot;
    }
    protected void setLastShot(Location lastShot) {
        this.lastShot = lastShot;
    }
}

