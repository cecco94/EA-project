package controller.playState.entityController.enemyController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;

public class RobotController extends EnemyController {
	
	private static int hitboxWidth = (int)(31), hitboxHeight = (int)(31);	
	private static int numberOfRobots;
	
	public RobotController(int ind, String type, int xPos, int yPos, PlayStateController p) {
		super(ind, type, new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight), p);
				
		speed = (int)(play.getController().getGameScale()*1f);	
		attack = 10;
		defense = 2;
		life = maxLife;		
		numberOfRobots++;
	}
	
	@Override
	public void update(float playerX, float playerY) {

		//quando il robot sta morendo, diventa puù cattivo e lento
		checkIfIsDying();
		
		switch(currentState) {
		case NORMAL_STATE:
			float xDistance = Math.abs(hitbox.x - playerX);
			float yDistance = Math.abs(hitbox.y - playerY);
			
			if(xDistance < play.getController().getTileSize()*6 && yDistance < play.getController().getTileSize()*6) {
				play.getController().getView().drawExclamationAboveEnemy(index);
				searchPathToPlayer(playerX, playerY);
			}
			else 
				randomMove();
			break;
			
		case IN_WAY:
			currentAction = MOVE;
			shootToPlayer(playerX, playerY);
			
			//se il robot è arrivato dove stava il giocatere, si ferma
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
				turnToPlayer(playerX, playerY);
				shootToPlayer(playerX, playerY);
			}
			
			else if(xDistance < play.getController().getTileSize()*6 && yDistance < play.getController().getTileSize()*6) {
				play.getController().getView().drawExclamationAboveEnemy(index);
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
			if(dyingCounter >= 400) {
				die();
				numberOfRobots--;
			}
			if(numberOfRobots <= 0) {
				play.getController().getView().setMessageToShowInUI("hai eliminato tutti i robot!");
				play.getPlayer().addCFU(60);
			}
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

	private void shootToPlayer(float playerX, float playerY) {
		bulletCounter++;
		if(bulletCounter > 100) {
			
			int playerCol = (int)(playerX)/play.getController().getTileSize();
			int playerRow = (int)(playerY)/play.getController().getTileSize();
			
			int enemyCol = (int)(hitbox.x)/play.getController().getTileSize();
			int enemyRow = (int)(hitbox.y)/play.getController().getTileSize();

			if(playerCol == enemyCol) {
				//spara up, down
				play.addBullets(this);
				play.getController().getView().addBulletView();
			}
			
			else if(playerRow == enemyRow) {
				//spara di lato
				play.addBullets(this);
				play.getController().getView().addBulletView();
			}
			
			bulletCounter = 0;
		}
		
	}

	public String toString() {
		return "xpos " + hitbox.x + " ypos " + hitbox.y;
	}


}

