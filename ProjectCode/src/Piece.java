import java.awt.*;

public class Piece {

    Point pair;
    Piece() {


        this.pair = null;
    }

    Piece(int x, int y){

        this.pair = new Point(x, y);
    }

    public Point getPair() {

        return this.pair;
    }
}
