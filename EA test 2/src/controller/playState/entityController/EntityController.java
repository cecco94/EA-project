package controller.playState.entityController;

import java.awt.Rectangle;

import controller.playState.PlayStateController;

public abstract class EntityController {

	protected int xPos, yPos;
	protected Rectangle hitbox;
	protected int speed;
	protected PlayStateController play;
	
	public EntityController (Rectangle r, PlayStateController p) {
		play = p;
		
		hitbox = r;
		xPos = r.x;
		yPos = r.y;
	}
	
	public abstract void update(PlayerController player);

	public String toString() {
		return xPos + ", " + yPos;
	}
}
