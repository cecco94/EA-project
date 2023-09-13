package controller.playState.entityController.enemyController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;

public class RobotController extends EnemyController {
	
	private static int hitboxWidth = (int)(18*1.5), hitboxHeight = (int)(23*1.5);
	
	public RobotController(int ind, String type, int xPos, int yPos, PlayStateController p) {
		super(ind, type, new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight), p);
		speed = (int)(play.getController().getGameScale()*0.7f);	
		
		attack = 30;
		defense = 10;
		life = 100;
	}

	public String toString() {
		return "xpos " + hitbox.x + " ypos " + hitbox.y;
	}
}

