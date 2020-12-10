package NineMensMorris;

import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.Vector;


public abstract class EventHandler {

    private static final Game theGame = Gui.getMyGame();

    public static javafx.event.EventHandler<? super MouseEvent> handleClick(Cell cell) {

        //Local variables for the GamePlay class, the current player and the quickTable
        HashMap<Point, Player> qTable = theGame.getQuickTable();
        Player currentPlayer = theGame.getCurrentPlayer();
        Point clickedPair = cell.getMyPair();

        switch (theGame.gameState) {
            case Mill: //Try to Remove piece at this location
                millFunction(cell, theGame);
                break;
            case MidMove: //Try to Move lastPiece to new Location
                if (Move.changeLocation(currentPlayer, theGame.getLastCell().getMyPair(), clickedPair)) {

                    //Tell GUI to 'move' visual piece
                    Cell.removeVisualPiece(theGame.getLastCell());
                    cell.colorVisualPiece(currentPlayer);

                    //Undo all highlights, as we have completed moving the piece
                    Cell.undoHighlights();
                    if (theGame.unresolvedMills()) {
                        Cell.hightlightMills(theGame.getFreePieces());
                    }
                    //If you click on the same piece that you selected to move, you will unselect that piece
                } else if (clickedPair == theGame.getLastCell().getMyPair()) {

                    //Reset GameState flag from midMove to Moving
                    theGame.gameState = Game.GameState.Moving;

                    //Undo highlights from all cells
                    Cell.undoHighlights();
                }
                break;

            case Placing: //Place piece on board
                placingFunction(cell, theGame);
                break;

            case Moving: //Capture first click, change myGame.midMove to true
                //If a cell that was clicked has a piece on it of the current player...
                if (qTable.get(clickedPair) != null && qTable.get(clickedPair).equals(currentPlayer)) {

                    //Make sure that piece has at least one legal move, using moveTable <Point, Point[Available Locations]>
                    boolean canMove = false;
                    for (Point checkPair : Move.getMoveTable().get(clickedPair)) {
                        if (Move.isOpen(checkPair)) {
                            Cell.showAvailableSpaces(checkPair); //Highlight as an available place to move
                            canMove = true;
                        }
                    }
                    //If player is flying, then all open cells are available to move to and should be highlighted
                    if (theGame.getCurrentPlayer().isFlying()) {
                        canMove = true;
                        for (Point pair : cell.getCoordTable().values()) {
                            if (Move.isOpen(pair)) {
                                Cell.showAvailableSpaces(pair);
                            }
                        }
                    }

                    //If we clicked on a movable piece, capture the cell we are moving from (this),
                    //Set midMove flag to true, because the next click should attempt to move piece to new location
                    //and Update the GameState
                    if (canMove) {
                        theGame.setLastCell(cell);
                        theGame.setMidMove(true);
                        theGame.updateGameState();
                    }
                }
                break;
        }

        if (theGame.singlePlayer && theGame.getCurrentPlayer() == theGame.pl2 && theGame.getCurrentMills() == 0) { (theGame.pl2).computersTurn(); }
        if (theGame.singlePlayer && theGame.getCurrentPlayer() == theGame.pl2 && theGame.getCurrentMills() != 0) { (theGame.pl2).botMilling(theGame); }
        return null;
    }

    public static boolean placingFunction(Cell cell, Game game) {
        Player currentPlayer = game.getCurrentPlayer();

        if (Move.changeLocation(currentPlayer, Game.IN_BAG, cell.getMyPair())) {

            //Tell GUI to place visual piece
            cell.colorVisualPiece(currentPlayer);
            if (theGame.unresolvedMills()) {
                Cell.hightlightMills(theGame.getFreePieces());
            }
            return true;
        }
        return false;
    }

    public static boolean millFunction(Cell cell, Game theGame) {
        HashMap<Point, Player> qTable = theGame.getQuickTable();
        Point pair = cell.getMyPair();

        //If there is a piece on this cell AND it is of the other player
        if (qTable.get(pair) != null && qTable.get(pair) != theGame.getCurrentPlayer()) {

            //Local variable for freePieces Array
            Vector<Point> freePieces = theGame.getFreePieces();

            //If freePieces equals null, all opponents pieces are in mills, any piece can be taken
            //If the piece clicked is in the freePieces Array, is it not in a mill, and can be taken
            if (freePieces == null) {
                Cell.removeVisualPiece(cell);
                Move.removePiece(pair);
                Cell.undoHighlights();
            } else if (freePieces.contains(pair)) {
                Cell.removeVisualPiece(cell);
                Move.removePiece(pair);
                Cell.undoHighlights();
            }
            return true;
        }
        return false;
    }
}

