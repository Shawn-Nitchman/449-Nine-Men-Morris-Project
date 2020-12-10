package NineMensMorris;

import java.awt.*;
import java.util.*;

public class AutoBot extends Player {
    //A Vector of arrays of points that are possible mill points
    private static Vector<ArrayList<Point>> millTable = new Vector<ArrayList<Point>>();

    AutoBot(String player) {
        super(player);
        InitMillTable();
    }

    private void InitMillTable() {
        //This initializes the table that holds the 16 potential mills
        millTable.add(new ArrayList<Point>(Arrays.asList(new Point(0,0), new Point(0,1), new Point(0,2))));
        millTable.add(new ArrayList<Point>(Arrays.asList(new Point(1,0), new Point(1,1), new Point(1,2))));
        millTable.add(new ArrayList<Point>(Arrays.asList(new Point(2,0), new Point(2,1), new Point(2,2))));

        millTable.add(new ArrayList<Point>(Arrays.asList(new Point(0,2), new Point(0,3), new Point(0,4))));
        millTable.add(new ArrayList<Point>(Arrays.asList(new Point(1,2), new Point(1,3), new Point(1,4))));
        millTable.add(new ArrayList<Point>(Arrays.asList(new Point(2,2), new Point(2,3), new Point(2,4))));

        millTable.add(new ArrayList<Point>(Arrays.asList(new Point(0,4), new Point(0,5), new Point(0,6))));
        millTable.add(new ArrayList<Point>(Arrays.asList(new Point(1,4), new Point(1,5), new Point(1,6))));
        millTable.add(new ArrayList<Point>(Arrays.asList(new Point(2,4), new Point(2,5), new Point(2,6))));

        millTable.add(new ArrayList<Point>(Arrays.asList(new Point(0,6), new Point(0,7), new Point(0,0))));
        millTable.add(new ArrayList<Point>(Arrays.asList(new Point(1,6), new Point(1,7), new Point(1,0))));
        millTable.add(new ArrayList<Point>(Arrays.asList(new Point(2,6), new Point(2,7), new Point(2,0))));

        millTable.add(new ArrayList<Point>(Arrays.asList(new Point(0,1), new Point(1,1), new Point(2,1))));
        millTable.add(new ArrayList<Point>(Arrays.asList(new Point(0,3), new Point(1,3), new Point(2,3))));
        millTable.add(new ArrayList<Point>(Arrays.asList(new Point(0,5), new Point(1,5), new Point(2,5))));
        millTable.add(new ArrayList<Point>(Arrays.asList(new Point(0,7), new Point(1,7), new Point(2,7))));
    }

    public Point randomGenerator() {
        Random rand = new Random();
        int xCord = rand.nextInt(2);
        int yCord = rand.nextInt(7);
        return new Point(xCord, yCord);
    }

    //random index generator, takes size of vector and returns a point
    public Point randomPointInVector(Vector<Point> pointVector) {
        Random rand = new Random();
        int index = rand.nextInt(pointVector.size());
        return pointVector.elementAt(index);
    }

    public void computersTurn() {
        //Delay
        long start = System.currentTimeMillis();
        while(start >= System.currentTimeMillis() - 250);

        Game theGame = Gui.getMyGame();
        Game.GameState currentState = theGame.gameState;

        switch (currentState) {
            case Placing:
                while (!botPlacing(theGame)) {
                    continue;
                }
                break;
            case Mill:
                while (!botMilling(theGame)) {
                    continue;
                }
                break;
            case Moving:
                while (!botMoving(theGame)) {
                    continue;
                }
                break;
        }
    }

    public boolean botPlacing(Game theGame) {
        Vector<Point> botNearMills = nearMills(theGame, this, false);
        Vector<Point> playerNearMills;

        Point placingPoint;

        if (!botNearMills.isEmpty()) {
            //If I have a near mill, pick one of those spaces to place
            placingPoint = randomPointInVector(botNearMills);

        } else { //is empty
            playerNearMills = nearMills(theGame, theGame.pl1, false);
            if (!playerNearMills.isEmpty()) {
                //If player has near mills, place to block
                placingPoint = randomPointInVector(playerNearMills);
            } else {
                //Otherwise choose randomly
                placingPoint = new Point(randomGenerator());
            }
        }
        return EventHandler.placingFunction(Cell.pairToCell.get(placingPoint), theGame);
    }

