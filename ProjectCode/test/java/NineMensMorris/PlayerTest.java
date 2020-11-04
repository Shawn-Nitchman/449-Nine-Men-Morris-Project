package NineMensMorris;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {

    Player player;
    @BeforeEach
    void setUp() {
        player = new Player("playerTest");
    }

    @AfterEach
    void tearDown() {
        player = null;
    }


    @Test
    public void hasTwoPieces() {
        
    }

    @Test
    void isFlying() {


    }


}