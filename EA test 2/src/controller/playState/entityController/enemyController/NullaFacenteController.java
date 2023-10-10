package controller.playState.entityController.enemyController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;

public class NullaFacenteController extends EnemyController {
	
	private static int hitboxWidth = (int)(31), hitboxHeight = (int)(31);	

	
	public NullaFacenteController(int index, String type, int xPos, int yPos, PlayStateController p) {
		super(index, type, new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight), p);
				
		speed = (int)(play.getController().getGameScale()*1f);	
		attack = 10;
		defense = 2;
		life = maxLife;		
	}
	
	@Override
	public void update(float playerX, float playerY) {

		checkIfIsDying();
		
		switch(currentState) {
		case NORMAL_STATE:
			float xDistance = Math.abs(hitbox.x - playerX);
			float yDistance = Math.abs(hitbox.y - playerY);
			
			if(xDistance < play.getController().getTileSize()*6 && yDistance < play.getController().getTileSize()*6) {
				play.getController().getView().getPlay().getUI().activeExclamation(index);
				searchPathToPlayer(playerX, playerY);
			}
			else 
				randomMove();
			break;
			
		case IN_WAY:
			currentAction = MOVE;
			
			//se è arrivato dove stava il giocatere, si ferma
			if(currentPathIndex == path.size()) {
				currentAction = IDLE;
				currentState = GOAL_REACHED;
				path.clear();
				currentPathIndex = 0;
			}
			else 
				goTrhoughtSelectedPath();
			
			break;
			
		case GOAL_REACHED:
			xDistance = Math.abs(hitbox.x - playerX);
			yDistance = Math.abs(hitbox.y - playerY);
			
			if(xDistance < play.getController().getTileSize()*3 && yDistance < play.getController().getTileSize()*3) {
				System.out.println("sono vicino, attacco");
				turnToPlayer(xDistance, yDistance);
			}
			
			else if(xDistance < play.getController().getTileSize()*6 && yDistance < play.getController().getTileSize()*6) {
				play.getController().getView().getPlay().getUI().activeExclamation(index);
				searchPathToPlayer(playerX, playerY);
			}
			else 
				currentState = NORMAL_STATE;	
			
			break;
			
		case HITTED:
			hittedCounter++;
			if(hittedCounter >= timeOfBlockBeforeHitted) {
				hittedCounter = 0;
				currentState = stateBeforeHitted;
			}
			break;
			
			case KO_STATE:
			currentAction = DIE;
			dyingCounter++;
			if(dyingCounter >= 400) 
				die();
				
			break;			
		}
		
	}

	private void checkIfIsDying() {
		if(life <= 20) {
			attack += 10;
			speed = play.getController().getGameScale()*0.8f;
			
			if(life <= 0)
				currentState = KO_STATE;
		}	
	}

	private void turnToPlayer(float xDistance, float yDistance) {

		if(play.getPlayer().getHitbox().y >= hitbox.y && play.getPlayer().getHitbox().y < hitbox.y + hitbox.height) {
		
			if(play.getPlayer().getHitbox().x <= hitbox.x) 
				currentDirection = LEFT;
			
			if(play.getPlayer().getHitbox().x >= hitbox.x) 
				currentDirection = RIGHT;
		}
		
		else if(play.getPlayer().getHitbox().x >= hitbox.x && play.getPlayer().getHitbox().x < hitbox.x + hitbox.width){
			
			if(play.getPlayer().getHitbox().y >= hitbox.y) 
				currentDirection = DOWN;
			
			if(play.getPlayer().getHitbox().y <= hitbox.y) 
				currentDirection = UP;	
		}
		
	}


	public String toString() {
		return "xpos " + hitbox.x + " ypos " + hitbox.y;
	}


}


