import java.awt.*;

public class Piece {
    String color;
    int x, y;
    Point pair;
    Piece() {

        x = -1;
        y = -1;
        this.color = "unknown";
        this.pair = null;
    }

    Piece(int x, int y){
        this.x = x;
        this.y = y;
        this.pair = new Point(this.x, this.y);
    }

    public void setColor(String color) {

        this.color = color;
    }

    public String getColor() {

        return this.color;
    }

    public Point getPair() {

        return this.pair;
    }
}
