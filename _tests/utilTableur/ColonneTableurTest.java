package utilTableur;

import dataCenter.CellKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColonneTableurTest {
    private String strActual;
    private int intActual;

    @Test
    void toNumber() {
        intActual = ColonneTableur.toNumber("A");
        assertEquals(0, intActual);

        intActual = ColonneTableur.toNumber("AA");
        assertEquals(26, intActual);

        intActual = ColonneTableur.toNumber("BA");
        assertEquals(52, intActual);
    }

    @Test
    void toName() {
        strActual = ColonneTableur.toName(3);
        assertEquals("C", strActual);

        strActual = ColonneTableur.toName(27);
        assertEquals("AA", strActual);
    }

    @Test //Pour que ce test fonctionne le equals doit correctement définit dans la classe CellKey
    void convertirNomCell_A_LigneColonne() {

        assertEquals(new CellKey(0,0), ColonneTableur.convertirNomCell_A_LigneColonne("A1"));
        assertEquals(new CellKey(1,0), ColonneTableur.convertirNomCell_A_LigneColonne("A2"));
    }
}