package NineMensMorris;
import java.awt.*;

public abstract class Move {
    private static Game.GamePlay myGame;

    public static void linkUp(Game.GamePlay myGame) {
        Move.myGame = myGame;
    }

    public static boolean isOpen(Point pair) {
        for (Player player : myGame.players) {
            for (Piece piece : player.getPieces()) {
                if (piece.getPair().equals(pair)) return false;
            }
        }
        return true; //none found, so return true the spot isOpen
    }

    protected static boolean isFlying(Player player) {
        return player.isFlying();
    }

    public static boolean move(Player player, Point oldPair, Point newPair) {
        if (isOpen(newPair) && isLegal(player, oldPair, newPair)) {
            for (Piece piece : player.getPieces()) {
                if (piece.getPair().equals(oldPair)) {
                    piece.setPair(newPair);
                    return true;
                }
            }
        } else {
            return false;
        }

        return false; //error if this line of code is left out
    }

    protected static boolean isLegal(Player player, Point oldPair, Point newPair) {
        if (oldPair.equals(Game.IN_BAG)) {
            return true;
        } else if (isFlying(player)) {
            return true;
        } else if (oldPair.equals(newPair)) {
            return false;
        } else {
            return testY(oldPair, newPair);
            //FIXME: Do Math for isLegal
        }
    }

    private static boolean testY(Point oldPair, Point newPair) {
        if (oldPair.getY() == newPair.getY()) {return testX(oldPair, newPair); }
        
        switch ((int) (oldPair.getY() % 8)) {
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
                        && (testX(oldPair, newPair)))
                { return true; }
                else {return false;}

            case 7:
                if ((newPair.getY() == 6 || newPair.getY() == 0)
                        && (testX(oldPair, newPair)))
                { return true; }
                else {return false;}

            default:
                break;
        }
        return false;
    }

    private static boolean testX(Point oldPair, Point newPair) {
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
