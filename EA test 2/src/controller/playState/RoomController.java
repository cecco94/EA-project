package controller.playState;

import java.util.ArrayList;

import controller.playState.entityController.EntityController;
import controller.playState.entityController.enemyController.EnemyController;
import controller.playState.entityController.enemyController.RobotController;
import controller.playState.entityController.npcController.CatController;
import controller.playState.entityController.npcController.DocController;
import controller.playState.entityController.npcController.ErmenegildoController;
import controller.playState.entityController.npcController.NerdController;
import controller.playState.entityController.npcController.PupaController;

public class RoomController {

	private ArrayList<EnemyController> enemy;
	private ArrayList<EntityController> NPC;
	private PlayStateController play;
	//quante righe e quante colonne ha la stanza
	private int rowRoom, colRoom;
	
	//ogni stanza ha una matrice di booleani, ogni entità segna sul quadratino dove si trova true.
	//quando lascisa il quadratino, segna false e segna true su quello che oppurà
	//prima o poi finirànel model
	private int[][] entityPositionsForPathFinding;
	
	
	public RoomController(PlayStateController p, int righe, int colonne) {
		play = p;
		enemy = new ArrayList<>();
		NPC = new ArrayList<>();
		
		rowRoom = righe;
		colRoom = colonne;
		entityPositionsForPathFinding = new int[righe][colonne];
		resetMatriceEntita();

	}

	public void update() {
				
		for(int i = 0; i < NPC.size(); i++) {
			NPC.get(i).update();
		}
		
		for(int i = 0; i < enemy.size(); i++) {
			enemy.get(i).update();
		}
	}
	
//	public void removeEnemy(int index) {
//		enemy.remove(index);
//	}
//	
//	public void removeNPC(int index) {
//		NPC.remove(index);
//	}
	
	public ArrayList<EnemyController> getEnemy(){
		return enemy;
	}
	
	public ArrayList<EntityController> getNPC(){
		return NPC;
	}
	
	public void printData() {
		for(EntityController n :enemy) 
			System.out.println(n.toString());
			
		for(EntityController a : NPC) 
			System.out.println(a.toString());	
		
	}

	public void addEnemy(String type, int xPos, int yPos) {
		enemy.add(new RobotController(enemy.size(), type, xPos, yPos, play));
	}
	
	public void removeEnemy(int index) {
		for(int i = index; i < enemy.size(); i++)
			enemy.get(i).decreaseIndexInList();
		try {
			enemy.remove(index);
		}
		catch(IndexOutOfBoundsException iobe) {
			iobe.printStackTrace();
		}
	}

	public void addNPC (String type, int xPos, int yPos) {
		
		if(type.compareTo("gatto") == 0) 	//se la stringa dentro al file è uguale a "-gatto"
			NPC.add(new CatController(NPC.size(), type, xPos, yPos, play));	
		
		else if(type.compareTo("vecchio") == 0) 
			NPC.add(new ErmenegildoController(NPC.size(), type, xPos, yPos, play));	
		
		else if(type.compareTo("prof") == 0)
			NPC.add(new DocController(NPC.size(), type, xPos, yPos, play));
		
		else if(type.compareTo("nerd") == 0)
			NPC.add(new NerdController(NPC.size(), type, xPos, yPos, play));
	    
		else if(type.compareTo("pupa") == 0) 
			NPC.add(new PupaController(NPC.size(), type, xPos, yPos, play));
		
		int colNumber = (int)(NPC.get(NPC.size() - 1).getHitbox().x)/play.getController().getTileSize();
		int rowNumber = (int)(NPC.get(NPC.size() - 1).getHitbox().y)/play.getController().getTileSize();
		entityPositionsForPathFinding[rowNumber][colNumber] = 1;
	}
	
	public int[][] getEntityPositionsForPathFinding(){
		return entityPositionsForPathFinding;
	}
	
	public void resetMatriceEntita() {
		//inizializza matrice della posizione dei personaggi
		for(int i = 0; i < rowRoom; i++) {
			for (int j = 0; j < colRoom; j++) {
				entityPositionsForPathFinding[i][j] = 0;
			}
		}
		
	}
	
	public void printMatriceEntita() {
		int colonnaTrue = -1;
		int rigaTrue = -1;
		for(int row = 0; row < rowRoom; row++) {
			for(int col = 0; col < colRoom; col++) {
				System.out.print(entityPositionsForPathFinding[row][col] + " ");
				if(entityPositionsForPathFinding[row][col] > 0) {
					colonnaTrue = col;
					rigaTrue = row;
					System.out.print("QUI");
				}
			}
			System.out.println();

		}
		System.out.println(colonnaTrue + ", " + rigaTrue);
	}
	
}
