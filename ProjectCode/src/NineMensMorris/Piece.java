package NineMensMorris;
import java.awt.*;

public class Piece {
    //Variable
    private Point pair;

    //Constructors
    Piece() { this.pair = new Point(Game.IN_BAG); }
    Piece(Point newPair){ this.pair = newPair; }

    //Getter
    public Point getPair() { return this.pair; }

    //Setter
    public void setPair(Point newPair) { this.pair.setLocation(newPair); }
}
