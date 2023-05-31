import Model.*;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;
import com.sun.tools.javac.util.Pair;
import java.util.List;

public class TicTacToeGame {
    Deque<Player>players;
    Board gameBoard;

    public TicTacToeGame() {
        initializeGame();
    }

    public void initializeGame() {
        players = new LinkedList<>();
        PlayingPieceX  crossPiece = new PlayingPieceX();
        Player player1 = new Player("Player1", crossPiece);

        PlayingPieceO noughtPiece = new PlayingPieceO();
        Player player2 = new Player("Player2", noughtPiece);

        players.add(player1);
        players.add(player2);

        gameBoard = new Board(3);
    }

    public String startGame() {
        boolean noWinner = true;
        while(noWinner) {
            //take out first player and also put in list back
            Player playerTurn = players.removeFirst();

            gameBoard.printBoard();
            List<Pair<Integer, Integer>> freeSpaces = gameBoard.getFreeCells();
            if(freeSpaces.isEmpty()) {
                noWinner = false;
                continue;
            }

            //reads the user input
            System.out.println("Player: " + playerTurn.name + "Enter row, column: ");
            Scanner inputScanner = new Scanner(System.in);
            String s = inputScanner.nextLine();
            String [] values = s.split(",");
            int inputRow = Integer.valueOf(values[0]);
            int inputColumn = Integer.valueOf(values[1]);

            //place the piece
            boolean pieceAddedSuccessfully  = gameBoard.addPiece(inputRow, inputColumn, playerTurn.playingPiece);
            if(!pieceAddedSuccessfully) {
                System.out.println("incorrect position, try again !!");
                players.addFirst(playerTurn);
                continue;
            }

            players.addLast(playerTurn);
            boolean winner = isThereWinner(inputRow, inputColumn, playerTurn.getPlayingPiece());
            if(winner) {
                return playerTurn.name;
            }

        }
        return "tie";
    }

    public boolean isThereWinner(int row, int column, PlayingPiece playingPiece) {
        boolean rowMatch = true;
        boolean columnMatch = true;
        boolean diagonalMatch = true;
        boolean antiDiagonalMatch = true;

        //need to check in row
        for(int i=0;i<gameBoard.size;i++) {

            if(gameBoard.board[row][i] == null || gameBoard.board[row][i].pieceType != playingPiece.getPieceType()) {
                rowMatch = false;
            }
        }

        //need to check in column
        for(int i=0;i<gameBoard.size;i++) {

            if(gameBoard.board[i][column] == null || gameBoard.board[i][column].pieceType != playingPiece.getPieceType()) {
                columnMatch = false;
            }
        }

        //need to check diagonals
        for(int i=0, j=0; i<gameBoard.size;i++,j++) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != playingPiece.getPieceType()) {
                diagonalMatch = false;
            }
        }

        //need to check anti-diagonals
        for(int i=0, j=gameBoard.size-1; i<gameBoard.size;i++,j--) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != playingPiece.getPieceType()) {
                antiDiagonalMatch = false;
            }
        }

        return rowMatch || columnMatch || diagonalMatch || antiDiagonalMatch;

    }

}
