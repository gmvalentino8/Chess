package model.pieces;

import model.Board;
import model.Player;
import model.Square;

import static java.lang.Integer.signum;

/**
 * A Pawn is an abstract class that represents a Pawn piece in chess
 * @author Gian Marco Valentino (gvalent2@illinois.edu)
 * $Rev:: 1                                                                $:  Revision of last commit
 * $Author:: gvalent2                                                      $:  Author of last commit
 * $Date:: 2017-09-19 00:00:00 -0600 (Tues, 19 Sep 2017)                   $:  Date of last commit
 */
public class Pawn extends Piece {
    public boolean firstMove;

    /**
     * Default constructor for a Pawn piece
     * @param color the color of the piece
     * @param xPosition the x position of the piece
     * @param yPosition the y position of the piece
     */
    public Pawn(Color color, int xPosition, int yPosition) {
        super(color, xPosition, yPosition, PieceType.Pawn);
        this.firstMove = true;
    }

    /**
     * Implements the rules for movement for a Pawn
     * @param player the player making the move
     * @param board  the board the player is making the move on
     * @param target the target square the piece is being moved to
     * @return true if the move is valid, false if the move is not valid
     */
    @Override
    public boolean checkMove(Player player, Board board, Square target) {
        int maxMove = firstMove ? 2 : 1;
        // System.out.println("Check: " + type.toString() + ":" + xPosition + ", " + yPosition + " MaxMove: " + maxMove);
        //Check for only moving 1 square forward
        if ((Math.abs(target.xPosition - xPosition) == 0 && Math.abs(target.yPosition - yPosition) <= maxMove)) {
            // Get vertical direction
            int yDirection = signum(target.yPosition - yPosition);
            // Check for obstacles when moving 2 spaces on the first move
            if(firstMove && (board.gameState[xPosition][yPosition + yDirection].currentPiece != null)) {
                // System.out.print("Exit at 1");
                return false;
            }
            // Check if pawn is moving upwards for white player or downwards for black player
            if (board.gameState[target.xPosition][target.yPosition].currentPiece == null
                && ((player.playerColor == Color.White && yDirection == -1) || (player.playerColor == Color.Black && yDirection == 1))) {
                return true;
            }
        }

        //Check for eating diagonally
        if (Math.abs(target.xPosition - xPosition) == 1 && Math.abs((target.yPosition - yPosition)) == 1) {
            int yDirection = signum(target.yPosition - yPosition);
            // Check if pawn is moving upwards for white player or downwards for black player
            if ((player.playerColor == Color.White && yDirection == -1) ||
                    (player.playerColor == Color.Black && yDirection == 1)) {
                // Pawn can only move diagonally when eating another piece
                return (target.currentPiece != null && !target.checkPieceColor(player));
            }
        }
        // System.out.println("Exit at 2");
        return false;
    }

}
