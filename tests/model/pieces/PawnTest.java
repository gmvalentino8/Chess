package model.pieces;

import controller.GameController;
import model.Board;
import model.Game;
import model.Player;
import org.junit.Before;
import org.junit.Test;
import view.GameView;

import static org.junit.Assert.*;

public class PawnTest {
    Game game = new Game();
    Piece testPawn;

    @Before
    public void setUp() throws Exception {
        game.gameBoard.addPiece(Piece.PieceType.Pawn, game.currPlayer, 4, 4);
        testPawn = game.gameBoard.gameState[4][4].currentPiece;
    }

    @Test
    public void testInitialization() {
        testPawn = new Pawn(game.currPlayer.playerColor, 4, 4);
        assertEquals(Piece.Color.White, testPawn.color);
        assertEquals(4, testPawn.xPosition);
        assertEquals(4, testPawn.yPosition);
        assertEquals(Piece.PieceType.Pawn, testPawn.type);
        assertEquals(true, testPawn.live);
    }

    @Test
    public void testOutOfBoundsInitialization() {
        try {
            testPawn = new Pawn(game.currPlayer.playerColor, 9, 4);
        }
        catch (Exception e) {
            assertEquals("Position is out of bounds", e.getMessage());
        }
    }

    @Test
    public void testBug() {
        GameController game = new GameController(new Game(), new GameView(Board.boardWidth, Board.boardHeight));
        Piece testPiece = game.gameModel.gameBoard.gameState[0][6].currentPiece;
        game.gameModel.takeTurn(0,6,0,4);
        assertEquals(0, testPiece.xPosition);
        assertEquals(4, testPiece.yPosition);
    }

    @Test
    public void testPawnCheckMove() {
        // Check for first turn 2-step forward Movement
        game.makeMove(game.currPlayer, 4,4, 4, 2, false);
        assertEquals(4, testPawn.xPosition);
        assertEquals(2, testPawn.yPosition);
        // Check for valid forward Movement
        game.makeMove(game.currPlayer, 4,2, 4, 1, false);
        assertEquals(4, testPawn.xPosition);
        assertEquals(1, testPawn.yPosition);
        // Check diagonal movement when eating enemy piece
        game.gameBoard.addPiece(Piece.PieceType.Pawn, game.nextPlayer, 3, 0);
        game.makeMove(game.currPlayer, 4,1, 3, 0, false);
        assertEquals(3, testPawn.xPosition);
        assertEquals(0, testPawn.yPosition);
    }

    @Test
    public void testPawnInvalidCheckMove() {
        // Check for obstacle in first turn 2-step forward movement
        game.gameBoard.addPiece(Piece.PieceType.Pawn, game.currPlayer, 4,3);
        game.makeMove(game.currPlayer, 4, 4, 4, 2, false);
        assertEquals(4, testPawn.xPosition);
        assertEquals(4, testPawn.yPosition);
        // Check for moving backwards
        game.makeMove(game.currPlayer, 4,4, 4, 5, false);
        assertEquals(4, testPawn.xPosition);
        assertEquals(4,testPawn.yPosition);
        // Check for moving diagonally without eating
        game.makeMove(game.currPlayer, 4,4, 3, 3, false);
        assertEquals(4, testPawn.xPosition);
        assertEquals(4,testPawn.yPosition);
        // Check for piece in path
        game.makeMove(game.currPlayer, 4, 4, 4, 3, false);
        assertEquals(4, testPawn.xPosition);
        assertEquals(4, testPawn.yPosition);
    }

}