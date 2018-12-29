package io.gameoftrades.student18;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;

/**
 *
 * @author kean yew lee
 *
 */
public class Node {

    private final Coordinaat Position;
    private final Coordinaat eind;
    private final Kaart kaart;
    private final int HCost;
    private Node parent;
    private int GCost;

    //   nieuwe klasse voor de aster algoritme om de per node data op te slaan

    public Node(Coordinaat Position, Coordinaat Eind, Kaart kaart) {
        this.Position = Position;
        this.kaart = kaart;
        this.eind = Eind;

        GCost = kaart.getTerreinOp(Position).getTerreinType().getBewegingspunten();
        HCost = (Math.abs(eind.getX() - Position.getX()) + Math.abs(eind.getY() - Position.getY()));
    }

    public Coordinaat getPosition() {
        return Position;
    }

    public int getGCost() {
        return this.GCost;
    }

    public int getTerreinGCost() {
        int getTerreinGCost = kaart.getTerreinOp(Position).getTerreinType().getBewegingspunten();
        return getTerreinGCost;
    }

    public void setGCost(int GCost) {
        this.GCost = GCost;
    }

    public int getFCost() {
        return GCost + HCost;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        Node temp = (Node) o;

        if (temp.getPosition().equals(this.getPosition())) {
            return true;
        }
        return false;
    }
}
