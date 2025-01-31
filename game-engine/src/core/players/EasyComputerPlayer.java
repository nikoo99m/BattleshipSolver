package core.players;

import core.Board;
import enums.Direction;
import models.Location;
import models.ships.DefaultShip;
import models.ships.Ship;

import java.util.List;
import java.util.Random;

public class EasyComputerPlayer extends AbstractPlayer {

        private final Random random;

        public EasyComputerPlayer(Board board) {
            super(board);
            this.random = new Random();
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
            } else {
                System.out.println("EasyComputerPlayer missed.");

            }
        }

        @Override
        public Board getBoard() {
            return board;
        }
    }


