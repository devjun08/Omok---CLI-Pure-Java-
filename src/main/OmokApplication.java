package main;

import engine.GameEngine;
import network.NetworkManager;
import board.Board;
import player.LocalPlayer;
import player.Player;
import player.RemotePlayer;

import java.util.Scanner;

/**
 * 오목 애플리케이션의 진입점 클래스입니다.
 * 게임 설정을 진행하고 필요한 객체들을 생성하여 게임을 실행합니다.
 */
public class OmokApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== CLI 오목 게임에 오신 것을 환영합니다! ===");
        System.out.println("방장(Host)으로 플레이하시겠습니까, 참여자(Guest)로 플레이하시겠습니까?");
        System.out.print("입력 (Host/Guest): ");
        
        String playerType = sc.next();
        boolean isHost = playerType.equalsIgnoreCase("Host");
        boolean isGuest = playerType.equalsIgnoreCase("Guest");

        if (!isHost && !isGuest) {
            System.out.println("Host 또는 Guest를 정확히 입력해주세요.");
            return;
        }

        String targetIp = "localhost";
        if (isGuest) {
            System.out.print("접속할 Host의 IP 주소를 입력하세요: ");
            targetIp = sc.next();
        }

        // 핵심 객체 생성
        Board board = new Board();
        NetworkManager networkManager = new NetworkManager(isHost, targetIp);

        /*
         * 플레이어 설정
         * Host는 흑돌(1), Guest는 백돌(2)을 기본으로 합니다.
         */
        Player me = new LocalPlayer(isHost ? 1 : 2);
        Player opponent = new RemotePlayer(networkManager, isHost ? 2 : 1);

        try {
            System.out.println("연결을 시도 중입니다...");
            networkManager.connect();
            System.out.println("연결 성공!");

            // 게임 엔진 생성 및 실행
            GameEngine engine = new GameEngine(board, me, opponent, networkManager);
            engine.run();
        } catch (Exception e) {
            System.err.println("연결 실패: " + e.getMessage());
        } finally {
            networkManager.close();
            sc.close();
        }
    }
}