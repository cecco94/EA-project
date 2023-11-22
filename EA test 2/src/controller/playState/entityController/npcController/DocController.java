package controller.playState.entityController.npcController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.entityController.EntityController;

public class DocController extends NPCcontroller {
	
	private static int hitboxWidth = (int)(18*1.5), hitboxHeight = (int)(13*1.5);

	public DocController(int ind, String type, int xPos, int yPos, PlayStateController p) {
		super(ind, type, new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight), p);
		speed = (int)(play.getController().getGameScale()*0.7f);	

		typeOfTarget = EntityController.NPC;

	}

	@Override
	public void update(float playerX, float playerY) {
		float xDistance = Math.abs(hitbox.x - playerX);
		float yDistance = Math.abs(hitbox.y - playerY);
		
		if(xDistance < play.getController().getTileSize()*1.5 && yDistance < play.getController().getTileSize()*1.5) {			
			play.getController().getView().showMessageInUI("premi E per parlare");
			
			if(play.getPlayer().isInteracting()) {
				tunrToInteract();
				play.getPlayer().speak(index);
			}
		}
		else
			randomMove();
		
	}
	

}
