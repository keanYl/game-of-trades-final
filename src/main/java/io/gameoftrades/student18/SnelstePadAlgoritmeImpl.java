package io.gameoftrades.student18;

import io.gameoftrades.debug.AsciiArtDebugger;
import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;

import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author kean yew lee
 *
 */
public class SnelstePadAlgoritmeImpl implements SnelstePadAlgoritme, Debuggable {

    private Debugger debug = new AsciiArtDebugger();

    private PadImpl Pad;
    private Coordinaat Start, End;
    private Kaart kaart;

    private List<Coordinaat> RightPath;
    private List<Node> OpenList;
    private List<Node> ClosedList;

    private int GCostPath;

    @Override
    public Pad bereken(Kaart kaart, Coordinaat Start, Coordinaat End) {
        this.Start = Start;
        this.End = End;
        this.kaart = kaart;
        RightPath = new ArrayList();
        OpenList = new ArrayList();
        ClosedList = new ArrayList();
        if (Start.equals(End)) {
            throw new IllegalArgumentException("start en doel is hetzelfde");
        }
        AStarAlgorithm();
        CalculateShortestPath();

        GCostPath = ClosedList.get(ClosedList.size() - 1).getGCost();
        Collections.reverse(RightPath);
        Pad = new PadImpl(RightPath, GCostPath-1);
        debug.debugPad(kaart, Start, Pad);

        return Pad;
    }

    private void AStarAlgorithm() {
//        nieuwe node maken om de algoritme te maken
        Node startTile = new Node(Start, End, kaart);
//         Aster algoritme begint
        OpenList.add(startTile);
        Node CurrentTile = startTile;

        while (!CurrentTile.getPosition().equals(End)) {
            CurrentTile = CalculateLowestFCost(OpenList);

            OpenList.remove(CurrentTile);
            ClosedList.add(CurrentTile);

            List<Node> CurrentTileNB = getNeighbours(CurrentTile);
            for (int i = 0; i < CurrentTileNB.size(); i++) {
                Node Neighbour = CurrentTileNB.get(i);
                if (!ClosedList.contains(Neighbour)) {
                    if (OpenList.contains(Neighbour)) {
                        for (Node t : OpenList) {
                            if (Neighbour.equals(t) && Neighbour.getFCost() < t.getFCost()) {
                                t.setParent(CurrentTile);
                                t.setGCost(CurrentTile.getGCost() + t.getTerreinGCost());
                            }
                        }
                    }
                    if (!OpenList.contains(Neighbour)) {
                        Neighbour.setParent(CurrentTile);
                        Neighbour.setGCost(CurrentTile.getGCost() + Neighbour.getGCost());
                        OpenList.add(Neighbour);
                    }
                }
            }
        }
    }

    private List<Node> getNeighbours(Node CurrentTile) {
//        de buren van de currenttile in een array zetten
        List<Node> neighbours = new ArrayList();

        Richting[] Directions = kaart.getTerreinOp(CurrentTile.getPosition()).getMogelijkeRichtingen();

        for (Richting x : Directions) {
            neighbours.add(new Node(CurrentTile.getPosition().naar(x), End, kaart));
        }
        return neighbours;
    }

    private Node CalculateLowestFCost(List<Node> openList) {
//        laagste FCost in de openlist zoeken en terug geven

        Node LowestFNode = openList.get(0);

        for (Node x : openList) {
            if (x.getFCost() < LowestFNode.getFCost()) {
                LowestFNode = x;
            }

        }

        return LowestFNode;

    }

    private void CalculateShortestPath() {
//        pad zoeken via de parent

        Node CurrentTile = ClosedList.get(ClosedList.size() - 1);

        RightPath.add(End);
        while (!CurrentTile.getParent().getPosition().equals(Start)) {
            RightPath.add(CurrentTile.getParent().getPosition());
            CurrentTile = CurrentTile.getParent();
        }

        RightPath.add(Start);

    }

    @Override
    public void setDebugger(Debugger debugger) {
        this.debug = debugger;
    }

    @Override
    public String toString() {
        return "A* Algorrithm";
    }
}
