package controller.playState.entityController.npcController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import controller.playState.entityController.EntityController;

public abstract class NPCcontroller extends EntityController{

	public NPCcontroller(int ind, String type, Hitbox r, PlayStateController p) {
		super(ind, type, r, p);
		
	}

	protected void tunrToInteract() {
		
		currentAction = IDLE;
		
		if(play.getPlayer().getCurrentDirection() == DOWN) {
			currentDirection = UP;
		}
		
		else if(play.getPlayer().getCurrentDirection() == UP) {
			currentDirection = DOWN;
		}
		
		else if(play.getPlayer().getCurrentDirection() == RIGHT) {
			currentDirection = LEFT;
		}
		
		else if(play.getPlayer().getCurrentDirection() == LEFT) {
			currentDirection = RIGHT;
		}	
	}
	
	public String toString() {
		int indexOfDialogue = play.getController().getView().getPlay().getRoom(play.getCurrentroomIndex()).getNPC(index).getDialogueIndex();
		String dataPlayer = "NPCdata, " + index + ", " + hitbox.x + ", " + hitbox.y + ", " + indexOfDialogue;
		return dataPlayer;
	}
	
}
