package model;

import junit.framework.TestCase;
import model.pieces.Piece;
import org.junit.Test;

public class GameTest extends TestCase {
    @Test
    public void testDefaultGameControllerConstructor() {
        Game game = new Game();
        assertEquals(game.gameBoard.gameState[0][0].currentPiece, null);
        assertEquals(Piece.Color.White, game.currPlayer.playerColor);
        assertEquals("Player 1", game.currPlayer.playerName);
        assertEquals(Piece.Color.Black, game.nextPlayer.playerColor);
        assertEquals("Player 2", game.nextPlayer.playerName);
    }

    @Test
    public void testCheckValidMove() {
        Game game;
        boolean valid;

        // Check for selecting empty square
        game = new Game();
        valid = game.checkValidMove(game.nextPlayer, 4,4,5,5);
        assertEquals(false, valid);

        // Check for selecting enemy piece
        game = new Game();
        game.gameBoard.addPiece(Piece.PieceType.King, game.nextPlayer, 4,4);
        valid = game.checkValidMove(game.currPlayer, 4,4,5,5);
        assertEquals(false, valid);

        // Check for no movement
        game = new Game();
        game.gameBoard.addPiece(Piece.PieceType.King, game.nextPlayer, 4,4);
        valid = game.checkValidMove(game.nextPlayer, 4,4,4,4);
        assertEquals(false, valid);

        // Check for own piece in target square
        game = new Game();
        game.gameBoard.addPiece(Piece.PieceType.King, game.nextPlayer, 4,4);
        game.gameBoard.addPiece(Piece.PieceType.Queen, game.nextPlayer, 5,5);
        valid = game.checkValidMove(game.nextPlayer, 4,4,5,5);
        assertEquals(false, valid);

        // Check for known non-valid move
        game = new Game();
        game.gameBoard.addPiece(Piece.PieceType.King, game.nextPlayer, 4,4);
        valid = game.checkValidMove(game.nextPlayer, 4,4,6,6);
        assertEquals(false, valid);

        // Check for known valid move
        game = new Game();
        game.gameBoard.addPiece(Piece.PieceType.King, game.nextPlayer, 4,4);
        valid = game.checkValidMove(game.nextPlayer, 4,4,5,5);
        assertEquals(true, valid);

    }

    @Test
    public void testTakeTurn() {
        // Take turn with Checkmate
        Game game = new Game();
        game.gameBoard.addPiece(Piece.PieceType.Rook, game.currPlayer,0,0);
        game.gameBoard.addPiece(Piece.PieceType.Rook, game.currPlayer, 0,2);
        game.gameBoard.addPiece(Piece.PieceType.King, game.currPlayer, 7,7);
        game.gameBoard.addPiece(Piece.PieceType.King, game.nextPlayer, 2,0);
        game.takeTurn(0,2,0,1);
        assertEquals(Game.GameCondition.Checkmate, game.gameCondition);
        // Take turn with Check
        game = new Game();
        game.gameBoard.addPiece(Piece.PieceType.Rook, game.currPlayer,0,2);
        game.gameBoard.addPiece(Piece.PieceType.King, game.currPlayer, 7,7);
        game.gameBoard.addPiece(Piece.PieceType.King, game.nextPlayer, 2,0);
        game.takeTurn(0,2,0,0);
        assertEquals(Game.GameCondition.Check, game.gameCondition);
        // Take turn with Stalemate
        game = new Game();
        game.gameBoard.addPiece(Piece.PieceType.Queen, game.currPlayer,5,1);
        game.gameBoard.addPiece(Piece.PieceType.King, game.currPlayer, 5,2);
        game.gameBoard.addPiece(Piece.PieceType.King, game.nextPlayer, 7,0);
        game.takeTurn(5,2,6,2);
        assertEquals(Game.GameCondition.Stalemate, game.gameCondition);
        // Take normal turn
        game = new Game();
        game.gameBoard.addPiece(Piece.PieceType.King, game.currPlayer, 0,0);
        game.gameBoard.addPiece(Piece.PieceType.King, game.nextPlayer, 7,7);
        game.takeTurn(0,0,1,1);
        assertEquals(Game.GameCondition.Normal, game.gameCondition);
        // Take invalid turn
        game = new Game();
        game.gameBoard.addPiece(Piece.PieceType.King, game.currPlayer, 0,0);
        game.gameBoard.addPiece(Piece.PieceType.King, game.nextPlayer, 7,7);
        game.takeTurn(7,7,8,8);
        assertEquals(Game.GameCondition.Invalid, game.gameCondition);
    }

    @Test
    public void testCheckCheck() {
        // Set up conditions for known check
        Game game = new Game();
        game.gameBoard.addPiece(Piece.PieceType.Rook, game.currPlayer,0,0);
        game.gameBoard.addPiece(Piece.PieceType.King, game.nextPlayer, 2,0);
        boolean check = game.checkCheck(game.nextPlayer);
        assertEquals(true, check);
        // Set up conditions for not check
        game = new Game();
        game.gameBoard.addPiece(Piece.PieceType.Rook, game.currPlayer,0,2);
        game.gameBoard.addPiece(Piece.PieceType.King, game.nextPlayer, 2,0);
        check = game.checkCheck(game.nextPlayer);
        assertEquals(false, check);
    }

