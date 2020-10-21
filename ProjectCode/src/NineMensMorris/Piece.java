package NineMensMorris;
import java.awt.*;

public class Piece {
    //Variable
    private Point pair;
    private String name;

    //Constructors
    Piece() { this.pair = new Point(Game.IN_BAG); }
    Piece(Point newPair, String newName){ this.pair = newPair; this.name = newName; }

    //Getter
    public Point getPair() { return this.pair; }
    public String getName() {return this.name; }



    //Setter
    public void setPair(Point newPair) { this.pair.setLocation(newPair); }


}
