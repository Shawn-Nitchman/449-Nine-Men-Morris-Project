package NineMensMorris;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class AutoPlay extends Player {

    private int xCord, yCord;

    AutoPlay(String player) {
        super(player);
    }

    public Point randomGenerator() {
        Random rand = new Random();
        xCord = rand.nextInt(2);
        yCord = rand.nextInt(7);
        return new Point(xCord, yCord);
    }

    //random index generator, takes size of vector and returns a point
    public Point randomPointInVector(Vector<Point> pointVector) {
        Random rand = new Random();
        int index = rand.nextInt(pointVector.size());
        return pointVector.elementAt(index);
    }

    public void computersTurn() {
        Game theGame = Gui.getMyGame();
        Game.GameState currentState = theGame.gameState;

        switch (currentState) {
            case Placing:
                while (!placing(theGame)) {
                    continue;
                }
                break;
            case Mill:
                while (!removeOpponentsPiece(theGame)) {
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
        Point placingPoint = new Point(randomGenerator());
        return EventHandler.placingFunction(Cell.pairToCell.get(placingPoint), theGame);
    }

    public boolean removeOpponentsPiece(Game theGame) {
        Vector<Point> freePieces = theGame.getFreePieces();
        if (freePieces == null) {
            Vector<Point> opsPoints = new Vector<Point>();
            for (Piece piece : theGame.pl1.getPieces()) {
                opsPoints.add(piece.getPair());
            }
            Point pair = randomPointInVector(opsPoints);
            EventHandler.millFunction(Cell.pairToCell.get(pair), theGame);
            return true;
        } else {
            Point pair = randomPointInVector(freePieces);
            EventHandler.millFunction(Cell.pairToCell.get(pair), theGame);
            return true;
        }
    }

    public boolean moving(Game theGame) {

        Vector<Point> movePoints = new Vector<Point>();
        Vector<Point> possibleMoves = new Vector<Point>();
        Point moveFromPair;

        for (Piece piece : theGame.pl2.getPieces()) {
            movePoints.add(piece.getPair());
        }
        if (theGame.getCurrentPlayer().isFlying()) {
            moveFromPair = randomPointInVector(movePoints);
            for (Point pair : Cell.getCoordTable().values()) {
                if (Move.isOpen(pair)) {
                    possibleMoves.add(pair);
                }
            }
        } else {
            do {
                moveFromPair = randomPointInVector(movePoints);
                System.out.println("Trying :" + moveFromPair);
                for (Point checkPair : Move.getMoveTable().get(moveFromPair)) {
                    if (Move.isOpen(checkPair)) {
                        possibleMoves.add(checkPair);
                    }
                }
                if (possibleMoves.isEmpty()) {
                    movePoints.remove(moveFromPair);
                }
                System.out.println("Possible move vector = " + possibleMoves.toString());
            } while (possibleMoves.isEmpty());
        }

        Point moveToPair = randomPointInVector(possibleMoves);

        if (Move.changeLocation(this, moveFromPair, moveToPair)) {

            //Tell GUI to 'move' visual piece
            Cell.removeVisualPiece(Cell.pairToCell.get(moveFromPair));
            Cell.pairToCell.get(moveToPair).colorVisualPiece(this);

            //Undo all highlights, as we have completed moving the piece
            Cell.undoHighlights();
           /* if (theGame.unresolvedMills()) {
                Cell.hightlightMills(theGame.getFreePieces());
            }*/
            //If you click on the same piece that you selected to move, you will unselect that piece
        /*} else if (clickedPair == theGame.getLastCell().getMyPair()) {

            //Reset GameState flag from midMove to Moving
            theGame.gameState = Game.GameState.Moving;

            //Undo highlights from all cells
            Cell.undoHighlights();*/
            //}
            //return true;
        }
        return true;
    }
}