package model;

import model.pieces.Piece;

import java.util.List;


/**
 * A Move is an object that holds basic information of a move made by a player
 * @author Gian Marco Valentino (gvalent2@illinois.edu)
 * $Rev:: 1                                                                $:  Revision of last commit
 * $Author:: gvalent2                                                      $:  Author of last commit
 * $Date:: 2017-09-19 00:00:00 -0600 (Tues, 19 Sep 2017)                   $:  Date of last commit
 */
public class Move {
    public Player currPlayer;
    public Player nextPlayer;
    public Piece selectedPiece;
    public Piece takenPiece;
    public Square originSquare;
    public Square targetSquare;
    public int originX;
    public int originY;
    public boolean pawnFirstMove;


    public static void printMove(Move move) {
        System.out.println("Curr Player: " + move.currPlayer
                + " Selected Piece: " + move.selectedPiece.type
                + " Taken Piece: " + move.takenPiece
                + " Origin Square X: " + move.originSquare.xPosition
                + " Origin Square Y: " + move.originSquare.yPosition
                + " Target Square X: " + move.targetSquare.xPosition
                + " Target Square Y: " + move.targetSquare.yPosition);
    }

    public static void printMoveList(List<Move> moves) {
        System.out.println("Move List: ");
        for (int i = 0; i < moves.size(); i++) {
            System.out.print(i + ". ");
            printMove(moves.get(i));
        }
    }

    /**
     * Default constructor for Move
     */
    public Move() {
        this.selectedPiece = null;
        this.takenPiece = null;
        this.originSquare = null;
        this.targetSquare = null;
        this.pawnFirstMove = false;
    }



}
