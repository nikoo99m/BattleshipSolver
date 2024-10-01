package core.players;

import core.Board;
import enums.Direction;
import helpers.InputCheckHelper;
import models.Location;
import models.ships.DefaultShip;
import models.ships.Ship;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class HumanPlayer extends AbstractPlayer {

    public HumanPlayer(Board board) {
        super(board);
    }

    @Override
    public void placeAllShips() {
        Scanner scanner = new Scanner(System.in);

        boolean manualPlacement = InputCheckHelper.checkYesOrNoInput(scanner, "Do you want to place your ships manually? (yes or no): ");

        if (manualPlacement) {

            placeShipsManually(scanner);
        } else {
            super.placeAllShips();
        }
    }

    private void placeShipsManually(Scanner scanner) {
        DefaultShip defaultShip = new DefaultShip();
        List<Ship> defaultShips = defaultShip.initializeDefaultShip();

        for (Ship ship : defaultShips) {
            boolean placed = false;
            while (!placed) {
                System.out.println("Placing " + ship.getName() + " of size " + ship.getSize());

                String getRow = "Enter starting row";
                String getColumn = "Enter starting column";
                Location location = InputCheckHelper.checkLocationInput(scanner, board, getRow, getColumn);
                String getDirection = "Enter direction (0 for HORIZONTAL, 1 for VERTICAL): ";
                Direction direction = InputCheckHelper.checkDirectipnInput(scanner, getDirection);

                ship.setLocation(location);
                ship.setDirection(direction);

                if (board.addShip(ship)) {
                    placed = true;
                    System.out.println(ship.getName() + " placed successfully.");
                    board.printBoard();
                } else {
                    System.out.println("Cannot place ship at specified location. Please try again.");
                }
            }
        }
    }
    @Override
    public void shoot(Board enemyBoard) {
        board.printBoardsSideBySide(enemyBoard);
        Scanner scanner = new Scanner(System.in);
        String rowMessage = "Enter the row of the point for shooting: ";
        String columnMessage = "Enter the column of the point for shooting: ";
        Location firedLocation = InputCheckHelper.checkShootingInput(scanner, enemyBoard, rowMessage, columnMessage);

        setLastShot(firedLocation);

        if (enemyBoard.addHit(firedLocation)) {
            System.out.println("It's a Hit!");
        } else {
            System.out.println("It's a Miss! Next player's turn.");
        }
    }
    @Override
    public Board getBoard() {
        return board;
    }
}
