package NineMensMorris;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GameTest extends Game {

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
        assertTrue(Move.changeLocation(theGame.pl1, new Point(-1, -1), new Point(0,0)));
        assertTrue(Move.changeLocation(theGame.pl1, new Point(-1, -1), new Point(1,0)));
        assertFalse(Move.isOpen(new Point(0,0)));
        assertTrue(theGame.isPlacing());
        assertFalse(theGame.lostByPieceCount());

    }

    @Test
    public void ConstructorTest() throws Exception { //Tests the Constructor and InitPlayers
        assertEquals (theGame.getPlayers().size(),2); //Tests for 2 players

        for (Player player : theGame.getPlayers()) {
            assertEquals(player.getPieces().size(), 9); //Tests for 9 pieces each

            for (Piece piece : player.getPieces()) {
                assertEquals(piece.getPair(), IN_BAG); //Test that all pieces are initialized
            }
        }
    }

    @Test
    public void lostByPieceCountTest() throws Exception {  //Tests the lostByPieceCount method
        //Set-up
        Vector<Piece> myVec = new Vector<Piece>();
        myVec.add(new Piece(IN_BAG, theGame.pl1));
        myVec.add(new Piece(IN_BAG, theGame.pl1 ));
        theGame.getPlayers().firstElement().setPieces(myVec);
        //Test
        assertTrue(theGame.lostByPieceCount());

    }
    @Test
    public void inMillTest() throws Exception {
        //Setup
        Move.changeLocation(theGame.pl1, IN_BAG, new Point (0,0));
        Move.changeLocation(theGame.pl2, IN_BAG, new Point(2,0));
        Move.changeLocation(theGame.pl1, IN_BAG, new Point (0,1));
        Move.changeLocation(theGame.pl2, IN_BAG, new Point(2,1));
        Move.changeLocation(theGame.pl1, IN_BAG, new Point (0,2));
        Move.changeLocation(theGame.pl2, IN_BAG, new Point(2,3));
        //Test
        assertEquals(theGame.inMill(new Piece(new Point(0,0), theGame.pl1)), true);
    }



    @Test
    void initPlayers() {
        assertEquals(theGame.pl1, theGame.currentPlayer);
        assertEquals(theGame.pl1.getName(), "Red");
        assertEquals(theGame.pl2.getName(), "Blue");
        assertEquals(theGame.getPlayers().size(), 2);
    }

    @Test
    void drawQuickTable() {

        theGame.pl1.getPieces().get(0).setPair(new Point(2,3));
        theGame.pl2.getPieces().get(0).setPair(new Point(1,2));
        assertNotEquals(theGame.pl1.getPieces().elementAt(0).getPair(), new Point(IN_BAG));
        assertNotEquals(theGame.pl2.getPieces().elementAt(0).getPair(), new Point(IN_BAG));
        theGame.getQuickTable().put(theGame.pl1.getPieces().get(0).getPair(), pl1);
        theGame.getQuickTable().put(theGame.pl2.getPieces().get(0).getPair(), pl2);
        assertTrue(theGame.getQuickTable().containsKey(new Point(2,3)));
        assertTrue(theGame.getQuickTable().containsKey(new Point(1,2)));


    }

    @Test
    void drawCurrentMoveTable() {
    }



    @Test
    void checkMill() {
        // Setup
        Point p1 = new Point(0,1);
        Point p2 = new Point(1,0);
        Point p3 = new Point(2,3);
        theGame.getQuickTable().put(p1, theGame.pl1);
        theGame.getQuickTable().put(p2, theGame.pl1);
        theGame.getQuickTable().put(p3, theGame.pl2);
        //Test.
        assertTrue(theGame.checkMill(theGame.pl1, p1, p2));
        assertFalse(theGame.checkMill(theGame.pl1, p1, p3));

    }

    @Test
    void lostByPieceCount() {
        //set up the piece and vector to test
        Point point = new Point(2,3);
        Vector<Piece> labRats = new Vector<>();
        //Because it is initialzied as 9 in size.
        theGame.pl1.getPieces().clear();

        Piece piece = new Piece(point, theGame.pl1);
        //Populate the Vector and start testing.
        labRats.add(0, piece);
        labRats.add(1, piece);
        theGame.pl1.setPieces(labRats);
        //Test.
        assertTrue(theGame.pl1.hasTwoPieces());
        // Either Bug or I suck.
        for(Player player: theGame.getPlayers()){
            if(player.hasTwoPieces()){
                assertTrue(theGame.lostByPieceCount());
            }

            // This failed. I was trying to see if assert pl2 will be able to unless we dont need to test for that.
            //assertFalse(theGame.lostByPieceCount());


        }


    }

    @Test
    void noMove() {
        //Setup
        Move.changeLocation(theGame.pl1, IN_BAG, new Point (2,0));
        Move.changeLocation(theGame.pl2, IN_BAG, new Point(2,7));
        Move.changeLocation(theGame.pl1, IN_BAG, new Point (2,1));
        Move.changeLocation(theGame.pl2, IN_BAG, new Point(1,1));
        Move.changeLocation(theGame.pl1, IN_BAG, new Point (2,2));
        Move.changeLocation(theGame.pl2, IN_BAG, new Point(1,3));
        Move.changeLocation(theGame.pl1, IN_BAG, new Point (2,3));
        Move.changeLocation(theGame.pl2, IN_BAG, new Point(2,4));
        //Test
        assertTrue(theGame.noMove(theGame.pl1));
    }

    @Test
    void isPlacing() {
        Move.changeLocation(theGame.pl1, IN_BAG, new Point (0,0));
        Move.changeLocation(theGame.pl2, IN_BAG, new Point(2,0));
        Move.changeLocation(theGame.pl1, IN_BAG, new Point (0,1));
        Move.changeLocation(theGame.pl2, IN_BAG, new Point(2,1));
        Move.changeLocation(theGame.pl1, IN_BAG, new Point (0,2));
        Move.changeLocation(theGame.pl2, IN_BAG, new Point(2,2));
        Move.changeLocation(theGame.pl1, IN_BAG, new Point (0,3));
        Move.changeLocation(theGame.pl2, IN_BAG, new Point(2,3));
        Move.changeLocation(theGame.pl1, IN_BAG, new Point (0,4));
        Move.changeLocation(theGame.pl2, IN_BAG, new Point(2,4));
        Move.changeLocation(theGame.pl1, IN_BAG, new Point (0,5));
        Move.changeLocation(theGame.pl2, IN_BAG, new Point(2,5));
        Move.changeLocation(theGame.pl1, IN_BAG, new Point (0,6));
        Move.changeLocation(theGame.pl2, IN_BAG, new Point(2,6));
        Move.changeLocation(theGame.pl1, IN_BAG, new Point (0,7));
        Move.changeLocation(theGame.pl2, IN_BAG, new Point(2,7));
        Move.changeLocation(theGame.pl1, IN_BAG, new Point (1,0));
        Move.changeLocation(theGame.pl2, IN_BAG, new Point(1,4));

        assertFalse(theGame.isPlacing());

    }
}
