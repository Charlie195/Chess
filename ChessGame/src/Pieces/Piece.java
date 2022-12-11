package Pieces;

import java.util.*;
import javax.swing.*;
import Runtime.GameEvents;

/**
 * Base Piece Object.
 * 
 * @author Charlie Li
 * @author Justin Hanna
 */
public class Piece {

    // Declare Object Variables
    public int posX, posY, colour;
    public Piece[][] selectedBoard;
    public ImageIcon pieceImg;

    /**
     * General Constructor for Pieces.
     * 
     * @param posX     X Co-Ordinate on Board.
     * @param posY     Y Co-Ordinate on Board.
     * @param colour   Colour of this piece.
     * @param selectedBoard    Piece Board.
     * @param pieceImg Assigned Piece Image.
     */
    public Piece(int posX, int posY, int colour, Piece[][] selectedBoard, ImageIcon pieceImg) {
        this.posX = posX;
        this.posY = posY;
        this.colour = colour;
        this.selectedBoard = selectedBoard;
        this.pieceImg = pieceImg;
        selectedBoard[posX][posY] = this;
//        this.selectedBoard[posX][posY] = this; test this
    }
    
    public Piece(Piece copy) {
        this.posX = copy.posX;
        this.posY = copy.posY;
        this.colour = copy.colour;
        this.pieceImg = copy.pieceImg;
    }

    /**
     * Method to generate a list of possible moves for this piece.
     * 
     * @param board Piece Board.
     * @return List of possible moves.
     */
    public ArrayList<int[]> possibleMoves(Piece[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();
        return moves;
    }

    /**
     * Method to print position and colour of this piece for debugging.
     */
    public void printValues() {
        System.out.println(posX + " " + posY + "\t" + colour);
    }

    /**
     * Method to return check state for King pieces, otherwise useless.
     * 
     * @param board Game Board.
     * @return Check state of King. Null if piece is not a King.
     */
    public boolean checked(Piece[][] board) {
        return false;
    }

    /**
     * Method to reset Piece Image to original value.
     */
    public void resetImg() {
    }
    
    public ArrayList<int[]> removeCheckedImpossibleMoves() {
        ArrayList<int[]> calculatingMoves = this.possibleMoves(GameEvents.board);
        Piece futureWhiteKing = null, futureBlackKing = null;
        
        for (int i = 0; i < calculatingMoves.size(); i++) {
            for (int j = 0; j < 8; j++){
                for (int k = 0; k < 8; k++) {
                    if (GameEvents.board[j][k] instanceof Pawn) {
                        GameEvents.futureBoard[j][k] = new Pawn((Pawn) GameEvents.board[j][k]);
                        GameEvents.futureBoard[j][k].selectedBoard = GameEvents.futureBoard;
                    }
                    else if (GameEvents.board[j][k] instanceof Knight) {
                        GameEvents.futureBoard[j][k] = new Knight((Knight) GameEvents.board[j][k]);
                        GameEvents.futureBoard[j][k].selectedBoard = GameEvents.futureBoard;
                    }
                    else if (GameEvents.board[j][k] instanceof Bishop) {
                        GameEvents.futureBoard[j][k] = new Bishop((Bishop) GameEvents.board[j][k]);
                        GameEvents.futureBoard[j][k].selectedBoard = GameEvents.futureBoard;
                    }
                    else if (GameEvents.board[j][k] instanceof Rook) {
                        GameEvents.futureBoard[j][k] = new Rook((Rook) GameEvents.board[j][k]);
                        GameEvents.futureBoard[j][k].selectedBoard = GameEvents.futureBoard;
                    }
                    else if (GameEvents.board[j][k] instanceof Queen) {
                        GameEvents.futureBoard[j][k] = new Queen((Queen) GameEvents.board[j][k]);
                        GameEvents.futureBoard[j][k].selectedBoard = GameEvents.futureBoard;
                    }
                    else if (GameEvents.board[j][k] instanceof King) {
                        GameEvents.futureBoard[j][k] = new King((King) GameEvents.board[j][k]);
                        GameEvents.futureBoard[j][k].selectedBoard = GameEvents.futureBoard;
                        
                        if (GameEvents.board[j][k].colour == 1) {
                            futureWhiteKing = GameEvents.futureBoard[j][k];
                        }
                        if (GameEvents.board[j][k].colour == 2) {
                            futureBlackKing = GameEvents.futureBoard[j][k];
                        }
                    }
                    else {
                        GameEvents.futureBoard[j][k] = new Piece(GameEvents.board[j][k]);
                        GameEvents.futureBoard[j][k].selectedBoard = GameEvents.futureBoard;
                    }
                }
            }
            
            // Initializing another piece object to copy the selected piece onto
            Piece oldPiece = GameEvents.futureBoard[this.posX][this.posY];

            // Change the coordinate properties of the selected piece
            oldPiece.posX = calculatingMoves.get(i)[0];
            oldPiece.posY = calculatingMoves.get(i)[1];
//          System.out.println("This is (" + totalPossibleWhitePieces.get(i)[0] + ", " + totalPossibleWhitePieces.get(i)[1] + ") Should be future board: " + oldPiece.selectedBoard);
//          System.out.println("This is (" + totalPossibleWhitePieces.get(i)[0] + ", " + totalPossibleWhitePieces.get(i)[1] + ") Should be board: " + board[oldPiece.posX][oldPiece.posY].selectedBoard);

            // Setting the selected position on the board to the selected piece
            GameEvents.futureBoard[oldPiece.posX][oldPiece.posY] = oldPiece;

            // Setting the original position of the piece to an empty piece
            GameEvents.futureBoard[this.posX][this.posY] = new Piece(this.posX, this.posY, 0, GameEvents.futureBoard, null);
            
            if (GameEvents.board[this.posX][this.posY].colour == 1) {
                if (futureWhiteKing.checked(GameEvents.futureBoard)) {
//                    System.out.println("This move is impossible: (" + calculatingMoves.get(i)[0] + ", " + calculatingMoves.get(i)[1] + ")");
                    calculatingMoves.remove(i);
                    i--;
                }
            }
            
            if (GameEvents.board[this.posX][this.posY].colour == 2) {
                if (futureBlackKing.checked(GameEvents.futureBoard)) {
//                    System.out.println("This move is impossible: (" + calculatingMoves.get(i)[0] + ", " + calculatingMoves.get(i)[1] + ")");
                    calculatingMoves.remove(i);
                    i--;
                }
            }
        }
        
        return calculatingMoves;
    }
}
