package player;

import board.Board;
import network.NetworkManager;

public class RemotePlayer implements Player {
    public RemotePlayer(NetworkManager networkManager) {
    }

    public int[] getNextMove(Board board) {
        return new int[]{};
    } // place coordinate
    public int getStoneType() {
        return 0;
    }

}
