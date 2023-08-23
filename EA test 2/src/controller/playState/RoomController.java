package controller.playState;

import java.awt.Rectangle;
import java.util.ArrayList;

import controller.playState.entityController.CatController;
import controller.playState.entityController.EnemyController;
import controller.playState.entityController.EntityController;
import controller.playState.entityController.NPCcontroller;

public class RoomController {

	private ArrayList<EntityController> nemici;
	private ArrayList<EntityController> NPC;
	private PlayStateController play;
	
	public RoomController(PlayStateController p) {
		play = p;
		nemici = new ArrayList<>();
		NPC = new ArrayList<>();
	}
	
	public void update() {
		
		for(int i = 0; i < NPC.size(); i++) {
			NPC.get(i).update();
			NPC.get(i).toString();
		}
	}
	
	public void removeEnemy(int index) {
		nemici.remove(index);
	}
	
	public void removeNPC(int index) {
		NPC.remove(index);
	}
	
	public ArrayList<EntityController> getEnemy(){
		return nemici;
	}
	
	public ArrayList<EntityController> getNPC(){
		return NPC;
	}
	
	public void printData() {
		for(EntityController n :nemici) 
			System.out.println(n.toString());
			
		for(EntityController a : NPC) 
			System.out.println(a.toString());	
		
	}

	public void addEnemy(int type, Rectangle r) {
		nemici.add(new EnemyController(r, play));
	}

	public void addNPC(int type, Rectangle r) {
		NPC.add(new CatController(r, play));	
	}
}