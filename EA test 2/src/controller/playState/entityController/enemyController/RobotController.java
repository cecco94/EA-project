package controller.playState.entityController.enemyController;

import controller.playState.Hitbox;
import controller.playState.PlayStateController;

public class RobotController extends EnemyController {
	
	private static int hitboxWidth = (int)(18*1.5), hitboxHeight = (int)(18*1.5);
	
	public RobotController(int ind, String type, int xPos, int yPos, PlayStateController p) {
		super(ind, type, new Hitbox(xPos, yPos, hitboxWidth, hitboxHeight), p);
				
		speed = (int)(play.getController().getGameScale()*1f);	
		
		attack = 30;
		defense = 10;
		life = 100;
	}

	public String toString() {
		return "xpos " + hitbox.x + " ypos " + hitbox.y;
	}
	
	@Override
	public void update() {

		switch(currentState) {
		case NORMAL_STATE:
			float xDistance = Math.abs(hitbox.x - play.getPlayer().getHitbox().x);
			float yDistance = Math.abs(hitbox.y - play.getPlayer().getHitbox().y);
			
			if(xDistance < play.getController().getTileSize()*4 && yDistance < play.getController().getTileSize()*4) {
				if(currentState == NORMAL_STATE) {
					play.getController().getView().getPlay().getUI().activeExclamation(index);
					goToYourDestination();	
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
			
			if(currentPathIndex == path.size() - 1) {
				currentAction = IDLE;
				currentState = NORMAL_STATE;
				currentPathIndex = 0;
				System.out.println("giunto a destinazione");
			}
			else {
				goTrhoughtSelectedPath();
				shootToPlayer();	
			}
			break;
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
			
			else if( playerRow == enemyRow) {
				//spara di lato
				play.addBullets(this);
				play.getController().getView().getPlay().addBullet();
			}
			
			bulletCounter = 0;
		}
		
	}
	
}

