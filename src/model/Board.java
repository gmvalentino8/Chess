package model;

import model.pieces.*;


/**
 * A Board is an object that holds basic information of the game board
 * @author Gian Marco Valentino (gvalent2@illinois.edu)
 * $Rev:: 1                                                                $:  Revision of last commit
 * $Author:: gvalent2                                                      $:  Author of last commit
 * $Date:: 2017-09-19 00:00:00 -0600 (Tues, 19 Sep 2017)                   $:  Date of last commit
 */
public class Board {
    public static int boardWidth = 8;
    public static int boardHeight = 8;
    public Square[][] gameState = new Square[boardHeight][boardWidth];

    /**
     * Default constructor for board
     */
    public Board() {
        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardHeight; y++) {
                gameState[x][y] = new Square(x,y);
            }
        }
    }

    /**
     * Adds a new piece to the board and updates the player's piece list
     * @param type the type of piece being added
     * @param player the player the piece is allocated to
     * @param xPosition the x position of the piece
     * @param yPosition the y position of the piece
     */
    public void addPiece(Piece.PieceType type, Player player, int xPosition, int yPosition) {
        Square.checkOutOfBounds(xPosition, yPosition);
        Piece newPiece;
        switch (type) {
            // For each case, create a new piece, then add it to the gameState and player's piece set
            case Bishop:
                newPiece = new Bishop(player.playerColor, xPosition, yPosition);
                gameState[xPosition][yPosition].currentPiece = newPiece;
                player.pieceSet.add(newPiece);
                break;
            case Knight:
                newPiece = new Knight(player.playerColor, xPosition, yPosition);
                gameState[xPosition][yPosition].currentPiece = newPiece;
                player.pieceSet.add(newPiece);
                break;
            case King:
                newPiece = new King(player.playerColor, xPosition, yPosition);
                gameState[xPosition][yPosition].currentPiece = newPiece;
                player.pieceSet.add(newPiece);
                break;
            case Pawn:
                newPiece = new Pawn(player.playerColor, xPosition, yPosition);
                gameState[xPosition][yPosition].currentPiece = newPiece;
                player.pieceSet.add(newPiece);
                break;
            case Queen:
                newPiece = new Queen(player.playerColor, xPosition, yPosition);
                gameState[xPosition][yPosition].currentPiece = newPiece;
                player.pieceSet.add(newPiece);
                break;
            case Rook:
                newPiece = new Rook(player.playerColor, xPosition, yPosition);
                gameState[xPosition][yPosition].currentPiece = newPiece;
                player.pieceSet.add(newPiece);
                break;
            case Cannon:
                newPiece = new Cannon(player.playerColor, xPosition, yPosition);
                gameState[xPosition][yPosition].currentPiece = newPiece;
                player.pieceSet.add(newPiece);
                break;
            case Zebra:
                newPiece = new Zebra(player.playerColor, xPosition, yPosition);
                gameState[xPosition][yPosition].currentPiece = newPiece;
                player.pieceSet.add(newPiece);
                break;

        }
    }
}
