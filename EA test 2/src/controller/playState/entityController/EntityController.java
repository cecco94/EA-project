package controller.playState.entityController;

import java.awt.Rectangle;

import controller.playState.Collisions;
import controller.playState.PlayerController;

public abstract class EntityController {

	protected int type;
	protected int xPos, yPos;
	protected Rectangle hitbox;
	protected int speed;
	protected Collisions collisionChecker;
	
	public EntityController (int t, int x, int y, int w, int h) {
		type = t;
		xPos = x;
		yPos = y;
		hitbox = new Rectangle(x, y, w, h);
	}
	
	public abstract void update(PlayerController player);

	public String toString() {
		return type + ", " + xPos + ", " + yPos;
	}
}
