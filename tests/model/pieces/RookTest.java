package model.pieces;

import model.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RookTest {
    Game game = new Game();
    Piece testRook;

    @Before
    public void setUp() throws Exception {
        game.gameBoard.addPiece(Piece.PieceType.Rook, game.currPlayer, 4, 4);
        testRook = game.gameBoard.gameState[4][4].currentPiece;
    }

    @Test
    public void testInitialization() {
        testRook = new Rook(game.currPlayer.playerColor, 4, 4);
        assertEquals(Piece.Color.White, testRook.color);
        assertEquals(4, testRook.xPosition);
        assertEquals(4, testRook.yPosition);
        assertEquals(Piece.PieceType.Rook, testRook.type);
        assertEquals(true, testRook.live);
    }

    @Test
    public void testOutOfBoundsInitialization() {
        try {
            testRook = new Rook(game.currPlayer.playerColor, 9, 4);
        }
        catch (Exception e) {
            assertEquals("Position is out of bounds", e.getMessage());
        }
    }

    @Test
    public void testRookCheckMove() {
        // Check for valid horizontal Movement
        game.makeMove(game.currPlayer, 4,4, 6, 4, false);
        assertEquals(6, testRook.xPosition);
        assertEquals(4, testRook.yPosition);
        // Check for valid vertical Movement
        game.makeMove(game.currPlayer, 6,4, 6, 6, false);
        assertEquals(6, testRook.xPosition);
        assertEquals(6, testRook.yPosition);
    }

    @Test
    public void testRookInvalidCheckMove() {
        // Check for invalid move
        game.makeMove(game.currPlayer, 4,4, 6, 7, false);
        assertEquals(4, testRook.xPosition);
        assertEquals(4,testRook.yPosition);
        // Check for piece in path
        game.gameBoard.addPiece(Piece.PieceType.Rook, game.currPlayer, 6,4);
        game.makeMove(game.currPlayer, 4, 4, 7, 4, false);
        assertEquals(4, testRook.xPosition);
        assertEquals(4, testRook.yPosition);
    }
}