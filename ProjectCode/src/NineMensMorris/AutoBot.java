package NineMensMorris;

import javafx.event.Event;

import java.awt.*;
import java.util.*;

public class AutoBot extends Player {
    private static Vector<ArrayList<Point>> millTable = new Vector<ArrayList<Point>>();

    AutoBot(String player) {
        super(player);
        InitMillTable();
    }

    private void InitMillTable() {
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
        long start = System.currentTimeMillis();
        while(start >= System.currentTimeMillis() - 500);

        Game theGame = Gui.getMyGame();
        Game.GameState currentState = theGame.gameState;

        switch (currentState) {
            case Placing:
                while (!placing(theGame)) {
                    continue;
                }
                break;
            case Mill:
                while (!milling(theGame)) {
                    continue;
                }
                break;
            case Moving:
                while (!moving(theGame)) {
                    continue;
                }
                break;
        }
    }

    public boolean placing(Game theGame) {
        Vector<Point> myPossibleMills = canMakeMill(theGame, this, false);
        Vector<Point> opsPossibleMills;

        Point placingPoint;

        if (!myPossibleMills.isEmpty()) {
            placingPoint = randomPointInVector(myPossibleMills);

        } else { //is empty
            opsPossibleMills = canMakeMill(theGame, theGame.pl1, false);
            if (!opsPossibleMills.isEmpty()) {
                placingPoint = randomPointInVector(opsPossibleMills);
            } else {
                placingPoint = new Point(randomGenerator());
            }
        }
        return EventHandler.placingFunction(Cell.pairToCell.get(placingPoint), theGame);
    }

    public boolean milling(Game theGame) {
        Vector<Point> freePieces = theGame.getFreePieces();

        if (freePieces == null) {
            Vector<Point> opsPoints = new Vector<Point>();
            for (Piece piece : theGame.pl1.getPieces()) {
                opsPoints.add(piece.getPair());
            }
            removeOpponentsPiece(opsPoints, theGame);
        } else {
            removeOpponentsPiece(freePieces, theGame);
        }


        return true;
    }

    private void removeOpponentsPiece(Vector<Point> removablePairs, Game theGame) {
        Vector<Point> opsPossibleMills = canMakeMill(theGame, theGame.pl1, true);
        Vector<Point> screenedOpsPossibleMills = new Vector<Point>();
        Point pairToRemove;

        if (!opsPossibleMills.isEmpty()) {
            for (Point pair : opsPossibleMills) {
                if (removablePairs.contains(pair)) {
                    screenedOpsPossibleMills.add(pair);
                }
            }
            pairToRemove = randomPointInVector(screenedOpsPossibleMills);
        } else {
            pairToRemove = randomPointInVector(removablePairs);
        }

        EventHandler.millFunction(Cell.pairToCell.get(pairToRemove), theGame);
    }

