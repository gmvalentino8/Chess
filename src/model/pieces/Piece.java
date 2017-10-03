package model.pieces;

import model.Board;
import model.Player;
import model.Square;

import java.util.List;

import static java.lang.Integer.signum;

/**
 * A Piece is an abstract class that holds basic information of piece
 * @author Gian Marco Valentino (gvalent2@illinois.edu)
 * $Rev:: 1                                                                $:  Revision of last commit
 * $Author:: gvalent2                                                      $:  Author of last commit
 * $Date:: 2017-09-19 00:00:00 -0600 (Tues, 19 Sep 2017)                   $:  Date of last commit
 */
public abstract class Piece {
    public enum Color {
        White, Black;
    }

    public enum PieceType {
        Bishop, King, Knight, Pawn, Queen, Rook, Zebra, Cannon
    }

    public PieceType type;
    public Color color;
    public int xPosition;
    public int yPosition;
    public boolean live;

    /**
     * Constructor for a Piece
     * @param color the color of the piece
     * @param xPosition the x position of the piece
     * @param yPosition the y position of the piece
     * @param type the type of the piece
     */
    public Piece(Color color, int xPosition, int yPosition, PieceType type) {
        // Check for position out of bounds
        if (Square.checkOutOfBounds(xPosition, yPosition)) {
            throw new IndexOutOfBoundsException("Position is out of bounds");
        }
        this.color = color;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.type = type;
        this.live = true;
    }

    /**
     * Each type of piece has to implement the rules for movement
     * @param player the player making the move
     * @param board the board the player is making the move on
     * @param target the target square the piece is being moved to
     * @return true of the move is valid, false if the move is invalid
     */
    public abstract boolean checkMove(Player player, Board board, Square target);

    /**
     * Checks validity for a horizontal movement
     * @param board the board the player is making the move on
     * @param originX the x position of the origin
     * @param originY the y position of the origin
     * @param targetX the x position of the target
     * @param targetY the y position of the target
     * @param hopper true for hopper movement
     * @return true if the move is valid, false if the move is invalid
     */
    protected boolean checkHorizontalMovement(Board board, int originX, int originY, int targetX, int targetY, boolean hopper) {
        // Boolean to check for a hurdle piece
        boolean hurdle = false;
        // Checks for horizontal movement
        if (Math.abs(targetX - originX) != 0 && Math.abs((targetY - originY)) == 0) {
            // Find the direction of movement
            int xDirection = signum(targetX - originX);
            // Check if there are obstacles in the path of movement
            for (int i = originX + xDirection; i != targetX; i += xDirection) {
                if (board.gameState[i][originY].currentPiece != null) {
                    // Make sure there is only 1 hurdle for hopper movement
                    if (hopper && !hurdle) {
                        hurdle = true;
                    }
                    else {
                        return false;
                    }
                }
            }
            // Check for hopper eating pattern
            if (hopper && ((hurdle && board.gameState[targetX][targetY].currentPiece == null)
                    || (!hurdle && board.gameState[targetX][targetY].currentPiece != null))) {
                return false;
            }
            // Passes all conditions, so return true
            return true;
        }
        return false;
    }

    /**
     * Checks validity for a vertical movement
     * @param board the board the player is making the move on
     * @param originX the x position of the origin
     * @param originY the y position of the origin
     * @param targetX the x position of the target
     * @param targetY the y position of the target
     * @param hopper true for hopper movement
     * @return true if the move is valid, false if the move is invalid
     */
    boolean checkVerticalMovement(Board board, int originX, int originY, int targetX, int targetY, boolean hopper) {
        // Boolean to check for a hurdle piece
        boolean hurdle = false;
        // Checks for vertical movement
        if (Math.abs(targetX - originX) == 0 && Math.abs((targetY - originY)) != 0) {
            // Find the direction of movement
            int yDirection = signum(targetY - originY);
            // Check if there are obstacles in the path of movement
            for (int i = originY + yDirection; i != targetY; i += yDirection) {
                if (board.gameState[originX][i].currentPiece != null) {
                    // Make sure there is only 1 hurdle for hopper movement
                    if (hopper && !hurdle) {
                        hurdle = true;
                    }
                    else {
                        return false;
                    }
                }
            }
            // Check for hopper eating pattern
            if (hopper && ((hurdle && board.gameState[targetX][targetY].currentPiece == null) || (!hurdle && board.gameState[targetX][targetY].currentPiece != null))) {
                return false;
            }
            // Passes all conditions, so return true
            return true;
        }
        return false;
    }

    /**
     * Checks validity for a diagonal movement
     * @param board the board the player is making the move on
     * @param originX the x position of the origin
     * @param originY the y position of the origin
     * @param targetX the x position of the target
     * @param targetY the y position of the target
     * @param hopper true for hopper movement
     * @return true if the move is valid, false if the move is invalid
     */
    boolean checkDiagonalMovement(Board board, int originX, int originY, int targetX, int targetY, boolean hopper) {
        // Boolean to check for a hurdle piece
        boolean hurdle = false;
        // Checks for diagonal movement
        if (Math.abs(targetX - originX) == Math.abs((targetY - originY))) {
            // Find the direction of movement
            int xDirection = signum(targetX - originX);
            int yDirection = signum(targetY - originY);
            // Check if there are obstacles in the path of movement or for hurdles in hoppermovement
            for (int i = originX + xDirection, j = originY + yDirection; i != targetX && j != targetY; i += xDirection, j += yDirection) {
                if (board.gameState[i][j].currentPiece != null) {
                    // Make sure there is only 1 hurdle for hopper movement
                    if (hopper && !hurdle) {
                       hurdle = true;
                    }
                    else {
                        return false;
                    }
                }
            }
            // Check for hopper eating pattern
            if (hopper && ((hurdle && board.gameState[targetX][targetY].currentPiece == null)
                    || (!hurdle && board.gameState[targetX][targetY].currentPiece != null))) {
                return false;
            }
            // Passes all conditions, so return true
            return true;
        }
        return false;
    }

    /**
     * Checks validity for a leaper movement
     * @param originX the x position of the origin
     * @param originY the y position of the origin
     * @param targetX the x position of the target
     * @param targetY the y position of the target
     * @param leapN the n component of the leap
     * @param leapM the m component of the leap
     * @return true if the move is valid, false if the move is invalid
     */
    boolean checkLeaperMovement(int originX, int originY, int targetX, int targetY, int leapN, int leapM) {
        // The move is valid if the piece moves N horizontally and M vertically, or M vertically and N horizontally
        return (Math.abs(targetX - originX) == leapN && Math.abs((targetY - originY)) == leapM) ||
                (Math.abs(targetX - originX) == leapM && Math.abs((targetY - originY)) == leapN);
    }

}
