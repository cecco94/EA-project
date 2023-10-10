package controller.playState.pathfinding;

public class Node {

	private Node parent;
	private int col, row;
	private int distanceFromStart, distanceFromGoal, completeDistance;
	private boolean solid, inListOfNodesToExplore, explored;
	
	public Node(int row, int col) {
		this.col = col;
		this.row = row;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getColInGraph() {
		return col;
	}

	public int getRowInGraph() {
		return row;
	}

	public int getDistanceFromStart() {
		return distanceFromStart;
	}

	public void setDistanceFromStart(int gCost) {
		this.distanceFromStart = gCost;
	}

	public int getDistanceFromGoal() {
		return distanceFromGoal;
	}

	public void setDistanceFromGoal(int hCost) {
		this.distanceFromGoal = hCost;
	}

	public int getCompleteDistance() {
		return completeDistance;
	}

	public void setCompleteDistance(int fCost) {
		this.completeDistance = fCost;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public boolean isInListOfNodesToExplore() {
		return inListOfNodesToExplore;
	}

	public void setInListOfNodesToExplore(boolean open) {
		this.inListOfNodesToExplore = open;
	}

	public boolean isExplored() {
		return explored;
	}

	public void setExplored(boolean checked) {
		this.explored = checked;
	}
}
