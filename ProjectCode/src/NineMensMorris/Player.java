package ProjectCode.src.NineMensMorris;
import java.util.Vector;

public class Player {
    //Variables
    private String name;
    private Vector<Piece> pieces;

    //Constructors
    Player(String player) {
        InitPieces();
        this.name = player;
    }

    //Getters
    public Vector<Piece> getPieces() { return this.pieces; }
    public String getName() { return this.name; }
    public boolean hasTwoPieces() {return (this.pieces.size() <= 2); }
    public boolean isFlying() { return (this.pieces.size() == 3); }

    //Setter for testing
    public void setPieces(Vector<Piece> newPieces) { this.pieces = newPieces; }

    //Initializer
    private void InitPieces(){
        pieces = new Vector<>();
        for (int i = 0; i < Game.START_COUNT; i++) {
            pieces.add(new Piece(Game.IN_BAG, this));
        }
    }
}

