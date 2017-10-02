package model.pieces;

import model.Board;
import model.Player;
import model.Square;

public class Bishop extends Piece {
    /**
     * Default constructor for a Bishop piece
     * @param color the color of the piece
     * @param xPosition the x position of the piece
     * @param yPosition the y position of the piece
     */
    public Bishop(Color color, int xPosition, int yPosition) {
        super(color, xPosition, yPosition, PieceType.Bishop);
    }

    /**
     * Implements the rules for movement for a Bishop
     * @param player the player making the move
     * @param board  the board the player is making the move on
     * @param target the target square the piece is being moved to
     * @return true if the move is valid, false if the move is not valid
     */
    @Override
    public boolean checkMove(Player player, Board board, Square target) {
        // Check for diagonal movement
        return checkDiagonalMovement(board, xPosition, yPosition, target.xPosition, target.yPosition, false);
    }

}
