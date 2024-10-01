package core.players;

import core.Board;
import core.strategies.ShootStrategy;
import enums.Direction;
import models.Location;

import java.util.List;
import java.util.Set;

public abstract class AiPlayer extends AbstractPlayer {
    public boolean targetingMode;
    public List<Location> targetCells;
    public Direction targetDirection;
    public Location firstHitLocation;
    public Set<Location> sunkShipLocations;
    protected final ShootStrategy strategy;

    public AiPlayer(Board board) {
        super(board);
        strategy = new ShootStrategy(board);
    }
}
