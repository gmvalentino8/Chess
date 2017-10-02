package model;

import model.pieces.Piece;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void testDefaultBoardConstructor() {
        Board board = new Board();
        assertEquals(board.boardWidth, board.gameState.length);
        assertEquals(board.boardHeight, board.gameState[0].length);
        for (int i = 0; i < board.boardWidth; i++) {
            for (int j = 0; j < board.boardHeight; j++) {
                assertEquals(i, board.gameState[i][j].xPosition);
                assertEquals(j, board.gameState[i][j].yPosition);
                assertEquals(null, board.gameState[i][j].currentPiece);
            }
        }
    }

    @Test
    public void testAddPiece() {
        Board board = new Board();
        Player white = new Player("White", Piece.Color.White);
        Player black = new Player("Black", Piece.Color.Black);
        // Add one instance of every type of white piece
        board.addPiece(Piece.PieceType.Bishop, white, 0, 0);
        board.addPiece(Piece.PieceType.King, white, 0,1);
        board.addPiece(Piece.PieceType.Knight, white, 0,2);
        board.addPiece(Piece.PieceType.Pawn, white, 0,3);
        board.addPiece(Piece.PieceType.Queen, white, 0,4);
        board.addPiece(Piece.PieceType.Rook, white, 0,5);
        // Add one instance of every type of black piece
        board.addPiece(Piece.PieceType.Bishop, black, 1, 0);
        board.addPiece(Piece.PieceType.King, black, 1,1);
        board.addPiece(Piece.PieceType.Knight, black, 1,2);
        board.addPiece(Piece.PieceType.Pawn, black, 1,3);
        board.addPiece(Piece.PieceType.Queen, black, 1,4);
        board.addPiece(Piece.PieceType.Rook, black, 1,5);
        // Test to see if added white pieces are correct
        assertEquals(Piece.PieceType.Bishop, board.gameState[0][0].currentPiece.type);
        assertEquals(Piece.Color.White, board.gameState[0][0].currentPiece.color);
        assertEquals(Piece.PieceType.King, board.gameState[0][1].currentPiece.type);
        assertEquals(Piece.Color.White, board.gameState[0][1].currentPiece.color);
        assertEquals(Piece.PieceType.Knight, board.gameState[0][2].currentPiece.type);
        assertEquals(Piece.Color.White, board.gameState[0][2].currentPiece.color);
        assertEquals(Piece.PieceType.Pawn, board.gameState[0][3].currentPiece.type);
        assertEquals(Piece.Color.White, board.gameState[0][3].currentPiece.color);
        assertEquals(Piece.PieceType.Queen, board.gameState[0][4].currentPiece.type);
        assertEquals(Piece.Color.White, board.gameState[0][4].currentPiece.color);
        assertEquals(Piece.PieceType.Rook, board.gameState[0][5].currentPiece.type);
        assertEquals(Piece.Color.White, board.gameState[0][5].currentPiece.color);
        // Test to see if added black pieces are correct
        assertEquals(Piece.PieceType.Bishop, board.gameState[1][0].currentPiece.type);
        assertEquals(Piece.Color.Black, board.gameState[1][0].currentPiece.color);
        assertEquals(Piece.PieceType.King, board.gameState[1][1].currentPiece.type);
        assertEquals(Piece.Color.Black, board.gameState[1][1].currentPiece.color);
        assertEquals(Piece.PieceType.Knight, board.gameState[1][2].currentPiece.type);
        assertEquals(Piece.Color.Black, board.gameState[1][2].currentPiece.color);
        assertEquals(Piece.PieceType.Pawn, board.gameState[1][3].currentPiece.type);
        assertEquals(Piece.Color.Black, board.gameState[1][3].currentPiece.color);
        assertEquals(Piece.PieceType.Queen, board.gameState[1][4].currentPiece.type);
        assertEquals(Piece.Color.Black, board.gameState[1][4].currentPiece.color);
        assertEquals(Piece.PieceType.Rook, board.gameState[1][5].currentPiece.type);
        assertEquals(Piece.Color.Black, board.gameState[1][5].currentPiece.color);
    }

}