package astar;
import java.util.*;

/**
 * Interface of a search node.
 */
public interface ISearchNode {
  

	public int hashCode();
	 
    public double costToSuccessor(ISearchNode successor);

    public ArrayList<ISearchNode> getSuccessors();

    public ISearchNode getParent();

    public void setParent(ISearchNode parent);
    
    public void setEstimatedCost(double g);

    public double heuristicCost();
   
    public Integer keyCode();
    
    public boolean equals(Object other);

    public double estimatedCost();

    public double tentativeCost();
}

