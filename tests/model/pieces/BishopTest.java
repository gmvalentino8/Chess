package model.pieces;

import model.Game;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class BishopTest extends TestCase {
    Game game = new Game();
    Piece testBishop;

    @Before
    public void setUp() throws Exception {
        game.gameBoard.addPiece(Piece.PieceType.Bishop, game.currPlayer, 4, 4);
        testBishop = game.gameBoard.gameState[4][4].currentPiece;
    }

    @Test
    public void testInitialization() {
        testBishop = new Bishop(game.currPlayer.playerColor, 4, 4);
        assertEquals(Piece.Color.White, testBishop.color);
        assertEquals(4, testBishop.xPosition);
        assertEquals(4,testBishop.yPosition);
        assertEquals(Piece.PieceType.Bishop, testBishop.type);
        assertEquals(true, testBishop.live);
    }

    @Test
    public void testOutOfBoundsInitialization() {
        try {
            testBishop = new Bishop(game.currPlayer.playerColor, 9, 4);
        }
        catch (Exception e) {
            assertEquals("Position is out of bounds", e.getMessage());
        }
    }

    @Test
    public void testBishopCheckMove() {
        // Check for valid diagnoal movement
        game.makeMove(game.currPlayer, 4,4, 7, 7, false);
        assertEquals(7, testBishop.xPosition);
        assertEquals(7, testBishop.yPosition);
    }

    @Test
    public void testBishopInvalidCheckMove() {
        // Check for invalid movement
        game.makeMove(game.currPlayer, 4,4, 6, 7, false);
        assertEquals(4, testBishop.xPosition);
        assertEquals(4, testBishop.yPosition);
        // Check for piece in path
        game.gameBoard.addPiece(Piece.PieceType.Bishop, game.currPlayer, 6,6);
    game.makeMove(game.currPlayer, 4, 4, 7, 7, false);
        assertEquals(4, testBishop.xPosition);
        assertEquals(4, testBishop.yPosition);
    }

}