    public boolean botMilling(Game theGame) {
        Vector<Point> freePieces = theGame.getFreePieces();

        if (freePieces == null) {
            Vector<Point> opsPoints = new Vector<Point>();
            for (Piece piece : theGame.pl1.getPieces()) {
                opsPoints.add(piece.getPair());
            }
            //If there are no free pieces
            removeOpponentsPiece(opsPoints, theGame);
        } else {
            //If player has free pieces
            removeOpponentsPiece(freePieces, theGame);
        }
        return true;
    }

    private void removeOpponentsPiece(Vector<Point> removablePairs, Game theGame) {
        Vector<Point> playerNearMills = nearMills(theGame, theGame.pl1, true);
        Vector<Point> screenedPlayerNearMills = new Vector<Point>();
        Point pairToRemove;

        if (!playerNearMills.isEmpty()) { //If the player has near mills
            for (Point pair : playerNearMills) {
                if (removablePairs.contains(pair)) {
                    //If the pieces in a near mill are removable (ie, either not in a mill, or all players pieces are in mills)
                    screenedPlayerNearMills.add(pair);
                }
            }
            pairToRemove = randomPointInVector(screenedPlayerNearMills);
        } else {
            //Pick a random piece to remove
            pairToRemove = randomPointInVector(removablePairs);
        }

        EventHandler.millFunction(Cell.pairToCell.get(pairToRemove), theGame);
    }

    public boolean botMoving(Game theGame) {

        Vector<Point> botPiecePiars = new Vector<Point>();
        Vector<Point> possibleMoves = new Vector<Point>();
        Point moveFromPair;

        for (Piece piece : theGame.pl2.getPieces()) {
            botPiecePiars.add(piece.getPair());
        }

        if (this.isFlying()) {
            Vector<Point> botNearMills = nearMills(theGame, this, false);
            Vector<Point> botPiecesInNearMills = nearMills(theGame,this, true);

            if (!botNearMills.isEmpty()) { //If I have a near mill
                for (Piece piece : this.getPieces()) {
                    Point oldPair = piece.getPair();

                    //Try not to move a piece IN the near mill, but the other one into the mill
                    if (!botPiecesInNearMills.contains(oldPair)) {
                        Point newPair = randomPointInVector(botNearMills);
                        Move.changeLocation(this, oldPair, newPair);
                        Cell.removeVisualPiece(Cell.pairToCell.get(oldPair));
                        Cell.pairToCell.get(newPair).colorVisualPiece(this);
                        Cell.undoHighlights();
                        return true;
                    }
                }
            } else { //If I can't make a mill, try to block opponents mills by flying

                Vector<Point> playerNearMills = nearMills(theGame, theGame.pl1, false);
                Point placingPoint;

                //If player has near mills, remember the space to block, otherwise chose random
                if (!playerNearMills.isEmpty()) {
                    placingPoint = randomPointInVector(playerNearMills);
                } else {
                    placingPoint = new Point(randomGenerator());
                }
                for (Piece piece : this.getPieces()) {
                    if (!botPiecesInNearMills.isEmpty()) {
                        //If I have a near mill, try not to move either of those pieces
                        for (Point pair : botPiecesInNearMills) {
                            if (piece.getPair() != pair) {
                                Point oldPair = piece.getPair();
                                Move.changeLocation(this, oldPair, placingPoint);
                                Cell.removeVisualPiece(Cell.pairToCell.get(oldPair));
                                Cell.pairToCell.get(placingPoint).colorVisualPiece(this);
                                Cell.undoHighlights();
                                return true;
                            }
                        }
                    }
                }
            }

            moveFromPair = randomPointInVector(botPiecePiars);
            for (Point pair : Cell.getCoordTable().values()) {
                if (Move.isOpen(pair)) {
                    possibleMoves.add(pair);
                }
            }
        } else { //Not Flying
            Vector<Point> botNearMills = nearMills(theGame, this, false);

            if (!botNearMills.isEmpty()) {//If I have potential mills
                //Grab points of pieces almost in a mill
                Vector<Point> botPiecesInNearMills = nearMills(theGame,this, true);
                for (Piece piece : this.getPieces()) { //For each of my piece
                    Point oldPair = piece.getPair();

                    for (Point newPair : Move.getMoveTable().get(oldPair)) { //For each place I could move a piece
                        if (Move.isOpen(newPair)) { //If the place is open
                            //If the potential open space would make a mill AND the piece we are moving in isn't already to be part of a mill
                            if (botNearMills.contains(newPair) && !botPiecesInNearMills.contains(oldPair)) {
                                Move.changeLocation(this, oldPair, newPair);
                                Cell.removeVisualPiece(Cell.pairToCell.get(oldPair));
                                Cell.pairToCell.get(newPair).colorVisualPiece(this);
                                Cell.undoHighlights();
                                return true;
                            }
                        }
                    }
                }
            }

            do { //If there are no possible mills to make, choose randomly
                moveFromPair = randomPointInVector(botPiecePiars);
                for (Point checkPair : Move.getMoveTable().get(moveFromPair)) {
                    if (Move.isOpen(checkPair)) {
                        possibleMoves.add(checkPair);
                    }
                }
                //Don't try this piece again
                if (possibleMoves.isEmpty()) {
                    botPiecePiars.remove(moveFromPair);
                }
                //System.out.println("Possible move vector = " + possibleMoves.toString());
            } while (possibleMoves.isEmpty());
        }

        Point moveToPair = randomPointInVector(possibleMoves);

        //Move Randomly
        if (Move.changeLocation(this, moveFromPair, moveToPair)) {

            //Tell GUI to 'move' visual piece
            Cell.removeVisualPiece(Cell.pairToCell.get(moveFromPair));
            Cell.pairToCell.get(moveToPair).colorVisualPiece(this);
            Cell.undoHighlights();
        }
        return true;
    }

