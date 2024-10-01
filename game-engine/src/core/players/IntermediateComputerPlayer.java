package core.players;

import core.Board;
import models.Location;

import java.util.*;


public class IntermediateComputerPlayer extends AiPlayer {
    public IntermediateComputerPlayer(Board board) {
        super(board);
        this.targetingMode = false;
        this.targetCells = new ArrayList<>();
        this.targetDirection = null;
        this.firstHitLocation = null;
        this.sunkShipLocations = new HashSet<>();
    }
    @Override
    public void shoot(Board enemyBoard) {
        Location shotLocation;

        while (true) {
            if (targetingMode && !targetCells.isEmpty()) {
                shotLocation = targetCells.remove(0);
            } else {
                shotLocation = strategy.selectRandomShot(enemyBoard);
            }

            setLastShot(shotLocation);

            if (enemyBoard.addHit(shotLocation)) {
                System.out.println("Computer hits at (" + shotLocation.getRow() + ", " + shotLocation.getColumn() + ")");
                if (enemyBoard.isShipSunk(shotLocation)) {
                    System.out.println("Computer sunk a ship!");
                    strategy.markSunkShip(shotLocation, enemyBoard, this);
                    strategy.resetTargetingMode(this);
                    break;
                } else {
                    targetingMode = true;
                    if (firstHitLocation == null) {
                        firstHitLocation = shotLocation;
                        strategy.addAdjacentCellsToTarget(shotLocation, enemyBoard, targetCells);
                    } else if (targetDirection == null) {
                        strategy.determineTargetDirection(shotLocation, this);
                        strategy.refineDirectionalTargets(enemyBoard, this);
                    } else {
                        strategy.refineDirectionalTargets(enemyBoard, this);
                    }
                }
            } else {
                System.out.println("Computer misses at (" + shotLocation.getRow() + ", " + shotLocation.getColumn() + ")");
                if (targetDirection != null && !targetCells.isEmpty()) {
                    continue;
                } else {
                    break;
                }
            }
        }
    }
}