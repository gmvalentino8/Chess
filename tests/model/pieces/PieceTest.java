package model.pieces;

import junit.framework.TestCase;
import model.Board;
import model.Player;
import model.Square;
import model.pieces.Piece;
import org.junit.Test;

public class PieceTest extends TestCase {
    @Test
    public void testPieceConstructor() {
        Piece piece = new Piece(Piece.Color.White, 0,0, Piece.PieceType.King) {
            @Override
            public boolean checkMove(Player player, Board board, Square target) {
                return false;
            }
        };
        assertEquals(Piece.Color.White, piece.color);
        assertEquals(0, piece.xPosition);
        assertEquals(0, piece.yPosition);
        assertEquals(Piece.PieceType.King,piece.type);
        assertEquals(true, piece.live);
    }

    @Test
    public void testFailPieceConstructor() {
        try {
            Piece piece = new Piece(Piece.Color.White, 9, 9, Piece.PieceType.King) {
                @Override
                public boolean checkMove(Player player, Board board, Square target) {
                    return false;
                }
            };
        }
        catch (Exception e) {
            assertEquals(e.getMessage(), "Position is out of bounds");
        }
    }

    @Test
    public void testCheckHorizontalMovement() {
        Board board = new Board();
        Player white = new Player("Player", Piece.Color.White);
        board.addPiece(Piece.PieceType.Queen, white,0,0);
        Piece tester = board.gameState[0][0].currentPiece;
        boolean test;
        // Test no obstacles
        test = tester.checkHorizontalMovement(board, 0,0,4,0, false);
        assertEquals(true, test);
        // Test non-horizontal movement
        test = tester.checkHorizontalMovement(board, 0,0,4,3, false);
        assertEquals(false, test);
        // Test obstacle in path
        board.addPiece(Piece.PieceType.King, white, 2,0);
        test = tester.checkHorizontalMovement(board, 0,0,4,0, false);
        assertEquals(false, test);
    }

    @Test
    public void testCheckVerticalMovement() {
        Board board = new Board();
        Player white = new Player("Player", Piece.Color.White);
        board.addPiece(Piece.PieceType.Queen, white,0,0);
        Piece tester = board.gameState[0][0].currentPiece;
        boolean test;
        // Test no obstacles
        test = tester.checkVerticalMovement(board, 0,0,0,4, false);
        assertEquals(true, test);
        // Test non-horizontal movement
        test = tester.checkVerticalMovement(board, 0,0,3,4, false);
        assertEquals(false, test);
        // Test obstacle in path
        board.addPiece(Piece.PieceType.King, white, 0,2);
        test = tester.checkVerticalMovement(board, 0,0,0,4, false);
        assertEquals(false, test);
    }

    @Test
    public void testCheckDiagonalMovement() {
        Board board = new Board();
        Player white = new Player("Player", Piece.Color.White);
        board.addPiece(Piece.PieceType.Queen, white,0,0);
        Piece tester = board.gameState[0][0].currentPiece;
        boolean test;
        // Test no obstacles
        test = tester.checkDiagonalMovement(board, 0,0,4,4, false);
        assertEquals(true, test);
        // Test non-horizontal movement
        test = tester.checkDiagonalMovement(board, 0,0,3,4, false);
        assertEquals(false, test);
        // Test obstacle in path
        board.addPiece(Piece.PieceType.King, white, 2,2);
        test = tester.checkDiagonalMovement(board, 0,0,4,4, false);
        assertEquals(false, test);
    }

    @Test
    public void testLeaperMovement() {
        Board board = new Board();
        Player white = new Player("Player", Piece.Color.White);
        board.addPiece(Piece.PieceType.Knight, white,0,0);
        Piece tester = board.gameState[0][0].currentPiece;
        boolean test;
        // Test no obstacles
        test = tester.checkLeaperMovement(0,0,2,1,1,2);
        assertEquals(true, test);
        // Test non-valid movement
        test = tester.checkLeaperMovement(0,0,3,1,1,2);
        assertEquals(false, test);
        // Test obstacle in path
        board.addPiece(Piece.PieceType.King, white, 1,1);
        test = tester.checkLeaperMovement(0,0,2,1,1,2);
        assertEquals(true, test);
    }

    @Test
    public void testLeapRiderMovement() {
        Board board = new Board();
        Player white = new Player("Player", Piece.Color.White);
        board.addPiece(Piece.PieceType.Knight, white,0,0);
        Piece tester = board.gameState[0][0].currentPiece;
        boolean test;
        // Test no obstacles
        test = tester.checkLeapRiderMovement(0,0,2,3,2,3);
        assertEquals(true, test);
        // Test non-valid movement
        test = tester.checkLeapRiderMovement(0,0,3,3,2,3);
        assertEquals(false, test);
        // Test obstacle in path
        board.addPiece(Piece.PieceType.King, white, 1,1);
        test = tester.checkLeapRiderMovement(0,0,4,6,2,3);
        assertEquals(true, test);
    }
}