package core;

import interfaces.Player;
import models.Location;
import core.players.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class Game {
    private final Player player1;
    private Player player2;
    private int difficulty;

    public Game() {
        player1 = new HumanPlayer(new Board(10));
    }

    public void startOfTheGame() {
        System.out.println();
        setDifficulty();
        initializeAIPlayer();
        System.out.println();
        System.out.println("Here is your initial board to place ships:");
        Board board = new Board(10);
        board.printBoard();
        System.out.println();
        player1.placeAllShips();
        player2.placeAllShips();
    }

    private void turn(Player player, Player enemy) {
        boolean hit;
        do {
            System.out.println((player == player1 ? "AbstractPlayer 1" : "AbstractPlayer 2") + "'s turn:");
            player.shoot(enemy.getBoard());

            Location lastShot = player.getLastShot();
            hit = enemy.getBoard().hasHit(lastShot);
        } while (hit && !checkGameOver());
    }

    public void playGame() {
        boolean gameOver = false;
        Player currentPlayer = player1;
        Player enemyPlayer = player2;

        while (!gameOver) {
            turn(currentPlayer, enemyPlayer);
            gameOver = checkGameOver();

            if (!gameOver) {
                if (currentPlayer == player1) {
                    currentPlayer = player2;
                    enemyPlayer = player1;
                } else {
                    currentPlayer = player1;
                    enemyPlayer = player2;
                }
            }
        }
        System.out.println("core.Game over!");
        System.out.println((currentPlayer == player1 ? "AbstractPlayer 1" : "AbstractPlayer 2") + " wins!");
    }

    private boolean checkGameOver() {
        return player1.getBoard().areAllShipsSunk() || player2.getBoard().areAllShipsSunk();
    }

    public void setDifficulty() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = "";

        System.out.println("╔═══════════════╗  ╔════════════════════╗  ╔════════════════╗");
        System.out.println("║     1. Easy   ║  ║  2. Intermediate   ║  ║  3. Hard       ║");
        System.out.println("╚═══════════════╝  ╚════════════════════╝  ╚════════════════╝");
        System.out.println();

        do {
            System.out.print("Select your difficulty (1-3): ");
            try {
                input = in.readLine();
            } catch (java.io.IOException e) {
                System.out.println("An error has occurred: " + e.getMessage());
            }
        } while (!Pattern.matches("[123]", input));

        this.difficulty = Integer.parseInt(input);
        System.out.println("You have selected difficulty: " + this.difficulty);
    }

    private void initializeAIPlayer() {
        switch (this.difficulty) {
            case 1:
                player2 = new EasyComputerPlayer(new Board(10));
                break;
            case 2:
                player2 = new IntermediateComputerPlayer(new Board(10));
                break;
            case 3:
                player2 = new HardComputerPlayer(new Board(10));
                break;
            default:
                throw new IllegalArgumentException("Invalid difficulty level: " + this.difficulty);
        }
    }
}
