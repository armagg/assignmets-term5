package com.company;

public class IntPair {
    final int x;
    final int y;

    public IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public IntPair(IntPair intp) {
        this.x = intp.x;
        this.y = intp.y;
    }
}