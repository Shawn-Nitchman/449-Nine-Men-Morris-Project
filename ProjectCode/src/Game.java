import java.awt.*;
import java.util.*;

public abstract class Game {

    HashMap<Player, Piece> playerMap = new HashMap<>();

    public abstract boolean isIntersection(Point pair);

    public abstract void assignPlayerMap(Player player, Piece piece);

    public abstract HashMap getPlayerMap();


    public abstract Piece getPieceOff();

    public static class Gameplay extends Game{
        Point intersection;
        int x, y;

        @Override
        public boolean isIntersection(Point pair) {
            if (pair == null) {

                return false;
            }

            else{
                this.intersection.x = pair.x;
                this.intersection.y = pair.y;
                return true;
            }
        }

        @Override
        public HashMap getPlayerMap() {

            return playerMap;
        }

        @Override
        public void assignPlayerMap(Player player, Piece piece) {

        }


        @Override
        public Piece getPieceOff() {
            return null;
        }
    }



}
