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
        System.out.println("Are you Host or Geust?");
        System.out.println("---------------------------");

        // Waiting to user input
        System.out.print("입력(Host/Guest): ");
        String playerType = sc.next();
        boolean isHost = playerType.equalsIgnoreCase("Host");
        boolean isGuest = playerType.equalsIgnoreCase("Guest");
        // Input Validation
        if (!isHost && !isGuest) {
            throw new IllegalArgumentException("Please Reenter Host or Guest");
        }

        String targetIp = "localhost";
        if (isGuest) {
            System.out.print("Please Enter the Host's IP Adress to connect: ");
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

        /*
        Attempt to connect
         */
        try {
            System.out.println("Trying to connect...");
            networkManager.connect(); // Host waits, and Guest accesses
            System.out.println("Connection succesful!");

            //
            GameEngine engine = new GameEngine(board, me, opponent);
            engine.run();
        } catch (Exception e) {
            System.err.println("Unabel to connect: " + e.getMessage());
            return; // unable to connect
        }
    }
}