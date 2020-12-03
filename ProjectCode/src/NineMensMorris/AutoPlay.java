package NineMensMorris;

import java.awt.*;
import java.util.Random;

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
