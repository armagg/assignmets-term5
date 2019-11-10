package com.company.players;

import com.company.Board;
import com.company.IntPair;

import java.util.ArrayList;
import java.util.Random;

public class NaivePlayer extends Player {

    public NaivePlayer(int col, int x, int y) {
        super(col, x, y);
    }

    @Override
    public IntPair getMove(Board board) {
        Random rand = new Random();
        int x_next = x;
        int y_next = y;
        while (x_next == x && y_next == y) {
            int rnd = rand.nextInt(4);
            if (rnd == 0 && x + 1 < Board.size && board.getCell(x + 1, y).getColor() == 0)
                x_next += 1;
            else if (rnd == 1 && x - 1 >= 0 && board.getCell(x - 1, y).getColor() == 0)
                x_next -= 1;
            else if (rnd == 2 && y + 1 < Board.size && board.getCell(x, y + 1).getColor() == 0)
                y_next += 1;
            else if (rnd == 3 && y - 1 >= 0 && board.getCell(x, y - 1).getColor() == 0)
                y_next -= 1;
        }
        return new IntPair(x_next, y_next);
    }
}
