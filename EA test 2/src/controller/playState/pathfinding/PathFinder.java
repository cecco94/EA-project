package controller.playState.pathfinding;

import java.util.ArrayList;

import controller.playState.PlayStateController;
import model.mappa.Map;

public class PathFinder {

	private PlayStateController play;

	private Node[][] graph;
	private ArrayList<Node> nodesToExplore, pathList;
	
	private Node startNode, goalNode, currentNode;
	private boolean goalReached = false;
	private int steps;
	private int roomCol, roomRow;
	
	public PathFinder(PlayStateController p, int maxRow, int maxCol) {
		play = p;
		nodesToExplore = new ArrayList<>();
		pathList = new ArrayList<>();
		roomCol = maxCol;
		roomRow = maxRow;
		createGraph();
		
	}
	
	private void createGraph() {
		graph = new Node[roomRow][roomCol];
		
		for(int row = 0; row < roomRow; row++)
			for(int col = 0; col < roomCol; col++)
				graph[row][col] = new Node(row, col);	
	}
	
	private void resetNodes() {
		
		for(int row = 0; row < roomRow; row++)
			for(int col = 0; col < roomCol; col++) {
				graph[row][col].setInListOfNodesToExplore(false);
				graph[row][col].setExplored(false);
				graph[row][col].setSolid(false);
				
				graph[row][col].setDistanceFromGoal(999);
				graph[row][col].setDistanceFromStart(999);
				graph[row][col].setCompleteDistance(999);
			}	
		nodesToExplore.clear();
		pathList.clear();
		goalReached = false;
		steps = 0;
	}
	
	private void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
		resetNodes();
		
		startNode = graph[startRow][startCol];
		goalNode = graph[goalRow][goalCol];
		
		//setta il costo di tutti i nodi, saltando quelli fuori dalle mura
		for(int row = 7; row < roomRow - 7; row++) {
			for(int col = 10; col < roomCol - 10; col++) {
				int tileIndex = play.getController().getModel().getMap().getLayer(play.getCurrentroomIndex(), Map.THIRD_LAYER)[row][col];
				
				if(play.getController().getModel().getTilesetModel().getTile(tileIndex).isSolid()) 
					graph[row][col].setSolid(true);
				
				else 
					setCostOfThisNode(graph[row][col]);
				}
			}
		
		startNode.setSolid(false);
		setCostOfThisNode(startNode);
		currentNode = startNode;
		
		//all'inizio la lista contiene solo il nodo di partenza
		nodesToExplore.add(currentNode);
	}

	private void setCostOfThisNode(Node node) {
		int xDistance = Math.abs(node.getColInGraph() - startNode.getColInGraph());
		int yDistance = Math.abs(node.getRowInGraph() - startNode.getRowInGraph());
		
		node.setDistanceFromStart(xDistance + yDistance);
		
		xDistance = Math.abs(node.getColInGraph() - goalNode.getColInGraph());
		yDistance = Math.abs(node.getRowInGraph() - goalNode.getRowInGraph());
		node.setDistanceFromGoal(xDistance + yDistance);
		
		node.setCompleteDistance(node.getDistanceFromStart() + node.getDistanceFromGoal());
	}
	
	public boolean existPath(int startCol, int startRow, int goalCol, int goalRow) {
		setNodes(startCol, startRow, goalCol, goalRow);
		
		//se il nodo di arrivo non è valido, si ferma subito
		if(graph[goalRow][goalCol].isSolid()) 
			return false;
		
		while(goalReached == false && steps < 300) {
			
			int col = currentNode.getColInGraph();
			int row = currentNode.getRowInGraph();
			
			exploreCurrentNode(col, row);
			
			findTheBestNode();
			
			if(currentNode == goalNode) {
				goalReached = true;
				trackThePath();
			}
			
			if(nodesToExplore.isEmpty())
				return false;
			
			steps++;
		}
		
		//printPathList();
		return goalReached;
	}
	
	private void exploreCurrentNode(int col, int row) {
		//open the up Node
		if(row - 1 >= 0)
			addNodeToListOfNodesToExlpore(graph[row -1][col]);
		
		//open the down Node
		if(row + 1 < roomRow)
			addNodeToListOfNodesToExlpore(graph[row + 1][col]);
		
		//open the left Node
		if(col - 1 >= 0)
			addNodeToListOfNodesToExlpore(graph[row][col - 1]);
		
		//open the right Node
		if(row + 1 < roomCol)
			addNodeToListOfNodesToExlpore(graph[row][col + 1]);
			
		currentNode.setExplored(true);
		nodesToExplore.remove(currentNode);
	}

	private void findTheBestNode() {
		int bestNodeIndex = 0;
		int bestNodeCost = 999;
		
		if(nodesToExplore.size() > 0) { 
			
			for(int i = 0; i < nodesToExplore.size(); i++) {
				//first, check the f cost
				if(nodesToExplore.get(i).getCompleteDistance() < bestNodeCost) {
					bestNodeIndex = i;
					bestNodeCost = nodesToExplore.get(i).getCompleteDistance();
				}
				//if i cost is equal, check the g cost
				else if(nodesToExplore.get(i).getCompleteDistance() == bestNodeCost) {
					if(nodesToExplore.get(i).getDistanceFromStart() < nodesToExplore.get(bestNodeIndex).getDistanceFromStart())
						bestNodeIndex = i;
				}		
			}
			//if the openList.size = 0, we have no exception, because we use this command only if size > 1
			currentNode = nodesToExplore.get(bestNodeIndex);
		}

	}

	private void addNodeToListOfNodesToExlpore(Node node) {
		if(node.isInListOfNodesToExplore() == false && node.isExplored() == false && node.isSolid() == false) {
			nodesToExplore.add(node);
			node.setInListOfNodesToExplore(true);

			node.setParent(currentNode);
		}
	}
	
	private void trackThePath() {
		Node current = goalNode;
		while(current != startNode) {
			pathList.add(0, current);
			current = current.getParent();
		}
	}
	
	//questa prende il percorso di una entità e lo riscrive
	public void getPathToEntity(ArrayList<Node> entityPath) {
		for(int index = 0; index < pathList.size(); index++) {
			entityPath.add(pathList.get(index));
		}
	}
	
	public void printPathList() {
		System.out.println("goal found " + goalReached + ", in " + steps + " steps");
		for(int i = 0; i < pathList.size(); i++) {
			System.out.println(pathList.get(i).getColInGraph() + ", " + pathList.get(i).getRowInGraph());
		}
		System.out.println("-----------------------------------------");
		System.out.println("startcol " + startNode.getColInGraph() + " startrow " +startNode.getRowInGraph() + " node solid " + startNode.isSolid());
		System.out.println("cost of start node " + startNode.getCompleteDistance());
//		for(int row = 0; row < roomRow; row++) {
//			for(int col = 0; col < roomCol; col++) {
//				System.out.print(graph[row][col].getDistanceFromStart() + "   " );
//			}
//			System.out.println();
//		}
	}
	
}
