package NineMensMorris;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class Game {

    //Constants
    public static final Point IN_BAG = new Point(-1, -1);
    public static final int START_COUNT = 9;
    public static final int DRAW_COUNT = 100;

    //Variables
    protected Vector<Player> players; // Container of 2 players in the Game.
    protected HashMap<Point, Player> quickTable = new HashMap<Point, Player>(); //HashTable for quick reference

    public enum GameState {Draw, Finished, MidMove, Mill, Moving, Placing}

    public GameState gameState = GameState.Placing;
    public Player pl1;
    public AutoPlay pl2;
    protected Player currentPlayer;
    protected int currentMills = 0;
    protected boolean midMove = false;
    protected Cell lastCell = null;
    protected Vector<Point> freePieces = null;
    protected boolean singlePlayer;


    //Getters

    public Vector<Player> getPlayers() {
        return players;
    }

    public HashMap<Point, Player> getQuickTable() {
        return quickTable;
    }

    public int getCurrentMills() {
        return currentMills;
    }

    public boolean unresolvedMills() {
        return currentMills > 0;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Cell getLastCell() {
        return lastCell;
    }

    public Vector<Point> getFreePieces() {
        return freePieces;
    }

    public boolean isMidMove() {
        return midMove;
    }

    public boolean isSinglePlayer() {
        return singlePlayer;
    }

    //Setters
    public void switchTurn() {
        currentPlayer = currentPlayer == pl1 ? pl2 : pl1;
        setFreePiecesToNull();
    }

    public void decrementMill() {
        currentMills--;
    }

    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public void setLastCell(Cell cell) {
        lastCell = cell;
    }

    public void setFreePiecesToNull() {
        freePieces = null;
    }

    public void setMidMove(boolean flag) {
        midMove = flag;
    }

    public void setSinglePlayer(boolean isSinglePlayer) {
        singlePlayer = isSinglePlayer;
    }

    //Constructor
    public Game() {
        InitPlayers();
        Move.linkUp(this); //Gives the Move module a static reference to this instance
    }

    //Initializers
    protected void InitPlayers() {
        players = new Vector<>();

        //Could use Dialog Box to get player names from users
        players.add(pl1 = new Player("Red"));

        if (isSinglePlayer()) {
            players.add(pl2 = new AutoPlay("BlueBot"));
        } else {
            players.add(pl2 = new AutoPlay("Blue"));
        }
        currentPlayer = pl1;
    }

    //Main Functions
    //The quick table maps the piece vectors to their player for quicker (and safe) access
    protected void drawQuickTable() {
        quickTable = new HashMap<Point, Player>();
        for (Player player : players) {
            for (Piece piece : player.getPieces()) {
                if (!piece.getPair().equals(IN_BAG)) {
                    getQuickTable().put(piece.getPair(), player);
                }
            }
        }
    }

    //This function updates the GameState enumeration to the correct state (WARNING: Pass-thru behavior)
    public void updateGameState() {
        if (noMove(currentPlayer) || lostByPieceCount(currentPlayer)) {
            switchTurn(); //Switch turn back to winner
            gameState = GameState.Finished;
        } else if (Move.getMoveCount() >= DRAW_COUNT) {
            gameState = GameState.Draw;
        } else if (unresolvedMills()) {
            gameState = GameState.Mill;
            checkForUnmilledPieces(this.getCurrentPlayer() == pl1 ? pl2 : pl1);
        } else if (midMove) {
            gameState = GameState.MidMove;
        } else if (isPlacing()) {
            gameState = GameState.Placing;
        } else {
            gameState = GameState.Moving;
        }
        updateGuiStatus();
    }

    protected void updateGuiStatus() {
        String currentPlayerName = getCurrentPlayer().getName();
        String statusMessage;

        switch (gameState) {
            case Finished:
                statusMessage = currentPlayerName + ": Has WON the game!!";
                break;
            case Draw:
                statusMessage = "The game is a Draw!!";
                break;
            case Mill:
                statusMessage = currentPlayerName + " has a mill remove an opponent's piece";
                break;
            case MidMove:
                statusMessage = currentPlayerName + " select where to move your piece";
                break;
            case Placing:
                statusMessage = currentPlayerName + " place a piece on the board";
                break;
            default: //Moving
                statusMessage = currentPlayerName + " select a piece to move";
        }
        Gui.changeStatus(statusMessage);
    }

    //Instantiates the freePieces Array and populates it with a players 'free' (unmilled) pieces,
    // or sets it to null if all pieces are in mills
    private void checkForUnmilledPieces(Player player) {
        freePieces = new Vector<Point>();
        for (Map.Entry<Point, Player> pair : getQuickTable().entrySet()) {
            if (pair.getValue().equals(player)) {
                if (!isInMill(pair.getKey(), false)) {
                    freePieces.add(pair.getKey());
                }
            }
        }
        if (freePieces.size() == 0) {
            freePieces = null;
        }
    }

    //Checks a point if it is in a mill, will also modify currentMills if toCount == true
    public boolean isInMill(Point pair, boolean toCount) {
        boolean isInAMill = false;
        Player myPlayer = quickTable.get(pair);
        int myX = pair.x;
        int myY = pair.y;

        switch (myY % 2) {//Mod operator for even/odd
            case 0: //even
                //Check two below for Y, SPECIAL CASE for myY == 0
                if (checkMill(
                        myPlayer,
                        new Point(myX, (myY == 0) ? 7 : myY - 1),
                        new Point(myX, (myY == 0) ? 6 : myY - 2))) {
                    isInAMill = true;
                    if (toCount) {
                        currentMills++;
                    }
                }
                //Check two above for Y, SPECIAL CASE for myY == 6
                if (checkMill(
                        myPlayer,
                        new Point(myX, myY + 1),
                        new Point(myX, (myY == 6) ? 0 : myY + 2))) {
                    isInAMill = true;
                    if (toCount) {
                        currentMills++;
                    }
                }
                break;

            default:  //odd
                int x1, x2;
                //Figuring which squares to check for mid-mill
                switch (myX) {
                    case 0 -> {
                        x1 = 1;
                        x2 = 2;
                    } //myX == 0
                    case 1 -> {
                        x1 = 0;
                        x2 = 2;
                    } //myX == 1
                    case 2 -> {
                        x1 = 0;
                        x2 = 1;
                    } //myX == 2
                    default -> {
                        return false;
                    } //Out-of-Bounds
                }

                //Check X per above switch
                if (checkMill(
                        myPlayer,
                        new Point(x1, myY),
                        new Point(x2, myY))) {
                    isInAMill = true;
                    if (toCount) {
                        currentMills++;
                    }
                }
                //Check one below and one above for Y, SPECIAL CASE for myY == 7
                if (checkMill(
                        myPlayer,
                        new Point(myX, myY - 1),
                        new Point(myX, (myY == 7) ? 0 : myY + 1))) {
                    isInAMill = true;
                    if (toCount) {
                        currentMills++;
                    }
                }
                break;
        }

        //FIXME: Delete this System.out for release
        // System.out.println("Current Mills = " + currentMills);
        if (currentMills > 0) {
            gameState = GameState.Mill;
        }
        if (toCount && currentMills > 0) {
            checkForUnmilledPieces(this.getCurrentPlayer() == pl1 ? pl2 : pl1);
        }
        return isInAMill;
    }

    //Helper Function for isInMill
    private boolean checkMill(Player player, Point p1, Point p2) {
        HashMap<Point, Player> myQuickTable = getQuickTable();
        return myQuickTable.containsKey(p1) &&
                myQuickTable.get(p1).equals(player) &&
                myQuickTable.containsKey(p2) &&
                myQuickTable.get(p2).equals(player);
    }

    //Checks if the game is in the 'Placing' phase
    public boolean isPlacing() {
        //If the quickTable and the sum of the player's pieces vectors are not equal,
        // there are still unplaced pieces (the only pieces in one and not the other are IN_BAG)
        return !(getQuickTable().keySet().size() == pl1.getPieces().size() + pl2.getPieces().size());
    }

    //Checks if a player lost because the have only two pieces left
    public boolean lostByPieceCount(Player player) {
        if (player.hasTwoPieces()) return true;
        return false;
    }

    //Checks if a player lost because they have no available moves
    public boolean noMove(Player player) {

        if (player.isFlying() || isPlacing() || getQuickTable().size() < 8) return false;

        for (Map.Entry<Point, Player> myPair : getQuickTable().entrySet()) {
            if (myPair.getValue().equals(player)) {
                for (Point checkPair : Move.getMoveTable().get(myPair.getKey())) {
                    if (Move.isOpen(checkPair)) return false;
                }
            }
        }
        return true;
    }
}
