package NineMensMorris;

import java.awt.*;
import java.util.HashMap;

public abstract class Move {
    private static HashMap<Point, Point> coordTable;
    private static Game.GamePlay myGame;

    public static void linkUp(Game.GamePlay myGame) {
        Move.myGame = myGame;
        Move.InitCoordTable();
    }

    public static HashMap<Point, Point> getCoordTable() { return coordTable; }
    protected static Game.GamePlay getMyGame() { return myGame; }

    private static void InitCoordTable(){
        coordTable = new HashMap<Point, Point>();

        coordTable.put(new Point(0, 0), new Point(2,0));
        coordTable.put(new Point(0, 3), new Point(2,1));
        coordTable.put(new Point(0,6), new Point(2,2));

        coordTable.put(new Point(1, 1), new Point(1,0));
        coordTable.put(new Point(1, 3), new Point(1,1));
        coordTable.put(new Point(1, 5), new Point(1,2));

        coordTable.put(new Point(2, 2), new Point(0,0));
        coordTable.put(new Point(2, 3), new Point(0,1));
        coordTable.put(new Point(2, 4), new Point(0,2));

        coordTable.put(new Point(3, 0), new Point(2,7));
        coordTable.put(new Point(3, 1), new Point(1,7));
        coordTable.put(new Point(3, 2), new Point(0,7));
        coordTable.put(new Point(3, 4), new Point(0,3));
        coordTable.put(new Point(3, 5), new Point(1,3));
        coordTable.put(new Point(3, 6), new Point(2,3));

        coordTable.put(new Point(4, 2), new Point(0,6));
        coordTable.put(new Point(4, 3), new Point(0,5));
        coordTable.put(new Point(4, 4), new Point(0,4));

        coordTable.put(new Point(5, 1), new Point(1,6));
        coordTable.put(new Point(5, 3), new Point(1,5));
        coordTable.put(new Point(5, 5), new Point(1,4));

        coordTable.put(new Point(6, 0), new Point(2,6));
        coordTable.put(new Point(6, 3), new Point(2,5));
        coordTable.put(new Point(6, 6), new Point(2,4));
    }

    public static boolean isOpen(Point pair) {
        for (Player player : myGame.getPlayers()) {
            for (Piece piece : player.getPieces()) {
                if (piece.getPair().equals(pair)) return false;
            }
        }
        return true; //none found, so return true the spot isOpen
    }

    //Not Sprint1.
    protected static boolean isFlying(Player player) {
        return player.isFlying();
    }


    // Move function.
    public static boolean changeLocation(Player player, Point oldPair, Point newPair){

    	if (isOpen(newPair) && isLegal(player, oldPair, newPair)) {
    		for (Piece piece : player.getPieces()) {
    			if (piece.getPair().equals(oldPair)) {
    				piece.setPair(newPair); //FIXME: Is this threadsafe?
                    //System.out.println(player.getName() + " just placed at " + piece.getPair());
    				return true;
    			}
    		}
    	} else {
    		return false;
    	}

    	return false; //error if this line of code is left out
    }


    //
    protected static boolean isLegal(Player player, Point oldPair, Point newPair) {
        if (oldPair.equals(Game.IN_BAG)) {
            return true;
        } else if (isFlying(player)) {
            return true;
        } else if (oldPair.equals(newPair)) {
            return false;
        } else {
            return checkYCoord(oldPair, newPair);
        }
    }

    private static boolean checkYCoord(Point oldPair, Point newPair) {
        if (oldPair.getY() == newPair.getY() && oldPair.getY() % 2 == 0) {
            //If the Y coords are equal and even (i.e. corner spot)
            return false;
        } else if (oldPair.getY() == newPair.getY() && oldPair.getY() % 2 == 1) {
            //If te Y coords are equal and odd (i.e. midpoint spot)
            return checkXCoord(oldPair, newPair);
        }
        
        switch ((int) (oldPair.getY())) {
            case 0:
                if ((newPair.getY() == 1 || newPair.getY() == 7)
                        && (oldPair.getX() == newPair.getX()))
                        { return true; }
                else {return false;}

            case 2: case 4: case 6:
                if (((newPair.getY() == oldPair.getY() - 1) || newPair.getY() == oldPair.getY() + 1)
                        && (oldPair.getX() == newPair.getX()))
                        { return true; }
                else {return false;}

            case 1: case 3: case 5:
                if (((newPair.getY() == oldPair.getY() - 1) || newPair.getY() == oldPair.getY() + 1)
                        && (checkXCoord(oldPair, newPair)))
                { return true; }
                else {return false;}

            case 7:
                if ((newPair.getY() == 6 || newPair.getY() == 0)
                        && (checkXCoord(oldPair, newPair)))
                { return true; }
                else {return false;}

            default:
                break;
        }
        return false;
    }

    private static boolean checkXCoord(Point oldPair, Point newPair) {
        switch ((int) oldPair.getX()) {
            case 0: case 2:
                if (newPair.getX() == 1 || newPair.getX() == oldPair.getX()) { return true; }
                else { return false; }
            case 1:
                if (newPair.getX() == 0 || newPair.getX() == 2 || newPair.getX() == oldPair.getX()) { return true; }
                else { return false; }
            default:
                return false;
        }
    }

    private static boolean testEven(Point oldPair) { return true; } //FIXME: Declaration needs Definition?
}
