package NineMensMorris;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class AutoPlay extends Player {

    private int xCord, yCord;

    AutoPlay(String player) {
        super(player);
    }

    public Point randomGenerator() {
        Random rand = new Random();
        xCord = rand.nextInt(2);
        yCord = rand.nextInt(7);
        return new Point(xCord, yCord);
    }

    //random index generator, takes size of vector and returns a point
    public Point randomPointInVector(Vector<Point> pointVector){
        Random rand = new Random();
        int index = rand.nextInt(pointVector.size());
        return pointVector.elementAt(index);
    }

    public void computersTurn() {
        Game.GameState currentState = Gui.getMyGame().gameState;
        switch (currentState) {
            case Placing:
                placing();
                break;
            case Mill:
                break;
            case Moving:
                break;
            case MidMove:
                break;
        }
    }

    public boolean placing(){
        Point placingPoint = new Point(randomGenerator());
        if (Move.isOpen(placingPoint)){
            Move.changeLocation(this, Game.IN_BAG, placingPoint);
            return true;
        }
        return false;
    }

    public boolean removeOpponentsPiece(){
        return true;
    }

    public boolean moving(){
        return true;
    }



}
