package controller.playState.entityController.enemyController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;

public class BossController  extends EnemyController {
	
	private static int hitboxWidth = (int)(40), hitboxHeight = (int)(43);	
	private int goalReachedCounter, attackCounter;
	private Hitbox attackHitbox;
	private int attack = 10, defense = 2; 
	
	public BossController(int index, String type, int xPos, int yPos, PlayStateController p) {
		super(index, type, new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight), p);
		
		attackHitbox = new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight);
		speed = (int)(play.getController().getGameScale()*1f);	
		attack = 10;
		defense = 2;
		life = maxLife;	
	}
	
	@Override
	public void update(float playerX, float playerY) {
				
		checkIfIsDying();
		float xDistance = Math.abs(hitbox.x - playerX);
		float yDistance = Math.abs(hitbox.y - playerY);
				
		switch(currentState) {
		case NORMAL_STATE:
			if(xDistance < play.getController().getTileSize()*5 && yDistance < play.getController().getTileSize()*5) {
				play.getController().getView().drawExclamationAboveEnemy(index);
				
				if(xDistance <= play.getController().getTileSize() && yDistance <= play.getController().getTileSize())
					currentState = PLAYER_IN_RANGE;
		
				else
					searchPathToPlayer(playerX, playerY);
			}
			else 
				randomMove();
			break;
			
		case IN_WAY:	
			if(xDistance <= play.getController().getTileSize()*1.2 && yDistance <= play.getController().getTileSize()*1.2) {
				currentState = PLAYER_IN_RANGE;
				path.clear();
				currentPathIndex = 0;
			}	
			
			else if(currentPathIndex == path.size()) {
				currentAction = IDLE;
				currentState = NORMAL_STATE;
				path.clear();
				currentPathIndex = 0;
			}
			
			else {
				currentAction = MOVE;
				goTrhoughtSelectedPath();
			}
			break;
			
		case PLAYER_IN_RANGE:
			currentAction = ATTACK;
			turnToPlayer(playerX, playerY);
			damagePlayer();

			//se non è più nel range di attacco
			if(xDistance > play.getController().getTileSize()*1.2 || yDistance > play.getController().getTileSize()*1.2) {
				//controlla, con questo metodo, se può raggiungere il player
				searchPathToPlayer(playerX, playerY);
				//se non ha trovato un percorso, lo  stato non va su IN_WAY
				if(currentState != IN_WAY) 
					currentState = GOAL_REACHED;	
			}
			break;
			
		//stato che serve per evitare che il nemico faccia troppo spesso il path finding, soprattutto se 
		//il player si posiziona su una casella non raggiungibile
		case GOAL_REACHED:
			goalReachedCounter++;
			if(goalReachedCounter >= 100) {
				goalReachedCounter = 0;
				currentState = NORMAL_STATE;
			}	
			break;			
			
		case HITTED:
			hittedCounter++;
			currentAction = IDLE;
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

	private void damagePlayer() {
		
		attackCounter++;
		if(attackCounter >= 100) {
			attackCounter = 0;
			
			if(currentDirection == UP) {
				attackHitbox.y = hitbox.y - attackHitbox.height;
				attackHitbox.x = hitbox.x;
			}
			else if(currentDirection == DOWN) {
				attackHitbox.x = hitbox.x;
				attackHitbox.y = hitbox.y + hitbox.height;
			}
			else if(currentDirection == LEFT) {
				attackHitbox.y = hitbox.y;
				attackHitbox.x = hitbox.x - attackHitbox.width;
			}
			else if(currentDirection == RIGHT) {
				attackHitbox.y = hitbox.y;
				attackHitbox.x = hitbox.x + hitbox.width;
			}
			
			if(attackHitbox.intersects(play.getPlayer().getHitbox()))
				play.getPlayer().hitted(attack, currentDirection);
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

