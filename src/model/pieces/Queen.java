package model.pieces;

import model.Board;
import model.Player;
import model.Square;

/**
 * A Queen is an abstract class that represents a Queen piece in chess
 * @author Gian Marco Valentino (gvalent2@illinois.edu)
 * $Rev:: 1                                                                $:  Revision of last commit
 * $Author:: gvalent2                                                      $:  Author of last commit
 * $Date:: 2017-09-19 00:00:00 -0600 (Tues, 19 Sep 2017)                   $:  Date of last commit
 */
public class Queen extends Piece {
    /**
     * Default constructor for a Queen piece
     * @param color the color of the piece
     * @param xPosition the x position of the piece
     * @param yPosition the y position of the piece
     */
    public Queen(Color color, int xPosition, int yPosition) {
        super(color, xPosition, yPosition, PieceType.Queen);
    }

    /**
     * Implements the rules for movement for a Queen
     * @param player the player making the move
     * @param board  the board the player is making the move on
     * @param target the target square the piece is being moved to
     * @return true if the move is valid, false if the move is not valid
     */
    @Override
    public boolean checkMove(Player player, Board board, Square target) {
        // Checks for horizontal, vertical and diagonal movement
        return checkHorizontalMovement(board, xPosition, yPosition, target.xPosition, target.yPosition, false)
                || checkVerticalMovement(board, xPosition, yPosition, target.xPosition, target.yPosition, false)
                || checkDiagonalMovement(board, xPosition, yPosition, target.xPosition, target.yPosition, false);
    }

}
