package NineMensMorris;
import java.awt.*;
import java.util.*;


public abstract class Game {

    //Constants
    public static final Point IN_BAG = new Point(-1,-1);
    public static final int START_COUNT = 9;

    //Variables
    protected Vector<Player> players; // Container of 2 players in the Game.
    protected HashMap<Point, Player> quickTable = new HashMap<Point, Player>(); //HashTable for quick reference
    public Player pl1, pl2;
    protected Player currentPlayer;
    protected int currentMills = 0;

    public static class GamePlay extends Game {

        //Constructor
        public GamePlay() {
            InitPlayers();
            Move.linkUp(this); //Gives the Move module a static reference to this instance
        }

        //Getters
        public Vector<Player> getPlayers() { return super.players; }
        public HashMap<Point, Player> getQuickTable() {return super.quickTable; }
        public Player getCurrentPlayer() { return super.currentPlayer; }
        public int newMills() { return currentMills; }

        //Setters
        public void switchTurn() { currentPlayer = currentPlayer == pl1 ? pl2 : pl1; }
        public void decrementMill() { currentMills--; }

        //Initializers
        protected void InitPlayers() {
            players = new Vector<>();

            //Could use Dialog Box to get player names from users
            players.add(pl1 = new Player("Red"));
            players.add(pl2 = new Player("Blue"));

            currentPlayer = pl1;
        }

        //Main Functions
        protected void DrawQuickTable() {
            quickTable = new HashMap<Point, Player>();
            for (Player player : players) {
                for (Piece piece : player.getPieces()) {
                    if (!piece.getPair().equals(new Point(IN_BAG))) {
                        getQuickTable().put(piece.getPair(), player);
                    }
                }
            }
        }

        public boolean inMill(Piece piece) {
            int myX = piece.getPair().x;
            int myY = piece.getPair().y;
            Player myPlayer = piece.getMyPlayer();

            switch (myY % 2) {//Mod operator for even/odd
                case 0: //even
                        //Check two below for Y, SPECIAL CASE for myY == 0
                        if (checkMill(
                                myPlayer,
                                new Point(myX, (myY == 0) ? 7 : myY - 1),
                                new Point(myX, (myY == 0) ? 6 : myY - 2))) {
                            currentMills++;
                        }
                        //Check two above for Y, SPECIAL CASE for myY == 6
                        if (checkMill(
                                myPlayer,
                                new Point(myX, myY + 1),
                                new Point(myX, (myY == 6) ? 0 : myY + 2))) {
                            currentMills++;
                        }
                    break;

                default:  //odd
                    int x1, x2;
                    //Figuring which squares to check for mid-mill
                    switch (myX) {
                        case 0 -> { x1 = 1; x2 = 2; } //myX == 0
                        case 1 -> { x1 = 0; x2 = 2; } //myX == 1
                        case 2 -> { x1 = 0; x2 = 1; } //myX == 2
                        default -> { return false; } //Out-of-Bounds
                    }

                    //Check X per above switch
                    if (checkMill(
                            myPlayer,
                            new Point(x1, myY),
                            new Point(x2, myY))) {
                        currentMills++;
                    }
                    //Check one below and one above for Y, SPECIAL CASE for myY == 7
                    if (checkMill(
                            myPlayer,
                            new Point(myX, myY - 1),
                            new Point(myX, (myY == 7) ? 0 : myY + 1))) {
                        currentMills++;
                    }
                    break;
            }

            System.out.println("Current Mills = " + currentMills);
            return (currentMills > 0);
        }

        //Helper Function for inMill
        public boolean checkMill(Player player, Point p1, Point p2) {
            HashMap<Point, Player> myQuickTable = getQuickTable();
            return myQuickTable.containsKey(p1) &&
                    myQuickTable.get(p1).equals(player) &&
                    myQuickTable.containsKey(p2) &&
                    myQuickTable.get(p2).equals(player);
        }



        public boolean isPlacing() {
            //If the quickTable and the sum of the player's pieces vectors are not equal,
            // there are still unplaced pieces
            return !(getQuickTable().keySet().size() == pl1.getPieces().size() + pl2.getPieces().size());
        }

        public boolean lostByPieceCount() {
            for (Player player : this.players) {
                if (player.hasTwoPieces()) return true;
            }
            return false;
        }

        public boolean noMove(Player player) {

            if (player.isFlying()) return false;

            for (Map.Entry<Point, Player> myPair : getQuickTable().entrySet()) {
                if (myPair.getValue().equals(player)) {
                    for (Point checkPair : Move.getMoveTable().get(myPair.getKey())) {
                        if (Move.isOpen(checkPair)) return false;
                    }
                }
            }
            return true;
        }

        //Entry for use without GUI
        public static void main(String[] args){
            GamePlay theGame = new Game.GamePlay();
        }
    }
}
