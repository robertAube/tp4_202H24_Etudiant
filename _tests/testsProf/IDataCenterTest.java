package testsProf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tableur.Tableur;
import dataCenter.*;


import static org.junit.jupiter.api.Assertions.*;

class IDataCenterTest {
    IDataCenter dataCenter;
    IDataCenter[] tabDataCenter = {
            new TabDataCenter(Tableur.NB_LIGNE, Tableur.NB_COLONNE),
//            new LinkedListDataCenter(),
//            new ArrayListDataCenter(),
//            new VectorDataCenter(),
//            new HashSetDataCenter(),
//            new TreeSetDataCenter(),
//            new HashMapDataCenter(),
//            new TreeMapDataCenter()
    };


    @BeforeEach
    void setup() {
    }

    @Test
    void tester_() {
        for (IDataCenter iDataCenter : tabDataCenter) {
        }
    }
}
