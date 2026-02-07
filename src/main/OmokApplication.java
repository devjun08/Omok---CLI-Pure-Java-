package main;

import engine.GameEngine;
import network.NetworkManager;
import board.Board;
import player.LocalPlayer;
import player.Player;
import player.RemotePlayer;

import java.util.Scanner;

public class OmokApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        /*
        start
         */
        System.out.println("Hello! This is Omok Game in CLI.");
        System.out.println("Are you Host or Guest?");
        System.out.println("---------------------------");

        // Waiting to user input
        System.out.print("Enter(Host/Guest): ");
        String playerType = sc.next();
        boolean isHost = playerType.equalsIgnoreCase("Host");
        boolean isGuest = playerType.equalsIgnoreCase("Guest");
        // Input Validation
        if (!isHost && !isGuest) {
            throw new IllegalArgumentException("Please Reenter Host or Guest");
        }

        String targetIp = "localhost";
        if (isGuest) {
            System.out.print("Please Enter the Host's IP Address to connect: ");
            targetIp = sc.next();
        }

        /*

         */
        Board board = new Board();
        NetworkManager networkManager = new NetworkManager(isHost, targetIp);

        /*
        Player Interface Declare
         */
        Player me = new LocalPlayer(isHost ? 2 : 1);
        Player opponent = new RemotePlayer(networkManager);

        // test
        int coordinate[] = me.getNextMove(board);
        board.place(coordinate[0], coordinate[1], 1);
        board.render();

        /*
        Attempt to connect
         */
        try {
            System.out.println("Trying to connect...");
            networkManager.connect(); // Host waits, and Guest accesses
            System.out.println("Connection succesful!");


            // GameEngine engine = new GameEngine(board, me, opponent);
            // engine.run();
        } catch (Exception e) {
            System.err.println("Unable to connect: " + e.getMessage());
            return; // unable to connect
        }
    }
}