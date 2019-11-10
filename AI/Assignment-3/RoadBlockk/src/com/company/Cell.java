package com.company;


public class Cell {

    private int x, y;
    private int color;
    private int id = -1;
    private int terminalSourceId = -1;

    Cell(int x, int y, int color) {
        this(x, y);
        this.color = color;
    }

    private Cell(int x, int y) {
        this.y = y;
        this.x = x;
    }

    Cell(Cell cell) {
        this.y = cell.getY();
        this.x = cell.getX();
        this.color = cell.getColor();
        this.id = cell.id;
        this.terminalSourceId = cell.terminalSourceId;
    }

    private int getY() {
        return y;
    }

    private int getX() {
        return x;
    }

    public int getColor() {
        return color;
    }

    public int getId() { return id; }

    void setColor(int color) {
        this.color = color;
    }

    void assignId(int id) { this.id = id; }

    int getTerminalSourceId() { return terminalSourceId; }

    void setTerminalSourceId(int terminalSourceId) { this.terminalSourceId = terminalSourceId; }
}