    public Vector<Point> nearMills(Game theGame, Player player, boolean findPieces) {
        Vector<Point> nearMills = new Vector<Point>();
        HashMap<Point, Player> qTable = theGame.getQuickTable();

        //This iterates thru all possible mills to see if there are any near mills
        //A near mill is any two pieces of the same player with a third spot open
        //Will either return a Vector of the spaces or of the pieces depended on boolean findPieces
        for (ArrayList<Point> millPoints : millTable) {
            Point firstPoint = millPoints.get(0);
            Point secondPoint = millPoints.get(1);
            Point thirdPoint = millPoints.get(2);

            if (qTable.containsKey(firstPoint) && qTable.get(firstPoint).equals(player)
                    && qTable.containsKey(secondPoint) && qTable.get(secondPoint).equals(player)
                    && Move.isOpen(thirdPoint)) {

                if (findPieces) {
                    nearMills.add(secondPoint);
                    nearMills.add(firstPoint);
                } else { //Add Space
                    nearMills.add(thirdPoint);
                }

            } else if (qTable.containsKey(firstPoint) && qTable.get(firstPoint).equals(player)
                    && Move.isOpen(secondPoint)
                    && qTable.containsKey(thirdPoint) && qTable.get(thirdPoint).equals(player)) {

                if (findPieces) {
                    nearMills.add(firstPoint);
                    nearMills.add(thirdPoint);
                } else { //Add Space
                    nearMills.add(secondPoint);
                }

            } else if (Move.isOpen(firstPoint) &&
                    qTable.containsKey(secondPoint) && qTable.get(secondPoint).equals(player)
                    && qTable.containsKey(thirdPoint) && qTable.get(thirdPoint).equals(player)) {

                if (findPieces) {
                    nearMills.add(secondPoint);
                    nearMills.add(thirdPoint);
                } else { //Add Space
                    nearMills.add(firstPoint);
                }
            }
        }
            return nearMills;
    }
}