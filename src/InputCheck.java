import java.util.Scanner;


public class InputCheck {
    public static Location checkLocationInput(Scanner scanner, Board board, String rowMessage, String columnMessage) {
        int row, column;
        while (true) {
            System.out.print(rowMessage);
            row = scanner.nextInt();
            System.out.print(columnMessage);
            column = scanner.nextInt();

            if (Location.locationIsValid(row, column, board)) {
                break;
            } else {
                System.out.println("Invalid location. Please enter again.");
            }
        }

        return new Location(column, row);
    }

    public static Direction checkDirectipnInput(Scanner scanner, String directionMessage) {
        int direction;
        while (true) {
            System.out.print(directionMessage);
            direction = scanner.nextInt();
            if (direction == 0)
                return Direction.HORIZONTAL;
            else if (direction == 1) {
                return Direction.VERTICAL;
            } else {
                System.out.println("Invalid direction. Please enter again.");
            }
        }
    }
    public static boolean checkYesOrNoInput(Scanner scanner, String message) {
        String input;
        while (true) {
            System.out.print(message);
            input = scanner.next().trim().toLowerCase();

            if (input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }

    public static Location checkShootingInput(Scanner scanner, Board enemyBoard, String rowMessage, String columnMessage) {
        int row, column;
        while (true) {
            try {
                System.out.print(rowMessage);
                row = scanner.nextInt();
                System.out.print(columnMessage);
                column = scanner.nextInt();

                if (row < 0 || row >= enemyBoard.getLength()) {
                    System.out.println("Invalid row. Please enter a row between 0 and " + (enemyBoard.getLength() - 1) + ".");
                } else if (column < 0 || column >= enemyBoard.getLength()) {
                    System.out.println("Invalid column. Please enter a column between 0 and " + (enemyBoard.getLength() - 1) + ".");
                } else if (enemyBoard.isAlreadyShot(new Location(column, row))) {
                    System.out.println("This location has already been shot. Please choose a different location.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numeric values.");
                scanner.next();
            }
        }

        return new Location(column, row);
    }
}
