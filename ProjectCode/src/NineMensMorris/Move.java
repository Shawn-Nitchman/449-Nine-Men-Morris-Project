package NineMensMorris;
import java.awt.*;

public abstract class Move {
    private static Game.GamePlay myGame;

    public static void linkUp(Game.GamePlay myGame) {
        Move.myGame = myGame;
    }

    protected static Game.GamePlay getMyGame() { return myGame; }

    public static boolean isOpen(Point pair) {
        for (Player player : myGame.getPlayers()) {
            for (Piece piece : player.getPieces()) {
                if (piece.getPair().equals(pair)) return false;
            }
        }
        return true; //none found, so return true the spot isOpen
    }

    protected static boolean isFlying(Player player) {
        return player.isFlying();
    }
    
        public static boolean move (Player player, Point oldPair, Point newPair){
        if (isOpen(newPair) && isLegal(player, oldPair, newPair)) {
            for (Piece piece : player.getPieces()) {
                if (piece.getPair().equals(oldPair)) {
                    piece.setPair(newPair); //FIXME: Is this threadsafe?
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
