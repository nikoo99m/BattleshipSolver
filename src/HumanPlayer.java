import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class HumanPlayer extends Player {

    HumanPlayer(Board board) {
        super(board);
    }

    @Override
    public void placeAllShips() {
        Scanner scanner = new Scanner(System.in);

        boolean manualPlacement = InputCheck.checkYesOrNoInput(scanner, "Do you want to place your ships manually? (yes or no): ");

        if (manualPlacement) {

            placeShipsManually(scanner);
        } else {

            placeShipsRandomly();
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
                Location location = InputCheck.checkLocationInput(scanner, board, getRow, getColumn);
                String getDirection = "Enter direction (0 for HORIZONTAL, 1 for VERTICAL): ";
                Direction direction = InputCheck.checkDirectipnInput(scanner, getDirection);

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

    private void placeShipsRandomly() {
        DefaultShip defaultShip = new DefaultShip();
        List<Ship> defaultShips = defaultShip.initializeDefaultShip();
        Random random = new Random();

        for (Ship ship : defaultShips) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(board.getLength());
                int column = random.nextInt(board.getLength());
                Direction direction = random.nextBoolean() ? Direction.HORIZONTAL : Direction.VERTICAL;

                ship.setLocation(new Location(column, row));
                ship.setDirection(direction);

                if (board.addShip(ship)) {
                    placed = true;
                }
            }
        }
        board.printBoard();
    }

    @Override
    public void shoot(Board enemyBoard) {
        board.printBoardsSideBySide(enemyBoard);
        Scanner scanner = new Scanner(System.in);
        String rowMessage = "Enter the row of the point for shooting: ";
        String columnMessage = "Enter the column of the point for shooting: ";
        Location firedLocation = InputCheck.checkShootingInput(scanner, enemyBoard, rowMessage, columnMessage);

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
