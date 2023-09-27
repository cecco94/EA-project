package controller.playState.pathfinding;

import java.util.ArrayList;

import controller.playState.PlayStateController;
import model.mappa.Map;

public class PathFinder {

	private Node[][] graph;
	//open, vuol dire che è nella lista ma non sono stati ancora inseriti i suoi vicini
	private ArrayList<Node> openList, pathList;
	private PlayStateController play;
	private Node startNode, goalNode, currentNode;
	private boolean goalReached = false;
	private int steps;
	private int roomCol, roomRow;
	
	public PathFinder(PlayStateController p, int maxRow, int maxCol) {
		play = p;
		openList = new ArrayList<>();
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
				graph[row][col].setOpen(false);
				graph[row][col].setChecked(false);
				graph[row][col].setSolid(false);
			}
		
		openList.clear();
		pathList.clear();
		goalReached = false;
		steps = 0;
	}
	
	private void setNodes(int startCol, int startRow, int goalCol, int goalRow, boolean isEnemy) {
		resetNodes();
		
		//occhio a come definiamo questi valori di partenza: valori nella finestra != valori nella mappa
		//penso che basti dividere valore nella mappa per numero di col/row nella finestra
		startNode = graph[startRow][startCol];
		currentNode = startNode;
		goalNode = graph[goalRow][goalCol];
		
		//all'inizio la lista contiene solo il nodo di partenza
		openList.add(currentNode);
		
		//setta il costo di tutti i nodi
		for(int row = 0; row < roomRow; row++) {
			for(int col = 0; col < roomCol; col++) {
				//per ora, siccome le dimensioni della stanza sono quasi sempre minori di quelle del grafo, ci viene un errore di indice
				try {
					int tileIndex = play.getController().getModel().getMap().getLayer(play.getCurrentroomIndex(), Map.THIRD_LAYER)[row][col];
					
					if(play.getController().getModel().getTilesetModel().getTile(tileIndex).isSolid())
						graph[row][col].setSolid(true);
					
					else {
						//dopo aver visto i tile, controlla la posizione delle entità
						if(play.getRoom(play.getCurrentroomIndex()).getEntityPositionsForPathFinding()[row][col] == 1) 
							graph[row][col].setSolid(true);
						
						//il menico non considera solido il tile occupato dal giocatore, in questo modo può inseguirlo
						if(!isEnemy && play.getRoom(play.getCurrentroomIndex()).getEntityPositionsForPathFinding()[row][col] == 2)
							graph[row][col].setSolid(true);
						
						else
							setCostOfThisNode(graph[row][col]);
					}
						
				}
				//se è un nodo che non corrisponde ad un tile della mappa perchè la mappa è più piccola, lo settiamo solido, così non dovrebbe rompere
				catch(IndexOutOfBoundsException e) {
					graph[row][col].setSolid(true);
				}
			}
		}
		
	}

	private void setCostOfThisNode(Node node) {
		//g cost, distanza di ciascun nodo dalla partenza 
		int xDistance = Math.abs(node.getColInGraph() - startNode.getColInGraph());
		int yDistance = Math.abs(node.getRowInGraph() - startNode.getRowInGraph());
		node.setDistanceFromStart(xDistance + yDistance);
		
		//h cost, distanza di ciasun nodo dall'arrivo
		xDistance = Math.abs(node.getColInGraph() - goalNode.getColInGraph());
		yDistance = Math.abs(node.getRowInGraph() - goalNode.getRowInGraph());
		node.setDistanceFromGoal(xDistance + yDistance);
		
		//f costo generale del nodo
		node.setCompleteDistance(node.getDistanceFromStart() + node.getDistanceFromGoal());
	}
	
	public boolean existPath(int startCol, int startRow, int goalCol, int goalRow, boolean isEnemy) {
		setNodes(startCol, startRow, goalCol, goalRow, isEnemy);
		
		//se il nodo di arrivo non è valido, si ferma subito
		if(graph[goalRow][goalCol].isSolid()) {
			return false;
		}
		
		while(goalReached == false && steps < 500) {
			
			int col = currentNode.getColInGraph();
			int row = currentNode.getRowInGraph();
			
			checkCurrentNode(col, row);
			
			findTheBestNode();
			
			if(currentNode == goalNode) {
				goalReached = true;
				trackThePath();
			}
			//if there is no node to check and we aree not in the goal, there is no more to do
			if(openList.isEmpty())
				return false;
			
			steps++;
		}
		
	//	printPathList();
		return goalReached;
	}
	
	private void checkCurrentNode(int col, int row) {
		currentNode.setChecked(true);
		openList.remove(currentNode);
		
		//open the up Node
		if(row - 1 >= 0)
			openNode(graph[row -1][col]);
		
		//open the down Node
		if(row + 1 < roomRow)
			openNode(graph[row + 1][col]);
		
		//open the left Node
		if(col - 1 >= 0)
			openNode(graph[row][col - 1]);
		
		//open the right Node
		if(row + 1 < roomCol)
			openNode(graph[row][col + 1]);
			
	}

	private void findTheBestNode() {
		int bestNodeIndex = 0;
		int bestNodeCost = 999;
		
		if(openList.size() > 1) { 
			
			for(int i = 0; i < openList.size(); i++) {
				//first, check the f cost
				if(openList.get(i).getCompleteDistance() < bestNodeCost) {
					bestNodeIndex = i;
					bestNodeCost = openList.get(i).getCompleteDistance();
				}
				//if i cost is equal, check the g cost
				else if(openList.get(i).getCompleteDistance() == bestNodeCost) {
					if(openList.get(i).getDistanceFromStart() < openList.get(bestNodeIndex).getDistanceFromStart())
						bestNodeIndex = i;
				}		
			}
			//if the openList.size = 0, we have no exception, because we use this command only if size > 1
			currentNode = openList.get(bestNodeIndex);
		}
		

	}

	private void openNode(Node node) {
		if(node.isOpen() == false && node.isChecked() == false && node.isSolid() == false) {
			node.setOpen(true);
			node.setParent(currentNode);
			openList.add(node);
		}
	}
	
	public void printPathList() {
		System.out.println("goal found " + goalReached + ", in " + steps + " steps");
		for(int i = 0; i < pathList.size(); i++) {
			System.out.println(pathList.get(i).getColInGraph() + ", " + pathList.get(i).getRowInGraph());
		}
		System.out.println("-----------------------------------------");
	}
	
	private void trackThePath() {
		Node current = goalNode;
		while(current != startNode) {
			pathList.add(0, current);
			current = current.getParent();
		}
	}
	
	//questa restituisce il riferimento
	public ArrayList<Node> getPathList() {
		return pathList;
	}
	
	//questa prende il percorso di una entità e lo riscrive
	public void getPathToEntity(ArrayList<Node> entityPath) {
		for(int index = 0; index < pathList.size(); index++) {
			entityPath.add(pathList.get(index));
		}
	}
	
	
	
}
