package board;

/**
 * 오목 게임의 바둑판 데이터를 관리하고 렌더링하는 클래스입니다.
 * 15x15 크기의 격자를 가지며, 돌의 배치 및 승리 조건 검사를 담당합니다.
 */
public class Board {
    private static final int SIZE = 15; // 바둑판의 크기 (15x15)
    private final int[][] map = new int[SIZE][SIZE]; // 0: 빈 칸, 1: 흑돌(●), 2: 백돌(○)

    /**
     * 바둑판의 특정 좌표에 돌을 놓습니다.
     * 
     * @param col 문자 형식의 열 좌표 (예: 'A', 'B', ...)
     * @param row 1부터 시작하는 행 좌표 (1~15)
     * @param stoneType 돌의 종류 (1: 흑, 2: 백)
     * @return 돌을 놓는 데 성공하면 true, 실패하면 false
     */
    public boolean place(int col, int row, int stoneType) {
        // 입력받은 문자 좌표를 배열 인덱스로 변환 (예: 'A' -> 0, 1 -> 0)
        int c = col - 'A';
        int r = row - 1;

        // 착수 유효성 검사 (범위 밖이거나 이미 돌이 있는 경우)
        if (!moveValidation(r, c)) {
            return false;
        };

        map[r][c] = stoneType;
        return true;
    }

    /**
     * 해당 좌표에 돌을 놓을 수 있는지 검사합니다.
     * 
     * @param row 배열 행 인덱스
     * @param col 배열 열 인덱스
     * @return 놓을 수 있으면 true
     */
    public boolean moveValidation(int row, int col) {
        // 바둑판 범위를 벗어나는지 확인
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE)
            return false;
        // 이미 돌이 놓여있는지 확인 (0이 아니면 돌이 있는 것)
        if (map[row][col] != 0)
            return false;

        return true;
    }

    /**
     * 바둑판 전체를 순회하며 승리자(5목)가 있는지 확인합니다.
     * 
     * @return 5목이 완성되었으면 true
     */
    public boolean winCheck() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                // 돌이 있는 칸에서만 8방향 검사 시작
                if (map[r][c] == 1 || map[r][c] == 2) {
                    if (isFive(r, c) == 5) return true;
                }
            }
        }
        return false;
    }

    /**
     * 특정 위치에서 시작하여 8방향 중 어느 한 방향이라도 5개의 돌이 연속되는지 확인합니다.
     * 
     * @param r 기준 행 인덱스
     * @param c 기준 열 인덱스
     * @return 연속된 돌의 최대 개수 (5개가 발견되면 즉시 5 반환)
     */
    public int isFive(int r, int c) {
        // 8방향 탐색을 위한 방향 벡터 (상, 하, 좌, 우, 대각선 4방향)
        int[] dr = {-1, 1, 0, 0, -1, 1, -1, 1};
        int[] dc = {0, 0, -1, 1, -1, 1, 1, -1};
        int targetStone = map[r][c];

        for (int i = 0; i < 8; i++) {
            int count = 1;
            int nr = r + dr[i];
            int nc = c + dc[i];

            // 현재 방향으로 같은 색의 돌이 몇 개 연속되는지 카운트
            while (nr >= 0 && nr < SIZE && nc >= 0 && nc < SIZE && map[nr][nc] == targetStone) {
                count++;
                if (count == 5) return 5;
                nr += dr[i];
                nc += dc[i];
            }
        }
        return 1;
    }

    /**
     * 현재 바둑판의 상태를 터미널에 시각적으로 출력합니다.
     */
    public void render() {
        // 1. 바둑판 격자와 행 번호(1~15) 출력
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                String symbol = switch (map[r][c]) {
                    case 1 -> "●";
                    case 2 -> "○";
                    default -> "+";
                };
                System.out.print(" " + symbol + " ");
            }
            System.out.printf("%2d ", (r + 1));
            System.out.println();
        }

        // 2. 하단 열 문자(A~O) 출력
        for (int i = 0; i < SIZE; i++) {
            System.out.printf("%2c ", (i + 'A'));
        }
        System.out.println();
    }
}
