package com.company;

public class Position {
    int X;
    int Y;

    public Position(int y, int x) {
        Y = y;
        X = x;

    }
    public Position()
    {
        X = 0;
        Y = 0;
    }
    public Position(Position P)
    {
        X = P.getX();
        Y = P.getY();
    }
    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }
}