    @Test
    public void testCheckCheckmate() {
        // Set up conditions for known checkmate
        Game game = new Game();
        game.gameBoard.addPiece(Piece.PieceType.Rook, game.currPlayer,0,0);
        game.gameBoard.addPiece(Piece.PieceType.Rook, game.currPlayer, 0,1);
        game.gameBoard.addPiece(Piece.PieceType.King, game.nextPlayer, 2,0);
        boolean checkmate = game.checkStaleCheckmate(game.nextPlayer);
        assertEquals(true, checkmate);
        //Set up conditions for not checkmate
        game = new Game();
        game.gameBoard.addPiece(Piece.PieceType.Rook, game.currPlayer,0,0);
        game.gameBoard.addPiece(Piece.PieceType.Rook, game.currPlayer, 0,1);
        game.gameBoard.addPiece(Piece.PieceType.King, game.nextPlayer, 2,1);
        checkmate = game.checkStaleCheckmate(game.nextPlayer);
        assertEquals(false, checkmate);
    }

    @Test
    public void testCheckStalemate() {
        // Set up conditions for known stalemate
        Game game = new Game();
        game.gameBoard.addPiece(Piece.PieceType.Queen, game.currPlayer,5,1);
        game.gameBoard.addPiece(Piece.PieceType.King, game.currPlayer, 6,2);
        game.gameBoard.addPiece(Piece.PieceType.King, game.nextPlayer, 7,0);
        boolean stalemate = game.checkStaleCheckmate(game.nextPlayer);
        assertEquals(true, stalemate);
        //Set up conditions for not stalemate
        game = new Game();
        game.gameBoard.addPiece(Piece.PieceType.Rook, game.currPlayer,0,0);
        game.gameBoard.addPiece(Piece.PieceType.Rook, game.currPlayer, 0,1);
        game.gameBoard.addPiece(Piece.PieceType.King, game.nextPlayer, 2,1);
        stalemate = game.checkStaleCheckmate(game.nextPlayer);
        assertEquals(false, stalemate);
    }

    @Test
    public void testSetupGame() {
        Game game = new Game();
        game.setupGame();
        // Check for one instance of each type of black piece
        assertEquals(Piece.PieceType.Rook, game.gameBoard.gameState[0][0].currentPiece.type);
        assertEquals(Piece.Color.Black, game.gameBoard.gameState[0][0].currentPiece.color);
        assertEquals(Piece.PieceType.Knight, game.gameBoard.gameState[1][0].currentPiece.type);
        assertEquals(Piece.Color.Black, game.gameBoard.gameState[1][0].currentPiece.color);
        assertEquals(Piece.PieceType.Bishop, game.gameBoard.gameState[2][0].currentPiece.type);
        assertEquals(Piece.Color.Black, game.gameBoard.gameState[2][0].currentPiece.color);
        assertEquals(Piece.PieceType.King, game.gameBoard.gameState[3][0].currentPiece.type);
        assertEquals(Piece.Color.Black, game.gameBoard.gameState[3][0].currentPiece.color);
        assertEquals(Piece.PieceType.Queen, game.gameBoard.gameState[4][0].currentPiece.type);
        assertEquals(Piece.Color.Black, game.gameBoard.gameState[4][0].currentPiece.color);
        assertEquals(Piece.PieceType.Pawn, game.gameBoard.gameState[0][1].currentPiece.type);
        assertEquals(Piece.Color.Black, game.gameBoard.gameState[0][1].currentPiece.color);
        // Check for one instance of each type of white piece
        assertEquals(Piece.PieceType.Rook, game.gameBoard.gameState[0][7].currentPiece.type);
        assertEquals(Piece.Color.White, game.gameBoard.gameState[0][7].currentPiece.color);
        assertEquals(Piece.PieceType.Knight, game.gameBoard.gameState[1][7].currentPiece.type);
        assertEquals(Piece.Color.White, game.gameBoard.gameState[1][7].currentPiece.color);
        assertEquals(Piece.PieceType.Bishop, game.gameBoard.gameState[2][7].currentPiece.type);
        assertEquals(Piece.Color.White, game.gameBoard.gameState[2][7].currentPiece.color);
        assertEquals(Piece.PieceType.King, game.gameBoard.gameState[3][7].currentPiece.type);
        assertEquals(Piece.Color.White, game.gameBoard.gameState[3][7].currentPiece.color);
        assertEquals(Piece.PieceType.Queen, game.gameBoard.gameState[4][7].currentPiece.type);
        assertEquals(Piece.Color.White, game.gameBoard.gameState[4][7].currentPiece.color);
        assertEquals(Piece.PieceType.Pawn, game.gameBoard.gameState[0][6].currentPiece.type);
        assertEquals(Piece.Color.White, game.gameBoard.gameState[0][6].currentPiece.color);
    }

}