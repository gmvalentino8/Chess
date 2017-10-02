package model.pieces;

import model.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KnightTest {
    Game game = new Game();
    Piece testKnight;

    @Before
    public void setUp() throws Exception {
        game.gameBoard.addPiece(Piece.PieceType.Knight, game.currPlayer, 4, 4);
        testKnight = game.gameBoard.gameState[4][4].currentPiece;
    }

    @Test
    public void testInitialization() {
        testKnight = new Knight(game.currPlayer.playerColor, 4, 4);
        assertEquals(Piece.Color.White, testKnight.color);
        assertEquals(4, testKnight.xPosition);
        assertEquals(4, testKnight.yPosition);
        assertEquals(Piece.PieceType.Knight, testKnight.type);
        assertEquals(true, testKnight.live);
    }

    @Test
    public void testOutOfBoundsInitialization() {
        try {
            testKnight = new Knight(game.currPlayer.playerColor, 9, 4);
        }
        catch (Exception e) {
            assertEquals("Position is out of bounds", e.getMessage());
        }
    }

    @Test
    public void testKnightCheckMove() {
        // Check for valid L-Movement
        game.makeMove(game.currPlayer, 4,4, 6, 5, false);
        assertEquals(6, testKnight.xPosition);
        assertEquals(5, testKnight.yPosition);

    }

    @Test
    public void testKnightInvalidCheckMove() {
        // Check for invalid movement
        game.makeMove(game.currPlayer, 4,4, 6, 7, false);
        assertEquals(4, testKnight.xPosition);
        assertEquals(4,testKnight.yPosition);
    }
}