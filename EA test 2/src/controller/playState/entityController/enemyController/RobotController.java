package controller.playState.entityController.enemyController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;

public class RobotController extends EnemyController {
	
	private static int hitboxWidth = (int)(32), hitboxHeight = (int)(32);	
	
	public RobotController(int ind, String type, int xPos, int yPos, PlayStateController p) {
		super(ind, type, new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight), p);
				
		speed = (int)(play.getController().getGameScale()*1f);	
		attack = 10;
		defense = 2;
		life = maxLife;
				
		currentState = NORMAL_STATE;
	}
	
	public void hitted(int damage, boolean isNearAttack) {
		currentState = HITTED;		
		if(isNearAttack) {
			damage /= 2;
		}
		int realDamage = damage - defense;
		if(realDamage > 0)
			life -= realDamage;
	}
	
	@Override
	public void update() {

		//quando il robot sta morendo, diventa puù cattivo e lento
		if(life <= 20) {
			attack += 10;
			speed = play.getController().getGameScale()*0.8f;
			
			if(life <= 0)
				currentState = KO_STATE;
		}
		
		float xDistance = Math.abs(hitbox.x - play.getPlayer().getHitbox().x);
		float yDistance = Math.abs(hitbox.y - play.getPlayer().getHitbox().y);
		
		if(currentState != KO_STATE && currentState != HITTED) 
			checkIsNearThePlayer(xDistance, yDistance);
				
		switch(currentState) {
		case NORMAL_STATE:			
			if(xDistance < play.getController().getTileSize()*6 && yDistance < play.getController().getTileSize()*6) {
				if(currentState == NORMAL_STATE) {
					play.getController().getView().getPlay().getUI().activeExclamation(index);
					goToPlayerPosition();	
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
			//se il robot è arrivato dove stava il giocatere, si ferma
			if(currentPathIndex == path.size() - 1) {
				currentAction = IDLE;
				currentState = NORMAL_STATE;
				path.clear();
				currentPathIndex = 0;
			}
			
			else {
				goTrhoughtSelectedPath();
				shootToPlayer();	
			}
			break;
			
		case HITTED:
			hittedCounter++;
			if(hittedCounter >= 100) {
				hittedCounter = 0;
				currentState = stateBeforeHitted;
			}
			break;
			
		case KO_STATE:
			currentAction = DIE;
			dyingCounter++;
			if(dyingCounter >= 400) {
				play.getController().getView().getPlay().removeEnemy(index);
				play.removeEnemy(index);
			}
			break;
		}
		
	}

	private void checkIsNearThePlayer(float xDistance, float yDistance) {
		//se il player e il robot sono vicini, il robot si gira e gli spara
		if(xDistance <= play.getTileSize()*1.5 && yDistance <= play.getTileSize()*1.5) {
						
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
			
			path.clear();
			currentPathIndex = 0;
			currentAction = IDLE;
			shootToPlayer();
			currentState = NORMAL_STATE;
		}
	}

	private void shootToPlayer() {
		bulletCounter++;
		if(bulletCounter > 50) {
			
			int playerCol = (int)(play.getPlayer().getHitbox().x)/play.getController().getTileSize();
			int playerRow = (int)(play.getPlayer().getHitbox().y)/play.getController().getTileSize();
			
			int enemyCol = (int)(hitbox.x)/play.getController().getTileSize();
			int enemyRow = (int)(hitbox.y)/play.getController().getTileSize();

			if(playerCol == enemyCol) {
				//spara up, down
				play.addBullets(this);
				play.getController().getView().getPlay().addBullet();
			}
			
			else if(playerRow == enemyRow) {
				//spara di lato
				play.addBullets(this);
				play.getController().getView().getPlay().addBullet();
			}
			
			bulletCounter = 0;
		}
		
	}

	public String toString() {
		return "xpos " + hitbox.x + " ypos " + hitbox.y;
	}


}

