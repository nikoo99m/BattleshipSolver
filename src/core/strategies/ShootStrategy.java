package core.strategies;

import core.Board;
import core.players.AiPlayer;
import enums.Direction;
import models.Cell;
import models.Location;

import java.util.List;
import java.util.Random;

public class ShootStrategy {
    protected Board board;
    public ShootStrategy(Board board) {
        this.board = board;
    }

    public Location selectRandomShot(Board enemyBoard) {
        Random random = new Random();
        Location shotLocation;
        int boardSize = board.getLength();

        do {
            int row = random.nextInt(boardSize);
            int column = random.nextInt(boardSize);
            shotLocation = new Location(column, row);
        } while (isAlreadyShot(shotLocation, enemyBoard)  || isAdjacentToSunkShip(shotLocation, enemyBoard));


        return shotLocation;
    }
    private boolean isAdjacentToSunkShip(Location location, Board enemyBoard) {
        int row = location.getRow();
        int col = location.getColumn();

        if (isSunkShip(row - 1, col, enemyBoard)) return true;
        if (isSunkShip(row + 1, col, enemyBoard)) return true;
        if (isSunkShip(row, col - 1, enemyBoard)) return true;
        if (isSunkShip(row, col + 1, enemyBoard)) return true;

        return false;
    }
    private boolean isSunkShip(int row, int col, Board enemyBoard) {
        int boardSize = board.getLength();
        if (row >= 0 && row < boardSize && col >= 0 && col < boardSize) {
            return enemyBoard.getCellStatus(new Location(col, row)).equals(Cell.SUNK);
        }
        return false;
    }
    public boolean isAlreadyShot(Location location, Board enemyBoard) {
        String status = enemyBoard.getCellStatus(location);
        return status.equals(Cell.MISS) || status.equals(Cell.HIT) || status.equals(Cell.SUNK);
    }
    public void addAdjacentCellsToTarget(Location hitLocation, Board enemyBoard, List<Location> targetCells) {
        int row = hitLocation.getRow();
        int col = hitLocation.getColumn();
        int boardSize = board.getLength();

        if (row > 0 && !isAlreadyShot(new Location(col, row - 1), enemyBoard)) {
            targetCells.add(new Location(col, row - 1));
        }
        if (row < boardSize - 1 && !isAlreadyShot(new Location(col, row + 1), enemyBoard)) {
            targetCells.add(new Location(col, row + 1));
        }
        if (col > 0 && !isAlreadyShot(new Location(col - 1, row), enemyBoard)) {
            targetCells.add(new Location(col - 1, row));
        }
        if (col < boardSize - 1 && !isAlreadyShot(new Location(col + 1, row), enemyBoard)) {
            targetCells.add(new Location(col + 1, row));
        }
    }
    public void resetTargetingMode(AiPlayer player) {
        player.targetingMode = false;
        player.targetCells.clear();
        player.targetDirection = null;
        player.firstHitLocation = null;
    }
    private void refineVerticalTargets(Board enemyBoard, AiPlayer player) {
        int col = player.firstHitLocation.getColumn();
        int row = player.firstHitLocation.getRow();
        int boardSize = board.getLength();

        for (int i = row + 1; i < boardSize; i++) {
            Location loc = new Location(col, i);
            if (enemyBoard.getCellStatus(loc).equals(Cell.MISS)) break;
            if (enemyBoard.getCellStatus(loc).equals(Cell.HIT)) continue;
            player.targetCells.add(loc);
            break;
        }

        for (int i = row - 1; i >= 0; i--) {
            Location loc = new Location(col, i);
            if (enemyBoard.getCellStatus(loc).equals(Cell.MISS)) break;
            if (enemyBoard.getCellStatus(loc).equals(Cell.HIT)) continue;
            player.targetCells.add(loc);
            break;
        }
    }
    public void refineDirectionalTargets(Board enemyBoard, AiPlayer player) {
        player.targetCells.clear();
        if (player.targetDirection == Direction.HORIZONTAL) {
            refineHorizontalTargets(enemyBoard, player);
        } else {
            refineVerticalTargets(enemyBoard, player);
        }
    }
    private void refineHorizontalTargets(Board enemyBoard, AiPlayer player) {
        int col = player.firstHitLocation.getColumn();
        int row = player.firstHitLocation.getRow();
        int boardSize = board.getLength();
        for (int i = col + 1; i < boardSize; i++) {
            Location loc = new Location(i, row);
            if (enemyBoard.getCellStatus(loc).equals(Cell.MISS)) break;
            if (enemyBoard.getCellStatus(loc).equals(Cell.HIT)) continue;
            player.targetCells.add(loc);
            break;
        }

        for (int i = col - 1; i >= 0; i--) {
            Location loc = new Location(i, row);
            if (enemyBoard.getCellStatus(loc).equals(Cell.MISS)) break;
            if (enemyBoard.getCellStatus(loc).equals(Cell.HIT)) continue;
            player.targetCells.add(loc);
            break;
        }
    }
    public void determineTargetDirection(Location newHitLocation, AiPlayer player) {
        if (newHitLocation.getRow() == player.firstHitLocation.getRow()) {
            player.targetDirection = Direction.HORIZONTAL;
        } else if (newHitLocation.getColumn() == player.firstHitLocation.getColumn()) {
            player.targetDirection = Direction.VERTICAL;
        }
    }
    public void markSunkShip(Location shotLocation, Board enemyBoard, AiPlayer player) {
        int row = shotLocation.getRow();
        int col = shotLocation.getColumn();
        int boardSize = board.getLength();
        for (int i = col; i >= 0; i--) {
            Location loc = new Location(i, row);
            if (enemyBoard.getCellStatus(loc).equals(Cell.WATER)) break;
            player.sunkShipLocations.add(loc);
        }
        for (int i = col + 1; i < boardSize; i++) {
            Location loc = new Location(i, row);
            if (enemyBoard.getCellStatus(loc).equals(Cell.WATER)) break;
            player.sunkShipLocations.add(loc);
        }
        for (int i = row; i >= 0; i--) {
            Location loc = new Location(col, i);
            if (enemyBoard.getCellStatus(loc).equals(Cell.WATER)) break;
            player.sunkShipLocations.add(loc);
        }
        for (int i = row + 1; i < boardSize; i++) {
            Location loc = new Location(col, i);
            if (enemyBoard.getCellStatus(loc).equals(Cell.WATER)) break;
            player.sunkShipLocations.add(loc);
        }
    }
}
