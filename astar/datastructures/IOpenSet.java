package astar.datastructures;

import astar.ISearchNode;

public interface IOpenSet {
	
	public void add(ISearchNode node);
	public void remove(ISearchNode node);
	public ISearchNode poll();
	public ISearchNode getNode(ISearchNode node);
	public int size();

}
