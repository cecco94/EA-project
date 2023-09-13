package controller.playState.entityController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;

public class RobotController extends EntityController {
	
	private static int hitboxWidth = (int)(18*1.5), hitboxHeight = (int)(23*1.5);
	
	
	public RobotController(int ind, String type, int xPos, int yPos, PlayStateController p) {
		super(ind, type, new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight), p);
		speed = (int)(play.getController().getGameScale()*0.7f);	
		idle = true;
		down = true;
	}

	@Override
	public void update() {
		randomMove();
	}
	

}

