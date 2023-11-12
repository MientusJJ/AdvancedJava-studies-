import java.util.;
import java.io.;
import java.math.;


  Auto-generated code below aims at helping you parse
  the standard input according to the problem statement.
 
class Player {
 class Mark {
    final static int UNASSIGNED = 0;
    final static int PLAYER1 = 1;
    final static int PLAYER2 = 2;
    final static int DRAW = 3;
}
static class Prepare
{
    static class OneMove
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
    static class init
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
 static class Position {
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

static class MTCS {
    static public Position mtcs(int turn, Board s, Position opp,ArrayListPosition moves)
    {
        Board tempBoard = new Board(s);
        ArrayListPosition pos = moves;
        int [] games = new int[pos.size()];
        int [] vic = new int[pos.size()];
        int [] draws = new int[pos.size()];
        Prepare.OneMove[] score = new Prepare.OneMove[pos.size()];
        int n = pos.size();
        long maxTime = 0l;
        if(turn  1)
        {
            maxTime = 900;
        }
        else
        {
            maxTime = 90;
        }
        long startTime = System.currentTimeMillis();
        for(int i = 0; i % 500 != 0  (System.currentTimeMillis()-startTime)  maxTime; i++)
        {
            int indx = i % n;
            Position toPlay = pos.get(indx);
            tempBoard.markCell(toPlay);
            int winner = Play(tempBoard, toPlay);
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
        for (int i = 0; i  n; i++)
        {
            score[i] = new Prepare.OneMove(pos.get(i),((double)vic[i](double)games[i])1000+ (double)draws[i](double)games[i]);
        }
        Arrays.sort(score, (move1, move2) - Double.compare(move2.getScore(), move1.getScore()));
        return score[0].getP();
    }
    static public int Play(Board b,Position opp)
    {
        Board newBoard = new Board(b);
        Position lastOpp = new Position(opp.getY(), opp.getX());
        while (newBoard.getWinner() == Mark.UNASSIGNED)
        {
            ArrayListPosition available = newBoard.getMoves(lastOpp);
            if(available.size() == 0)
            {
                int cnt1 = 0;
                int cnt2 = 0;
                for(int i = 0; i  3; i++)
                {
                    for(int j = 0; j  3; j++)
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
                if(cnt1  cnt2)
                {
                    return Mark.PLAYER1;
                }
                else if(cnt2  cnt1)
                {
                    return Mark.PLAYER2;
                }
                else
                {
                    return Mark.DRAW;
                }
            }
            int indx = (int)(Math.random()  ((available.size())));
            Position p = available.get(indx);
            newBoard.markCell(p);
            lastOpp = p;
        }
        return newBoard.getWinner();
    }
}


static class Board {
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
        for(int i = 0; i  gridSize; i++)
        {
            for(int j = 0; j  gridSize; j++)
            {
                grid[i][j] = B.grid[i][j];
            }
        }
        for(int i = 0; i  bigGridSize; i++)
        {
            for(int j = 0; j  bigGridSize; j++)
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
       for(var row grid)
       {
            for(var cell  row)
            {
                cell = Mark.UNASSIGNED;
            }
       }
        for(var row  bigGrid)
        {
            for(var cell  row)
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

        for (int i =0;i  bigGridSize ; i++)
        {
            if (getBigGrid(i,0) != Mark.UNASSIGNED && getBigGrid(i,0)  == getBigGrid(i,1)  && getBigGrid(i,1) == getBigGrid(i,2)) {
            return getBigGrid(i,0);
        }
        }


        for (int j = 0; j  bigGridSize; j++) {
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
    public int checkGridWinner(int BigX, int BigY)
    {
        int X = 3  BigX;
        int Y = 3  BigY;
        for (int i =0;i  bigGridSize ; i++)
        {
            if (getGrid(Y+i,X+0) != Mark.UNASSIGNED && getGrid(Y+i,X+0)  == getGrid(Y+i,X+1)  && getGrid(Y+i,X+1) == getGrid(Y+i,X+2)) {
                return getGrid(Y+i,X+0);
            }
        }

         check columns
        for (int j = 0; j  bigGridSize; j++) {
            if (getGrid(Y+0,X+j) != Mark.UNASSIGNED && getGrid(Y+0,X+j)  == getGrid(Y+1,X+j)  && getGrid(Y+1,X+j)  == getGrid(Y+2,X+j))  {
                return getGrid(Y+0,X+j);
            }
        }

         check diag
        if (getGrid(Y+0,X+0) != Mark.UNASSIGNED && getGrid(Y+0,X+0) == getGrid(Y+1,X+1) && getGrid(Y+1,X+1) == getGrid(Y+2,X+2)) {
        return getGrid(Y,X);
    }

         check anti diag
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
        int X = p.X3;
        int Y = p.Y3;
        int win = checkGridWinner(X,Y);
        if(win == Mark.UNASSIGNED)
        {
            boolean unassign = false;
            for (int i = 0; i  bigGridSize; i++)
            {
                for(int j = 0; j  bigGridSize; j++)
                {
                    if(getGrid(Y3+i,X3+j) == Mark.UNASSIGNED)
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
    public ArrayListPosition getMoves(Position opp)
    {
        ArrayListPosition ret = new ArrayListPosition();
        if(opp.getX() == -1 && opp.getY() == -1)
        {
            getMovesGrid(ret);
        }
        else {
            Position Big = getBigCell(opp);

            if (getBigGrid(Big.getY(), Big.getX()) == Mark.UNASSIGNED getBigGrid(Big.getY(), Big.getX()) == Mark.DRAW) {
                getMovesSubGrid(ret, Big.getX(), Big.getY());
            } else {
                getMovesGrid(ret);
            }
        }
        return ret;
    }
    public void getMovesGrid(ArrayListPosition pos)
    {
        for(int i = 0; i  bigGridSize; i++)
        {
            for(int j = 0; j  bigGridSize; j++)
            {

                if(getBigGrid(j, i) == Mark.UNASSIGNED  getBigGrid(j, i) == Mark.DRAW)
                {
                    getMovesSubGrid(pos,i,j);
                }
            }
        }
    }
    public void getMovesSubGrid (ArrayListPosition pos,int x,int y)
    {
        for(int i = 0; i  bigGridSize; i++)
        {
            for(int j = 0; j  bigGridSize; j++)
            {
                int X = x3+j;
                int Y = y3+i;
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

        
    public static void main(String[] args) {
    int player = Mark.UNASSIGNED;
    Board board= new Board();
    Scanner in = new Scanner(System.in);

      for(int turn = -1;;turn+=2)
      {
            int opponentRow = in.nextInt();
            int opponentCol = in.nextInt();
            Prepare.init start = Prepare.startGame(player,turn,opponentRow,opponentCol);
            player = start.getPlayer();
            turn = start.getTurn();
            board.setNextPlayer(Board.getNextPlayer(player));
            Position OpponentMove = new Position(opponentCol,opponentRow);
            if(OpponentMove.getX() != -1 && OpponentMove.getY() != -1)
            {
               board.markCell(OpponentMove);
            }

            board.setNextPlayer(player);
            int validActionCount = in.nextInt();
            ArrayListPosition positions = new ArrayList();
            for (int i = 0; i  validActionCount; i++)
            {
              int row = in.nextInt();
              int col = in.nextInt();
              positions.add(new Position(col,row));
            }
            Position playerPosition = MTCS.mtcs(turn,board,OpponentMove,positions);
            board.markCell(playerPosition);
            System.out.println(playerPosition.getX() +   + playerPosition.getY());
        }
    }
}