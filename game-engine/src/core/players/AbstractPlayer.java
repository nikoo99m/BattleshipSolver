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
                int row = 0;
                int column = 0;

                // Randomly decide to place the ship on a vertical or horizontal wall
                Direction direction = random.nextBoolean() ? Direction.HORIZONTAL : Direction.VERTICAL;

                if (direction == Direction.HORIZONTAL) {
                    // Horizontal placement: ship touching either left or right wall
                    column = random.nextBoolean() ? 0 : boardSize - ship.getSize();  // Start     at the left or extend from the right wall
                    row = random.nextInt(boardSize);  // Row can be anywhere
                } else {
                    // Vertical placement: ship touching either top or bottom wall
                    row = random.nextBoolean() ? 0 : boardSize - ship.getSize();  // Start at the top or extend from the bottom wall
                    column = random.nextInt(boardSize);  // Column can be anywhere
                }

                Location location = new Location(column, row);
                ship.setLocation(location);
                ship.setDirection(direction);

                // Try placing the ship at this location
                if (board.addShip(ship)) {
                    placed = true;
                }
            }
        }
        board.printBoard();
    }
}

