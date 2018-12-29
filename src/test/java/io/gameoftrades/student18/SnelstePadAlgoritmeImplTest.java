/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student18;

import io.gameoftrades.model.Handelaar;
import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author keanYl
 */
public class SnelstePadAlgoritmeImplTest {

    private Handelaar handelaar;

    @Before
    public void init() {
        handelaar = new HandelaarImpl();
    }

  
    @Test
    public void StartEnEindCoordinaatMoetNietHetzelfdeZijn() {
        try {
            Wereld wereld = handelaar.nieuweWereldLader().laad("/kaarten/voorbeeld-kaart.txt");
            Kaart kaart = wereld.getKaart();
            Stad van = wereld.getSteden().get(0);
            Stad zelfde = wereld.getSteden().get(1);
            SnelstePadAlgoritme algoritme = handelaar.nieuwSnelstePadAlgoritme();
            algoritme.bereken(kaart, van.getCoordinaat(), zelfde.getCoordinaat());
        } catch (IllegalArgumentException e) {
            assertEquals("start en doel is hetzelfde", e.getMessage());
        }
    }
}
