package model.pieces;

import model.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZebraTest {

    Game game = new Game();
    Piece testZebrarider;

    @Before
    public void setUp() throws Exception {
        game.gameBoard.addPiece(Piece.PieceType.Zebra, game.currPlayer, 0, 0);
        testZebrarider = game.gameBoard.gameState[0][0].currentPiece;
    }

    @Test
    public void testInitialization() {
        testZebrarider = new Zebra(game.currPlayer.playerColor, 4, 4);
        assertEquals(Piece.Color.White, testZebrarider.color);
        assertEquals(4, testZebrarider.xPosition);
        assertEquals(4, testZebrarider.yPosition);
        assertEquals(Piece.PieceType.Zebra, testZebrarider.type);
        assertEquals(true, testZebrarider.live);
    }

    @Test
    public void testOutOfBoundsInitialization() {
        try {
            testZebrarider = new Zebra(game.currPlayer.playerColor, 9, 4);
        }
        catch (Exception e) {
            assertEquals("Position is out of bounds", e.getMessage());
        }
    }

    @Test
    public void setTestZebrariderCheckMove() {
        // Check for valid movement
        game.makeMove(game.currPlayer, 0,0, 2, 3, false);
        assertEquals(2, testZebrarider.xPosition);
        assertEquals(3, testZebrarider.yPosition);

    }

    @Test
    public void setTestZebrariderInvalidCheckMove() {
        // Check for invalid movement
        game.makeMove(game.currPlayer, 0,0, 4, 7, false);
        assertEquals(0, testZebrarider.xPosition);
        assertEquals(0,testZebrarider.yPosition);
    }

}