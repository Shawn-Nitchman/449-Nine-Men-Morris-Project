package NineMensMorris;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GameTest extends Game {

    NineMensMorris.GamePlay theGame;

    @BeforeEach
    void setUp() {
        theGame = new Game.GamePlay();
    }

    @AfterEach
    void tearDown() {
        theGame = null;
    }

    @Test
    public void FirstTest() throws Exception {
        assertTrue(Move.move(theGame.pl1, new Point(-1, -1), new Point(0,0)));
        assertTrue(Move.move(theGame.pl1, new Point(-1, -1), new Point(1,0)));
        assertFalse(Move.isOpen(new Point(0,0)));
        assertTrue(theGame.pl1.isPlacing());
        assertFalse(theGame.lostByPieceCount());

    }

    @Test
    public void ConstructorTest() throws Exception { //Tests the Constructor and InitPlayers

        assertEquals (theGame.getPlayers().size(),2); //Tests for 2 players

        for (Player player : theGame.getPlayers()) {
            assertEquals(player.getPieces().size(), 9); //Tests for 9 pieces each

            for (Piece piece : player.getPieces()) {
                assertEquals(piece.getPair(), Game.IN_BAG); //Test that all pieces are initialized
            }
        }
    }

    @Test
    public void lostByPieceCountTest() throws Exception {  //Tests the lostByPieceCount method
        //Set-up
        Vector<Piece> myVec = new Vector<Piece>();
        myVec.add(new Piece());
        myVec.add(new Piece());
        theGame.getPlayers().firstElement().setPieces(myVec);
        assertTrue(theGame.lostByPieceCount());

    }

}
