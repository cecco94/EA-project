package controller.playState.entityController.npcController;

import java.util.ArrayList;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.entityController.EntityController;

public class ErmenegildoController extends EntityController {
	
	private static int hitboxWidth = (int)(16*1.5), hitboxHeight = (int)(17*1.5);
	
	public ErmenegildoController(int i, String type, int xPos, int yPos, PlayStateController p) {
		super(i, type, new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight), p);
		speed = play.getController().getGameScale()*0.3f;	
		path = new ArrayList<>();
		
		this.typeOfTarget = "npc";

	}

	@Override
	public void update() {
		switch(currentState){
		case NORMAL_STATE:
			float xDistance = Math.abs(hitbox.x - play.getPlayer().getHitbox().x);
			float yDistance = Math.abs(hitbox.y - play.getPlayer().getHitbox().y);
			
			if(xDistance < play.getController().getTileSize()*1.5 && yDistance < play.getController().getTileSize()*1.5) {			
				play.getController().getView().getPlay().getUI().setMessage("premi E per parlare");
				play.getController().getView().getPlay().getUI().setShowMessage(true);
				
				if(play.getPlayer().isInteracting()) {
					goToYourDestination(27, 9, false);
					play.getPlayer().setInteracting(false);
//					tunrToInteract();
//					play.getPlayer().speak(index);
				}
			}
			else 
				randomMove();
			break;
			
		case GO_TO_FIRST_TILE:
			currentAction = MOVE;
			goToEdgeOfTile();
			break;
				
		case IN_WAY:			
			if(currentPathIndex == path.size()) {
				currentAction = IDLE;
				currentState = NORMAL_STATE;
				currentPathIndex = 0;
				path.clear();
			}
			else
				goTrhoughtSelectedPath();
			break;
		}
	}
	
	
}

