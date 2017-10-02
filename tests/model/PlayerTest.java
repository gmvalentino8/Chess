package model;

import junit.framework.TestCase;
import model.pieces.Piece;
import org.junit.Test;

public class PlayerTest extends TestCase {
    @Test
    public void testPlayerConstructor() {
        Player player = new Player("Player Name", Piece.Color.White);
        assertEquals("Player Name", player.playerName);
        assertEquals(Piece.Color.White, player.playerColor);
    }


}