package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int player = Mark.UNASSIGNED;
        Board board= new Board();
        Scanner in = new Scanner(System.in);
        int turn = -1;
        while(true)
        {

            int opponentRow = in.nextInt();
            int opponentCol = in.nextInt();
            if(turn == -1)
            {
                Prepare.init start = Prepare.startGame(player,turn,opponentRow,opponentCol);
                player = start.getPlayer();
                turn = start.getTurn();
            }

            board.setNextPlayer(Board.getNextPlayer(player));
            Position OpponentMove = new Position(opponentCol,opponentRow);
            if(OpponentMove.getX() != -1 && OpponentMove.getY() != -1)
            {
                board.markCell(OpponentMove);
            }

            board.setNextPlayer(player);
            int validActionCount = in.nextInt();
            ArrayList<Position> positions = new ArrayList<>();
            for (int i = 0; i < validActionCount; i++)
            {
                int row = in.nextInt();
                int col = in.nextInt();
                positions.add(new Position(col,row));
            }
            Position playerPosition = MTCS.mtcs(turn,board,OpponentMove,positions);
            board.markCell(playerPosition);
            System.out.println(playerPosition.getX() + " " + playerPosition.getY());
            if(turn <2)
            {
                turn = 2;
            }
        }
    }
}
