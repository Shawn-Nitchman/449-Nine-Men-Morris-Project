package NineMensMorris;
import org.junit.jupiter.api.Test;
import java.util.Vector;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {
        Gui theGui;
        Game.GamePlay theGame;


        @org.junit.jupiter.api.BeforeEach
        void setUp() {
            theGui = new Gui();
            theGame = new Game.GamePlay();
        }

        @org.junit.jupiter.api.AfterEach
        void tearDown() {
            theGui = null;
            theGame = null;
        }

    @Test
    public void isOpenTest() throws Exception {
        //Check empty board
        for (int i = 0; i < 3; i++ ) {
            for (int j = 0; j < 8; j++) {
                assertTrue(Move.isOpen(new Point(i, j)));
            }
        }

        //Setup
        Move.changeLocation(theGame.pl1, new Point(Game.IN_BAG), new Point(0,0));
        Move.changeLocation(theGame.pl2, new Point(Game.IN_BAG), new Point(0,4));
        Move.changeLocation(theGame.pl1, new Point(Game.IN_BAG), new Point(1,1));
        Move.changeLocation(theGame.pl2, new Point(Game.IN_BAG), new Point(2,1));

        //Test
        assertTrue(Move.isOpen(new Point(0,1)));
        assertTrue(Move.isOpen(new Point(0,1)));

        assertFalse(Move.isOpen(new Point(0,0)));
        assertFalse(Move.isOpen(new Point(0,4)));
        assertFalse(Move.isOpen(new Point(1,1)));
        assertFalse(Move.isOpen(new Point(2,1)));
    }

    @Test
    public void linkUpTest() {
        assertEquals(theGame, Move.getMyGame());
    }

    @Test
    public void isLegalTest() {
        theGame.pl1.setPieces(new Vector<>());
        theGame.pl2.setPieces(new Vector<>());

        assertTrue(Move.isLegal(new Piece(new Point(0, 0), theGame.pl1), new Point(0,1)));
        assertTrue(Move.isLegal(new Piece(new Point(0,0), theGame.pl1), new Point(0,1)));

        assertFalse(Move.isLegal(new Piece(new Point(2,3), theGame.pl1), new Point(0, 2)));
        assertFalse(Move.isLegal(new Piece (new Point(2,3), theGame.pl1), new Point(2, 5)));
    }

    @Test
    void moveTestOne() throws Exception {
        assertTrue(Move.changeLocation(theGame.pl1, Game.IN_BAG, new Point(0,0)));
        assertTrue(Move.changeLocation(theGame.pl1, new Point(0,0), new Point(0,7)));

        assertFalse(Move.changeLocation(theGame.pl1, new Point(0,7), new Point(2,7)));
    }

    @Test
    void removePieceTest() {
        Move.changeLocation(theGame.pl1, new Point(Game.IN_BAG), new Point(0,0));
        Move.changeLocation(theGame.pl2, new Point(Game.IN_BAG), new Point(0,4));
        Move.changeLocation(theGame.pl1, new Point(Game.IN_BAG), new Point(1,1));
        Move.changeLocation(theGame.pl2, new Point(Game.IN_BAG), new Point(2,1));

        ///Seems like quickTable contains 0,0
        assertFalse(Move.isOpen(new Point(0,0)));
        assertTrue(Move.removePiece(new Point(0,0)));
        assertTrue(Move.isOpen(new Point(0, 0)));
    }
}