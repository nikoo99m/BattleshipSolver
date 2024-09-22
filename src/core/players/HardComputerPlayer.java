package core.players;

import core.Board;
import models.Cell;
import models.Location;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class HardComputerPlayer extends AiPlayer {
    private final int boardSize;
    private double[][] predictedBoard;

    public HardComputerPlayer(Board board) {
        super(board);
        this.boardSize = board.getLength();
        this.targetingMode = false;
        this.targetCells = new ArrayList<>();
        this.targetDirection = null;
        this.firstHitLocation = null;
        this.sunkShipLocations = new HashSet<>();
        this.predictedBoard = fetchPredictedBoard(board);
    }

    private double[][] fetchPredictedBoard(Board enemyBoard) {
        double[][] predictedBoard = new double[boardSize][boardSize];
        try {
            String[][] board = new String[boardSize][boardSize];
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    Location loc = new Location(j, i);
                    Cell cell = enemyBoard.getCell(loc);
                    board[i][j] = cell.getStatus();
                }
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("board", board);

            URL url = new URL("http://localhost:5000/predict");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonObject.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (Scanner scanner = new Scanner(conn.getInputStream())) {
                String responseBody = scanner.useDelimiter("\\A").next();
                JSONArray predictions = new JSONArray(responseBody);

                for (int i = 0; i < predictions.length(); i++) {
                    JSONArray row = predictions.getJSONArray(i);
                    for (int j = 0; j < row.length(); j++) {
                        predictedBoard[i][j] = row.getDouble(j);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return predictedBoard;
    }

    @Override
    public void shoot(Board enemyBoard) {

        Location shotLocation;

        while (true) {

            if (targetingMode && !targetCells.isEmpty()) {
                shotLocation = targetCells.remove(0);
            } else {
                shotLocation = selectPredictedShot(enemyBoard);
            }

            setLastShot(shotLocation);

            if (enemyBoard.addHit(shotLocation)) {
                System.out.println("Computer hits at (" + shotLocation.getRow() + ", " + shotLocation.getColumn() + ")");

                if (enemyBoard.isShipSunk(shotLocation)) {
                    System.out.println("Computer sunk a ship!");
                    strategy.markSunkShip(shotLocation, enemyBoard, this);
                    strategy.resetTargetingMode(this);
                    updatePredictions(enemyBoard);
                    break;
                } else {
                    targetingMode = true;
                    if (firstHitLocation == null) {
                        firstHitLocation = shotLocation;
                        strategy.addAdjacentCellsToTarget(shotLocation, enemyBoard, targetCells);
                    } else if ( targetDirection == null) {
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

    private void updatePredictions(Board enemyBoard) {
        this.predictedBoard = fetchPredictedBoard(enemyBoard);
    }

    private Location selectPredictedShot(Board enemyBoard) {
        Location bestShotLocation = null;
        double highestProbability = -1.0;

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Location location = new Location(col, row);
                if (!strategy.isAlreadyShot(location, enemyBoard) && predictedBoard[row][col] > highestProbability) {
                    highestProbability = predictedBoard[row][col];
                    bestShotLocation = location;
                }
            }
        }

        if (bestShotLocation == null) {
            bestShotLocation = strategy.selectRandomShot(enemyBoard);
        }

        return bestShotLocation;
    }
}