package player;

import board.Board;
import java.util.Scanner;

/**
 * 사용자의 키보드 입력을 통해 돌을 놓는 로컬 플레이어 클래스입니다.
 */
public class LocalPlayer implements Player {
    private final int stoneType;
    private final Scanner sc = new Scanner(System.in);

    /**
     * @param stoneType 플레이어의 돌 종류 (1: 흑, 2: 백)
     */
    public LocalPlayer(int stoneType) {
        this.stoneType = stoneType;
    }

    @Override
    public int[] getNextMove(Board board) {
        while (true) {
            System.out.print("돌을 놓을 좌표를 입력하세요 (예: A1): ");
            String input = sc.next().toUpperCase();

            try {
                // 첫 글자는 열(A~O), 나머지는 행(1~15)으로 파싱
                char col = input.charAt(0);
                int row = Integer.parseInt(input.substring(1));

                return new int[]{col, row};
            } catch (Exception e) {
                System.out.println("잘못된 입력 형식입니다. 다시 입력해주세요.");
            }
        }
    }

    @Override
    public int getStoneType() {
        return stoneType;
    }
}
