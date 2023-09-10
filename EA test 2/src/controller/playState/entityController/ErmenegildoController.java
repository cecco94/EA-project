package controller.playState.entityController;

import java.util.Random;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import view.main.GamePanel;

public class ErmenegildoController extends EntityController {
	
	private static int hitboxWidth = (int)(16*1.5), hitboxHeight = (int)(23*1.5);
	private int oldManSpeed = (int)(GamePanel.SCALE*1f);
	//la usiamo per capire se il player è vicino all'npc
	private Hitbox areaOfInteraction;
	
//	private int actionCounter;
//	private Random randomGenerator = new Random();
//	private int randomAction, randomDirection;
	
	
	public ErmenegildoController(int i, String type, int xPos, int yPos, PlayStateController p) {
		super(i, type, new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight), p);
		speed = oldManSpeed;	
		idle = true;
		down = true;
	}

	@Override
	public void update() {
		int xDistance = Math.abs(hitbox.x - play.getPlayer().getHitbox().x);
		int yDistance = Math.abs(hitbox.y - play.getPlayer().getHitbox().y);
		
		if(xDistance < GamePanel.TILES_SIZE*1.5 && yDistance < GamePanel.TILES_SIZE*1.5) {
			play.getController().getView().getPlay().getUI().setMessage("premi E per parlare");
			play.getController().getView().getPlay().getUI().setShowMessage(true);
			
			if(play.getPlayer().isInteracting()) {
				tunrToInteract();
				play.getPlayer().speak(index);
			}
		}
		
	}

	private void tunrToInteract() {
		
		resetDirection();
		
		if(play.getPlayer().getDirection() == DOWN) {
			direction = UP;
			up = true;
		}
		
		else if(play.getPlayer().getDirection() == UP) {
			direction = DOWN;
			down = true;
		}
		
		else if(play.getPlayer().getDirection() == RIGHT) {
			direction = RIGHT;
			right = true;
		}
		
		else if(play.getPlayer().getDirection() == LEFT) {
			direction = LEFT;
			left = true;
		}	
	}
		

}
