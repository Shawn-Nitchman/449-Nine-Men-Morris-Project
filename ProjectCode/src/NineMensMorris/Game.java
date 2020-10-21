package NineMensMorris;
import java.awt.*;
import java.util.*;


public abstract class Game {

    //Constants
    public static final Point IN_BAG = new Point(-1,-1);
    public static final int START_COUNT = 9;

    //Variables
    protected Vector<Player> players; // Container of 2 players in the Game.
    // Player class declarations.
    public Player pl1, pl2;


    public static void main(String[] args){
        GamePlay theGame = new Game.GamePlay();
    }

    public static class GamePlay extends Game {

        //Constructor
        public GamePlay() {
            InitPlayers();
            Move.linkUp(this); //Gives the Move module a static reference to this instance
        }

        //Getters
        public Vector<Player> getPlayers(){ return super.players; }

        //Initializers
        protected void InitPlayers() {
            players = new Vector<>();

            //Could use Dialog Box to get player names from users
            players.add(pl1 = new Player("Red"));
            players.add(pl2 = new Player("Blue"));
        }


    public boolean inMill(Piece piece) {
        if (piece.getPair().getY() % 2 == 0) //even
        {
            //check two above in Y and check two below in Y
            if (checkMill(piece.getName(),
                    new Point(piece.getPair().x, piece.getPair().y - 1),
                    new Point(piece.getPair().x, piece.getPair().y - 2))) {
                return true;
            } else if (checkMill(piece.getName(),
                    new Point(piece.getPair().x, piece.getPair().y + 1),
                    new Point(piece.getPair().x, piece.getPair().y + 2))) {
                return true;
        }
            return true;
        }
        else { //odd
            int x1, x2;
            if (piece.getPair().x == 0) {x1 = 1; x2 = 2;}
            else if (piece.getPair().x == 1) {x1 = 0; x2 = 2;}
            else {x1 = 0; x2 = 1;}

            //check one above and one below in Y, check mids
            if (checkMill(piece.getName(),
                    new Point(piece.getPair().x, piece.getPair().y - 1),
                    new Point(piece.getPair().x, piece.getPair().y + 1))) {
                return true;
            } else if (checkMill(piece.getName(),
                    new Point(x1, piece.getPair().y),
                    new Point(x2, piece.getPair().y))) {
                return true;
            }
            return true;
        }
    }

        public boolean checkMill(String name, Point p1, Point p2) {
            Player player = this.pl1;
            if (name == this.pl2.getName()) {
                player = this.pl2;
            }

            Boolean flag1 = false, flag2 = false;
            for (Piece piece : player.getPieces()) {
                if (piece.getPair().equals(p1)) {
                    flag1 = true;
                } else if (piece.getPair().equals(p2)) {
                    flag2 = true;
                }
            }
            if (flag1 && flag2) {
                return true;
            }
            return false;
        }




        public boolean lostByPieceCount() {
            for (Player player : this.players) {
                if (player.hasTwoPieces()) {
                    return true;
                }
            }
            return false;
        }

        public boolean noMove(Player player) {
            /*
            for each player, same structure as isLegal (Move class) but check isOpen (Move class).

            If player is flying, then
            return false;

             */
            return true;
        }
    }
}
