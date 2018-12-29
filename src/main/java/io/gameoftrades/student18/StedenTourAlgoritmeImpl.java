package io.gameoftrades.student18;

import io.gameoftrades.debug.AsciiArtDebugger;
import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;

import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author keanyewlee
 */
public class StedenTourAlgoritmeImpl implements StedenTourAlgoritme, Debuggable {

    private Debugger debug = new AsciiArtDebugger();

    private Kaart Kaart;

    private List<Stad> Steden;
    private List<Stad> Steden1;
    private List<Stad> Volgorde;

    private int LowestTour;
    private int TourCost;
    private int lowesttour;
    private int lowesttour1;

    @Override
    public List<Stad> bereken(Kaart kaart, List<Stad> steden) {
        this.Kaart = kaart;
        Volgorde = new ArrayList<>();

        Steden1 = new ArrayList(steden);
        LowestTour = 1000000000;
        if(Steden1.isEmpty()){
         throw new IllegalArgumentException("geen steden mee gekregen");
        
        }

        setstartCity();
        opt2();
        debug.debugSteden(kaart, Volgorde);
        return Volgorde;
    }

    private void setstartCity() {
//        ieder stad geven in de list naar de algoritme
        for (int i = 0; i < Steden1.size(); i++) {
            Steden = new ArrayList(Steden1);

            GreedyAlgorithm(Steden1.get(i));

        }

    }

    private void GreedyAlgorithm(Stad CurrentCity) {
        
//        dit algoritme pakt voor de currentcity steeds de kortste pad naar een ander 
        List<Stad> checkpad = new ArrayList();
        AStar Distance = new AStar();

        TourCost = 0;
        while (!Steden.isEmpty()) {
            int check = 1000000000;
            int x = 0;

            Steden.remove(CurrentCity);
            checkpad.add(CurrentCity);

            if (!Steden.isEmpty()) {

                for (int i = 0; i < Steden.size(); i++) {

                    int GCost = Distance.calculatepath(Kaart, CurrentCity.getCoordinaat(), Steden.get(i).getCoordinaat());

                    int Hcost = (Math.abs(Steden.get(i).getCoordinaat().getX()) - Math.abs(CurrentCity.getCoordinaat().getX()))
                            + (Math.abs(Steden.get(i).getCoordinaat().getY()) - Math.abs(CurrentCity.getCoordinaat().getY()));

                    int FCost = GCost + Hcost;
                    if (FCost < check) {
                        check = FCost;
                        x = i;
                    }

                }
                CurrentCity = Steden.get(x);

                TourCost = TourCost + check;
            }
        }

        GetTourLowestFCost(checkpad);

    }

    private void GetTourLowestFCost(List checkpad) {
//        vergelijken welke pad het minst lang is
        if (TourCost < LowestTour) {
            LowestTour = TourCost;
            Volgorde = checkpad;

        }
    }

    private void opt2() {
//        opt2 om de pad meer te optimaliseren
        lowesttour = GetDistance(Volgorde);
        System.out.println(lowesttour);
        int totalcost = 0;
        lowesttour1 = 0;
        while (lowesttour1 < lowesttour) {
            List<Stad> dummy = new ArrayList(Volgorde);
            lowesttour=lowesttour1;
            for (int i = 0; i < Volgorde.size(); i++) {
                for (int k = i + 1; k < Volgorde.size(); k++) {

                    Collections.swap(dummy, i, k);
                    totalcost = GetDistance(dummy);
                    System.out.println(totalcost);
                    lowesttour1 = totalcost;
                    if (lowesttour1 < lowesttour) {
                        System.out.println(totalcost);
                        lowesttour1 = totalcost;
                        Volgorde = dummy;
                    }
                }
            }
        }
    }

    private int GetDistance(List<Stad> Dummy) {
//        de afstand berekenen per pad
        AStar Distance = new AStar();
        int totalcost = 0;
        for (int i = 0; i + 1 < Dummy.size(); i++) {

            int GCost = Distance.calculatepath(Kaart, Dummy.get(i).getCoordinaat(), Dummy.get(i + 1).getCoordinaat());
            totalcost = GCost + totalcost;

        }
        return totalcost;
    }

    @Override
    public void setDebugger(Debugger debugger) {
        this.debug = debugger;
    }
     public String toString() {
        return "A*/opt2 algorithm";
    }
}
