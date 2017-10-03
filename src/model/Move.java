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
