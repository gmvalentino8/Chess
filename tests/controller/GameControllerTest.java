package controller;

import model.Board;
import model.Game;
import model.pieces.King;
import model.pieces.Piece;
import org.junit.Assert;
import org.junit.Test;
import view.GameView;

import javax.swing.*;

import java.awt.event.ActionListener;

import static org.junit.Assert.*;

public class GameControllerTest {
    @Test
    public void testInitialization() {
        GameController gameController = new GameController(new Game(), new GameView(Board.boardWidth, Board.boardHeight));
        // Check for one instance of each type of black piece
        assertEquals(Piece.PieceType.Rook, gameController.gameModel.gameBoard.gameState[0][0].currentPiece.type);
        assertEquals(Piece.Color.Black, gameController.gameModel.gameBoard.gameState[0][0].currentPiece.color);
        assertEquals(Piece.PieceType.Knight, gameController.gameModel.gameBoard.gameState[1][0].currentPiece.type);
        assertEquals(Piece.Color.Black, gameController.gameModel.gameBoard.gameState[1][0].currentPiece.color);
        assertEquals(Piece.PieceType.Bishop, gameController.gameModel.gameBoard.gameState[2][0].currentPiece.type);
        assertEquals(Piece.Color.Black, gameController.gameModel.gameBoard.gameState[2][0].currentPiece.color);
        assertEquals(Piece.PieceType.King, gameController.gameModel.gameBoard.gameState[3][0].currentPiece.type);
        assertEquals(Piece.Color.Black, gameController.gameModel.gameBoard.gameState[3][0].currentPiece.color);
        assertEquals(Piece.PieceType.Queen, gameController.gameModel.gameBoard.gameState[4][0].currentPiece.type);
        assertEquals(Piece.Color.Black, gameController.gameModel.gameBoard.gameState[4][0].currentPiece.color);
        assertEquals(Piece.PieceType.Pawn, gameController.gameModel.gameBoard.gameState[0][1].currentPiece.type);
        assertEquals(Piece.Color.Black, gameController.gameModel.gameBoard.gameState[0][1].currentPiece.color);
        // Check for one instance of each type of white piece
        assertEquals(Piece.PieceType.Rook, gameController.gameModel.gameBoard.gameState[0][7].currentPiece.type);
        assertEquals(Piece.Color.White, gameController.gameModel.gameBoard.gameState[0][7].currentPiece.color);
        assertEquals(Piece.PieceType.Knight, gameController.gameModel.gameBoard.gameState[1][7].currentPiece.type);
        assertEquals(Piece.Color.White, gameController.gameModel.gameBoard.gameState[1][7].currentPiece.color);
        assertEquals(Piece.PieceType.Bishop, gameController.gameModel.gameBoard.gameState[2][7].currentPiece.type);
        assertEquals(Piece.Color.White, gameController.gameModel.gameBoard.gameState[2][7].currentPiece.color);
        assertEquals(Piece.PieceType.King, gameController.gameModel.gameBoard.gameState[3][7].currentPiece.type);
        assertEquals(Piece.Color.White, gameController.gameModel.gameBoard.gameState[3][7].currentPiece.color);
        assertEquals(Piece.PieceType.Queen, gameController.gameModel.gameBoard.gameState[4][7].currentPiece.type);
        assertEquals(Piece.Color.White, gameController.gameModel.gameBoard.gameState[4][7].currentPiece.color);
        assertEquals(Piece.PieceType.Pawn, gameController.gameModel.gameBoard.gameState[0][6].currentPiece.type);
        assertEquals(Piece.Color.White, gameController.gameModel.gameBoard.gameState[0][6].currentPiece.color);
    }

    @Test
    public void testSetupPieces() {
        GameController gameController = new GameController(new Game(), new GameView(Board.boardWidth, Board.boardHeight));
        assertEquals("./assets/BlackRook.png", gameController.gameView.squareButtons[0][0].getIcon().toString());
        assertEquals("./assets/BlackKnight.png", gameController.gameView.squareButtons[1][0].getIcon().toString());
        assertEquals("./assets/BlackBishop.png", gameController.gameView.squareButtons[2][0].getIcon().toString());
        assertEquals("./assets/BlackKing.png", gameController.gameView.squareButtons[3][0].getIcon().toString());
        assertEquals("./assets/BlackQueen.png", gameController.gameView.squareButtons[4][0].getIcon().toString());
        assertEquals("./assets/BlackPawn.png", gameController.gameView.squareButtons[0][1].getIcon().toString());
        assertEquals("./assets/WhiteRook.png", gameController.gameView.squareButtons[0][7].getIcon().toString());
        assertEquals("./assets/WhiteKnight.png", gameController.gameView.squareButtons[1][7].getIcon().toString());
        assertEquals("./assets/WhiteBishop.png", gameController.gameView.squareButtons[2][7].getIcon().toString());
        assertEquals("./assets/WhiteKing.png", gameController.gameView.squareButtons[3][7].getIcon().toString());
        assertEquals("./assets/WhiteQueen.png", gameController.gameView.squareButtons[4][7].getIcon().toString());
        assertEquals("./assets/WhitePawn.png", gameController.gameView.squareButtons[0][6].getIcon().toString());
    }

    @Test
    public void testGetPieceFilepath() {
        GameController gameController = new GameController(new Game(), new GameView(Board.boardWidth, Board.boardHeight));
        King testKing = new King(Piece.Color.White, 0, 0);
        String filepath = gameController.getPieceFilepath(testKing);
        assertEquals("./assets/WhiteKing.png", filepath);
    }

    @Test
    public void testListeners() {
        GameController gameController = new GameController(new Game(), new GameView(Board.boardWidth, Board.boardHeight));
        // Test for listeners
        for (int i = 0; i < Board.boardWidth; i++) {
            for (int j = 0; j < Board.boardHeight; j++) {
                ActionListener[] listeners = gameController.gameView.squareButtons[i][j].getActionListeners();
                assertEquals(true, listeners[0] instanceof GameController.SquareListener);
            }
        }
    }

}