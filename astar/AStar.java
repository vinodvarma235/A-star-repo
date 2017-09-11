package astar;
import java.util.ArrayList;
import java.util.Comparator;

import astar.datastructures.ClosedSet;
import astar.datastructures.ClosedSetHash;
import astar.datastructures.IClosedSet;
import astar.datastructures.IOpenSet;
import astar.datastructures.OpenSet;

/**
 * A* Algorithm for shortest path 
 * 
 *
 */

public class AStar {
    private int verbose = 0;

    private int maxSteps = -1;

    private int numSearchSteps;
    
    public ISearchNode bestNodeAfterSearch;

    public AStar() {
    }

    /**
     * Returns the shortest Path from a start node to an end node.
     * 
     */
    public ArrayList<ISearchNode> shortestPath(ISearchNode initialNode, IGoalNode goalNode) {
        ISearchNode endNode = this.search(initialNode, goalNode);
        if(endNode == null) 
            return null;
        return AStar.path(endNode);
    }
        

    /**
     * @param initialNode start of the search
     * @param goalNode end of the search
     * @return goal node from which you can reconstruct the path
     */
    public ISearchNode search(ISearchNode initialNode, IGoalNode goalNode) {

    	boolean implementsHash = initialNode.keyCode()!=null;
        IOpenSet openSet = new OpenSet(new SearchNodeComparator());
        openSet.add(initialNode);
        IClosedSet closedSet = implementsHash ? new ClosedSetHash(new SearchNodeComparator())
        						: new ClosedSet(new SearchNodeComparator());
        this.numSearchSteps = 0;

        while(openSet.size() > 0 && (maxSteps < 0 || this.numSearchSteps < maxSteps)) {
            ISearchNode currentNode = openSet.poll();

            System.out.println((verbose>1 ? "Open set: " + openSet.toString() + "\n" : "") 
                        + (verbose>0 ? "Current node: "+currentNode.toString()+"\n": "")
                        + (verbose>1 ? "Closed set: " + closedSet.toString() : ""));

            if(goalNode.inGoal(currentNode)) {
                this.bestNodeAfterSearch = currentNode;
                return currentNode;
            }
            ArrayList<ISearchNode> successorNodes = currentNode.getSuccessors();
            for(ISearchNode successorNode : successorNodes) {
                boolean inOpenSet;
                if(closedSet.contains(successorNode))
                    continue;
                ISearchNode discSuccessorNode = openSet.getNode(successorNode);
                if(discSuccessorNode != null) {
                    successorNode = discSuccessorNode;
                    inOpenSet = true;
                } else {
                    inOpenSet = false;
                }
                double tentativeG = currentNode.estimatedCost() + currentNode.costToSuccessor(successorNode);
                if(inOpenSet && tentativeG >= successorNode.estimatedCost())
                    continue;
                successorNode.setParent(currentNode);
                if(inOpenSet) {
                    openSet.remove(successorNode);
                    successorNode.setEstimatedCost(tentativeG);
                    openSet.add(successorNode);
                } else {
                    successorNode.setEstimatedCost(tentativeG);
                    openSet.add(successorNode);
                }
            }
            closedSet.add(currentNode);
            this.numSearchSteps += 1;
        }
        
        this.bestNodeAfterSearch = closedSet.min();
        return null;
    }

    /**
     * @param node
     * @return
     */
    public static ArrayList<ISearchNode> path(ISearchNode node) {
        ArrayList<ISearchNode> path = new ArrayList<ISearchNode>();
        path.add(node);
        ISearchNode currentNode = node;
        while(currentNode.getParent() != null) {
            ISearchNode parent = currentNode.getParent();
            path.add(0, parent);
            currentNode = parent;
        }
        return path; 
    }

    public int numSearchSteps() {
        return this.numSearchSteps;
    }

    public ISearchNode bestNodeAfterSearch() {
        return this.bestNodeAfterSearch;
    }

    public void setMaxSteps(int maxSteps) {
        this.maxSteps = maxSteps;
    }
    
    static class SearchNodeComparator implements Comparator<ISearchNode> {
    	public int compare(ISearchNode node1, ISearchNode node2) {
    		return Double.compare(node1.estimatedCost(), node2.estimatedCost());
    	}
    }

}
