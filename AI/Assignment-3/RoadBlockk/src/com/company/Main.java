package com.company;

import com.company.players.Player;
import com.company.players.NaivePlayer;


public class Main {

    public static void main(String[] args) {

        Player p1 = new NaivePlayer(1, 0, 0);
        Player p2 = new NaivePlayer(2, 7, 7);
        Game g = new Game(p1, p2);
        g.start();
    }

}