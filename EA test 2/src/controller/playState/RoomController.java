package controller.playState;

import java.util.ArrayList;

import controller.playState.entityController.EntityController;
import controller.playState.entityController.enemyController.EnemyController;
import controller.playState.entityController.enemyController.NullaFacenteController;
import controller.playState.entityController.enemyController.RobotController;
import controller.playState.entityController.npcController.CatController;
import controller.playState.entityController.npcController.DocController;
import controller.playState.entityController.npcController.ErmenegildoController;
import controller.playState.entityController.npcController.NerdController;
import controller.playState.entityController.npcController.PupaController;
import controller.playState.pathfinding.PathFinder;

public class RoomController {

	private ArrayList<EnemyController> enemy;
	private ArrayList<EntityController> NPC;
	private PlayStateController play;
	//quante righe e quante colonne ha la stanza
	private int rowRoom, colRoom;
	//ogni stanza ha un suo pathfinder con le dimensioni della stanza
	private PathFinder pathFinder;
	
	public RoomController(PlayStateController p, int righe, int colonne) {
		play = p;
		enemy = new ArrayList<>();
		NPC = new ArrayList<>();
		
		rowRoom = righe;
		colRoom = colonne;
		pathFinder = new PathFinder(play, rowRoom, colRoom);

	}

	public void update(float playerX, float playerY) {
				
		for(int i = 0; i < NPC.size(); i++) 
			NPC.get(i).update(playerX, playerY);
		
		for(int i = 0; i < enemy.size(); i++) 
			enemy.get(i).update(playerX, playerY);
		
	}
	
	public ArrayList<EnemyController> getEnemy(){
		return enemy;
	}
	
	public ArrayList<EntityController> getNPC(){
		return NPC;
	}
	
	public void addEnemy(String type, int xPos, int yPos) {
		if(type.compareTo("robot") == 0) 	
			enemy.add(new RobotController(enemy.size(), type, xPos, yPos, play));
		
		else if(type.compareTo("nullafacente") == 0) 
			enemy.add(new NullaFacenteController(enemy.size(), type, xPos, yPos, play));
		
	}
	
	//rimuove il nemico e libera la sua posizione per migliorare il pathfinding
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

	public void addNPC(String type, int xPos, int yPos) {
		
		if(type.compareTo("gatto") == 0)
			NPC.add(new CatController(NPC.size(), type, xPos, yPos, play));	
		
		else if(type.compareTo("vecchio") == 0) 
			NPC.add(new ErmenegildoController(NPC.size(), type, xPos, yPos, play));	
		
		else if(type.compareTo("prof") == 0)
			NPC.add(new DocController(NPC.size(), type, xPos, yPos, play));
		
		else if(type.compareTo("nerd") == 0)
			NPC.add(new NerdController(NPC.size(), type, xPos, yPos, play));
	    
		else if(type.compareTo("pupa") == 0) 
			NPC.add(new PupaController(NPC.size(), type, xPos, yPos, play));
	}
	
	public PathFinder getPathFinder() {
		return pathFinder;
	}
	
	public void printData() {
		for(EntityController n :enemy) 
			System.out.println(n.toString());
			
		for(EntityController a : NPC) 
			System.out.println(a.toString());	
		
	}
}
