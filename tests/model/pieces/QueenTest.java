package model.pieces;

import model.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueenTest {
    Game game = new Game();
    Piece testQueen;

    @Before
    public void setUp() throws Exception {
        game.gameBoard.addPiece(Piece.PieceType.Queen, game.currPlayer, 4, 4);
        testQueen = game.gameBoard.gameState[4][4].currentPiece;
    }

    @Test
    public void testInitialization() {
        testQueen = new Queen(game.currPlayer.playerColor, 4, 4);
        assertEquals(Piece.Color.White, testQueen.color);
        assertEquals(4, testQueen.xPosition);
        assertEquals(4, testQueen.yPosition);
        assertEquals(Piece.PieceType.Queen, testQueen.type);
        assertEquals(true, testQueen.live);
    }

    @Test
    public void testOutOfBoundsInitialization() {
        try {
            testQueen = new Queen(game.currPlayer.playerColor, 9, 4);
        }
        catch (Exception e) {
            assertEquals("Position is out of bounds", e.getMessage());
        }
    }

    @Test
    public void testQueenCheckMove() {
        // Check for valid diagonal Movement
        game.makeMove(game.currPlayer, 4,4, 6, 6, false);
        assertEquals(6, testQueen.xPosition);
        assertEquals(6, testQueen.yPosition);
        // Check for valid horizontal Movement
        game.makeMove(game.currPlayer, 6,6, 3, 6, false);
        assertEquals(3, testQueen.xPosition);
        assertEquals(6, testQueen.yPosition);
        // Check for valid vertical Movement
        game.makeMove(game.currPlayer, 3,6, 3, 3, false);
        assertEquals(3, testQueen.xPosition);
        assertEquals(3, testQueen.yPosition);
    }

    @Test
    public void testQueenInvalidCheckMove() {
        game.makeMove(game.currPlayer, 4,4, 6, 7, false);
        assertEquals(4, testQueen.xPosition);
        assertEquals(4,testQueen.yPosition);
        // Check for piece in path
        game.gameBoard.addPiece(Piece.PieceType.Queen, game.currPlayer, 6,6);
        game.makeMove(game.currPlayer, 4, 4, 7, 7, false);
        assertEquals(4, testQueen.xPosition);
        assertEquals(4, testQueen.yPosition);
    }

}