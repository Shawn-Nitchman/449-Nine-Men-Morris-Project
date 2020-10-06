package NineMensMorris;
import NineMensMorris.Game;
import NineMensMorris.Move;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Enumeration;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game.GamePlay theGame;

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
}
