package player;

import board.Board;

public interface Player {
    int[] getNextMove(Board board); // place coordinate
    int getStoneType();
}
