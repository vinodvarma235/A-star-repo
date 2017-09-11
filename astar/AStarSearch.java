package astar;
import astar.ISearchNode;

/**
 * Implements trivial functions for a search node.
 */
public abstract class AStarSearch implements ISearchNode {
    private Double g = 0.0;

    public double estimatedCost() {
        return this.costFromStart() + this.heuristicCost();
    }

    public double costFromStart() {
        return this.g;
    }

    public void setEstimatedCost(double g) {
        this.g = g;
    } 
    
}

