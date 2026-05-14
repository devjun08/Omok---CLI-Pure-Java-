package player;

import network.NetworkManager;
import board.Board;
import java.io.IOException;

/**
 * 네트워크를 통해 상대방의 입력을 받아오는 원격 플레이어 클래스입니다.
 */
public class RemotePlayer implements Player {
    private final NetworkManager nm;
    private final int stoneType;

    /**
     * @param nm 연결된 네트워크 매니저
     * @param stoneType 플레이어의 돌 종류 (1: 흑, 2: 백)
     */
    public RemotePlayer(NetworkManager nm, int stoneType) {
        this.nm = nm;
        this.stoneType = stoneType;
    }

    @Override
    public int[] getNextMove(Board board) {
        try {
            System.out.println("상대방의 수를 기다리는 중...");
            // 네트워크로부터 "좌표1,좌표2" 형태의 문자열 수신
            String msg = nm.receive();
            if (msg == null) return null;

            String[] parts = msg.split(",");
            // GameEngine에서 nm.send(col + "," + row)로 보냈으므로
            // parts[0]은 col, parts[1]은 row임
            int col = Integer.parseInt(parts[0]);
            int row = Integer.parseInt(parts[1]);

            return new int[]{col, row};
        } catch (IOException e) {
            System.err.println("상대방의 입력을 받는 중 오류 발생: " + e.getMessage());
            return null;
        }
    }

    @Override
    public int getStoneType() {
        return stoneType;
    }
}