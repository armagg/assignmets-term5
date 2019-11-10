package com.company;

import com.company.players.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Board {

    public final static int size = 8;
    private final static int maxNumberOfMoves = 80;
    private Cell[][] cells = new Cell[size][size];
    private int score[] = new int[2];
    private int numberOfMoves = 0;
    private IntPair terminals[] = new IntPair[8];
    Player[] players;
    private int playerTerminalSourceId[] = new int[2];

    public Board(Player[] players) {
        this.players = players;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                this.cells[i][j] = new Cell(i, j, 0);
        }
        // Assign terminals randomly
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i < size * size - 2; i++)
            list.add(i);
        Collections.shuffle(list);
        for (int i = 0; i < 8; i++){
            int point = list.get(i);
            int xTrm = (point - point % size) / size;
            int yTrm = point % size;
            terminals[i] = new IntPair(xTrm, yTrm);
            cells[xTrm][yTrm].assignId(i);
        }
        score[0] = 0;
        score[1] = 0;
        playerTerminalSourceId[0] = -1;
        playerTerminalSourceId[1] = -1;
    }

    public Board(Board board) {
        this.players = new Player[2];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                this.cells[i][j] = new Cell(board.cells[i][j]);
        }
        this.terminals = board.terminals;
        this.numberOfMoves = board.getNumberOfMoves();
        this.score = board.score;
        this.playerTerminalSourceId[0] = board.playerTerminalSourceId[0];
        this.playerTerminalSourceId[1] = board.playerTerminalSourceId[1];
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }


    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    int win() {
        return 0;
    }

    public int move(IntPair nextPlace, int playerColor) {
        numberOfMoves ++;
        IntPair currentPlace = new IntPair(players[playerColor-1].getX(), players[playerColor-1].getY());
        if (nextPlace.x < 0 || nextPlace.y < 0 || nextPlace.x >= size || nextPlace.y >= size)
            return -1;
        else if (Math.abs(currentPlace.y - nextPlace.y) > 1 || Math.abs(currentPlace.x - nextPlace.x) > 1)
            return -1;
        else if (cells[nextPlace.x][nextPlace.y].getColor() != 0)
            return -1;
        else if (nextPlace.x == players[2-playerColor].getX() && nextPlace.y == players[2-playerColor].getY())
            return -1;
        else if (numberOfMoves > maxNumberOfMoves)
            return -2;
        else {
            int id = cells[nextPlace.x][nextPlace.y].getId();
            if (id == -1){
                if (playerTerminalSourceId[playerColor-1] != -1) {
                    cells[nextPlace.x][nextPlace.y].setColor(playerColor);
                }
            }
            else {
                if (playerTerminalSourceId[playerColor-1] == -1){
                    players[playerColor-1].setBuildingBlocks(true);
                    playerTerminalSourceId[playerColor-1] = id;
                    cells[nextPlace.x][nextPlace.y].setColor(playerColor);
                }
                else {
                    players[playerColor-1].setBuildingBlocks(false);
                    playerTerminalSourceId[playerColor-1] = -1;
                    cells[nextPlace.x][nextPlace.y].setColor(playerColor);
                }
            }
        }
        players[playerColor-1].setX(nextPlace.x);
        players[playerColor-1].setY(nextPlace.y);
        return 0;
    }

    public int getScore(int player){
        int x = players[player-1].getX();
        int y = players[player-1].getY();
        boolean checked[][] = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                checked[i][j] = false;
            }
        }
        int walls = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (getCell(i, j).getColor() == player)
                    walls ++;
            }
        }
        return movableSquares(x, y, checked) + walls;
    }

    private int movableSquares(int x, int y, boolean[][] checked) {
        int score = 1;
        checked[x][y] = true;
        if (x+1 < size && cells[x+1][y].getColor() == 0 && !checked[x+1][y]) {
            checked[x+1][y] = true;
            score += movableSquares(x + 1, y, checked);
        }
        if (y+1 < size && cells[x][y+1].getColor() == 0 && !checked[x][y+1]) {
            checked[x][y+1] = true;
            score += movableSquares(x, y + 1, checked);
        }
        if (x-1 >= 0 && cells[x-1][y].getColor() == 0 && !checked[x-1][y]) {
            checked[x-1][y] = true;
            score += movableSquares(x - 1, y, checked);
        }
        if (y-1 >= 0 && cells[x][y-1].getColor() == 0 && !checked[x][y-1]) {
            checked[x][y-1] = true;
            score += movableSquares(x, y - 1, checked);
        }
        if (y+1 < size && x+1 < size && cells[x+1][y+1].getColor() == 0 && !checked[x+1][y+1]) {
            checked[x+1][y+1] = true;
            score += movableSquares(x+1, y+1, checked);
        }
        if (y+1 < size && x-1 >= 0 && cells[x-1][y+1].getColor() == 0 && !checked[x-1][y+1]) {
            checked[x-1][y+1] = true;
            score += movableSquares(x-1, y + 1, checked);
        }
        if (y-1 >= 0 && x+1 < size && cells[x+1][y-1].getColor() == 0 && !checked[x+1][y-1]) {
            checked[x+1][y-1] = true;
            score += movableSquares(x+1, y - 1, checked);
        }
        if (y-1 >= 0 && x-1>=0 && cells[x-1][y-1].getColor() == 0 && !checked[x-1][y-1]) {
            checked[x-1][y-1] = true;
            score += movableSquares(x-1, y - 1, checked);
        }
        return score;
    }

    public int getPlayerX (int color) { return players[color-1].getX(); }

    public int getPlayerY (int color) { return players[color-1].getY(); }

}