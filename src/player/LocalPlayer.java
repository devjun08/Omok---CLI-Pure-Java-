package player;

import board.Board;
import java.util.Scanner;

public class LocalPlayer implements Player {
    private final int stoneType;
    Scanner sc = new Scanner(System.in);

    public LocalPlayer(int stoneType) {
        this.stoneType = stoneType;
    }

    @Override
    public int[] getNextMove(Board board) {
        System.out.print("Where are you going to put it?(ex) A17): ");
        String input = sc.next().toUpperCase();

        while (true) {
            try {
                char col = input.charAt(0);
                int row = Integer.parseInt(input.substring(1));

                return new int[]{col, row};
            } catch (Exception e) {
                System.out.println("Invalid input format");
            }
        }
    }

    @Override
    public int getStoneType() {
        return stoneType;
    }
}
