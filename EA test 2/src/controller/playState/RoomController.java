package controller.playState;

import java.util.ArrayList;

import controller.playState.entityController.EntityController;
import controller.playState.entityController.enemyController.RobotController;
import controller.playState.entityController.npcController.CatController;
import controller.playState.entityController.npcController.DocController;
import controller.playState.entityController.npcController.ErmenegildoController;
import controller.playState.entityController.npcController.NerdController;

public class RoomController {

	private ArrayList<EntityController> enemy;
	private ArrayList<EntityController> NPC;
	private PlayStateController play;
	
	public RoomController(PlayStateController p) {
		play = p;
		enemy = new ArrayList<>();
		NPC = new ArrayList<>();
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
	
	public ArrayList<EntityController> getEnemy(){
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

	public void addNPC (String type, int xPos, int yPos) {
		
		if(type.compareTo("gatto") == 0) {	//se la stringa dentro al file è uguale a "-gatto"
			NPC.add(new CatController(NPC.size(), type, xPos, yPos, play));	
		}
		
	    if(type.compareTo("vecchio") == 0) 
			NPC.add(new ErmenegildoController(NPC.size(), type, xPos, yPos, play));	
		
		else if(type.compareTo("prof") == 0)
			NPC.add(new DocController(NPC.size(), type, xPos, yPos, play));
		
		else if(type.compareTo("nerd") == 0)
			NPC.add(new NerdController(NPC.size(), type, xPos, yPos, play));
	}
	
	
	
	
	
	
	
	
}
