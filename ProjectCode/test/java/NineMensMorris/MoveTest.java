package NineMensMorris;
import NineMensMorris.Game;
import NineMensMorris.Move;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    final int START_COUNT = Game.START_COUNT;
    final Point IN_BAG = Game.IN_BAG;

    Game.GamePlay theGame;


    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        theGame = new Game.GamePlay();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        theGame = null;
    }


    @Test
    void isOpenTest() throws Exception {
        for (int i = 0; i < 3; i++ ) {
            for (int j = 0; j < 8; j++) {
                assertTrue(Move.isOpen(new Point(i, j)));
            }
        }
    }

    @Test
    void isFlyingTest() throws Exception {
        assertFalse(Move.isFlying(theGame.pl1));
        assertFalse(Move.isFlying(theGame.pl2));
    }

    @Test
    void moveTestOne() throws Exception {
        assertTrue(Move.move(theGame.pl2, IN_BAG, new Point(2,7)));
        assertTrue(Move.move(theGame.pl2, new Point(2,7), new Point(1,7)));
        assertFalse(Move.move(theGame.pl2, new Point(1,7), new Point(1,4)));
    }
}