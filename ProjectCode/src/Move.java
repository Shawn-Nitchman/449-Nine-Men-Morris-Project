import java.awt.*;

public class Move extends Game.GamePlay {

    public boolean isOpen(Point pair) {


//        if (playerMap.containsValue(pair)) {
//            return false;
//
//        }
//        else{
//            return true;
//        }


        return true;
    }

    public boolean isFlying(Player player)  {

        /*
        iterate through map counting the keys. is the keys count = 3 ?


         */

        return true;
    }

    public boolean move(Player player, Point oldPair, Point newPair) {

        /*

        if isOpen(newPair) && isLegal(player, oldPair, newPair)
            oldPair = new Point(newPair.x, newPair.y);
            return true;
        else
            return false;

         */

        return true;
    }

    public boolean isLegal(Player player, Point oldPair, Point newPair) {
        /*


        if oldPair == Point (-1, -1)
            return true;
        else if isFlying(player)
            return true;
        else
            do math.


         */
        return true;
    }




}
