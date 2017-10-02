package model.pieces;

import model.Board;
import model.Player;
import model.Square;

public class Cannon extends Piece {
    /**
     * Default constructor for a Cannon piece
     * @param color the color of the piece
     * @param xPosition the x position of the piece
     * @param yPosition the y position of the piece
     */
    public Cannon(Piece.Color color, int xPosition, int yPosition) {
        super(color, xPosition, yPosition, PieceType.Cannon);
    }

    /**
     * Implements the rules for movement for a Cannon
     * @param player the player making the move
     * @param board  the board the player is making the move on
     * @param target the target square the piece is being moved to
     * @return true if the move is valid, false if the move is not valid
     */
    @Override
    public boolean checkMove(Player player, Board board, Square target) {
        // Checks if the piece moves in an leaper(1,2) shape
        return checkVerticalMovement(board, xPosition, yPosition, target.xPosition, target.yPosition, true)
                || checkHorizontalMovement(board, xPosition, yPosition, target.xPosition, target.yPosition, true);
    }
}
