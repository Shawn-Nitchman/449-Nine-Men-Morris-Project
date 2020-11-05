package NineMensMorris;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.shape.Ellipse;

import java.awt.*;

public class Piece {
    //Variable
    private Point pair;
    private final Player myPlayer;
    public static Ellipse visualPiece;

    //Constructor
    Piece(Point newPair, Player player){ this.pair = newPair; this.myPlayer = player;}

    //Getters
    public Point getPair() { return this.pair; }
    public Player getMyPlayer() {return this.myPlayer; }

    public static void drawVisualPiece(double width, double height,
                                       ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty){
        visualPiece = new Ellipse(width, height, width, height);
        visualPiece.centerXProperty().bind(widthProperty.divide(2));
        visualPiece.centerYProperty().bind(heightProperty.divide(2));
        visualPiece.radiusXProperty().bind(widthProperty.divide(3));
        visualPiece.radiusYProperty().bind(heightProperty.divide(3));
        visualPiece.setStroke(javafx.scene.paint.Color.BLACK);
    }

    //Setter
    public void setPair(Point newPair) { this.pair = newPair; }
   }

