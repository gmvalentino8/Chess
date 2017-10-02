package model.pieces;

import model.Board;
import model.Player;
import model.Square;

public class King extends Piece {
    /**
     * Default constructor for a King piece
     * @param color the color of the piece
     * @param xPosition the x position of the piece
     * @param yPosition the y position of the piece
     */
    public King(Color color, int xPosition, int yPosition) {
        super(color, xPosition, yPosition, PieceType.King);
    }

    /**
     * Implements the rules for movement for a King
     * @param player the player making the move
     * @param board  the board the player is making the move on
     * @param target the target square the piece is being moved to
     * @return true if the move is valid, false if the move is not valid
     */
    @Override
    public boolean checkMove(Player player, Board board, Square target) {
        //Check for only moving 1 square in any direction
        return Math.abs(target.xPosition - xPosition) < 2 && Math.abs(target.yPosition - yPosition) < 2;

    }

}
