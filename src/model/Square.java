package model;

import model.pieces.Piece;

/**
 * A Square is an object that holds basic information a single square on the board
 * @author Gian Marco Valentino (gvalent2@illinois.edu)
 * $Rev:: 1                                                                $:  Revision of last commit
 * $Author:: gvalent2                                                      $:  Author of last commit
 * $Date:: 2017-09-19 00:00:00 -0600 (Tues, 19 Sep 2017)                   $:  Date of last commit
 */
public class Square {
    public int xPosition;
    public int yPosition;
    public Piece currentPiece;

    /**
     * Default constructor for a square
     * @param x the x position of the square
     * @param y the y position of the square
     */
    public Square(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        this.currentPiece = null;
    }

    /**
     * Method to check if the piece is not moved
     * @param originX the x position of the origin
     * @param originY the y position of the origin
     * @return true if there is movement, false if there is no movement
     */
    public boolean checkNoMovement(int originX, int originY) {
        if (xPosition == originX && yPosition == originY) {
            return false;
        }
        return true;
    }

    /**
     * Method to check if a coordinate is out of bounds
     * @param xPosition the x position of the item being checked
     * @param yPosition the y position of the item being checked
     * @return true if out of bounds, false if in bounds
     */
     public static boolean checkOutOfBounds(int xPosition, int yPosition) {
         return xPosition < 0 || xPosition >= Board.boardWidth || yPosition < 0 || yPosition >= Board.boardHeight;
     }

    /**
     * Method to check if a square is empty
     * @return true if the square is empty, false if the square is not empty
     */
    public boolean checkEmptySquare() {
        return this.currentPiece == null;
    }

    /**
     * Method to check if the the piece on a square the same color as the given player
     * @param player the player who's piece to check
     * @return true if the piece belongs to the player, false if the piece does not belong to the player
     */
    public boolean checkPieceColor(Player player) {
        return currentPiece.color == player.playerColor;
    }

}
