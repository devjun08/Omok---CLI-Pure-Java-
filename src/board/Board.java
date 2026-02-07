package board;

public class Board {
    private static final int SIZE = 15;
    private final int[][] map = new int[SIZE][SIZE];

    // Board.java
    public boolean place(int col, int row, int stoneType) {
        // coordinate transformation
        col = col - 'A';
        row = row - 1;

        // move validation
        if (!moveValidation(row, col)) {
            return false;
        };

        map[row][col] = stoneType;
        return true;
    }

    // move validation
    public boolean moveValidation(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || map[row][col] != 0) return false;
        if (map[row][col] == 1 || map[row][col] == 2) return false;

        return true;
    }

    // TODO: o(n^4) Optimize algorithm performance
    public boolean winCheck() {
        for (int r = 0; r < 15; r++) {
            for (int c = 0; c < 15; c++) {
                if (map[r][c] == 1 || map[r][c] == 2) {
                    if (isFive(r, c) == 5) return true;
                }
            }
        }

        return false;
    }

    public int isFive(int r, int c) {
        int dr[] = {-1, -1, -1, 0, 0, 1, 1, 1};
        int dc[] = {0, -1, 1, -1, 1, 0, -1, 1};
        int targetStone = map[r][c];

        for (int i = 0; i < 8; i++) {
            int count = 1; // 방향이 바뀔 때마다 다시 1개부터 세기
            int nr = r + dr[i]; // 기준점(r, c)에서 해당 방향으로 한 칸 이동한 좌표로 초기화
            int nc = c + dc[i];

            // TODO: convert to for-loop
            while (nr >= 0 && nr < SIZE && nc >= 0 && nc < SIZE && map[nr][nc] == targetStone) {
                count++;
                if (count == 5) return 5; // 5개 찾으면 즉시 리턴

                // 같은 방향으로 한 칸 더 전진
                nr += dr[i];
                nc += dc[i];
            }
        }
        return 1;
    }

    public void render() {
        // 1. 바둑판 내용 및 세로 번호 출력
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
            System.out.printf("%2d ", (r + 1)); // 좌측 세로 번호 (A~O)
            System.out.println(); // 한 줄 완료 후 줄바꿈
        }

        // 2. 하단 가로 문자 출력
        for (int i = 0; i < SIZE; i++) {
            System.out.printf("%2c ", (i + 'A'));
        }

        System.out.println();
    }
}
