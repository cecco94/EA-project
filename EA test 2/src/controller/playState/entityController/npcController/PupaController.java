package controller.playState.entityController.npcController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.entityController.EntityController;

public class PupaController extends EntityController {
	
	private static int hitboxWidth = (int)(16*1.5), hitboxHeight = (int)(23*1.5);
	
	private final int GO_RIGHT = 0, GO_LEFT = 1;
	private int actualState = GO_LEFT;
	
	public PupaController(int i, String type, int xPos, int yPos, PlayStateController p) {
		super(i, type, new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight), p);
		speed = play.getController().getGameScale()*0.5f;	
		currentDirection = LEFT;
		currentAction = MOVE;
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
		
		else {
			switch(actualState) {
			case GO_RIGHT:     //va a destra,se incontra ostacoli e arrivata a una certa posizione torna indietro
				currentDirection = RIGHT;
				tempHitboxForCheck.x = hitbox.x + speed;
				if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck)) 
					hitbox.x += speed;
				
				else 
					currentAction = IDLE;	
				
				if(hitbox.x/play.getController().getTileSize() >= 37) {
					actualState = GO_LEFT;	
					currentAction = MOVE;
				}
				break;
				
			case GO_LEFT:
				currentDirection = LEFT;
				tempHitboxForCheck.x = hitbox.x - speed;
				if(!play.getCollisionChecker().isCollisionInEntityList(tempHitboxForCheck)) 
					hitbox.x -= speed;
				
				else 
					currentAction = IDLE;	
				
				if(hitbox.x/play.getController().getTileSize() <= 31) { 
					actualState = GO_RIGHT;	
					currentAction = MOVE;
				}
				break;
			}
		}
		
		
	}
	
}
		
		
	


