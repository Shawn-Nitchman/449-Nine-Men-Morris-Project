package NineMensMorris;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class AutoPlayTest {
    AutoPlay autoBot;

    @BeforeEach
    void setUp() {
        autoBot = new AutoPlay("Decepticon");

    }

    @AfterEach
    void tearDown() {
        autoBot = null;
    }

    @Test
    void randomGenerator() {

        for (int i = 0; i < 50; i++) {
            Point testPoint = new Point(autoBot.randomGenerator());
            assertTrue(testPoint.x >= 0 && testPoint.x <= 2);
            assertTrue(testPoint.y >= 0 && testPoint.y <= 7);
            assertFalse(testPoint.x < 0 && testPoint.x >2);
            assertFalse(testPoint.y < 0 && testPoint.y > 7);
        }
    }
}