package NineMensMorris;
import java.awt.*;
import java.util.Vector;

public class Player {

    //Constants
    private final int START_COUNT = Game.START_COUNT;
    private final Point IN_BAG = Game.IN_BAG;

    //Variables
    private String name;
    private Vector<Piece> pieces;

    //Constructors
    Player() {
        InitPieces();
        this.name = null;
    }

    Player(String player) {
        this();
        this.name = player;
    }

    //Getters
    public Vector<Piece> getPieces() { return this.pieces; }
    public boolean isPlacing() { return this.hasInBag(); }
    public boolean hasTwoPieces() {return (this.getPieces().size() == 2); }
    public boolean isFlying() { return (this.getPieces().size() == 3); }

    //Setter
    public void setPieces(Vector<Piece> newPieces) { this.pieces = newPieces; }

    //Initializer
    private void InitPieces(){
        pieces = new Vector<>();
        for (int i = 0; i < START_COUNT; i++) {
            pieces.add(new Piece());
        }
    }

    //Helper Functions
    private boolean hasInBag() {
        for (Piece piece : this.getPieces()){
            if (piece.getPair().equals(IN_BAG)){
                return true; //Player still has at least one unplaced piece
            }
        }
            return false; //Player does not have unplaced pieces
    }
    



}

