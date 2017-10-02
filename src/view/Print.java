package view;

import model.*;
import model.pieces.Piece;


/**
 * Class for temporary GUI
 * @author Gian Marco Valentino (gvalent2@illinois.edu)
 * $Rev:: 1                                                                $:  Revision of last commit
 * $Author:: gvalent2                                                      $:  Author of last commit
 * $Date:: 2017-09-19 00:00:00 -0600 (Tues, 19 Sep 2017)                   $:  Date of last commit
 */
public class Print {
    /**
     * Prints the board with piece information
     * @param board the board to print
     */
    public static void printBoard(Board board) {
        // Create white space and number labels for x axis
        System.out.print("   ");
        for (int i = 0; i < 8; i++) {
            System.out.print(" " + i + "  ");
        }
        System.out.println("\n");
        // Print the piece information for each row and column
        for (int y = 0; y < 8; y++) {
            // Print the number label for y axis
            System.out.print (" " + y + " ");
            // Find the piece and print the short string
            for (int x = 0; x < 8; x++) {
                Piece currPiece = board.gameState[x][y].currentPiece;
                // Represent no piece as --
                if (currPiece == null) {
                    System.out.print(" -- ");
                }
                else {
                    String pieceString = pieceToSimpleString(currPiece);
                    System.out.print(pieceString);
                }
            }
            System.out.println("\n");
        }
    }

    /**
     * Converts piece information to shortened string
     * @param piece the piece to convert to string
     * @return shortened string with piece color and type
     */
    public static String pieceToSimpleString(Piece piece) {
        String color = "?";
        String name = "?";

        // Find the piece color and convert to shortened string
        switch (piece.color) {
            case White:
                color = "w";
                break;
            case Black:
                color = "b";
        }
        // Find the piece type and convert to shortened string
        switch (piece.type) {
            case Bishop:
                name = "B";
                break;
            case King:
                name = "K";
                break;
            case Knight:
                name = "N";
                break;
            case Pawn:
                name = "P";
                break;
            case Queen:
                name = "Q";
                break;
            case Rook:
                name = "R";
                break;
        }
        // Return the piece color and name in this format
        return " " + color + name + " ";
    }
}