package model;

import model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * A Player is an object that holds basic information of a player
 * @author Gian Marco Valentino (gvalent2@illinois.edu)
 * $Rev:: 1                                                                $:  Revision of last commit
 * $Author:: gvalent2                                                      $:  Author of last commit
 * $Date:: 2017-09-19 00:00:00 -0600 (Tues, 19 Sep 2017)                   $:  Date of last commit
 */
public class Player {
    public String playerName;
    public Piece.Color playerColor;
    public List<Piece> pieceSet = new ArrayList<>();
    public int score;

    /**
     * Default constructor for player
     * @param color the color of the player
     */
    public Player(String playerName, Piece.Color color) {
        this.playerName = playerName;
        this.playerColor = color;
        this.score = 0;
    }
}