    public boolean moving(Game theGame) {

        Vector<Point> movePoints = new Vector<Point>();
        Vector<Point> possibleMoves = new Vector<Point>();
        Point moveFromPair;

        for (Piece piece : theGame.pl2.getPieces()) {
            movePoints.add(piece.getPair());
        }

        if (this.isFlying()) {
            Vector<Point> myPossibleMills = canMakeMill(theGame, this, false);
            Vector<Point> piecesInMills = canMakeMill(theGame,this, true);

            if (!myPossibleMills.isEmpty()) {
                for (Piece piece : this.getPieces()) {
                    Point oldPair = piece.getPair();

                    if (!piecesInMills.contains(oldPair)) {
                        Point newPair = randomPointInVector(myPossibleMills);
                        Move.changeLocation(this, oldPair, newPair);
                        Cell.removeVisualPiece(Cell.pairToCell.get(oldPair));
                        Cell.pairToCell.get(newPair).colorVisualPiece(this);
                        Cell.undoHighlights();
                        return true;
                    }
                }
            } else { //If i can't make a mill, try to block opps mills by flying

                Vector<Point> opsPossibleMills = canMakeMill(theGame, theGame.pl1, false);
                Point placingPoint;
                if (!opsPossibleMills.isEmpty()) {
                    placingPoint = randomPointInVector(opsPossibleMills);
                } else {
                    placingPoint = new Point(randomGenerator());
                }
                for (Piece piece : this.getPieces()) {
                    if (!piecesInMills.isEmpty()) {
                        for (Point pair : piecesInMills) {
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

            moveFromPair = randomPointInVector(movePoints);
            for (Point pair : Cell.getCoordTable().values()) {
                if (Move.isOpen(pair)) {
                    possibleMoves.add(pair);
                }
            }
        } else { //Not Flying
            Vector<Point> possibleMills = canMakeMill(theGame, this, false);

            if (!possibleMills.isEmpty()) {//If I have potential mills
                //Grab points of pieces almost in a mill
                Vector<Point> piecesInMills = canMakeMill(theGame,this, true);
                for (Piece piece : this.getPieces()) { //For each of my piece
                    Point oldPair = piece.getPair();

                    for (Point newPair : Move.getMoveTable().get(oldPair)) { //For each place I could move a piece
                        if (Move.isOpen(newPair)) { //If the place is open
                            //If the potential open space would make a mill AND the piece we are moving in isn't already to be part of a mill
                            if (possibleMills.contains(newPair) && !piecesInMills.contains(oldPair)) {
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
                moveFromPair = randomPointInVector(movePoints);
                for (Point checkPair : Move.getMoveTable().get(moveFromPair)) {
                    if (Move.isOpen(checkPair)) {
                        possibleMoves.add(checkPair);
                    }
                }
                if (possibleMoves.isEmpty()) {
                    movePoints.remove(moveFromPair);
                }
                //System.out.println("Possible move vector = " + possibleMoves.toString());
            } while (possibleMoves.isEmpty());
        }

        Point moveToPair = randomPointInVector(possibleMoves);

        if (Move.changeLocation(this, moveFromPair, moveToPair)) {

            //Tell GUI to 'move' visual piece
            Cell.removeVisualPiece(Cell.pairToCell.get(moveFromPair));
            Cell.pairToCell.get(moveToPair).colorVisualPiece(this);
            Cell.undoHighlights();
        }
        return true;
    }

    //ATTEMPT AT AI

    public Vector<Point> canMakeMill(Game theGame, Player player, boolean toRemove) {
        Vector<Point> possibleMills = new Vector<Point>();
        HashMap<Point, Player> qTable = theGame.getQuickTable();

        for (ArrayList<Point> millPoints : millTable) {

            if (qTable.containsKey(millPoints.get(0)) && qTable.get(millPoints.get(0)).equals(player)
                    && qTable.containsKey(millPoints.get(1)) && qTable.get(millPoints.get(1)).equals(player)
                    && Move.isOpen(millPoints.get(2))) {

                if (toRemove) {
                    possibleMills.add(millPoints.get(1));
                    possibleMills.add(millPoints.get(0));
                } else {
                    possibleMills.add(millPoints.get(2));
                }

            } else if (qTable.containsKey(millPoints.get(0)) && qTable.get(millPoints.get(0)).equals(player)
                    && Move.isOpen(millPoints.get(1))
                    && qTable.containsKey(millPoints.get(2)) && qTable.get(millPoints.get(2)).equals(player)) {

                if (toRemove) {
                    possibleMills.add(millPoints.get(0));
                    possibleMills.add(millPoints.get(2));
                } else {
                    possibleMills.add(millPoints.get(1));
                }

            } else if (Move.isOpen(millPoints.get(0)) &&
                    qTable.containsKey(millPoints.get(1)) && qTable.get(millPoints.get(1)).equals(player)
                    && qTable.containsKey(millPoints.get(2)) && qTable.get(millPoints.get(2)).equals(player)) {

                if (toRemove) {
                    possibleMills.add(millPoints.get(1));
                    possibleMills.add(millPoints.get(2));
                } else {
                    possibleMills.add(millPoints.get(0));
                }
            }
        }
            return possibleMills;
    }
}