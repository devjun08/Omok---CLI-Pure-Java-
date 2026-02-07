package engine;

import board.Board;
import network.NetworkManager;
import player.LocalPlayer;
import player.Player;

public class GameEngine {
    private final Board board;
    private final Player blackPlayer;
    private final Player whitePlayer;
    private final NetworkManager nm;
    private Player currentPlayer;

    public GameEngine(Board board, Player p1, Player p2, NetworkManager nm) {
        this.board = board;
        this.blackPlayer = p1;
        this.whitePlayer = p2;
        this.nm = nm;
        currentPlayer = blackPlayer;
    }


    public void run() {
        System.out.println("Game Start!");
        board.render();

        while (true) {
            String stoneColor = (currentPlayer == blackPlayer) ? "Black (●)" : "White (○)";
            System.out.println("\nCurrent Player: " + stoneColor);

            // Get coordinates from the current player
            int move[] = currentPlayer.getNextMove(board);
            int col = move[0];
            int row = move[1];

            // Try to place
            boolean success = board.place(col, row, currentPlayer.getStoneType());

            // place success
            if (success) {
                // if currentPlayer == LocalPlayer
                if (currentPlayer instanceof LocalPlayer) {
                    nm.send(col + "," + row);
                }

                // Win check Logic
                if (board.winCheck()) {
                    board.render();
                    System.out.println(stoneColor + " Win!");
                    return;
                }

                board.render();
                switchPlayer(); // switch to the next player
            } else {
                System.out.println("You cannot place a stone here...");
            }

        }
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == blackPlayer) ? whitePlayer : blackPlayer;
    }
}
