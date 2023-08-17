package controller.playState.entityController;

import controller.playState.PlayerController;

public abstract class EntityController {

	protected int type;
	protected int xPos, yPos;
	
	public EntityController (int t, int x, int y) {
		type = t;
		xPos = x;
		yPos = y;
	}
	
	public abstract void update(PlayerController player);

	
}
