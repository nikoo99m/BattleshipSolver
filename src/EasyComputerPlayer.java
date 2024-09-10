import java.util.List;
import java.util.Random;

public class EasyComputerPlayer extends Player {

        private final Random random;

        public EasyComputerPlayer(Board board) {
            super(board);
            this.random = new Random();
        }

        @Override
        public void placeAllShips() {
            DefaultShip defaultShip = new DefaultShip();
            List<Ship> defaultShips = defaultShip.initializeDefaultShip();

            for (Ship ship : defaultShips) {
                boolean placed = false;
                while (!placed) {

                    int row = random.nextInt(board.getLength());
                    int column = random.nextInt(board.getLength());

                    Direction direction = random.nextBoolean() ? Direction.HORIZONTAL : Direction.VERTICAL;

                    ship.setLocation(new Location(row, column));
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
            boolean validShot = false;
            Location shotLocation = null;

            while (!validShot) {
                int row = random.nextInt(board.getLength());
                int column = random.nextInt(board.getLength());

                shotLocation = new Location(row, column);

                if (!enemyBoard.hasHit(shotLocation) && !enemyBoard.hasMiss(shotLocation)) {
                    validShot = true;
                }
            }

            setLastShot(shotLocation);

            if (enemyBoard.addHit(shotLocation)) {
                System.out.println("EasyComputerPlayer hit a ship!");
                enemyBoard.printBoardForEnemy();
            } else {
                System.out.println("EasyComputerPlayer missed.");
                enemyBoard.printBoardForEnemy();
            }
        }

        @Override
        public Board getBoard() {
            return board;
        }
    }


