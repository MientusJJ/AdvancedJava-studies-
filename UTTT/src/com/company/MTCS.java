package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class MTCS {
    public static Position mtcs(int turn, Board s, Position opp,ArrayList<Position> moves)
    {
        ArrayList<Position> pos = s.getMoves(opp);
        int [] games = new int[pos.size()];
        int [] vic = new int[pos.size()];
        int [] draws = new int[pos.size()];
        Prepare.OneMove[] score = new Prepare.OneMove[pos.size()];
        int n = pos.size();
        long maxTime = 0l;
        if(turn < 2)
        {
            maxTime = 950;
        }
        else
        {
            maxTime = 95;
        }
        long startTime = System.currentTimeMillis();
        for(int i = 0; i % 500 != 0 || (System.currentTimeMillis()-startTime) < maxTime; i++)
        {
            int indx = i % n;
            Position toPlay = pos.get(indx);
            Board newBoard = new Board(s);
            newBoard.markCell(toPlay);
            int winner = Play(newBoard, toPlay);
            games[indx]++;
            if(winner == s.getNextPlayer())
            {
                vic[indx]++;
            }
            else if(winner == Mark.DRAW)
            {
                draws[indx]++;
            }
        }
        for (int i = 0; i < n; i++)
        {
            score[i] = new Prepare.OneMove(pos.get(i),(double)vic[i]/(double)games[i]*1000+ (double)draws[i]/(double)games[i]);
        }
        Arrays.sort(score, (move1, move2) -> Double.compare(move2.getScore(), move1.getScore()));
        return score[0].getP();
    }
    public static int Play(Board b,Position opp)
    {
        Board newBoard = new Board(b);
        Position lastOpp = new Position(opp.getY(), opp.getX());
        while (newBoard.getWinner() == Mark.UNASSIGNED)
        {
            ArrayList<Position> available = newBoard.getMoves(lastOpp);
            if(available.size() == 0)
            {
                int cnt1 = 0;
                int cnt2 = 0;
                for(int i = 0; i < 3; i++)
                {
                    for(int j = 0; j < 3; j++)
                    {
                        if(newBoard.getBigGrid(i,j) == Mark.PLAYER1)
                        {
                            cnt1++;
                        }
                        else if (newBoard.getBigGrid(i,j) == Mark.PLAYER2) {
                            cnt2++;

                        }
                    }
                }
                if(cnt1 > cnt2)
                {
                    return Mark.PLAYER1;
                }
                else if(cnt2 > cnt1)
                {
                    return Mark.PLAYER2;
                }
                else
                {
                    return Mark.DRAW;
                }
            }
            int indx = (int)(Math.random() * ((available.size())));
            Position p = available.get(indx);
            newBoard.markCell(p);
            lastOpp = p;
        }
        return newBoard.getWinner();
    }
}
