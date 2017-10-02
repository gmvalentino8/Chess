package model;

import junit.framework.TestCase;
import model.pieces.King;
import model.pieces.Piece;
import org.junit.Test;

public class SquareTest extends TestCase {
    @Test
    public void testSquareConstructor() {
        Square square = new Square(0,0);
        assertEquals(0, square.xPosition);
        assertEquals(0, square.yPosition);
        assertEquals(null, square.currentPiece);
    }

    @Test
    public void testCheckNoMovement() {
        Square square = new Square(0,0);
        // Test a case with no movement
        boolean noMovement = square.checkNoMovement(0,0);
        assertEquals(false, noMovement);
        // Test a case with movement
        boolean movement = square.checkNoMovement(1,1);
        assertEquals(true, movement);
    }

    @Test
    public void testCheckOutOfBoundsMove() {
        // Test negative x position
        boolean outOfBounds = Square.checkOutOfBounds(-1,0);
        assertEquals(true, outOfBounds);
        // Test negative y position
        outOfBounds = Square.checkOutOfBounds(0,-1);
        assertEquals(true, outOfBounds);
        // Test too big x position
        outOfBounds = Square.checkOutOfBounds(8,0);
        assertEquals(true, outOfBounds);
        // Test too big y position
        outOfBounds = Square.checkOutOfBounds(0,8);
        assertEquals(true, outOfBounds);
        // Test in bounds position
        outOfBounds = Square.checkOutOfBounds(0,0);
        assertEquals(false, outOfBounds);

    }

    @Test
    public void testEmptySquare() {
        // Create and test an empty square
        Square square = new Square(0,0);
        boolean emptySquare = square.checkEmptySquare();
        assertEquals(true, emptySquare);
        // Add a piece and test non-empty square
        square.currentPiece = new King(Piece.Color.White, 0,0);
        boolean nonEmptySquare = square.checkEmptySquare();
        assertEquals(false, nonEmptySquare);
    }

    @Test
    public void testCheckPieceColor() {
        // Create square with a white piece on it
        Square square = new Square(0,0);
        square.currentPiece = new King(Piece.Color.White, 0, 0);
        // Check for same color as white player
        Player white = new Player("Player", Piece.Color.White);
        boolean sameColor = square.checkPieceColor(white);
        assertEquals(true, sameColor);
        // Check for same color as black player
        Player black = new Player("Player", Piece.Color.Black);
        boolean diffColor = square.checkPieceColor(black);
        assertEquals(false, diffColor);
    }

}