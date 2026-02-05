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
        사용자 설정
         */
        System.out.println("Hello! This is Omok.");
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
            System.out.println("연결 시도 중...");
            networkManager.connect(); // 여기서 방장은 기다리고, 게스트는 접속합니다.
            System.out.println("연결 성공!");

            // [추가] 이제 게임판과 두 플레이어를 엔진에 넣고 돌립니다.
            GameEngine engine = new GameEngine(board, me, opponent);
            engine.run();
        } catch (Exception e) {
            System.err.println("연결 실패: " + e.getMessage());
            return; // 연결 안 되면 더 진행할 수 없으니 종료
        }
    }
}
