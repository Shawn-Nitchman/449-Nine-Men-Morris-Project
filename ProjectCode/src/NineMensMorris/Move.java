package NineMensMorris;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Move {
    private static HashMap<Point, Point> coordTable;
    private static HashMap<Point, ArrayList<Point>> moveTable;
    private static Game.GamePlay myGame;

    public static void linkUp(Game.GamePlay myGame) {
        Move.myGame = myGame;
        Move.InitCoordTable();
        Move.InitMoveTable();
    }

    //Getters
    public static HashMap<Point, Point> getCoordTable() { return coordTable; }
    public static HashMap<Point, ArrayList<Point>> getMoveTable(){ return moveTable; }
    protected static Game.GamePlay getMyGame() { return myGame; }

    //Initializers
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
    public static boolean isOpen(Point pair) {

        for (Point point : myGame.getQuickTable().keySet()) {
            if (pair.equals(point)) return false;
        }
        return true; //none found, so return true the spot isOpen
    }

    protected static boolean isLegal(Piece piece, Point newPair) {
        //return oldPair.equals(Game.IN_BAG) || isFlying(player) || checkYCoord(oldPair, newPair); FIXME: Delete for release
        boolean fromInBag = piece.getPair().equals(Game.IN_BAG);
        boolean isFlying = piece.getMyPlayer().isFlying();

        return  fromInBag || isFlying || moveTable.get(piece.getPair()).contains(newPair);
    }

    // Move function.
    public static boolean changeLocation(Player player, Point oldPair, Point newPair) {
        Piece thePiece = findPiece(player, oldPair);

        if (thePiece != null && isOpen(newPair) && isLegal(thePiece, newPair)) {
                thePiece.setPair(newPair);
                myGame.DrawQuickTable();
                //System.out.println(player.getName() + " just placed at " + thePiece.getPair());
                return true;
            }
        return false;
    }

    public static boolean removePiece(Point pair) {
        Piece thePiece = findPiece(null, pair);

        if (thePiece != null && thePiece.getMyPlayer().getPieces().remove(thePiece)) {
            myGame.DrawQuickTable();
            return true;
        }
        return false;
    }

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

/* Keep this for reference FIXME: Delete before release
    private static boolean checkYCoord(Point oldPair, Point newPair) {


        int myY = oldPair.y;
        int myX = oldPair.x;
        int newY = newPair.y;
        int newX = newPair.x;

        //If the Y coords are equal and even (i.e. corner spot)
        //Or if we are trying to move to/from the same spot
        if (oldPair == newPair || myY == newY && myY % 2 == 0) {
            return false;
        }

        //If the Y coords are equal and odd (i.e. midpoint spot)
        if (myY == newY && myY % 2 == 1) {
            return checkXCoord(myX, newX);
        }

        //If the Y coords are different AND the X coords are the same
        if (myY != newY && myX == newX) switch (myY) {
            case 0: //Special Case
                return (newY == 1 || newY == 7);

            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6: //All regular cases
                return (newY == myY - 1) || (newY == myY + 1);

            case 7: //Special Case
                return (newY == 6 || newY == 0);

            default:
                return false; //Any number outside of 0..7 is illegal
        }
        return false; //Any other combo of X & Y is illegal (i.e. different Y and different X)
    }

    private static boolean checkXCoord(int myX, int newX) {

        switch (myX) {
            case 0:
            case 2:
                return (newX == 1);
            case 1:
                return (newX == 0 || newX == 2);
            default:
                return false;
        }
    }

 */
}
