/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student18;

import io.gameoftrades.model.Handelaar;
import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.kaart.Stad;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author keanYl
 */
public class StedenTourAlgoritmeImplTest {

    private Handelaar handelaar;

    @Before
    public void init() {
        handelaar = new HandelaarImpl();
    }

    @Test
    public void DeListStedenMoetNietLeegZijn() {
        try {
            Wereld wereld = handelaar.nieuweWereldLader().laad("/kaarten/voorbeeld-kaart.txt");

            StedenTourAlgoritme algoritme = handelaar.nieuwStedenTourAlgoritme();
            List leeg = new ArrayList();
            List<Stad> result = algoritme.bereken(wereld.getKaart(), leeg);

        } catch (IllegalArgumentException e) {
            assertEquals("geen steden mee gekregen", e.getMessage());
        }
    }
}
