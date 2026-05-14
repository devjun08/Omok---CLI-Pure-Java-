package engine;

import board.Board;
import network.NetworkManager;
import player.LocalPlayer;
import player.Player;

/**
 * 오목 게임의 전체적인 흐름(Turn, Win Check 등)을 관리하는 엔진 클래스입니다.
 */
public class GameEngine {
    private final Board board;
    private final Player blackPlayer;
    private final Player whitePlayer;
    private final NetworkManager nm;
    private Player currentPlayer;

    /**
     * @param board 사용할 바둑판
     * @param p1 첫 번째 플레이어
     * @param p2 두 번째 플레이어
     * @param nm 네트워크 통신 매니저
     */
    public GameEngine(Board board, Player p1, Player p2, NetworkManager nm) {
        this.board = board;
        this.blackPlayer = p1;
        this.whitePlayer = p2;
        this.nm = nm;
        currentPlayer = blackPlayer; // 흑돌부터 시작
    }

    /**
     * 게임 루프를 실행합니다.
     */
    public void run() {
        System.out.println("게임 시작!");
        board.render();

        while (true) {
            String stoneColor = (currentPlayer == blackPlayer) ? "Black (●)" : "White (○)";
            System.out.println("\n현재 턴: " + stoneColor);

            // 1. 현재 플레이어로부터 좌표 획득
            int[] move = currentPlayer.getNextMove(board);
            if (move == null) break; // 오류 발생 시 종료

            int col = move[0];
            int row = move[1];

            // 2. 바둑판에 돌 놓기
            boolean success = board.place(col, row, currentPlayer.getStoneType());

            if (success) {
                /*
                 * [학습 포인트 - SOLID 원칙]
                 * 아래의 instanceof 체크는 '개방-폐쇄 원칙(OCP)'을 위반할 소지가 있습니다.
                 * 엔진이 플레이어의 구체적인 타입(LocalPlayer)을 알아야만 하기 때문입니다.
                 * 더 좋은 설계는 Player 인터페이스에 handlePostMove() 같은 메서드를 두는 것입니다.
                 */
                if (currentPlayer instanceof LocalPlayer) {
                    nm.send(col + "," + row);
                }

                // 3. 승리 판정
                if (board.winCheck()) {
                    board.render();
                    System.out.println("\n" + stoneColor + " 승리!");
                    return;
                }

                board.render();
                switchPlayer(); // 턴 교체
            } else {
                System.out.println("그곳에는 돌을 놓을 수 없습니다.");
            }
        }
    }

    /**
     * 현재 플레이어를 교체합니다.
     */
    private void switchPlayer() {
        currentPlayer = (currentPlayer == blackPlayer) ? whitePlayer : blackPlayer;
    }
}
