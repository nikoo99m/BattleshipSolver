package core.players;

import core.Board;
import enums.Direction;
import models.Location;
import models.ships.DefaultShip;
import models.ships.Ship;

import java.util.List;
import java.util.Random;

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

    @Override
    public void placeAllShips() {
        DefaultShip defaultShip = new DefaultShip();
        List<Ship> defaultShips = defaultShip.initializeDefaultShip();

        Random random = new Random();
        int boardSize = board.getLength();
        for (Ship ship : defaultShips) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(boardSize);
                int column = random.nextInt(boardSize);
                Direction direction = random.nextBoolean() ? Direction.HORIZONTAL : Direction.VERTICAL;

                Location location = new Location(column, row);
                ship.setLocation(location);
                ship.setDirection(direction);

                if (board.addShip(ship)) {
                    placed = true;
                }
            }
        }
    }
}

