package model.pieces;

import model.Board;
import model.Player;
import model.Square;

/**
 * A Knight is an abstract class that represents a Knight piece in chess
 * @author Gian Marco Valentino (gvalent2@illinois.edu)
 * $Rev:: 1                                                                $:  Revision of last commit
 * $Author:: gvalent2                                                      $:  Author of last commit
 * $Date:: 2017-09-19 00:00:00 -0600 (Tues, 19 Sep 2017)                   $:  Date of last commit
 */
public class Knight extends Piece {
    /**
     * Default constructor for a Knight piece
     * @param color the color of the piece
     * @param xPosition the x position of the piece
     * @param yPosition the y position of the piece
     */
    public Knight(Color color, int xPosition, int yPosition) {
        super(color, xPosition, yPosition, PieceType.Knight);
    }

    /**
     * Implements the rules for movement for a Knight
     * @param player the player making the move
     * @param board  the board the player is making the move on
     * @param target the target square the piece is being moved to
     * @return true if the move is valid, false if the move is not valid
     */
    @Override
    public boolean checkMove(Player player, Board board, Square target) {
        // Checks if the piece moves in an leaper(1,2) shape
        return checkLeaperMovement(xPosition, yPosition, target.xPosition, target.yPosition, 1, 2);
    }

}
