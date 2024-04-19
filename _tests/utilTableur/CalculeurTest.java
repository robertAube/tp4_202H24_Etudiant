package utilTableur;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculeurTest {

    private double actual;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getReponse() {
        actual = Calculeur.calculer("1+ 2");
        assertEquals(3.0, actual);

        actual = Calculeur.calculer("((4 - 2 ^ 3 + 1) * -sqrt(3 * 3 + 4 * 4)) / 2");
        assertEquals(7.5, actual);
    }
}