import java.awt.*;

public class Piece {
    int x, y;
    Point pair;
    Piece() {

        x = -1;
        y = -1;
        this.pair = null;
    }

    Piece(int x, int y){
        this.x = x;
        this.y = y;
        this.pair = new Point(this.x, this.y);
    }

    public Point getPair() {

        return this.pair;
    }
}
