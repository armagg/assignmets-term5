package com.company.players;

import com.company.Board;
import com.company.IntPair;

public abstract class Player {

    private final int col;
    int x;
    int y;
    boolean buildingBlocks = false;

    abstract public IntPair getMove(Board board);

    public Player(int col, int x, int y) {
        this.col = col;
        this.x = x;
        this.y = y;
    }

    public int getCol() {
        return col;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) { this.y = y; }

    public void setBuildingBlocks(boolean buildingBlocks) { this.buildingBlocks = buildingBlocks; }

    public boolean getBuildingBlocks() { return buildingBlocks; }
}