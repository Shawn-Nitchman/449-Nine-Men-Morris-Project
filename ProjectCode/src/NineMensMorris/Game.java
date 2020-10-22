package ProjectCode.src.NineMensMorris;
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
