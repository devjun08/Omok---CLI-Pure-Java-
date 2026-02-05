package board;

public class Board {
    private static final int SIZE = 15;
    private final int[][] map = new int[SIZE][SIZE];

    public void render() {
        // 1. 상단 가로 번호 출력
        for (int i = 0; i < SIZE; i++) {
            System.out.printf("%2c ", (i + 'A'));
        }
        System.out.println();

        // 2. 바둑판 내용 및 세로 번호 출력
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                // 현재 칸의 상태에 따른 심볼 결정
                String symbol = switch (map[r][c]) {
                    case 1 -> "●"; // 흑돌
                    case 2 -> "○"; // 백돌
                    default -> "+"; // 빈 공간
                };
                System.out.print(" " + symbol + " ");
            }
            System.out.printf("%2d ", (15 - r)); // 좌측 세로 번호 (A~O)
            System.out.println(); // 한 줄 완료 후 줄바꿈
        }
    }
}
