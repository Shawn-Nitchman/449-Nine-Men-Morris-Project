import java.awt.*;
import java.util.*;

public abstract class Game {
    public static Object Gameplay;

    HashMap<Player, ArrayList<Piece>> playerMap;

    //public abstract boolean isIntersection(Point pair);


    public abstract void assignPlayerMap(Player player, Piece piece);

    public abstract HashMap getPlayerMap();


    public abstract Piece getPieceOff();

    public static class GamePlay extends Game{
        Point intersection;

        GamePlay() {

            super.playerMap = new HashMap();
        }

        public boolean downToTwo(Player player) {
            /*
            iterate counting keys, is keys = 2, this player lost.


             */
            return true;
        }

        public boolean noMove(Player player){
            /*
            for each player, same structure as isLegal (Move class) but check isOpen (Move class).

            If player is flying, then
            return false;

             */
            return true;
        }


//        @Override
//        public boolean isIntersection(Point pair) {
//            if (pair == null) {
//
//                return false;
//            }
//
//            else{
//                this.intersection.x = pair.x;
//                this.intersection.y = pair.y;
//                return true;
//            }
//        }

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
