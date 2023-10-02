package controller.playState.entityController.npcController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.entityController.EntityController;

public class NerdController extends EntityController{
	
	private static int hitboxWidth = (int)(16*1.5), hitboxHeight = (int)(23*1.5);
	
	public NerdController(int i, String type, int xPos, int yPos, PlayStateController p) {
		super(i, type, new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight), p);
		speed = (int)(play.getController().getGameScale()*0.7f);
		
		typeOfTarget = EntityController.NPC;

	}
	
	@Override
	public void update(float playerX, float playerY) {
		float xDistance = Math.abs(hitbox.x - playerX);
		float yDistance = Math.abs(hitbox.y - playerY);
		
		if(xDistance < play.getController().getTileSize()*1.5 && yDistance < play.getController().getTileSize()*1.5) {			
			play.getController().getView().getPlay().getUI().setMessage("premi E per parlare");
			play.getController().getView().getPlay().getUI().setShowMessage(true);
			
			if(play.getPlayer().isInteracting()) {
				tunrToInteract();
				play.getPlayer().speak(index);
			}
		}
		
		
	}
	
	

}
