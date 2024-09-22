package core.players;

import core.Board;
import models.Location;

public abstract class AbstractPlayer implements interfaces.Player {
    protected Location lastShot;
    
    protected Board board;

    public AbstractPlayer(Board board) {
        this.board = board;
    }

    @Override
    public Board getBoard() {
        return board;
    }
    @Override
    public Location getLastShot() {
        return lastShot;
    }
    @Override
    public void setLastShot(Location lastShot) {
        this.lastShot = lastShot;
    }
}

