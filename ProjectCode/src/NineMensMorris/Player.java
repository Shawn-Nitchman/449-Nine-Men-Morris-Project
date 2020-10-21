package NineMensMorris;
import java.util.Vector;

public class Player {
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
    public String getName() { return this.name; }
    public boolean isPlacing() { return this.hasInBag(); }
    public boolean hasTwoPieces() {return (this.pieces.size() == 2); }
    public boolean isFlying() { return (this.pieces.size() == 3); }

    //Setter
    public void setPieces(Vector<Piece> newPieces) { this.pieces = newPieces; }

    //Initializer
    private void InitPieces(){
        pieces = new Vector<>();
        for (int i = 0; i < Game.START_COUNT; i++) {
            pieces.add(new Piece(Game.IN_BAG, this.name));
        }
    }

    //Helper Functions
    private boolean hasInBag() {
        for (Piece piece : this.getPieces()){
            if (piece.getPair().equals(Game.IN_BAG)){
                return true; //Player still has at least one unplaced piece
            }
        }
            return false; //Player does not have unplaced pieces
    }
    



}

