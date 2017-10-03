package model.pieces;

import model.Board;
import model.Player;
import model.Square;

public class Zebra extends Piece {
    /**
     * Default constructor for a Zebra piece
     * @param color the color of the piece
     * @param xPosition the x position of the piece
     * @param yPosition the y position of the piece
     */
    public Zebra(Piece.Color color, int xPosition, int yPosition) {
        super(color, xPosition, yPosition, Piece.PieceType.Zebra);
    }

    /**
     * Implements the rules for movement for a Zebra
     * @param player the player making the move
     * @param board  the board the player is making the move on
     * @param target the target square the piece is being moved to
     * @return true if the move is valid, false if the move is not valid
     */
    @Override
    public boolean checkMove(Player player, Board board, Square target) {
        // Checks if the piece moves in an leaper(2,3) shape
        return checkLeaperMovement(xPosition, yPosition, target.xPosition, target.yPosition, 2,3);
    }
}
