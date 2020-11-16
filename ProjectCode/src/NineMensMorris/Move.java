package NineMensMorris;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Move {
    private static HashMap<Point, Point> coordTable;
    private static HashMap<Point, ArrayList<Point>> moveTable;
    private static Game.GamePlay myGame;
    private static int moveCount = 0;

    public static void linkUp(Game.GamePlay myGame) {
        Move.myGame = myGame;
        Move.InitCoordTable();
        Move.InitMoveTable();
    }

    //Getters
    public static HashMap<Point, Point> getCoordTable() { return coordTable; }
    public static HashMap<Point, ArrayList<Point>> getMoveTable(){ return moveTable; }
    public static int getMoveCount() { return moveCount; }
    protected static Game.GamePlay getMyGame() { return myGame; }

    //Setter
    public static void incrementMoveCount() {moveCount++; }

    //Initializers
    //This Table has the GUI grid coords as the keys and playing coords as values
    //For transforming between the two systems
    private static void InitCoordTable() {
        coordTable = new HashMap<Point, Point>();

        coordTable.put(new Point(0, 0), new Point(2, 0));
        coordTable.put(new Point(0, 3), new Point(2, 7));
        coordTable.put(new Point(0, 6), new Point(2, 6));

        coordTable.put(new Point(1, 1), new Point(1, 0));
        coordTable.put(new Point(1, 3), new Point(1, 7));
        coordTable.put(new Point(1, 5), new Point(1, 6));

        coordTable.put(new Point(2, 2), new Point(0, 0));
        coordTable.put(new Point(2, 3), new Point(0, 7));
        coordTable.put(new Point(2, 4), new Point(0, 6));

        coordTable.put(new Point(3, 0), new Point(2, 1));
        coordTable.put(new Point(3, 1), new Point(1, 1));
        coordTable.put(new Point(3, 2), new Point(0, 1));
        coordTable.put(new Point(3, 4), new Point(0, 5));
        coordTable.put(new Point(3, 5), new Point(1, 5));
        coordTable.put(new Point(3, 6), new Point(2, 5));

        coordTable.put(new Point(4, 2), new Point(0, 2));
        coordTable.put(new Point(4, 3), new Point(0, 3));
        coordTable.put(new Point(4, 4), new Point(0, 4));

        coordTable.put(new Point(5, 1), new Point(1, 2));
        coordTable.put(new Point(5, 3), new Point(1, 3));
        coordTable.put(new Point(5, 5), new Point(1, 4));

        coordTable.put(new Point(6, 0), new Point(2, 2));
        coordTable.put(new Point(6, 3), new Point(2, 3));
        coordTable.put(new Point(6, 6), new Point(2, 4));
    }

    //This table has the 24 playable places in playing coords as the keys and the places one can move from that key as values
    //For deciding if a move is legal and if a player has no moves left
    private static void InitMoveTable() {
        moveTable = new HashMap<Point, ArrayList<Point>>();

        for (Point pair : coordTable.values()) {
            int myY = pair.y;
            int myX = pair.x;

            moveTable.put(pair, new ArrayList<Point>());
            switch (myY % 2) {
                case 0:
                    moveTable.get(pair).add(new Point(myX, (myY == 0) ? 7 : myY - 1));
                    moveTable.get(pair).add(new Point(myX, myY + 1));
                    break;
                case 1:
                    moveTable.get(pair).add(new Point(myX, myY - 1));
                    moveTable.get(pair).add(new Point(myX, (myY == 7) ? 0 : myY + 1));
                    switch (myX) {
                        case 0:
                        case 2:
                            moveTable.get(pair).add(new Point(1, myY));
                            break;
                        case 1:
                            moveTable.get(pair).add(new Point(0, myY));
                            moveTable.get(pair).add(new Point(2, myY));
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }

    }

    //Main Functions
    //Pass in a point in question, returns if that space is open
    //Uses the QuickTable
    public static boolean isOpen(Point pair) {

        return !myGame.getQuickTable().containsKey(pair);//none found, so return true the spot isOpen
    }

    //Pass in a piece and a location to see if the piece is allowed to try to move from it's current location
    protected static boolean isLegal(Piece piece, Point newPair) {
        boolean fromInBag = piece.getPair().equals(Game.IN_BAG);
        boolean isFlying = piece.getMyPlayer().isFlying();

        return  fromInBag || isFlying || moveTable.get(piece.getPair()).contains(newPair);
    }

    //Moving function
    //WARNING: Modifies piece vector
    public static boolean changeLocation(Player player, Point oldPair, Point newPair) {
        Piece thePiece = findPiece(player, oldPair);

        if (thePiece != null && isOpen(newPair) && isLegal(thePiece, newPair)) {
                thePiece.setPair(newPair);
                incrementMoveCount();
                if (myGame.isMidMove()) {myGame.setMidMove(false); }
                myGame.DrawQuickTable();
                myGame.isInMill(newPair, true);
                myGame.updateGameState();
                //System.out.println(player.getName() + " just placed at " + thePiece.getPair());
                return true;
            }
        return false;
    }

    //WARNING: Modifies piece vector
    public static boolean removePiece(Point pair) {
        Piece thePiece = findPiece(null, pair);

        if (thePiece != null && thePiece.getMyPlayer().getPieces().remove(thePiece)) {
            myGame.decrementMill();
            myGame.DrawQuickTable();
            myGame.updateGameState();
            return true;
        }
        return false;
    }

    //Pass in a player/null and a pair, returns a piece if one is located at the location of pair, or null if not
    //Helper Function for removePiece and Move
    private static Piece findPiece(Player player, Point pair) {
        if (player != null) {
            for (Piece piece : player.getPieces()) {
                if (piece.getPair().equals(pair)) {
                    return piece;
                }
            }
        } else {
            for (Player players : myGame.getPlayers()) {
                for (Piece piece : players.getPieces()) {
                    if (piece.getPair().equals(pair)) {
                        return piece;
                    }
                }
            }
        }
        return null;
    }
}
