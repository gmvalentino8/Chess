package model.pieces;

import model.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KingTest {
    Game game = new Game();
    Piece testKing;

    @Before
    public void setUp() throws Exception {
        game.gameBoard.addPiece(Piece.PieceType.King, game.currPlayer, 4, 4);
        testKing = game.gameBoard.gameState[4][4].currentPiece;
        game.gameBoard.addPiece(Piece.PieceType.King, game.nextPlayer, 0,0);
    }

    @Test
    public void testInitialization() {
        testKing = new King(game.currPlayer.playerColor, 4, 4);
        assertEquals(Piece.Color.White, testKing.color);
        assertEquals(4, testKing.xPosition);
        assertEquals(4, testKing.yPosition);
        assertEquals(Piece.PieceType.King, testKing.type);
        assertEquals(true, testKing.live);
    }

    @Test
    public void testOutOfBoundsInitialization() {
        try {
            testKing = new King(game.currPlayer.playerColor, 9, 4);
        }
        catch (Exception e) {
            assertEquals("Position is out of bounds", e.getMessage());
        }
    }

    @Test
    public void testKingCheckMove() {
        // Check for valid diagonal Movement
        game.makeMove(game.currPlayer,4,4, 5, 5, false);
        assertEquals(5, testKing.xPosition);
        assertEquals(5, testKing.yPosition);
        // Check for valid horizontal Movement
        game.makeMove(game.currPlayer,5,5, 6, 5, false);
        assertEquals(6, testKing.xPosition);
        assertEquals(5, testKing.yPosition);
        // Check for valid vertical Movement
        game.makeMove(game.currPlayer, 6,5, 6, 6, false);
        assertEquals(6, testKing.xPosition);
        assertEquals(6, testKing.yPosition);
    }

    @Test
    public void testKingInvalidCheckMove() {
        // Check for invalid movement
        game.makeMove(game.currPlayer, 4,4, 6, 7, false);
        assertEquals(4, testKing.xPosition);
        assertEquals(4,testKing.yPosition);
        // Check for obstacle in path
        game.gameBoard.addPiece(Piece.PieceType.Queen, game.currPlayer, 5,5);
        game.makeMove(game.currPlayer,4, 4, 5, 5, false);
        assertEquals(4, testKing.xPosition);
        assertEquals(4, testKing.yPosition);
    }
}