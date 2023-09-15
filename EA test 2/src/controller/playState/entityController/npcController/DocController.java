package controller.playState.entityController.npcController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.entityController.EntityController;

public class DocController extends EntityController {
	
	private static int hitboxWidth = (int)(18*1.5), hitboxHeight = (int)(13*1.5);

	public DocController(int ind, String type, int xPos, int yPos, PlayStateController p) {
		super(ind, type, new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight), p);
		speed = (int)(play.getController().getGameScale()*0.7f);	

	}

	@Override
	public void update() {
		float xDistance = Math.abs(hitbox.x - play.getPlayer().getHitbox().x);
		float yDistance = Math.abs(hitbox.y - play.getPlayer().getHitbox().y);
		
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
