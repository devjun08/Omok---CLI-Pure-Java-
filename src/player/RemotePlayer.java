package player;

import network.NetworkManager;
import board.Board;
import java.io.IOException;

public class RemotePlayer implements Player {
    private NetworkManager nm;
    private int stoneType;

    public RemotePlayer(NetworkManager nm, int stoneType) {
        this.nm = nm;
        this.stoneType = stoneType;
    }

    @Override
    public int[] getNextMove(Board board) {
        try {
            System.out.println("상대방의 수를 기다리는 중...");
            // 1. 네트워크 너머에서 "3,4" 같은 문자열이 올 때까지 대기
            String msg = nm.receive();

            // 2. 받은 문자열을 좌표 숫자로 변환
            String[] parts = msg.split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);

            return new int[]{row, col};
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getStoneType() {
        return stoneType;
    }
}