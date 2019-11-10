package com.company;

import com.company.graphic.GamePanel;
import com.company.players.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class Game {

    private final static int timeLimit = 2000;
    private Player[] players = new Player[2];


    private Board board;
    private int turn = 0;
    private IntPair nextPlace;

    Game(Player p1, Player p2) {
        players[0] = p1;
        players[1] = p2;
    }

    void start() {
        board = new Board(players);
        GamePanel gamePanel = new GamePanel(board);
        gamePanel.setBounds(0, 0, GamePanel.size * (Board.size + 1), GamePanel.size * (Board.size + 1));
        JFrame frame = new JFrame("Cup");
        frame.setLayout(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(GamePanel.size * (Board.size + 1), GamePanel.size * (Board.size + 1) + 22));
        frame.pack();
        frame.setVisible(true);
        frame.add(gamePanel);
        gamePanel.repaint();
        while (board.win() == 0) {
            nextPlace = new IntPair(-10, -10);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread t = new Thread(() -> {
                try {
                    nextPlace = players[turn].getMove(new Board(board));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            t.start();
            try {
                t.join(timeLimit);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            if (nextPlace.x == -10) {
                System.out.println("Player " + players[turn].getCol() + " has exceeded the time limit\n" +
                        "Player " + players[1 - turn].getCol() + " has won\n");
                return;
            }
            int res = board.move(nextPlace, turn + 1);
            if (res == -2) {
                System.out.println("No more moves!");
                if (board.getScore(1) > board.getScore(2)) {
                    System.out.println("Player 1 has won");
                    System.out.println("score player 1: " + board.getScore(1));
                    System.out.println("score player 2: " + board.getScore(2));
                } else if (board.getScore(1) < board.getScore(2)) {
                    System.out.println("Player 2 has won");
                    System.out.println("score player 1: " + board.getScore(1));
                    System.out.println("score player 2: " + board.getScore(2));
                } else {
                    System.out.println("Draw!");
                    System.out.println("score player 1: " + board.getScore(1));
                    System.out.println("score player 2: " + board.getScore(2));
                }
                return;
            }
            if (res == -1) {
                System.out.println("Player " + players[turn].getCol() + " has made an invalid move\n" +
                        "Player " + players[1 - turn].getCol() + " has won\n");
                return;
            }
            
            gamePanel.repaint();

            turn = 1 - turn;
        }
            System.out.println("Player " + board.win() + " has won\n");
    }

}
