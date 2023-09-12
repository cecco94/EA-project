package controller.playState.entityController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;

public class DocController extends EntityController {
	
	private static int hitboxWidth = (int)(16*1.5), hitboxHeight = (int)(24*1.5);

	public DocController(int ind, String type, int xPos, int yPos, PlayStateController p) {
		super(ind, type, new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight), p);
		speed = (int)(play.getController().getGameScale()*0.7f);	
		idle = true;
		down = true;
	}

	@Override
	public void update() {
		int xDistance = Math.abs(hitbox.x - play.getPlayer().getHitbox().x);
		int yDistance = Math.abs(hitbox.y - play.getPlayer().getHitbox().y);
		
		if(xDistance < play.getController().getTileSize()*1.5 && yDistance < play.getController().getTileSize()*1.5) {			
			play.getController().getView().getPlay().getUI().setMessage("premi E per parlare");
			play.getController().getView().getPlay().getUI().setShowMessage(true);
			
			if(play.getPlayer().isInteracting()) {
				tunrToInteract();
				play.getPlayer().speak(index);
			}
		}
		
		else
			randomMove();
		
	}
	

}
