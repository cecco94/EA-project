package controller.playState.pathfinding;

import java.util.ArrayList;

import controller.playState.PlayStateController;
import model.mappa.Map;
import model.mappa.Rooms;

public class PathFinder {

	private Node[][] graph;
	//open, vuol dire che è nella lista ma non sono stati ancora inseriti i suoi vicini
	private ArrayList<Node> openList, pathiList;
	private PlayStateController play;
	private Node startNode, goalNode, currentNode;
	private boolean goalReached = false;
	private int steps;
	private int roomCol, roomRow;
	
	public PathFinder(PlayStateController p, int maxRow, int maxCol) {
		play = p;
		openList = new ArrayList<>();
		pathiList = new ArrayList<>();
		roomCol = maxCol;
		roomRow = maxRow;
		createGraph();
		
	}
	
	private void createGraph() {
		//invece di cercare il percorso in tutta la mappa, cerca il percorso solo all'interno della
		//finestra di gioco, questo perchè l'algoritomo ci serve solo per inseguire/scappare dal player
		//quindi possiamo risparmiare risorse creando un grafo più piccolo
		
		//per ora lo testo sulla bilioteca
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
		pathiList.clear();
		goalReached = false;
		steps = 0;
	}
	
	private void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
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
					int tileIndex = play.getController().getModel().getMap().getLayer(Rooms.currentRoom.mapIndex, Map.THIRD_LAYER)[row][col];
					if(play.getController().getModel().getTilesetModel().getTile(tileIndex).isSolid())
						graph[row][col].setSolid(true);
					else
						setCostOfThisNode(graph[row][col]);
				}
				//se è un nodo che non corrisponde ad un tile della mappa perchè la mappa è più piccola, lo settiamo solido, così non dovrebbe rompere
				catch(IndexOutOfBoundsException e) {
					graph[row][col].setSolid(true);
				}
			}
		}
		
		//prendiamo dalla mappa solo la sottomatrice dei tiles nella finestra di gioco, aggiungiamo questi a row, col
		//int firstColInFrame = (int)(play.getPlayer().getHitbox().x)/play.getController().getTileSize() - 10;
		//int firstRowInFrame = (int)(play.getPlayer().getHitbox().y)/play.getController().getTileSize() - 7;

//		int col = 0;
//		int row = 0;
//			
//		while(col < colonne && row < righe) {
//			//set solid
//			int tileNum = play.getController().getModel().getMap().getLayer(play.getCurrentroomIndex(), 2)[row][col];
//			if(play.getController().getModel().getTilesetModel().getTile(tileNum).isSolid())
//				graph[row][col].setSolid(true);
//			
//			stCostOfAllNodes(graph[row][col]);
//			
//			col++;
//			if(col == colonne) {
//				col = 0;
//				row++;
//			}
//			
//		}
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
	
	public boolean search(int startCol, int startRow, int goalCol, int goalRow) {
		setNodes(startCol, startRow, goalCol, goalRow);
		
		//se il nodo di partenza o di arrivo non sono validi, si ferma subito
//		if(graph[startRow][startCol].isSolid()) {
//			System.out.println("nodo di partenza solido");
//			return false;
//		}
		if(graph[goalRow][goalCol].isSolid()) {
			System.out.println("nodo di arrivo solido");
			return false;
		}
		
		while(goalReached == false && steps < 700) {
			
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
		
		drawGraph();
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
	
	public void drawGraph() {
//		for(int row = 0; row < graph.length; row++) {
//			for(int col = 0; col < graph[0].length; col++) {
//				System.out.print(graph[row][col].getDistanceFromGoal() + " ");
//				if(graph[row][col].isSolid())
//					System.out.print(1);
//				else
//					System.out.print(0);
//			}
//			System.out.println();
//		}
		System.out.println("goal found " + goalReached + ", in " + steps + " steps");
		for(int i = 0; i < pathiList.size(); i++) {
			System.out.println(pathiList.get(i).getColInGraph() + ", " + pathiList.get(i).getRowInGraph());
		}
		System.out.println("-----------------------------------------");
	}
	
	private void trackThePath() {
		Node current = goalNode;
		while(current != startNode) {
			pathiList.add(0, current);
			current = current.getParent();
		}
	}
	
	public ArrayList<Node> getPathList(){
		return pathiList;
	}
	
	
	
	
	
}
