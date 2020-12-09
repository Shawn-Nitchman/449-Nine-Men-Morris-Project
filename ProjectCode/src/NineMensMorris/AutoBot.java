package NineMensMorris;

import java.awt.*;
import java.util.*;

public class AutoBot extends Player {
    private static final Vector<ArrayList<Point>> millTable = new Vector<>();

    AutoBot(String player) {
        super(player);
        InitMillTable();
    }

    private void InitMillTable() {
        millTable.add(new ArrayList<>(Arrays.asList(new Point(0, 0), new Point(0, 1), new Point(0, 2))));
        millTable.add(new ArrayList<>(Arrays.asList(new Point(1, 0), new Point(1, 1), new Point(1, 2))));
        millTable.add(new ArrayList<>(Arrays.asList(new Point(2, 0), new Point(2, 1), new Point(2, 2))));

        millTable.add(new ArrayList<>(Arrays.asList(new Point(0, 2), new Point(0, 3), new Point(0, 4))));
        millTable.add(new ArrayList<>(Arrays.asList(new Point(1, 2), new Point(1, 3), new Point(1, 4))));
        millTable.add(new ArrayList<>(Arrays.asList(new Point(2, 2), new Point(2, 3), new Point(2, 4))));

        millTable.add(new ArrayList<>(Arrays.asList(new Point(0, 4), new Point(0, 5), new Point(0, 6))));
        millTable.add(new ArrayList<>(Arrays.asList(new Point(1, 4), new Point(1, 5), new Point(1, 6))));
        millTable.add(new ArrayList<>(Arrays.asList(new Point(2, 4), new Point(2, 5), new Point(2, 6))));

        millTable.add(new ArrayList<>(Arrays.asList(new Point(0, 6), new Point(0, 7), new Point(0, 0))));
        millTable.add(new ArrayList<>(Arrays.asList(new Point(1, 6), new Point(1, 7), new Point(1, 0))));
        millTable.add(new ArrayList<>(Arrays.asList(new Point(2, 6), new Point(2, 7), new Point(2, 0))));

        millTable.add(new ArrayList<>(Arrays.asList(new Point(0, 1), new Point(1, 1), new Point(2, 1))));
        millTable.add(new ArrayList<>(Arrays.asList(new Point(0, 3), new Point(1, 3), new Point(2, 3))));
        millTable.add(new ArrayList<>(Arrays.asList(new Point(0, 5), new Point(1, 5), new Point(2, 5))));
        millTable.add(new ArrayList<>(Arrays.asList(new Point(0, 7), new Point(1, 7), new Point(2, 7))));
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
        while (start >= System.currentTimeMillis() - 500) ;

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
        Vector<Point> botNearMillSpaces = nearMillFinder(theGame, this, false);
        Vector<Point> playerNearMills;

        Point placingPoint;

        if (!botNearMillSpaces.isEmpty()) {
            placingPoint = randomPointInVector(botNearMillSpaces);

        } else { //is empty
            playerNearMills = nearMillFinder(theGame, theGame.pl1, false);
            if (!playerNearMills.isEmpty()) {
                placingPoint = randomPointInVector(playerNearMills);
            } else {
                placingPoint = new Point(randomGenerator());
            }
        }
        return EventHandler.placingFunction(Cell.pairToCell.get(placingPoint), theGame);
    }

