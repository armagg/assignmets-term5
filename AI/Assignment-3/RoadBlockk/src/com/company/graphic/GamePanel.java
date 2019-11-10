package com.company.graphic;

import com.company.Board;
import com.company.IntPair;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public final static int size = 70;
    public final static int size1 = 50;
    private final static int dia = 68;
    private Board board;
    private Color[] colors = {Color.GRAY, Color.BLUE, Color.RED,
            Color.ORANGE};

    public GamePanel(Board board) {
        this.board = board;
        setLayout(null);
        setOpaque(true);
        setBackground(Color.black);
        setPreferredSize(new Dimension(size * (Board.size + 1), size * (Board.size + 1)));
        setSize(size * (Board.size + 1), size * (Board.size + 1));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < Board.size; i++) {
            for (int j = 0; j < Board.size; j++) {
                if (board.getCell(i, j).getId() != -1 && board.getCell(i, j).getColor() == 0) {
                    drawCenteredRectangle((Graphics2D) g, size * (i + 1), size * (j + 1),
                            colors[3], "fill");
                }
                else {
                    drawCenteredRectangle((Graphics2D) g, size * (i + 1), size * (j + 1),
                            colors[board.getCell(i, j).getColor()], "fill");
                }
            }
            for (int j = 0; j < 2; j++) {
                int x = board.getPlayerX(j+1);
                int y = board.getPlayerY(j+1);
                drawCenteredRectangle((Graphics2D) g, size * (x + 1), size * (y + 1),
                        colors[j+1], "fill");
            }
        }
    }


    private void drawCenteredRectangle(Graphics2D g, int x, int y, Color col, String str) {
        g.setColor(col);
        g.drawString(str, x, y);
        x = x - (GamePanel.dia / 2);
        y = y - (GamePanel.dia / 2);
        if (str.equals("fill")) {
            g.fillRect(x, y, GamePanel.dia, GamePanel.dia);
        } else
            g.drawRect(x, y, GamePanel.dia, GamePanel.dia);
    }
}