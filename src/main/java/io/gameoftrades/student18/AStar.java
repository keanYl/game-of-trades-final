package io.gameoftrades.student18;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Richting;

import java.util.ArrayList;
import java.util.List;
 
public class AStar {
 
    /**
     *
     * @author Damian/kean yewlee 
     *
     */
   
    private Coordinaat Start;
    private Coordinaat End;
    private Kaart kaart;
     
    private List<Node> OpenList;
    private List<Node> ClosedList;
    
    private int GCostPath;
    
    public int calculatepath(Kaart kaart, Coordinaat Start, Coordinaat End) {
        this.Start = Start;
        this.End = End;
        this.kaart = kaart;
      
        OpenList = new ArrayList();
        ClosedList = new ArrayList();
         
        AStarAlgorithm();

        GCostPath = ClosedList.get(ClosedList.size() - 1).getGCost();

        return GCostPath;
    }
 
    public void AStarAlgorithm() {
  
        Node startTile = new Node(Start, End, kaart);
//         astar algorithm begint
     
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
//      alle buren krijgen van de CurrentTile en in een array stoppen
        List<Node> neighbours = new ArrayList();
 
        Richting[] Directions = kaart.getTerreinOp(CurrentTile.getPosition()).getMogelijkeRichtingen();
 
        for (Richting x : Directions) {
            neighbours.add(new Node(CurrentTile.getPosition().naar(x), End, kaart));
        }
        return neighbours;
    }
 
    private Node CalculateLowestFCost(List<Node> openList) {
     //de node met de lowest fcost returnen
 
        Node LowestFNode = openList.get(0);
 
        for (Node x : openList) {
            if (x.getFCost() < LowestFNode.getFCost()) {
                LowestFNode = x;
            }
        }
 
        return LowestFNode;
 
    }
}
