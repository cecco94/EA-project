package controller.playState;

import java.util.ArrayList;

import controller.playState.entityController.CatController;
import controller.playState.entityController.Dockcontroller;
import controller.playState.entityController.EnemyController;
import controller.playState.entityController.EntityController;
import controller.playState.entityController.ErmenegildoController;
//import controller.playState.entityController.NPCcontroller;

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
			NPC.get(i).toString();
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

	public void addEnemy(String type, Hitbox r) {
		enemy.add(new EnemyController(enemy.size(), type, r, play));
	}

	public void addNPC (String type, int xPos, int yPos) {
		
		if(type.compareTo("gatto") == 0)		//se la stringa dentro al file è uguale a "-gatto"
			NPC.add(new CatController(NPC.size(), type, xPos, yPos, play));	
		
		else if(type.compareTo("vecchio") == 0) 
			NPC.add(new ErmenegildoController(NPC.size(), type, xPos, yPos, play));	
		
		else if(type.compareTo("prof") == 0)
			NPC.add(new Dockcontroller(NPC.size(), type, xPos, yPos, play));
		
	}
	
	
	
	
	
	
	
	
}
