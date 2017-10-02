package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MoveTest {
    @Test
    public void testMoveDefaultConstructor() {
        Move move = new Move();
        assertEquals(null, move.originSquare);
        assertEquals(null, move.targetSquare);
        assertEquals(null, move.selectedPiece);
        assertEquals(null, move.takenPiece);
    }
}