package com.company;

import javax.management.InvalidAttributeValueException;
import java.util.ArrayList;

public class Board {
    final int gridSize = 9;
    final int bigGridSize = 3;
    int [][] grid;
    int nextPlayer;
    int [][] bigGrid;
    int winner;
    public Board(Board B)
    {
        grid = new int[gridSize][gridSize];
        bigGrid = new int[bigGridSize][bigGridSize];
        nextPlayer = B.nextPlayer;
        winner = B.winner;
        for(int i = 0; i < gridSize; i++)
        {
            for(int j = 0; j < gridSize; j++)
            {
                grid[i][j] = B.grid[i][j];
            }
        }
        for(int i = 0; i < bigGridSize; i++)
        {
            for(int j = 0; j < bigGridSize; j++)
            {
                bigGrid[i][j] = B.bigGrid[i][j];
            }
        }
    }
    public Board() {
       nextPlayer = Mark.UNASSIGNED;
       winner = Mark.UNASSIGNED;
       grid = new int[gridSize][gridSize];
       bigGrid = new int[bigGridSize][bigGridSize];
       for(var row :grid)
       {
            for(var cell : row)
            {
                cell = Mark.UNASSIGNED;
            }
       }
        for(var row : bigGrid)
        {
            for(var cell : row)
            {
                cell = Mark.UNASSIGNED;
            }
        }
    }
    public int getGrid(int Y,int X)
    {
        return grid[Y][X];
    }
    public void setGrid(int Y,int X,Integer mark)
    {
        grid[Y][X] = mark;
    }
    public int getBigGrid(int Y,int X)
    {
        return bigGrid[Y][X];
    }
    public void setBigGrid(int Y,int X,int mark)
    {
        bigGrid[Y][X] = mark;
    }
    public int[][] getGrid() {
        return grid;
    }

    public int getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(int nextPlayer) {
        this.nextPlayer = nextPlayer;
    }
    static public int getNextPlayer(int player)  {
        if(player == Mark.PLAYER1)
        {
            return Mark.PLAYER2;
        }
        else if (player == Mark.PLAYER2)
        {
            return Mark.PLAYER1;
        }
        return -1;
    }
    public int checkbigGridWinner()
    {

        for (int i =0;i < bigGridSize ; i++)
        {
            if (getBigGrid(i,0) != Mark.UNASSIGNED && getBigGrid(i,0)  == getBigGrid(i,1)  && getBigGrid(i,1) == getBigGrid(i,2)) {
            return getBigGrid(i,0);
        }
        }


        for (int j = 0; j < bigGridSize; j++) {
        if (getBigGrid(0,j) != Mark.UNASSIGNED && getBigGrid(0,j)  == getBigGrid(1,j)  && getBigGrid(1,j)  == getBigGrid(2,j)) {
            return getBigGrid(0,j);
        }
    }


        if (getBigGrid(0,0) != Mark.UNASSIGNED && getBigGrid(0,0) == getBigGrid(1,1) && getBigGrid(1,1) == getBigGrid(2,2))
        {
        return getBigGrid(0,0);
    }


        if (getBigGrid(0,2) != Mark.UNASSIGNED && getBigGrid(0,2) == getBigGrid(1,1) && getBigGrid(1,1) == getBigGrid(2,0))
        {
            return getBigGrid(0,2);
        }

        return Mark.UNASSIGNED;
    }
    public int checkGridWinner(int BigY, int BigX)
    {
        int X = 3 * BigX;
        int Y = 3 * BigY;
        for (int i =0;i < bigGridSize ; i++)
        {
            if (getGrid(Y+i,X+0) != Mark.UNASSIGNED && getGrid(Y+i,X+0)  == getGrid(Y+i,X+1)  && getGrid(Y+i,X+1) == getGrid(Y+i,X+2)) {
                return getGrid(Y+i,X+0);
            }
        }

        // check columns
        for (int j = 0; j < bigGridSize; j++) {
            if (getGrid(Y+0,X+j) != Mark.UNASSIGNED && getGrid(Y+0,X+j)  == getGrid(Y+1,X+j)  && getGrid(Y+1,X+j)  == getGrid(Y+2,X+j))  {
                return getGrid(Y+0,X+j);
            }
        }

        // check diag
        if (getGrid(Y+0,X+0) != Mark.UNASSIGNED && getGrid(Y+0,X+0) == getGrid(Y+1,X+1) && getGrid(Y+1,X+1) == getGrid(Y+2,X+2)) {
        return getGrid(Y,X);
    }

        // check anti diag
        if (getGrid(Y+0,X+2) != Mark.UNASSIGNED && getGrid(Y+0,X+2) ==  getGrid(Y+1,X+1) && getGrid(Y+1,X+1) ==   getGrid(Y+2,X+0)) {
        return getGrid(Y+0,X+2);
    }

        return Mark.UNASSIGNED;
    }
    public int getWinner() {
        return winner;
    }
    public Position getBigCell (Position opp)
    {
        return new Position(opp.getY()%3,opp.getX()%3);
    }
    public void markCell(Position p)
    {
        setGrid(p.Y,p.X,getNextPlayer());
        setNextPlayer(getNextPlayer(getNextPlayer()));
        int X = p.X/3;
        int Y = p.Y/3;
        int win = checkGridWinner(Y,X);
        if(win == Mark.UNASSIGNED)
        {
            boolean unassign = false;
            for (int i = 0; i < bigGridSize; i++)
            {
                for(int j = 0; j < bigGridSize; j++)
                {
                    if(getGrid(Y*3+i,X*3+j) == Mark.UNASSIGNED)
                    {
                        unassign = true;
                    }
                }
            }
            if(unassign == false)
            {
                setBigGrid(Y,X,Mark.DRAW);
            }

        }
        else
        {
            setBigGrid(Y,X,win);
            setWinner(checkbigGridWinner());
        }
    }
    public ArrayList<Position> getMoves(Position opp)
    {
        ArrayList<Position> ret = new ArrayList<Position>();
        if(opp.getX() == -1 && opp.getY() == -1)
        {
            getMovesGrid(ret);
        }
        else {
            Position Big = getBigCell(opp);

            if (getBigGrid(Big.getY(), Big.getX()) == Mark.UNASSIGNED) {
                getMovesSubGrid(ret, Big.getX(), Big.getY());
            } else {
                getMovesGrid(ret);
            }
        }
        return ret;
    }
    public void getMovesGrid(ArrayList<Position> pos)
    {
        for(int i = 0; i < bigGridSize; i++)
        {
            for(int j = 0; j < bigGridSize; j++)
            {

                if(getBigGrid(j, i) == Mark.UNASSIGNED)
                {
                    getMovesSubGrid(pos,i,j);
                }
            }
        }
    }
    public void getMovesSubGrid (ArrayList<Position> pos,int x,int y)
    {
        for(int i = 0; i < bigGridSize; i++)
        {
            for(int j = 0; j < bigGridSize; j++)
            {
                int X = x*3+j;
                int Y = y*3+i;
                if(getGrid(Y,X) == Mark.UNASSIGNED)
                {
                    pos.add(new Position(Y,X));
                }
            }
        }
    }
    public void setWinner(int winner) {
        this.winner = winner;
    }
}
