package com.company;

public class Prepare
{
    public static class OneMove
    {
        Position p;
        double score;

        public OneMove(Position p, double score) {
            this.p = p;
            this.score = score;
        }
        public OneMove()
        {
            this.p = new Position();
            this.score = 0.;
        }

        public Position getP() {
            return p;
        }

        public void setP(Position p) {
            this.p = p;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }
    public static class init
    {
        int Player;
        int turn;

        public init(int player, int turn) {
            Player = player;
            this.turn = turn;
        }

        public int getPlayer() {
            return Player;
        }

        public void setPlayer(int player) {
            Player = player;
        }

        public int getTurn() {
            return turn;
        }

        public void setTurn(int turn) {
            this.turn = turn;
        }
    }

    public static init startGame(int p,int turn, int opponentRow,int opponentCol)
    {
        int pRet = p;
        int turnRet = turn;
        if(turn == -1)
        {
            if(opponentCol == -1 && opponentRow == -1)
            {
                pRet = Mark.PLAYER1;
                turnRet = 0;
            }
            else
            {
                pRet = Mark.PLAYER2;
                turnRet = 1;
            }
        }
        return new init(pRet,turnRet);
    }
}