    public boolean botMilling(Game theGame) {
        Vector<Point> freePieces = theGame.getFreePieces();

        if (freePieces == null) {
            Vector<Point> opsPoints = new Vector<>();
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
        Vector<Point> playerNearMills = nearMillFinder(theGame, theGame.pl1, true);
        Vector<Point> screenedPlayerNearMills = new Vector<>();
        Point pairToRemove;

        if (!playerNearMills.isEmpty()) {
            for (Point pair : playerNearMills) {
                if (removablePairs.contains(pair)) {
                    screenedPlayerNearMills.add(pair);
                }
            }
            pairToRemove = randomPointInVector(screenedPlayerNearMills);
        } else {
            pairToRemove = randomPointInVector(removablePairs);
        }

        EventHandler.millingFunction(Cell.pairToCell.get(pairToRemove), theGame);
    }

    public boolean botMoving(Game theGame) {

        Vector<Point> botNearMillSpaces = nearMillFinder(theGame, this, false);
        Vector<Point> botPiecesInNearMills = nearMillFinder(theGame, this, true);
        Vector<Point> movePoints = new Vector<>();
        Vector<Point> possibleMoves = new Vector<>();
        Point moveFromPair;

        for (Piece piece : theGame.pl2.getPieces()) {
            movePoints.add(piece.getPair());
        }

        if (botNearMillSpaces.size() > 0) { //I might be able to make a mill
            for (Piece piece : this.getPieces()) {
                Point oldPair = piece.getPair();
                Point newPair = null;

                if (!botPiecesInNearMills.contains(oldPair)) { //Don't move a piece out of a Near Mill
                    if (isFlying()) {
                        //FIXME: This might not work right
                        newPair = botNearMillSpaces.get(0);
                    } else { //Not flying
                        for (Point checkPair : Move.getMoveTable().get(oldPair)) { //For each place I could move current piece
                            if (Move.isOpen(checkPair)) { //If the place is open
                                newPair = checkPair;
                            }
                        }
                    }
                    if (newPair != null && botNearMillSpaces.contains(newPair) && !botPiecesInNearMills.contains(oldPair)) {
                        Move.changeLocation(this, oldPair, newPair);
                        Cell.removeVisualPiece(Cell.pairToCell.get(oldPair));
                        Cell.pairToCell.get(newPair).colorVisualPiece(this);
                        Cell.undoHighlights();
                        return true;
                    }
                }
            }
        } else { //Try to Block opps nearMill

        }
        return false;
    }




/*

        if (this.isFlying()) {
               }

                }
            } else { //If i can't make a mill, try to block opps mills by flying

                Vector<Point> playerNearMills = nearMillFinder(theGame, theGame.pl1, false);
                Point placingPoint;
                if (!playerNearMills.isEmpty()) {
                    placingPoint = randomPointInVector(playerNearMills);
                } else {
                    placingPoint = new Point(randomGenerator());
                }
                for (Piece piece : this.getPieces()) {
                    if (!botPiecesInNearMills.isEmpty()) {
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

            moveFromPair = randomPointInVector(movePoints);
            for (Point pair : Cell.getCoordTable().values()) {
                if (Move.isOpen(pair)) {
                    possibleMoves.add(pair);
                }
            }
        } else { //Not Flying


                            //If the potential open space would make a mill AND the piece we are moving in isn't already to be part of a mill

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
*/
    //ATTEMPT AT AI

    public Vector<Point> nearMillFinder(Game theGame, Player player, boolean getPieces) {
        Vector<Point> possibleMills = new Vector<>();
        HashMap<Point, Player> qTable = theGame.getQuickTable();

        for (ArrayList<Point> millPoints : millTable) {

            if (qTable.containsKey(millPoints.get(0)) && qTable.get(millPoints.get(0)).equals(player)
                    && qTable.containsKey(millPoints.get(1)) && qTable.get(millPoints.get(1)).equals(player)
                    && Move.isOpen(millPoints.get(2))) {

                if (getPieces) {
                    possibleMills.add(millPoints.get(1));
                    possibleMills.add(millPoints.get(0));
                } else {
                    possibleMills.add(millPoints.get(2));
                }

            } else if (qTable.containsKey(millPoints.get(0)) && qTable.get(millPoints.get(0)).equals(player)
                    && Move.isOpen(millPoints.get(1))
                    && qTable.containsKey(millPoints.get(2)) && qTable.get(millPoints.get(2)).equals(player)) {

                if (getPieces) {
                    possibleMills.add(millPoints.get(0));
                    possibleMills.add(millPoints.get(2));
                } else {
                    possibleMills.add(millPoints.get(1));
                }

            } else if (Move.isOpen(millPoints.get(0)) &&
                    qTable.containsKey(millPoints.get(1)) && qTable.get(millPoints.get(1)).equals(player)
                    && qTable.containsKey(millPoints.get(2)) && qTable.get(millPoints.get(2)).equals(player)) {

                if (getPieces) {
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