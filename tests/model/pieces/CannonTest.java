package model.pieces;

import model.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CannonTest {
    Game game = new Game();
    Piece testCannon;

    @Before
    public void setUp() throws Exception {
        game.gameBoard.addPiece(Piece.PieceType.Cannon, game.currPlayer, 4, 4);
        testCannon = game.gameBoard.gameState[4][4].currentPiece;
    }

    @Test
    public void testInitialization() {
        testCannon = new Cannon(game.currPlayer.playerColor, 4, 4);
        assertEquals(Piece.Color.White, testCannon.color);
        assertEquals(4, testCannon.xPosition);
        assertEquals(4, testCannon.yPosition);
        assertEquals(Piece.PieceType.Cannon, testCannon.type);
        assertEquals(true, testCannon.live);
    }

    @Test
    public void testOutOfBoundsInitialization() {
        try {
            testCannon = new Cannon(game.currPlayer.playerColor, 9, 4);
        }
        catch (Exception e) {
            assertEquals("Position is out of bounds", e.getMessage());
        }
    }

    @Test
    public void testCannonCheckMove() {
        // Check for valid horizontal Movement
        game.makeMove(game.currPlayer, 4,4, 6, 4, false);
        assertEquals(6, testCannon.xPosition);
        assertEquals(4, testCannon.yPosition);
        // Check for valid vertical Movement
        game.makeMove(game.currPlayer, 6,4, 6, 6, false);
        assertEquals(6, testCannon.xPosition);
        assertEquals(6, testCannon.yPosition);
        // Check for valid eating pattern
        game.gameBoard.addPiece(Piece.PieceType.Queen, game.currPlayer, 6,4);
        game.gameBoard.addPiece(Piece.PieceType.Queen, game.nextPlayer, 6,2);
        game.makeMove(game.currPlayer, 6,6,6,2, false);
        assertEquals(6, testCannon.xPosition);
        assertEquals(2, testCannon.yPosition);

    }

    @Test
    public void testCannonInvalidCheckMove() {
        // Check for invalid move
        game.makeMove(game.currPlayer, 4,4, 6, 7, false);
        assertEquals(4, testCannon.xPosition);
        assertEquals(4,testCannon.yPosition);
        // Check for piece in path
        game.gameBoard.addPiece(Piece.PieceType.Cannon, game.nextPlayer, 6,4);
        game.makeMove(game.currPlayer, 4, 4, 7, 4, false);
        assertEquals(4, testCannon.xPosition);
        assertEquals(4, testCannon.yPosition);
        // Check for invalid eating without hurdle
        game.makeMove(game.currPlayer, 4, 4, 6, 4, false);
        assertEquals(4, testCannon.xPosition);
        assertEquals(4, testCannon.yPosition);
        // Check for invalid eating with multiple hurdles
        game.gameBoard.addPiece(Piece.PieceType.Cannon, game.nextPlayer, 6,3);
        game.gameBoard.addPiece(Piece.PieceType.Cannon, game.nextPlayer, 6,2);
        game.makeMove(game.currPlayer, 4, 4, 6, 2, false);
        assertEquals(4, testCannon.xPosition);
        assertEquals(4, testCannon.yPosition);
    }
}