package NineMensMorris;
import java.awt.*;

public class Piece {
    //Variable
    private Point pair;
    private final Player myPlayer;

    //Constructor
    Piece(Point newPair, Player player){ this.pair = newPair; this.myPlayer = player; }

    //Getters
    public Point getPair() { return this.pair; }
    public Player getMyPlayer() {return this.myPlayer; }

    //Setter
    public void setPair(Point newPair) { this.pair = newPair; }
}
