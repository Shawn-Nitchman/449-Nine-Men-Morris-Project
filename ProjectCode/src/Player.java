import java.util.ArrayList;

public class Player extends Game.GamePlay {


    String color;
    Player() {


        this.color = "unknown";
    }

    Player(String color, Piece piece) {

        playerMap.get(color).add(piece);
        this.color = color;
    }

    



}

