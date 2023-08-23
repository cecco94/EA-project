package controller.playState.entityController;

import java.awt.Rectangle;

import controller.playState.PlayStateController;
import view.main.GamePanel;

public abstract class EntityController {

	protected Rectangle hitbox;
	protected int speed;
	protected PlayStateController play;
	protected boolean up, down, left, right, moving, idle;
	protected int type; //per capire, se è un NPC quale sia
	
	public EntityController (Rectangle r, PlayStateController p) {
		play = p;
		hitbox = r;
		
		//settiamo la posizione nella mappa e scaliamo la dimensione
		hitbox.x *= GamePanel.TILES_SIZE; 
		hitbox.y *= GamePanel.TILES_SIZE; 
		hitbox.width *= GamePanel.SCALE;
		hitbox.height *= GamePanel.SCALE;
	}
	
	public abstract void update();

	public String toString() {
		return "( " + hitbox.x + ", " + hitbox.y + ", " + hitbox.width + ", " +  hitbox.height + " )";
	}
	
	public int getType() {
		return type;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
}
