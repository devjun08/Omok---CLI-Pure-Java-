package player;

import board.Board;

/**
 * 게임 플레이어의 행동을 정의하는 인터페이스입니다.
 * 로컬 플레이어와 원격 플레이어 모두 이 인터페이스를 구현해야 합니다.
 */
public interface Player {
    /**
     * 다음 돌을 놓을 좌표를 결정하여 반환합니다.
     * 
     * @param board 현재 바둑판 상태
     * @return [열(col), 행(row)] 형태의 좌표 배열
     */
    int[] getNextMove(Board board);

    /**
     * 플레이어의 돌 종류(1: 흑, 2: 백)를 반환합니다.
     * 
     * @return 돌 종류
     */
    int getStoneType();
}
